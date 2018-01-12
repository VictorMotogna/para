package com.example.victormotogna.para.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.victormotogna.para.R;
import com.example.victormotogna.para.dal.AppDatabase;
import com.example.victormotogna.para.model.Expense;
import com.example.victormotogna.para.utils.expense.ExpenseAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victormotogna on 11/9/17.
 */

@EActivity(R.layout.activity_all_expenses)
public class ExpensesActivity extends AppCompatActivity {

    private List<Expense> expenses;

    @ViewById(R.id.rv_all_expenses)
    RecyclerView allExpenses;

    @ViewById(R.id.no_expenses)
    TextView noExpenses;

    private ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenses = new ArrayList<>();

        expenses = AppDatabase.getExpenseAppDatabase(this).expenseDao().getAll();
    }

    @AfterViews
    public void setupViews() {
        allExpenses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        expenseAdapter = new ExpenseAdapter(this, expenses);

        if(expenses.size() == 0) {
            noExpenses.setVisibility(View.VISIBLE);
        } else {
            noExpenses.setVisibility(View.GONE);
        }

        expenseAdapter.setListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, Expense expense) {
                Intent intent = new Intent(ExpensesActivity.this, ReadUpdateExpenseActivity_.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("expenses", (Serializable) expenses);
                bundle.putSerializable("expense", expense);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });

        allExpenses.setAdapter(expenseAdapter);
        expenseAdapter.notifyDataSetChanged();
    }
}