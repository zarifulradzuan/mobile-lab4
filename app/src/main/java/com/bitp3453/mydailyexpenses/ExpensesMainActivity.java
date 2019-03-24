package com.bitp3453.mydailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import model.ExpensesDBModel;
import sqliteexpense.ExpenseDB;

public class ExpensesMainActivity extends AppCompatActivity {
    EditText editExpPrice;
    EditText editExpName;
    EditText editExpDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_main);

        editExpName = (EditText) findViewById(R.id.editExpName);
        editExpDate = (EditText) findViewById(R.id.editExpDate);
        editExpPrice = (EditText) findViewById(R.id.editExpPrice);

    }

    public void fnSave (View vw){
        ExpensesDBModel expensesDBModel = new ExpensesDBModel(
                editExpName.getText().toString(),
                Double.valueOf(String.format("%.2f",Double.valueOf(editExpPrice.getText().toString()))),
                editExpDate.getText().toString());
        ExpenseDB expenseDB = new ExpenseDB(getApplicationContext());
        expenseDB.fnInsertExpense(expensesDBModel);
        Toast.makeText(getApplicationContext(), "Expenses Saved.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int idMenu = item.getItemId();
        Intent intent = null;

        if(idMenu == R.id.expInsert){
            return true;
        }
        if(idMenu == R.id.expAll){
            intent = new Intent(this, AcitivityExpList.class);
        }

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
