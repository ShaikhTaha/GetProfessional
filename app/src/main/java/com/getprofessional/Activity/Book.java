package com.getprofessional.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.getprofessional.R;
import com.getprofessional.Services.BookingHelper;
import com.getprofessional.models.GP_BG_Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class Book extends AppCompatActivity {

    static final int dialogD_id = 0, dialogT_id = 1;
    Button btn_date, btn_time;
    Button btnCnfBkg;
    int dayX, monthX, yearX, hourX, minX;
    String date = null, time = null;
    String serviceName, orderAmt, cust_id;
    Boolean bookingStatus = false;

    private DatePickerDialog.OnDateSetListener datePicListner
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yearX = year;
            monthX = month + 1;
            dayX = dayOfMonth;
            Toast.makeText(Book.this, "You Selected: " + dayX + "/" + monthX + "/" + yearX, Toast.LENGTH_SHORT).show();
            btn_date.setText(String.valueOf(dayX + "/" + monthX + "/" + yearX));
            date = String.valueOf(" " + dayX + "-" + monthX + "-" + yearX + " / ");
        }
    };
    private TimePickerDialog.OnTimeSetListener timePicListner
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourX = hourOfDay;
            minX = minute;
            Toast.makeText(Book.this, "You Selected: " + hourX + ":" + minX, Toast.LENGTH_SHORT).show();
            btn_time.setText(String.valueOf(hourX + ":" + minX));
            time = String.valueOf(hourX + ":" + minX);
        }
    };

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        serviceName = getIntent().getStringExtra("ServiceName");
        orderAmt = getIntent().getStringExtra("OrderAmt");
        bookingStatus = getIntent().getExtras().getBoolean("BookingStatus");

        if (bookingStatus) {
            Intent i = new Intent();
            i.setClass(this, MainActivity.class);
            finish();
            this.startActivity(i);
        } else {

            btnCnfBkg = findViewById(R.id.btn_confirm);
            GP_BG_Data DB = new GP_BG_Data(this);
            cust_id = DB.getCustID();

            Calendar calendar = Calendar.getInstance();
            dayX = calendar.get(Calendar.DAY_OF_MONTH);
            monthX = calendar.get(Calendar.MONTH);
            yearX = calendar.get(Calendar.YEAR);

            hourX = parseInt(new SimpleDateFormat("hh").format(Calendar.getInstance().getTime()));
            minX = parseInt(new SimpleDateFormat("mm").format(Calendar.getInstance().getTime()));

            showDateDlg();
            showTimeDlg();
        }
    }

    public void btnConfirmBooking(View view) {
        String dateTime = date + time;

        if (date != null && time != null) {
            btnCnfBkg.setEnabled(false);
            BookingHelper bookingHelper = new BookingHelper(this);
            bookingHelper.updateOrderToMysqlDB(serviceName, orderAmt, cust_id, dateTime);

        } else {
            Toast.makeText(this, "Please Select Date And Time For Your Service.", Toast.LENGTH_SHORT).show();
        }

    }

//    public void doUpdateOrders() {
//        GP_BG_Data bgData = new GP_BG_Data(this);
//        Cursor cursor = bgData.getUpdatedOrders();
//        if (cursor.moveToFirst()) {
//
//            UserDetails userDetails = new UserDetails(this);
//            userDetails.execute("update_odr", cursor.getString(2),
//                    cursor.getString(3), cursor.getString(4),
//                    cursor.getString(5), cursor.getString(6),
//                    cursor.getString(7));
//            Toast.makeText(this, "Order Updated On Server 2.", Toast.LENGTH_SHORT).show();
//            bgData.deleteUpdateOrder();
//            btnCnfBkg.setEnabled(false);
//            Intent i = new Intent();
//            i.setClass(this, MainActivity.class);
//            this.startActivity(i);
//        } else {
//            doUpdateOrders();
//        }
//    }

    public void showDateDlg() {
        btn_date = findViewById(R.id.btn_date);
        btn_date.setText(dayX + "/" + monthX + "/" + yearX);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogD_id);
            }
        });
    }

    public void showTimeDlg() {
        btn_time = findViewById(R.id.btn_time);
        btn_time.setText(hourX + ":" + minX);
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogT_id);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == dialogD_id) {
            return new DatePickerDialog(this, datePicListner, yearX, monthX, dayX);
        } else if (id == dialogT_id) {
            return new TimePickerDialog(Book.this, timePicListner, hourX, minX, false);
        } else {
            return null;
        }
    }

}
