package sqliteexpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.ExpensesDBModel;

/**
 * Created by user on 3/20/2019.
 */

public class ExpenseDB extends SQLiteOpenHelper {
    public static final String dbName = "dbMyExpense";
    public static final String tblNameExpense = "expenses";
    public static final String colExpName = "expenses_name";
    public static final String colExpPrice = "expenses_price";
    public static final String colExpDate = "expenses_date";
    public static final String colExpId = "expenses_id";

    public static final String strCrtTblExpenses = "CREATE TABLE "+ tblNameExpense +
            " ("+ colExpId + " INTEGER PRIMARY KEY, "+ colExpName +" TEXT, " +
            colExpPrice + " REAL, " + colExpDate + " DATE)";
    public static final String strDropTblExpenses = "DROP TABLE IF EXISTS " + tblNameExpense;

    public ExpenseDB(Context context){
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(strCrtTblExpenses);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL(strDropTblExpenses);
        onCreate(sqLiteDatabase);
    }

    public float fnInsertExpense(ExpensesDBModel meExpense){
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colExpName, meExpense.getStrExpName());
        values.put(colExpDate, meExpense.getStrExpDate());
        values.put(colExpPrice, meExpense.getStrExpPrice());

        retResult = db.insert(tblNameExpense, null, values);
        return retResult;
    }

    public ExpensesDBModel fnGetExpenses(int intExpId){

        String strSelQry = "Select * from" + tblNameExpense + " where "+ colExpId +" = " + intExpId;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelQry,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return new ExpensesDBModel(
                cursor.getString(cursor.getColumnIndex(colExpName)),
                cursor.getDouble(cursor.getColumnIndex(colExpPrice)),
                cursor.getString(cursor.getColumnIndex(colExpDate)));

    }

    public List<ExpensesDBModel> fnGetAllExpenses(){
        List<ExpensesDBModel> list = new ArrayList<ExpensesDBModel>();
        String strSelAll = "Select * from " + tblNameExpense;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelAll,null);
        if(cursor.moveToFirst()){
            do{
                ExpensesDBModel model = new ExpensesDBModel(
                        cursor.getString(cursor.getColumnIndex(colExpName)),
                        cursor.getDouble(cursor.getColumnIndex(colExpPrice)),
                        cursor.getString(cursor.getColumnIndex(colExpDate)),
                        cursor.getString(cursor.getColumnIndex(colExpId)));
                list.add(model);
            }while(cursor.moveToNext());
        }
        return list;
    }
}
