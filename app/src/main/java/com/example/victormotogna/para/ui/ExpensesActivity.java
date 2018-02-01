package com.example.victormotogna.para.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.victormotogna.para.R;
import com.example.victormotogna.para.dal.local.AppDatabase;
import com.example.victormotogna.para.model.Category;
import com.example.victormotogna.para.model.Expense;
import com.example.victormotogna.para.utils.expense.ExpenseAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
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

    @ViewById(R.id.delete_all)
    Button deleteAll;

    @ViewById(R.id.total_expenses)
    TextView totalExpenses;

//    @ViewById(R.id.refresh_layout)
//    SwipeRefreshLayout refreshLayout;

    private ExpenseAdapter expenseAdapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenses = AppDatabase.getExpenseAppDatabase(this).expenseDao().getAll();
    }

    public void deleteExpenseDb(Expense expense) {
        AppDatabase.getExpenseAppDatabase(this).expenseDao().delete(expense);
        notifyDeletion(expense);
        expenses = AppDatabase.getExpenseAppDatabase(this).expenseDao().getAll();
        setupViews();
    }

    public void notifyDeletion(final Expense expense) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Deteled " + expense.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void populateViews() {
        expenses = AppDatabase.getExpenseAppDatabase(this).expenseDao().getAll();

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

        expenseAdapter.setLongClickListener(new ExpenseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClicked(int position, final Expense expense) {
                MaterialDialog dialog = new MaterialDialog.Builder(ExpensesActivity.this)
                        .title("Delete expense?")
                        .positiveText("Yes")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                deleteExpenseDb(expense);
                            }
                        })
                        .negativeText("No")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        allExpenses.setAdapter(expenseAdapter);
        expenseAdapter.notifyDataSetChanged();
//        refreshLayout.setRefreshing(false);
    }

    @Click(R.id.delete_all)
    public void deleteAll() {
        for(Expense expense: expenses) {
            AppDatabase.getExpenseAppDatabase(this).expenseDao().deleteAll(expense);
        }
        expenses = AppDatabase.getExpenseAppDatabase(this).expenseDao().getAll();
        setupViews();
    }

    @AfterViews
    public void setupViews() {
        populateViews();

        int total = 0;
        for(Expense expense: expenses) {
            total += expense.getValue();
        }
        totalExpenses.setText("Total: " + total);
    }
}