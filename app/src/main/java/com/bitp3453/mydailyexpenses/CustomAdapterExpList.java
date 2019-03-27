package com.bitp3453.mydailyexpenses;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import model.ExpensesDBModel;

import static java.sql.Types.NULL;

/**
 * Created by user on 3/20/2019.
 */

public class CustomAdapterExpList extends RecyclerView.Adapter<CustomAdapterExpList.ViewHolder>  {



    List<ExpensesDBModel> listExpenses;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CustomAdapterExpList (List<ExpensesDBModel> expensesDBModels){
        this.listExpenses = expensesDBModels;
    }

    public void setItems(List<ExpensesDBModel> expensesDBModels){
        this.listExpenses = expensesDBModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expenses_recycler,parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomAdapterExpList.ViewHolder holder, int position){
        ExpensesDBModel expensesDBModel = listExpenses.get(position);
        holder.txtVwExpName.setText(expensesDBModel.getStrExpName());
        double price = 0;
        if(expensesDBModel.getStrExpPrice()!=NULL)
            price = expensesDBModel.getStrExpPrice();
        holder.txtVwExpPrice.setText(String.format("%.2f", price));
        holder.txtVwExpDate.setText(expensesDBModel.getStrExpDate());
        holder.txtVwExpTime.setText(expensesDBModel.getStrExpTime());
    }

    @Override
    public int getItemCount(){
        return listExpenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtVwExpName, txtVwExpPrice, txtVwExpDate, txtVwExpTime;

        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            txtVwExpName = (TextView) itemView.findViewById(R.id.txtVwExpName);
            txtVwExpDate = (TextView) itemView.findViewById(R.id.txtVwExpDate);
            txtVwExpPrice= (TextView) itemView.findViewById(R.id.txtVwExpPrice);
            txtVwExpTime= (TextView) itemView.findViewById(R.id.txtVwExpTime);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);

        }


    }


}
