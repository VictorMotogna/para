package com.example.victormotogna.para.utils.expense;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victormotogna.para.R;
import com.example.victormotogna.para.model.Expense;

import java.util.List;

import static com.example.victormotogna.para.model.Category.*;

/**
 * Created by victormotogna on 11/8/17.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ItemViewHolder> {

    private Context context;
    private List<Expense> expenses;
    private OnItemClickListener listener;

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    @Override
    public ExpenseAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExpenseAdapter.ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false));
    }

    @Override
    public void onBindViewHolder(ExpenseAdapter.ItemViewHolder holder, final int position) {
        final Expense expense = expenses.get(position);

        if(expense.getCategory().equals(CLOTHES)) {
            holder.imgCategoryPhoto.setImageResource(R.drawable.ic_category_clothes);
        } else if(expense.getCategory().equals(FOOD)) {
            holder.imgCategoryPhoto.setImageResource(R.drawable.ic_category_food);
        } else if(expense.getCategory().equals(DRINKS)) {
            holder.imgCategoryPhoto.setImageResource(R.drawable.ic_category_drinks);
        } else if(expense.getCategory().equals(FUN)) {
            holder.imgCategoryPhoto.setImageResource(R.drawable.ic_category_fun);
        }

        holder.txtExpensePrice.setText(expense.getValue() + "");
        holder.txtExpenseDescription.setText(expense.getDescription());
        holder.txtExpenseTitle.setText(expense.getName());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClicked(position, expense);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClicked(int position, Expense expense);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        View root;
        ImageView imgCategoryPhoto;
        TextView txtExpenseTitle;
        TextView txtExpenseDescription;
        TextView txtExpensePrice;

        public ItemViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            imgCategoryPhoto = (ImageView) itemView.findViewById(R.id.img_category);
            txtExpenseTitle = (TextView) itemView.findViewById(R.id.title_expense);
            txtExpenseDescription = (TextView) itemView.findViewById(R.id.description_expense);
            txtExpensePrice = (TextView) itemView.findViewById(R.id.price_expense);
        }
    }
}