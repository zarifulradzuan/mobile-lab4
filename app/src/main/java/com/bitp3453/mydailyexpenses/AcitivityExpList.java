package com.bitp3453.mydailyexpenses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import model.ExpensesDBModel;
import sqliteexpense.CustomAdapterExpList;
import sqliteexpense.ExpenseDB;

public class AcitivityExpList extends AppCompatActivity {
    RecyclerView recyclerViewExpList;
    ArrayList<ExpensesDBModel> expensesDBModels;

    ExpenseDB expenseDB;
    CustomAdapterExpList customAdapterExpList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_exp_list);

        recyclerViewExpList = (RecyclerView) findViewById(R.id.recyListExp);

        expenseDB = new ExpenseDB(getApplicationContext());
        expensesDBModels = (ArrayList<ExpensesDBModel>) expenseDB.fnGetAllExpenses();
        double totalExpenses = 0;
        for (ExpensesDBModel model :expensesDBModels){
            totalExpenses+=model.getStrExpPrice();
        }
        TextView totalExpensesVw = (TextView) findViewById(R.id.totalExp);
        totalExpensesVw.setText("Total Expense: RM "+String.format("%.2f",totalExpenses));
        customAdapterExpList = new CustomAdapterExpList(expenseDB.fnGetAllExpenses());

        recyclerViewExpList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpList.setAdapter(customAdapterExpList);
        customAdapterExpList.notifyDataSetChanged();
    }
}
