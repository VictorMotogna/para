package com.example.victormotogna.para.utils.expense;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.victormotogna.para.model.Expense;

import java.util.List;

/**
 * Created by victormotogna on 11/8/17.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ItemViewHolder> {

    private Context context;
    private List<Expense> expenses;
    private AdapterView.OnItemClickListener listener;

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
