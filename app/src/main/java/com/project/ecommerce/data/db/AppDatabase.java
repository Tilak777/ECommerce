package com.project.ecommerce.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.data.model.UserModel;
import com.project.ecommerce.data.model.WishlistModel;

@Database( entities = {
        UserModel.class,
        ProductModel.class,
        WishlistModel.class,
}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DbDao dbDao();

    public static AppDatabase instance;

    public AppDatabase getAppDatabase(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"AppDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
