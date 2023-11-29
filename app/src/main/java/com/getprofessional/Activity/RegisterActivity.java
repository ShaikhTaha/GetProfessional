package com.getprofessional.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.getprofessional.BackGrounds.RegisterUser;
import com.getprofessional.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            // "(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{6,15}" +               //at least 4 characters
            "$");
    EditText name, number, email, address, password;
    Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.rf_tv_name);
        number = findViewById(R.id.rf_tv_number);
        email = findViewById(R.id.rf_tv_email);
        address = findViewById(R.id.rf_tv_address);
        password = findViewById(R.id.rf_tv_password);
        buttonCreateAccount = findViewById(R.id.create_account_btn);

    }

    public void btnCreateAccount(View view) {
        if (checkValidity()) {
            buttonCreateAccount.setEnabled(false);
            String str_name = name.getText().toString();
            String str_number = number.getText().toString();
            String str_email = email.getText().toString();
            String str_address = address.getText().toString();
            String str_password = password.getText().toString();
            String type = "register";
            RegisterUser background = new RegisterUser(this);
            background.execute(type, str_name, str_number, str_email, str_address, str_password);
        }
    }

    private boolean validateName() {
        String strname = name.getText().toString();
        String strNN = strname.replaceAll("[^0-9]+", "");//Retrieve all number form name
        String strSymb = strname.replaceAll("[^-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,./]+", "");//Retrieve all symbols form name


        if (strname.isEmpty()) {
            name.setError("Please Enter Your Name");
            return false;
        } else if (strname.length() > 25 || strname.length() < 8) {
            if (strname.length() < 8) {
                name.setError("Name Is Too Short");
            } else {
                name.setError("Name Is Too Long");
            }
            return false;
        } else if (strNN.length() != 0) {
            String strCheckNum = strNN.substring(0, 1);//Retrieve the first single number from strNN(above variable)
            if (strCheckNum.matches("[0-9]*")) {
                name.setError("Name Should Not Contain Any Numbers.");
                return false;
            } else {
                return true;
            }
        } else if (strSymb.length() != 0) {
            String strCheckSymb = strSymb.substring(0, 1);//Retrieve the first single symbol from strSymb(above variable)
            if (strCheckSymb.matches("[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,./]")) {
                name.setError("Name Should Not Contain Any Special Characters.");
                return false;
            } else {
                return true;
            }
        } else {
            if (strname.matches("[a-z][A-Z]*")) {
                name.setError("Name Should Contains Characters Only.");
                return false;
            } else {
                name.setError(null);
                return true;
            }
        }

    }

    private boolean validateNumber() {
        String number = this.number.getText().toString();
        Pattern p = Pattern.compile("(7/8/9)?[7-9][0-9]{9}");
        Matcher m = p.matcher(number);

        if (number.isEmpty()) {
            this.number.setError("Please Enter Your Number");
            return false;
        } else if (!(m.find() && m.group().equals(number))) {
            this.number.setError("Please Enter Correct Number.");
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String emailInput = email.getText().toString();
        int emailLength = emailInput.length();

        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        } else if (emailInput.matches("[A-Z]*")) {
            email.setError("Contains Capital Letter, Invalid Email Address");
            return false;
        } else {
            for (int i = 0; i < emailLength; i++) {
                if (Character.isUpperCase(emailInput.charAt(i))) {

                    char UCL = emailInput.charAt(i);
                    email.setError("Contains Capital Letters, Invalid Email.");
                    return false;
                }
            }
            email.setError(null);
            return true;
        }
    }

    public boolean validateAddress() {
        String address = this.address.getText().toString();

        if (address.isEmpty()) {
            this.address.setError("Field can't be empty");
            return false;
        } else if (address.length() > 60 || address.length() < 8) {
            if (address.length() < 8) {
                this.address.setError("Please Enter Correct Address \neg: Near Imaad Masjid, Raoshan Gate, Aurangabad.");
            } else {
                this.address.setError("Too Long");
            }
            return false;
        } else {
            this.address.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = this.password.getText().toString();

        if (password.isEmpty()) {
            this.password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            this.password.setError("Password Should Contain At Least 1 Upper Case,1 Lower Case And 1 Digit Without Any Space. Min Length 6 / Max 18");
            return false;
        } else {
            this.password.setError(null);
            return true;
        }
    }

    public boolean checkValidity() {

        if (validateName() && validateNumber() && validateEmail()) {
            if(validateAddress() && validatePassword()){
                return true;
            }else{
                return false;
            }

        } else {
            return false;
        }
    }
}
