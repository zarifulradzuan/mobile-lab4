package model;

/**
 * Created by user on 3/20/2019.
 */

public class ExpensesDBModel {
    private String expId;
    private String expName;
    private Double expPrice;
    private String expDate;
    private String expTime;

    public ExpensesDBModel(String expName, double expPrice, String expDate, String expTime) {
        this.expDate = expDate;
        this.expName = expName;
        this.expPrice = expPrice;
        this.expTime = expTime;
    }

    public ExpensesDBModel(String expName, double expPrice, String expDate, String expTime, String expId) {
        this.expDate = expDate;
        this.expName = expName;
        this.expPrice = expPrice;
        this.expId = expId;
        this.expTime = expTime;
    }

    public ExpensesDBModel(){}

    public String getStrExpName(){
        return expName;
    }

    public double getStrExpPrice(){
        return expPrice;
    }

    public String getStrExpId(){
        return expId;
    }

    public String getStrExpDate(){
        return expDate;
    }

    public String getStrExpTime(){
        return expTime;
    }

    public void setStrExpName(String name){
        this.expName = name;
    }

    public void setStrExpPrice(double price){
        this.expPrice = price;
    }

    public void setStrExpDate(String date){
        this.expDate = date;
    }
}
