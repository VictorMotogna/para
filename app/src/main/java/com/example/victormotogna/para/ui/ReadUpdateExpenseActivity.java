package com.example.victormotogna.para.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victormotogna.para.R;
import com.example.victormotogna.para.model.Category;
import com.example.victormotogna.para.model.Expense;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by victormotogna on 11/9/17.
 */

@EActivity(R.layout.activity_read_update_delete_expense)
public class ReadUpdateExpenseActivity extends AppCompatActivity {

    private List<Expense> expenses;
    private Expense expense;

    @ViewById(R.id.ru_expense_name)
    EditText expenseName;

    @ViewById(R.id.ru_expense_value)
    EditText expenseCost;

    @ViewById(R.id.ru_expense_description)
    EditText expenseDescription;

    @ViewById(R.id.category_food_button)
    Button categoryFood;

    @ViewById(R.id.category_drinks_button)
    Button categoryDrinks;

    @ViewById(R.id.category_fun_button)
    Button categoryFun;

    @ViewById(R.id.category_other_button)
    Button categoryOther;

    @ViewById(R.id.calendarView)
    CalendarView calendarView;

    private Category category = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        expenses = ((List<Expense>) intent.getSerializableExtra("expenses"));
        expense = ((Expense) intent.getSerializableExtra("expense"));
    }

    @Click(R.id.category_food_button)
    @UiThread
    public void selectFoodCategory() {
        category = Category.FOOD;

        categoryDrinks.setActivated(false);
        categoryFun.setActivated(false);
        categoryOther.setActivated(false);
        categoryFood.setActivated(true);
    }

    @Click(R.id.category_drinks_button)
    @UiThread
    public void selectDrinkCategory() {
        category = Category.DRINKS;

        categoryFood.setActivated(false);
        categoryFun.setActivated(false);
        categoryOther.setActivated(false);
        categoryDrinks.setActivated(true);
    }

    @Click(R.id.category_fun_button)
    @UiThread
    public void selectFunCategory() {
        category = Category.FUN;

        categoryFood.setActivated(false);
        categoryDrinks.setActivated(false);
        categoryOther.setActivated(false);
        categoryFun.setActivated(true);
    }

    @Click(R.id.category_other_button)
    @UiThread
    public void selectOtherCategory() {
        category = Category.CLOTHES;

        categoryFood.setActivated(false);
        categoryDrinks.setActivated(false);
        categoryFun.setActivated(false);
        categoryOther.setActivated(true);
    }

    @AfterViews
    public void setupViews() {
        expenseName.setText(expense.getName());
        expenseCost.setText(expense.getValue()+"");
        expenseDescription.setText(expense.getDescription());

        if(expense.getCategory().equals(Category.FOOD)) {
            selectFoodCategory();
        } else if(expense.getCategory().equals(Category.DRINKS)) {
            selectDrinkCategory();
        } else if(expense.getCategory().equals(Category.FUN)) {
            selectFunCategory();
        } else if(expense.getCategory().equals(Category.CLOTHES)) {
            selectOtherCategory();
        }

        calendarView.setDate(expense.getDate().getTime());
    }

    @Click(R.id.save_update)
    public void saveUpdate() {
        expenses.remove(expense);

        String expensename;
        String expensedescription;
        Category expensecategory;
        int expenseValue = 0;
        boolean selected = true;

        expensecategory = category;

        if(!expenseCost.getText().toString().equals("") || !expenseCost.getText().toString().equals(null)) {
            expenseValue = Integer.parseInt(expenseCost.getText().toString());
        }

        expensename = expenseName.getText().toString();
        expensedescription = expenseDescription.getText().toString();

        if (expensename.equals("null") || expensename.equals("")) {
            selected = false;
        }

        if (expensedescription.equals("null") || expensedescription.equals("")) {
            selected = false;
        }


        if(expensecategory.toString().equals(null) || expensecategory.toString().equals("")) {
            selected = false;
        }

        if(expenseCost.getText().toString().equals("") || expenseCost.getText().toString().equals(null)) {
            selected = false;
        }

        if(selected) {
            Expense expense2 = new Expense(expensename, expenseValue, expensecategory, expensedescription, new Date());
            expenses.add(expense2);
        } else {
            Toast.makeText(this, "You must complete expense", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(ReadUpdateExpenseActivity.this, ExpensesActivity_.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("expenses", (Serializable) expenses);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    @Click(R.id.send_mail)
    public void tellOthers() {

        String expensename;
        String expensedescription;
        Category expensecategory;
        int expenseValue = 0;
        boolean selected = true;

        expensecategory = category;
        if(!expenseCost.getText().toString().equals("") || !expenseCost.getText().toString().equals(null)) {
            expenseValue = Integer.parseInt(expenseCost.getText().toString());
        }

        expensename = expenseName.getText().toString();
        expensedescription = expenseDescription.getText().toString();

        if (expensename.equals("null") || expensename.equals("")) {
            selected = false;
        }

        if (expensedescription.equals("null") || expensedescription.equals("")) {
            selected = false;
        }


        if(expensecategory.toString().equals(null) || expensecategory.toString().equals("")) {
            selected = false;
        }

        if(expenseCost.getText().toString().equals("") || expenseCost.getText().toString().equals(null)) {
            selected = false;
        }

        if(selected) {
            Expense expense = new Expense(expensename, expenseValue, expensecategory, expensedescription, new Date());
            expenses.add(expense);
        } else {
            Toast.makeText(this, "You must complete expense", Toast.LENGTH_SHORT).show();
        }

        Intent emailIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        emailIntent.setAction(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "" });
        emailIntent.putExtra(android.content.Intent.EXTRA_CC, "");
        emailIntent.putExtra(android.content.Intent.EXTRA_BCC, "");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "expense");
        emailIntent.putExtra(Intent.EXTRA_TEXT, expense.toString());
        emailIntent.setType("text/html");
        startActivity(emailIntent);
    }

    @Click(R.id.delete)
    public void deleteExpense() {
        expenses.remove(expense);

        Intent intent = new Intent(ReadUpdateExpenseActivity.this, ExpensesActivity_.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("expenses", (Serializable) expenses);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }
}