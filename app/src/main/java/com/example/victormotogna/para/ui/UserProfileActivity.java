package com.example.victormotogna.para.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victormotogna.para.R;
import com.example.victormotogna.para.dal.local.AppDatabase;
import com.example.victormotogna.para.model.Category;
import com.example.victormotogna.para.model.Expense;
import com.example.victormotogna.para.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by victormotogna on 10/29/17.
 */

@EActivity(R.layout.activity_user_profile)
public class UserProfileActivity extends AppCompatActivity {

    @ViewById(R.id.expenseName)
    EditText expenseName;

    @ViewById(R.id.expenseValue)
    EditText expenseValue;

    @ViewById(R.id.expenseDescription)
    EditText expenseDescription;

    @ViewById(R.id.user_name)
    TextView userName;

    @ViewById(R.id.profile_photo)
    ImageView profilePhoto;

    @ViewById(R.id.category_food_button)
    Button categoryFood;

    @ViewById(R.id.category_drinks_button)
    Button categoryDrinks;

    @ViewById(R.id.category_fun_button)
    Button categoryFun;

    @ViewById(R.id.category_other_button)
    Button categoryOther;

    @ViewById(R.id.categories)
    LinearLayout categories;

    private User user;
    private Category category = null;
    private List<Expense> expenses = new ArrayList<>();
    private double totalExpense = 0;
    private DatabaseReference mDatabase;
    private String photoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = (User) getIntent().getExtras().getSerializable("user");
        photoUrl = getIntent().getExtras().getString("photo");
    }

    @AfterViews
    public void setupViews() {
        userName.setText(user.getName());

        if(photoUrl != null) {
            Picasso.with(getApplicationContext())
                    .load(Uri.parse(photoUrl))
                    .fit()
                    .centerCrop()
                    .into(profilePhoto);
        }
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

    public Expense addExpenseToDb(final AppDatabase db, Expense expense) {
        db.expenseDao().insertAll(expense);
        return expense;
    }

    @Click(R.id.addExpense)
    public void addExpense() {
        String expensename;
        String expensedescription;
        int expensevalue = 0;
        Category expensecategory;
        boolean selected = true;

        expensecategory = category;
        if(!expenseValue.getText().toString().equals("") || !expenseValue.getText().toString().equals(null)) {
            expensevalue = Integer.parseInt(expenseValue.getText().toString());
        }
        expensename = expenseName.getText().toString();
        expensedescription = expenseDescription.getText().toString();

        if (expensename.equals("null") || expensename.equals("")) {
            selected = false;
        }

        if (expensedescription.equals("null") || expensedescription.equals("")) {
            selected = false;
        }

        if(!categoryDrinks.isActivated() && !categoryFood.isActivated() && !categoryFun.isActivated() && !categoryOther.isActivated()) {
            selected = false;
        }

        if(expensecategory.toString().equals(null) || expensecategory.toString().equals("")) {
            selected = false;
        }

        if(expenseValue.getText().toString().equals("") || expenseValue.getText().toString().equals(null)) {
            selected = false;
        }

        if(selected) {
            Expense expense = new Expense(expensename, expensevalue, expensecategory, expensedescription, new Date());
            expenses.add(expense);

            addExpenseToDb(AppDatabase.getExpenseAppDatabase(this), expense);
            for(Expense exp: expenses) {
                totalExpense += exp.getValue();
            }
        } else {
            Toast.makeText(this, "You must complete expense", Toast.LENGTH_SHORT).show();
        }

        expenseName.setText("");
        expenseValue.setText("");
        expenseDescription.setText("");
    }

    @Click(R.id.sign_out)
    public void signOut() {
        Intent intent = new Intent(UserProfileActivity.this, LandingActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.viewExpenses)
    public void viewExpenses() {
        Intent intent = new Intent(UserProfileActivity.this, ExpensesActivity_.class);
        startActivity(intent);
    }

}