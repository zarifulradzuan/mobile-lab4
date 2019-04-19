package com.bitp3453.mydailyexpenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import model.ExpensesDBModel;
import sqliteexpense.ExpenseDB;

public class ExpensesMainActivity extends AppCompatActivity {
    EditText editExpPrice;
    EditText editExpName;
    EditText editExpDate;
    EditText editExpTime;
    ProgressBar progressBar;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_main);
        url = getString(R.string.url);
        progressBar = findViewById(R.id.progressBar);
        editExpName = (EditText) findViewById(R.id.insertExpName);
        editExpDate = (EditText) findViewById(R.id.insertExpDate);
        editExpPrice = (EditText) findViewById(R.id.insertExpPrice);
        editExpTime = (EditText) findViewById(R.id.insertExpTime);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    editExpDate.setText(jsonObject.getString("currDate"));
                    editExpTime.setText(jsonObject.getString("currTime"));
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Error reading json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("ErrorListener", error.getMessage());
                } catch (NullPointerException e){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    dateFormat.setTimeZone(TimeZone.getDefault());
                    timeFormat.setTimeZone(TimeZone.getDefault());
                    editExpTime.setText(timeFormat.format(new Date()));
                    editExpDate.setText(dateFormat.format(new Date()));
                    Toast.makeText(getApplicationContext(),"Cannot connect to server", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("selectFn","fnGetDateTime");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void fnSave (final View vw){
        progressBar.setVisibility(View.VISIBLE);
        vw.setClickable(false);
        final ExpensesDBModel expensesDBModel = new ExpensesDBModel(
                editExpName.getText().toString(),
                Double.valueOf(String.format("%.2f",Double.valueOf(editExpPrice.getText().toString()))),
                editExpDate.getText().toString(),
                editExpTime.getText().toString());
        ExpenseDB expenseDB = new ExpenseDB(getApplicationContext());
        expenseDB.fnInsertExpense(expensesDBModel);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        vw.setClickable(true);
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        vw.setClickable(true);
                        Toast.makeText(getApplicationContext(),"Unable to make connection to web service", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("selectFn","fnAddExpense");
                params.put("varMobileDate",expensesDBModel.getStrExpDate());
                params.put("varExpName", expensesDBModel.getStrExpName());
                params.put("varExpPrice", expensesDBModel.getStrExpPrice()+"");
                params.put("varMobileTime", expensesDBModel.getStrExpTime());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        Toast.makeText(this, "Expense saved.",Toast.LENGTH_SHORT).show();
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
            intent = new Intent(this, ActivityExpList.class);
        }

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
