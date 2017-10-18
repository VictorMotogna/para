package com.example.victormotogna.para.model;

import java.util.Date;

/**
 * Created by victormotogna on 10/16/17.
 */

public class Expense {
    // TODO: 10/16/17 implement ormlite for persistance

    public String name;
    public int value;
    public Category category;
    private String description;
    private Date date;

    public Expense(String name, int value, Category category, String description, Date date) {
        this.name = name;
        this.value = value;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public double getValue() {
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

    @Override
    public String toString() {
        return "Expense: " + name +
                ", " + value + " lei" +
                ", " + category +
                ", " + description +
                ", " + date.getDate() + "." + date.getMonth();
    }
}
