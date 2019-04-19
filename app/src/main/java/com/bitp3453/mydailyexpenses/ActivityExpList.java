package com.bitp3453.mydailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.ExpensesDBModel;
import sqliteexpense.ExpenseDB;

import static sqliteexpense.ExpenseDB.colExpDate;
import static sqliteexpense.ExpenseDB.colExpId;
import static sqliteexpense.ExpenseDB.colExpName;
import static sqliteexpense.ExpenseDB.colExpPrice;
import static sqliteexpense.ExpenseDB.colExpTime;

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
            final ExpensesDBModel editedExpense =new  ExpensesDBModel(
                                                data.getStringExtra("name"),
                                                Double.valueOf(data.getStringExtra("price")),
                                                data.getStringExtra("date"),
                                                data.getStringExtra("time"),
                                                data.getStringExtra("id"));
            expenseDB.fnEditExpense(editedExpense);
            customAdapterExpList.setItems(expenseDB.fnGetAllExpenses());
            customAdapterExpList.notifyDataSetChanged();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.url),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("selectFn","fnEditExpense");
                    params.put("varMobileDate",editedExpense.getStrExpDate());
                    params.put("varExpId", editedExpense.getStrExpId());
                    params.put("varExpName", editedExpense.getStrExpName());
                    params.put("varExpPrice", editedExpense.getStrExpPrice()+"");
                    params.put("varMobileTime", editedExpense.getStrExpTime());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}
