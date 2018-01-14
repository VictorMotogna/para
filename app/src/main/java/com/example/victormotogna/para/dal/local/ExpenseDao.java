package com.example.victormotogna.para.dal.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.victormotogna.para.model.Expense;

import java.util.List;

/**
 * Created by victormotogna on 1/12/18.
 */

@Dao
public interface ExpenseDao {
    @Query("SELECT * FROM expense")
    List<Expense> getAll();

    @Query("SELECT * FROM Expense where name LIKE  :name")
    Expense findByName(String name);

    @Query("SELECT * FROM Expense where category LIKE  :category")
    Expense findByCategory(String category);

    @Query("SELECT COUNT(*) from expense")
    int countExpenses();

    @Insert
    void insertAll(Expense... Expenses);

    @Insert
    void insert(Expense expense);

    @Delete
    void delete(Expense expense);
}
