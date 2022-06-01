package com.project.ecommerce.data.network;

import androidx.annotation.Nullable;

import com.project.ecommerce.data.model.BaseApiResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.HttpException;
import timber.log.Timber;

public abstract class CallbackWrapper<T extends BaseApiResponse> extends DisposableObserver<T> {


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) t).response().errorBody();
            onFailure(getErrorMessage(responseBody));
        } else if (t instanceof SocketTimeoutException) {
            Timber.d("Server Time Out. Please try again later.");
            onFailure("Server Time Out. Please try again later..");
        } else if (t instanceof IOException) {
            Timber.d("IO Network Error.");
            onFailure("IO Network Error. Please try again later..");
        } else {
            Timber.d("Unknown Error:" + t.getMessage());
            onFailure("Unknown Error:" + t.getMessage());
        }
    }

    @Override
    public void onComplete() { }

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(String message);


    // catch the exception
    private String getErrorMessage(@Nullable ResponseBody responseBody) {
        try {
            if(responseBody == null){
                return "Could not parse data";
            }

            BufferedSource source = responseBody.source();
//            JSONObject jsonObject = new JSONObject(responseBody.string());
            JSONObject jsonObject = new JSONObject(source.readString(Charset.defaultCharset()));
            Timber.d("response on response body %s",jsonObject.toString());
            return jsonObject.getString("message");
        } catch (Exception e) {
            Timber.d("getErrorMessage on responsebody "+e.getMessage());
            Timber.d("getErrorMessage on responsebody "+ e.getStackTrace());
            return e.getMessage();
        }
    }
}