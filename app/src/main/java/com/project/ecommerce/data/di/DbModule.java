package com.project.ecommerce.data.di;

import android.content.Context;

import androidx.room.Room;

import com.project.ecommerce.data.db.AppDatabase;
import com.project.ecommerce.data.db.DbDao;
import com.project.ecommerce.data.model.UserModel;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module()
public class DbModule {

    @Provides
    @Singleton
    AppDatabase provideDB(@ApplicationContext Context context){
        return Room.databaseBuilder(context, AppDatabase.class,"AppDatabase").allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    DbDao provideDbDao(AppDatabase appDatabase){
        return appDatabase.dbDao();
    }

    @Provides
//    @Singleton
    @Named("accessToken")
    @Nullable String providesAccessToken(DbDao dbDao){
        UserModel model = dbDao.getUser();
        if(model == null){
            return null;
        }
        return model.getAccessToken();
    }

}
