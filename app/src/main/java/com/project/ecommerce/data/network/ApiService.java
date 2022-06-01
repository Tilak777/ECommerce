package com.project.ecommerce.data.network;

import com.project.ecommerce.data.model.BaseApiResponse;
import com.project.ecommerce.data.model.DeliveryInfoModel;
import com.project.ecommerce.data.model.OrderedModel;
import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.data.model.UserModel;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import io.reactivex.Observable;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Observable<BaseApiResponse<UserModel>> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Observable<BaseApiResponse<UserModel>> register(
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("product/product-search")
    Observable<BaseApiResponse<List<ProductModel>>> searchProduct(
            @Field("title") String search
    );

    @GET("product/allproduct")
    Observable<BaseApiResponse<List<ProductModel>>> allProducts();

    @GET("product/productbyid/{id}")
    Observable<BaseApiResponse<ProductModel>> getProductDetail(
            @Path("id") int id
    );

    @GET("product/allproduct")
    Observable<BaseApiResponse<List<ProductModel>>> getRelatedProducts(
//            @Path("id") int id
    );


    @POST("order/shipping-submit")
    Observable<BaseApiResponse<String>> saveDeliveryInfo(
            @Body DeliveryInfoModel model
    );

    @GET("order/getorderbyuser")
    Observable<BaseApiResponse<List<OrderedModel>>> getMyOrderHistory();

}
