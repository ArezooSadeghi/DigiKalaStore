package com.example.digikalastore.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.digikalastore.model.customer.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class UserDatabase extends RoomDatabase {

    public static final String DB_NAME = "user.db";

    private static UserDatabase sDatabase;
    private Context mContext;

    public synchronized static UserDatabase getInstance(Context context) {
        if (sDatabase == null) {
            sDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sDatabase;
    }

    public abstract UserDao getUserDao();
}
