package com.getprofessional.BackGrounds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.getprofessional.Activity.Book;
import com.getprofessional.Activity.MainActivity;
import com.getprofessional.Fragments.MainFragment;
import com.getprofessional.R;
import com.getprofessional.Services.BookingHelper;

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

public class UpdateOrders extends AsyncTask<String, Void, String> {

    private static String db_host = "HostUrl";
    private static String db_name = "UserName";
    private static String db_user = "UserName";
    private static String db_pass = "password";
    private AlertDialog dialog;
    private Context context;

    public UpdateOrders(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String type = voids[0];
        String service_name = voids[1];
        String order_amt = voids[2];
        String emp_id = voids[3];
        String cust_id = voids[4];
        String order_date = voids[5];
        String[] empImageName = BookingHelper.getEmployee();

        String update_url = "http://www.getprofessional.in/updodr.php";

        if (type.equals("UpdateOrders")) {

            try {

                URL url = new URL(update_url);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("cust_id", "UTF-8") + "=" + URLEncoder.encode(cust_id, "UTF-8")
                        + "&&" + URLEncoder.encode("emp_id", "UTF-8") + "=" + URLEncoder.encode(emp_id, "UTF-8")
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
        dialog.setTitle("Order Status.");
    }

    @Override
    protected void onPostExecute(String s) {

        if (s.contains("Order Inserted.")) {
            Toast.makeText(context, "Service Booked, Thank You.", Toast.LENGTH_LONG).show();
            Intent i = new Intent();
            i.setClass(context.getApplicationContext(), Book.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            i.putExtra("BookingStatus", true);
            context.startActivity(i);


        } else if (s.contains("No Orders Found.")) {

        } else {
//            dialog.setTitle("Error.");
//            dialog.setMessage(s);
//            dialog.show();
//            // Toast.makeText(context, "Something Wrong !", Toast.LENGTH_LONG).show();

        }
    }
}
