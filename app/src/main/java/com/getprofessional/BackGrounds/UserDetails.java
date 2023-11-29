package com.getprofessional.BackGrounds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.getprofessional.Activity.LoginActivity;
import com.getprofessional.Activity.MainActivity;
import com.getprofessional.Services.BookingHelper;
import com.getprofessional.models.GP_BG_Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class UserDetails extends AsyncTask<String, Void, String> {

    private static String db_host = "HostUrl";
    private static String db_name = "UserName";
    private static String db_user = "UserName";
    private static String db_pass = "password";
    private AlertDialog dialog;
    private Context context;

    public UserDetails(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String type = voids[0];

        String login_url = "http://www.getprofessional.in/lg.php";
        String orders_url = "http://www.getprofessional.in/orders.php";
        String emp_url = "http://www.getprofessional.in/emp.php";
        String update_url = "http://www.getprofessional.in/updodr.php";

        if (type.equals("login")) {
            String session = "SESSION";
            String username = "USERNAME";
            String user = voids[1];
            String pass = voids[2];
            try {

                URL url = new URL(login_url);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")
                        + "&&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8")
                        + "&&" + URLEncoder.encode("db_host", "UTF-8") + "=" + URLEncoder.encode(db_host, "UTF-8")
                        + "&&" + URLEncoder.encode("db_name", "UTF-8") + "=" + URLEncoder.encode(db_name, "UTF-8")
                        + "&&" + URLEncoder.encode("db_user", "UTF-8") + "=" + URLEncoder.encode(db_user, "UTF-8")
                        + "&&" + URLEncoder.encode("db_pass", "UTF-8") + "=" + URLEncoder.encode(db_pass, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;

                    //converting the string to json array object
                    JSONArray order = new JSONArray(result);
                    for (int i = 0; i < order.length(); i++) {
                        //getting product object from json array
                        JSONObject orderObject = order.getJSONObject(i);

                        int cust_id = orderObject.getInt("cust_id");
                        String cust_name = orderObject.getString("cust_name");
                        String cust_email = orderObject.getString("cust_email");

                        SharedPreferences sharedPref = context.getSharedPreferences(session,0);
                        sharedPref.edit().putString(username,cust_name).apply();

                        GP_BG_Data bgData = new GP_BG_Data(context);
                        bgData.insertUserDetals(cust_id, cust_name, cust_email);
                    }

                }
                reader.close();
                ips.close();
                http.disconnect();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (type.equals("orders")) {

            String cust_id = voids[1];
            try {
                GP_BG_Data db = new GP_BG_Data(context);
                db.deleteOrders();

                URL url = new URL(orders_url);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("cust_id", "UTF-8") + "=" + URLEncoder.encode(cust_id, "UTF-8")
                        + "&&" + URLEncoder.encode("db_host", "UTF-8") + "=" + URLEncoder.encode(db_host, "UTF-8")
                        + "&&" + URLEncoder.encode("db_name", "UTF-8") + "=" + URLEncoder.encode(db_name, "UTF-8")
                        + "&&" + URLEncoder.encode("db_user", "UTF-8") + "=" + URLEncoder.encode(db_user, "UTF-8")
                        + "&&" + URLEncoder.encode("db_pass", "UTF-8") + "=" + URLEncoder.encode(db_pass, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;

                    //converting the string to json array object
                    JSONArray order = new JSONArray(result);
                    //traversing through all the object
                    for (int i = 0; i < order.length(); i++) {

                        //getting product object from json array
                        JSONObject orderObject = order.getJSONObject(i);

                        int order_id = orderObject.getInt("order_id");
                        int cust_Id = orderObject.getInt("cust_id");
                        String emp_image = orderObject.getString("emp_image");
                        String emp_name = orderObject.getString("emp_name");
                        String service_name = orderObject.getString("service_name");
                        String order_date = orderObject.getString("order_date");
                        double order_amt = orderObject.getDouble("order_amt");

                        GP_BG_Data DB = new GP_BG_Data(context);
                        DB.insertOrders(order_id, cust_Id, emp_image, emp_name, service_name, order_date, order_amt);
                    }
                }
                reader.close();
                ips.close();
                http.disconnect();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (type.equals("GENI")) {
            String emp_id = voids[1];
            try {

                URL url = new URL(emp_url);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("emp_id", "UTF-8") + "=" + URLEncoder.encode(emp_id, "UTF-8")
                        + "&&" + URLEncoder.encode("db_host", "UTF-8") + "=" + URLEncoder.encode(db_host, "UTF-8")
                        + "&&" + URLEncoder.encode("db_name", "UTF-8") + "=" + URLEncoder.encode(db_name, "UTF-8")
                        + "&&" + URLEncoder.encode("db_user", "UTF-8") + "=" + URLEncoder.encode(db_user, "UTF-8")
                        + "&&" + URLEncoder.encode("db_pass", "UTF-8") + "=" + URLEncoder.encode(db_pass, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;

                    //converting the string to json array object
                    JSONArray order = new JSONArray(result);

                    for (int i = 0; i < order.length(); i++) {

                        //getting product object from json array
                        JSONObject orderObject = order.getJSONObject(i);

                        String emp_image = orderObject.getString("emp_image");
                        String emp_name = orderObject.getString("emp_name");

                        //Saving the data into the sharedPreferences.
                        BookingHelper.setEmployee(emp_image,emp_name);
                    }
                }
                reader.close();
                ips.close();
                http.disconnect();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (type.equals("UpdateOrders")) {
            String service_name = voids[1];
            String order_amt = voids[2];
            String cust_id = voids[3];
            String order_date = voids[4];
            String[] empImageName = BookingHelper.getEmployee();

            try {

                URL url = new URL(update_url);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("cust_id", "UTF-8") + "=" + URLEncoder.encode(cust_id, "UTF-8")
                        + "&&" + URLEncoder.encode("emp_image", "UTF-8") + "=" + URLEncoder.encode(empImageName[0], "UTF-8")
                        + "&&" + URLEncoder.encode("emp_name", "UTF-8") + "=" + URLEncoder.encode(empImageName[1], "UTF-8")
                        + "&&" + URLEncoder.encode("service_name", "UTF-8") + "=" + URLEncoder.encode(service_name, "UTF-8")
                        + "&&" + URLEncoder.encode("order_date", "UTF-8") + "=" + URLEncoder.encode(order_date, "UTF-8")
                        + "&&" + URLEncoder.encode("order_amt", "UTF-8") + "=" + URLEncoder.encode(order_amt, "UTF-8")
                        + "&&" + URLEncoder.encode("db_host", "UTF-8") + "=" + URLEncoder.encode(db_host, "UTF-8")
                        + "&&" + URLEncoder.encode("db_name", "UTF-8") + "=" + URLEncoder.encode(db_name, "UTF-8")
                        + "&&" + URLEncoder.encode("db_user", "UTF-8") + "=" + URLEncoder.encode(db_user, "UTF-8")
                        + "&&" + URLEncoder.encode("db_pass", "UTF-8") + "=" + URLEncoder.encode(db_pass, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
        }
        return result;
    }


    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String s) {

        if (s.contains("Login Failed.")) {
            dialog.setMessage("Please Enter Correct Details.");
            dialog.show();
            Toast.makeText(context, "Login Failed.!", Toast.LENGTH_LONG).show();

        } else if (s.contains("Login Successful.")) {
            dialog.setMessage("Login Successful.");
            dialog.show();
            Intent i = new Intent();
            i.setClass(context.getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);

        } else if (s.contains("Order Retrieved.")) {
//            dialog.setTitle("orders.php");
//            dialog.setMessage("Order Retrieved.");
//            dialog.show();
//            Toast.makeText(context, "Order Retrieved.", Toast.LENGTH_SHORT).show();

        } else if (s.contains("Order Inserted.")) {
            dialog.setTitle("Order Status");
            dialog.setMessage("Service Booked, Thank You.");
            dialog.show();

        } else if (s.contains("Employee Getted.")) {
//            dialog.setTitle("emp.php");
//            dialog.setMessage("E.G:- ");
//            dialog.show();
            Toast.makeText(context, "Please Wait.", Toast.LENGTH_SHORT).show();

        } else if (s.contains("No Orders Found.")) {

        } else {
//            dialog.setTitle("Error.");
//            dialog.setMessage(s);
//            dialog.show();
//            // Toast.makeText(context, "Something Wrong !", Toast.LENGTH_LONG).show();

        }
    }
}
