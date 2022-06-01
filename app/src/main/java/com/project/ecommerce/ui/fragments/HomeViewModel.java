package com.project.ecommerce.ui.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.ecommerce.data.db.DbDao;
import com.project.ecommerce.data.model.BaseApiResponse;
import com.project.ecommerce.data.model.DeliveryInfoModel;
import com.project.ecommerce.data.model.OrderedModel;
import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.data.model.UserModel;
import com.project.ecommerce.data.model.WishlistModel;
import com.project.ecommerce.data.network.ApiService;
import com.project.ecommerce.data.network.CallbackWrapper;
import com.project.ecommerce.ui.listener.DeliveryInfoListener;
import com.project.ecommerce.ui.listener.LoginListener;
import com.project.ecommerce.ui.listener.OrdersLoadListener;
import com.project.ecommerce.ui.listener.ProductListener;
import com.project.ecommerce.ui.listener.ProductLoadListener;
import com.project.ecommerce.ui.listener.RegisterListener;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class HomeViewModel extends ViewModel{

    DbDao dbDao;
    ApiService apiService;
    CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public HomeViewModel(DbDao dbDao,ApiService apiService){
        this.dbDao = dbDao;
        this.apiService = apiService;
    }

    public void addUser(UserModel userModel){
        dbDao.addUser(userModel);
    }
    public UserModel getUser(){
        return dbDao.getUser();
    }
    public LiveData<UserModel> getUserLD(){
        return dbDao.getUserLD();
    }

    public void addProduct(ProductModel model){
        dbDao.addCart(model);
    }
    public void removeAllProduct(){
        dbDao.deleteAllCart();
    }
    public void removeProduct(ProductModel model){
        dbDao.deleteCart(model.getId());
    }


    public List<ProductModel> getUserCart(){
        return dbDao.getCart();
    }
    public LiveData<ProductModel> getUserCart(int id){
        return dbDao.getCartByIdLD(id);
    }
    public LiveData<List<ProductModel>> getUserCartLD(){
        return dbDao.getCartLD();
    }

    public LiveData<List<WishlistModel>> getUserWishlist(){
        return dbDao.getWishlistLD();
    }

    public void login(
            LoginListener listener,
            String email, String pwd
    ){
        disposable.add(
            apiService.login(email,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<BaseApiResponse<UserModel>>(){
                    @Override
                    protected void onSuccess(BaseApiResponse<UserModel> response) {
                        Timber.d(response.toString());
                        if(response.getData() != null){
                            addUser(response.getData());
                            listener.loginSuccess(response.getData(),response.getMessage());
                        }
                        else{
                            listener.loginFailure(response.getErrorMsg());
                        }
                    }
                    @Override
                    protected void onFailure(String message) {
                        listener.loginFailure(message);
                    }
                })
        );
    }
    public void register(
            RegisterListener listener,
            String name,
            String email,
            String pwd,
            String confirmPwd
    ){
        disposable.add(
                apiService.register(email,pwd,confirmPwd,name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new CallbackWrapper<BaseApiResponse<UserModel>>(){
                            @Override
                            protected void onSuccess(BaseApiResponse<UserModel> response) {
                                if(response.getData() != null){
                                    addUser(response.getData());
                                    listener.registerSuccess(response.getData(),response.getMessage());
                                }
                                else{
                                    listener.registerFailure(response.getErrorMsg());
                                }
                            }
                            @Override
                            protected void onFailure(String message) {
                                listener.registerFailure(message);
                            }
                        })
        );
    }

    public void allProducts(
            ProductLoadListener listener
    ){
        disposable.add(
                apiService.allProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new CallbackWrapper<BaseApiResponse<List<ProductModel>>>(){
                            @Override
                            protected void onSuccess(BaseApiResponse<List<ProductModel>> response) {
                                Timber.d(response.toString());
                                if(response.getData() != null){

                                    listener.loadProducts(response.getData());
                                }
                                else{
                                    listener.loadError(response.getErrorMsg());
                                }
                            }
                            @Override
                            protected void onFailure(String message) {
                                listener.loadError(message);
                            }
                        })
        );
    }
    @Nullable Disposable searchDisposable;
    public void searchProducts(
            ProductLoadListener listener,
            String search
    ){
        searchDisposable = apiService.searchProduct(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<BaseApiResponse<List<ProductModel>>>(){
                    @Override
                    protected void onSuccess(BaseApiResponse<List<ProductModel>> response) {
                        Timber.d(response.toString());
                        if(response.getData() != null){

                            listener.loadProducts(response.getData());
                        }
                        else{
                            listener.loadError(response.getErrorMsg());
                        }
                    }
                    @Override
                    protected void onFailure(String message) {
                        listener.loadError(message);
                    }
                });
        disposable.add(
                searchDisposable
        );
    }
    public void getProductDetail(
            ProductLoadListener listener,
            int id
    ){
        disposable.add(
                apiService.getProductDetail(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new CallbackWrapper<BaseApiResponse<ProductModel>>(){
                            @Override
                            protected void onSuccess(BaseApiResponse<ProductModel> response) {
                                Timber.d(response.toString());
                                if(response.getData() != null){
                                    listener.loadProduct(response.getData());
                                }
                                else{
                                    listener.loadError(response.getErrorMsg());
                                }
                            }
                            @Override
                            protected void onFailure(String message) {
                                listener.loadError(message);
                            }
                        })
        );
    }
    public void getRelatedProducts(
            ProductLoadListener listener,
            int id
    ){
        disposable.add(
                apiService.getRelatedProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new CallbackWrapper<BaseApiResponse<List<ProductModel>>>(){
                            @Override
                            protected void onSuccess(BaseApiResponse<List<ProductModel>> response) {
                                Timber.d(response.toString());
                                if(response.getData() != null){
                                    listener.loadProducts(response.getData());
                                }
                                else{
                                    listener.loadError(response.getErrorMsg());
                                }
                            }
                            @Override
                            protected void onFailure(String message) {
                                listener.loadError(message);
                            }
                        })
        );
    }

    public void saveDeliveryInfo(
            DeliveryInfoListener listener,
            DeliveryInfoModel model
    ){
        disposable.add(
                apiService.saveDeliveryInfo(model)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new CallbackWrapper<BaseApiResponse<String>>(){
                            @Override
                            protected void onSuccess(BaseApiResponse<String> response) {
                                Timber.d(response.toString());
                                listener.success(response.getMessage());
                            }
                            @Override
                            protected void onFailure(String message) {
                                listener.failure(message);
                            }
                        })
        );
    }

    public void getMyOrderHistory(
            OrdersLoadListener listener
    ){
        disposable.add(
                apiService.getMyOrderHistory()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new CallbackWrapper<BaseApiResponse<List<OrderedModel>>>(){
                            @Override
                            protected void onSuccess(BaseApiResponse<List<OrderedModel>> response) {
                                Timber.d(response.toString());
                                if(response.getData() != null){
                                    listener.success(response.getData(),response.getMessage());
                                }
                                else{
                                    listener.failure(response.getErrorMsg());
                                }
                            }
                            @Override
                            protected void onFailure(String message) {

                                listener.failure(message);
                            }
                        })
        );
    }


    public void logout(){
        dbDao.deleteUser();
        dbDao.deleteAllCart();
        dbDao.deleteAllWishlist();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }
}
