package com.project.ecommerce.data.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

//@InstallIn(SingletonComponent.class)
//@Module
public class AppModule {

    private Application application;

    public AppModule(){}

    public AppModule(Application application){
        this.application = application;
    }

//    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }

}
