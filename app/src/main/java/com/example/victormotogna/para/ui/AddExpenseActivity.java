//package com.example.victormotogna.para.ui;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.victormotogna.para.R;
//import com.example.victormotogna.para.model.Category;
//import com.example.victormotogna.para.model.Expense;
//
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.ViewById;
//
//import java.util.Date;
//
//@EActivity(R.layout.activity_add_expense)
//public class AddExpenseActivity extends AppCompatActivity {
//    // TODO: 10/18/17 add view model - respect mvvm
//
//    @ViewById(R.id.expenseName)
//    EditText expenseName;
//
//    @ViewById(R.id.expenseValue)
//    EditText expenseValue;
//
//    @ViewById(R.id.expenseDescription)
//    EditText expenseDescription;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Click(R.id.addExpense)
//    public void inputData() {
//        String expensename;
//        String expensedescription;
//        int expensevalue;
//        Category expensecategory;
//        boolean selected = true;
//
//        expensecategory = category;
//        expensevalue = Integer.parseInt(expenseValue.getText().toString());
//        expensename = expenseName.getText().toString();
//        expensedescription = expenseDescription.getText().toString();
//
//        if (expensename.equals("null") || expensename.equals("")) {
//            selected = false;
//        }
//
//        if (expensedescription.equals("null") || expensedescription.equals("")) {
//            selected = false;
//        }
//
//
//        if(expensecategory.toString() == null || expensecategory.toString().equals("")) {
//            selected = false;
//        }
//
//        if(expensevalue == 0) {
//            selected = false;
//        }
//
//        if(selected) {
//            Expense expense = new Expense(expensename, expensevalue, expensecategory, expensedescription, new Date());
//
//            txtExpense.setText(expense.toString());
//        } else {
//            Toast.makeText(this, "You must complete expense", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
