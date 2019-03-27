package com.bitp3453.mydailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import model.ExpensesDBModel;
import sqliteexpense.ExpenseDB;

public class ActivityExpEdit extends AppCompatActivity {
    EditText name;
    EditText date;
    EditText price;
    String expId;
    ExpenseDB expenseDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_edit);
        Intent intent = getIntent();
        expId = intent.getStringExtra("id");
        name = (EditText) findViewById(R.id.editExpName);
        price = (EditText) findViewById(R.id.editExpPrice);
        date = (EditText) findViewById(R.id.editExpDate);

        expenseDB = new ExpenseDB(getApplicationContext());
        ExpensesDBModel toEdit = expenseDB.fnGetExpenses(expId);

        String expName = toEdit.getStrExpName();
        name.setText(expName);
        String expPrice;
        try{
            expPrice = String.valueOf(toEdit.getStrExpPrice());
        } catch(Exception e){
            expPrice = "0";
        }

        price.setText(expPrice);

        String expDate = toEdit.getStrExpDate();
        date.setText(expDate);
    }

    public void editExp(View vw) {
        Intent intent = new Intent();
        intent.putExtra("id",expId);
        intent.putExtra("date",date.getText().toString());
        intent.putExtra("price",price.getText().toString());
        intent.putExtra("name",name.getText().toString());
        setResult(0, intent);
        finish();
    }
}
