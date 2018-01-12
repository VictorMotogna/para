package com.example.victormotogna.para.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by victormotogna on 10/16/17.
 */

@Entity(tableName = "expense")
public class Expense implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "value")
    public int value;

    @ColumnInfo(name = "category")
    public Category category;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private Date date;

    public Expense(String name, int value, Category category, String description, Date date) {
        this.name = name;
        this.value = value;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Expense)
        {
            Expense c = (Expense) o;
            if ( this.name.equals(c.name) && this.category.equals(c.category) && this.value == c.value && this.description.equals(c.description) ) //whatever here
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Expense: " + name +
                ", " + value + " lei" +
                ", " + category +
                ", " + description +
                ", " + date.getDate() + "." + date.getMonth();
    }
}
