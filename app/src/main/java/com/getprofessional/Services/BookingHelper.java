package com.getprofessional.Services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.getprofessional.Activity.MainActivity;
import com.getprofessional.BackGrounds.UpdateOrders;
import com.getprofessional.BackGrounds.UserDetails;

import java.security.PublicKey;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BookingHelper {

    public static Context context;
    public static String orderPref = "order_pref";
    public static String imageKey = "image_key";
    public static String nameKey = "name_key";

    public BookingHelper(Context context) {
        this.context = context;
    }

    public void updateOrderToMysqlDB(String serviceName, String orderAmt, String cust_id, String dateTime) {
        Random getEmplID = new Random();
        String emp_id = String.valueOf(getEmplID.nextInt((6 - 1) + 1) + 1);

        UserDetails getEmployeeNameImage = new UserDetails(context);
        getEmployeeNameImage.execute("GENI",emp_id);

        UpdateOrders updateOrdersToMysqlDB = new UpdateOrders(context);
        updateOrdersToMysqlDB.execute("UpdateOrders",serviceName,orderAmt,emp_id,cust_id,dateTime);

    }

    public static void setEmployee(String empImgage, String empName){
        SharedPreferences sharedPref = context.getSharedPreferences(orderPref,0);
        sharedPref.edit().putString(imageKey,empImgage).apply();
        sharedPref.edit().putString(nameKey,empName).apply();
    }

    public static String[] getEmployee(){
        String[] employee = new String[3];

        SharedPreferences sharedPref = context.getSharedPreferences(orderPref,Context.MODE_PRIVATE);
        employee[0] = sharedPref.getString(imageKey,null);
        employee[1] = sharedPref.getString(nameKey,null);

        return employee;
    }

}
