package com.getprofessional.BackGrounds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;
import com.getprofessional.Activity.LoginActivity;
import com.getprofessional.Activity.MainActivity;
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

public class RegisterUser extends AsyncTask <String, Void,String> {

    private AlertDialog dialog;
    private Context context;
    private static String db_host = "HostUrl";
    private static String db_name = "UserName";
    private static String db_user = "UserName";
    private static String db_pass = "password";
    public Boolean login = false;

    public RegisterUser(Context context)
    {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String type = voids[0];

        String login_url = "http://www.getprofessional.in/login.php";
        String register_url = "http://www.getprofessional.in/register.php";

        if (type.equals("register")){

            try {
                String name = voids[1];
                String number = voids[2];
                String email = voids[3];
                String address = voids[4];
                String password = voids[5];

                URL url = new URL(register_url);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&&" + URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8")
                        + "&&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8")
                        + "&&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
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

    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }
    @Override

    protected void onPostExecute(String s) {
        dialog.setMessage(s);
        dialog.show();
        if(s.contains("Registration Successful")) {
            Intent i = new Intent();
            i.setClass(context.getApplicationContext(), LoginActivity.class);
            context.startActivity(i);
            Toast.makeText(context.getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
        }
    }
}
