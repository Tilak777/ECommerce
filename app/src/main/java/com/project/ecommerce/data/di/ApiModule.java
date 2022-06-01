package com.project.ecommerce.data.di;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.ecommerce.BuildConfig;
import com.project.ecommerce.data.network.ApiService;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module()
public class ApiModule {

    @Provides
    @Singleton
    public Cache providesCache(Application application){
        int cacheSize = 10*1024*1024;
        File dir = new File(application.getCacheDir(),"offlineCache");
        return new Cache(dir,cacheSize);
    }

    @Provides
    @Singleton
    public Gson providesGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        builder.setLenient();
        return builder.create();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLogging(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    @Provides
    public OkHttpClient providesOkHttpClient(
            @Named("accessToken") @Nullable String token,
            Cache cache,
            HttpLoggingInterceptor interceptor,
            @ApplicationContext Context context
    ){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(BuildConfig.DEBUG){
//            builder.addInterceptor(new ChuckerInterceptor.Builder(context).build());
        }
        builder.addInterceptor(chain ->
            {
                Request.Builder ongoing = chain.request().newBuilder();
                ongoing.addHeader("Accept", "application/json");
                if(token != null && !token.isEmpty()){
                    ongoing.addHeader("Authorization","Bearer "+token);
                }
                return chain.proceed(ongoing.build());
            }
        );
        builder.addInterceptor(interceptor);
        builder.cache(cache);
        return builder.build();
    }

    @Provides
    public Retrofit providesRetrofit(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("https://schoolcommerce.nirmanschedule.com/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
//    @Singleton
    public ApiService providesApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}
