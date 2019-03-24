package sqliteexpense;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitp3453.mydailyexpenses.R;

import java.util.List;

import model.ExpensesDBModel;

/**
 * Created by user on 3/20/2019.
 */

public class CustomAdapterExpList extends RecyclerView.Adapter<CustomAdapterExpList.ViewHolder> {

    //public void setClickListener(View.OnClickListener callBack){
      //  mClickListener = callBack;
    //}



    List<ExpensesDBModel> listExpenses;
    public CustomAdapterExpList (List<ExpensesDBModel> expensesDBModels){
        this.listExpenses = expensesDBModels;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expenses_recycler,parent, false);
        //RecyclerView.ViewHolder holder = new ViewHolder();
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomAdapterExpList.ViewHolder holder, int position){
        ExpensesDBModel expensesDBModel = listExpenses.get(position);
        holder.txtVwExpName.setText(expensesDBModel.getStrExpName());
        holder.txtVwExpPrice.setText(String.format("%.2f",expensesDBModel.getStrExpPrice()));
        holder.txtVwExpDate.setText(expensesDBModel.getStrExpDate());
    }

    @Override
    public int getItemCount(){
        return listExpenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtVwExpName, txtVwExpPrice, txtVwExpDate;
        public ViewHolder(View itemView){
            super(itemView);

            txtVwExpName = (TextView) itemView.findViewById(R.id.txtVwExpName);
            txtVwExpDate = (TextView) itemView.findViewById(R.id.txtVwExpDate);
            txtVwExpPrice= (TextView) itemView.findViewById(R.id.txtVwExpPrice);
        }


    }


}
