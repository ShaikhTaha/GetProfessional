package com.getprofessional.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.getprofessional.Services.BookingHelper.context;
import com.getprofessional.BackGrounds.UserDetails;
import com.getprofessional.R;
import com.getprofessional.models.GP_BG_Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            // "(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{6,15}" +               //at least 4 characters
            "$");
    GP_BG_Data bgData;
    EditText pas, usr;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bgData = new GP_BG_Data(this);
        // bgData.name_email();
        usr = (EditText) findViewById(R.id.li_et_number);
        pas = (EditText) findViewById(R.id.li_et_password);
        loginBtn = (Button) findViewById(R.id.login_btn);


    }

    public void onLoginBtn(View view) {

        final String user = usr.getText().toString();
        final String pass = pas.getText().toString();
        final String type = "login";

        if(user.contains("Admin")&&pass.contains("admin123")){
            Toast.makeText(this, "Logged In As Admin.", Toast.LENGTH_LONG).show();
            Intent i = new Intent();
            i.setClass(LoginActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }else if(checkValidatoin()) {
            UserDetails userDetails = new UserDetails(this);
            userDetails.execute("login", user, pass);
            bgData.insertUser(user, pass);
        } else {
            Toast.makeText(this, "Please Check Your Details.", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnNewAccount(View view) {
        Intent i = new Intent();
        i.setClass(this, RegisterActivity.class);
        this.startActivity(i);
    }


    private boolean validateEmail() {
        String emailInput = usr.getText().toString();

        if (emailInput.isEmpty()) {
            usr.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            usr.setError("Please Enter Correct Number Or Email.");
            return false;
        } else {
            usr.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String number = usr.getText().toString();
        Pattern p = Pattern.compile("(7/8/9)?[7-9][0-9]{9}");
        Matcher m = p.matcher(number);
        if (!(m.find() && m.group().equals(number))) {
            usr.setError("Please Enter Correct Number.");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = pas.getText().toString();

        if(password.isEmpty()) {
            pas.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            pas.setError("Incorrect Password.");
            return false;
        } else {
            pas.setError(null);
            return true;
        }
    }

    public boolean checkValidatoin() {
        if (usr.length() <= 12) {
            return validateUsername() && validatePassword();
        } else {
            return validateEmail() && validatePassword();
        }
    }

}
