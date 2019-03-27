package com.bitp3453.mydailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import model.ExpensesDBModel;
import sqliteexpense.ExpenseDB;

public class ActivityExpList extends AppCompatActivity {
    RecyclerView recyclerViewExpList;
    ArrayList<ExpensesDBModel> expensesDBModels;

    ExpenseDB expenseDB;
    CustomAdapterExpList customAdapterExpList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_list);

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
        customAdapterExpList.setOnItemClickListener(new CustomAdapterExpList.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityExpEdit.class);
                intent.putExtra("id",String.valueOf(position+1));
                startActivityForResult(intent,0);
            }
        });

        recyclerViewExpList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpList.setAdapter(customAdapterExpList);
        customAdapterExpList.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            expenseDB.fnEditExpense(new ExpensesDBModel(
                    data.getStringExtra("name"),
                    Double.valueOf(data.getStringExtra("price")),
                    data.getStringExtra("date"),
                    data.getStringExtra("time"),
                    data.getStringExtra("id")));
            customAdapterExpList.setItems(expenseDB.fnGetAllExpenses());
            customAdapterExpList.notifyDataSetChanged();
        }
    }
}
