package com.example.victormotogna.para.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

    private ExpenseAdapter expenseAdapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenses = AppDatabase.getExpenseAppDatabase(this).expenseDao().getAll();
    }

    public void deleteExpenseDb(Expense expense) {
        AppDatabase.getExpenseAppDatabase(this).expenseDao().delete(expense);
//        notifyDeletion(expense);
        expenses = AppDatabase.getExpenseAppDatabase(this).expenseDao().getAll();
        setupViews();
    }

    public void deleteExpenseRemote(Expense expense) {
        mDatabase = FirebaseDatabase.getInstance().getReference("expenses");
        mDatabase.child(String.valueOf(expense.getId())).removeValue();
    }

    public void notifyDeletion(Expense expense) {
        int icon = R.drawable.ic_para;

        if(expense.category == Category.DRINKS) {
            icon = R.drawable.ic_category_drinks;
        } else if(expense.category == Category.FOOD) {
            icon = R.drawable.ic_category_food;
        } else if(expense.category == Category.CLOTHES) {
            icon = R.drawable.ic_category_clothes;
        } else if(expense.category == Category.FUN) {
            icon = R.drawable.ic_category_fun;
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "default")
                        .setSmallIcon(icon)
                        .setContentTitle("Expense deleted")
                        .setContentText("You deleted expense: " + expense.name);

        int mNotificationId = 001;

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, mBuilder.build());
    }

    @Background
    public void syncExpensesRemote(Expense expense) {
        mDatabase = FirebaseDatabase.getInstance().getReference("expenses");

        mDatabase.child(String.valueOf(expense.getId())).setValue(expense);
    }

    @AfterViews
    public void setupViews() {
        for(Expense expense: expenses) {
            syncExpensesRemote(expense);
        }

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
                                deleteExpenseRemote(expense);
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
    }
}