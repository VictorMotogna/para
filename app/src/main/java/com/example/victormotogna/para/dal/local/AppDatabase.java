package com.example.victormotogna.para.dal.local;

import android.arch.persistence.room.*;
import android.content.Context;

import com.example.victormotogna.para.model.Expense;
import com.example.victormotogna.para.model.User;

/**
 * Created by victormotogna on 1/12/18.
 */

@Database(entities = {Expense.class, User.class}, version = 1)
@android.arch.persistence.room.TypeConverters({TypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract ExpenseDao expenseDao();

    public static AppDatabase getExpenseAppDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "expense-database")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

    public static AppDatabase getUserAppDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
