package com.citynote.sanky.ut_preliminary.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citynote.sanky.ut_preliminary.Email.SendMail;
import com.citynote.sanky.ut_preliminary.R;

import java.util.Calendar;

public class LeaveActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditText
    String editTextEmail ="sanketleader@gmail.com";
   String date;


    private EditText editText_gadgets_description;

    //Send button
    private Button buttonSend,buttonclear;

    //date picker
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView tvfrom,tvto;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        //Initializing the views
        //editTextEmail = (EditText) findViewById(R.id.editTextEmail);


        editText_gadgets_description = (EditText) findViewById(R.id.leave_reason);

        buttonSend = (Button) findViewById(R.id.leave_submit);
        buttonclear = (Button) findViewById(R.id.leave_clear);



        // Date picker
        tvfrom = (TextView) findViewById(R.id.tvfrom);
        tvto = (TextView) findViewById(R.id.tvto);

       // date =  tvfrom.getText().toString().trim() +" "+tvto.getText().toString().trim();
       // date =tvfrom.toString()+tvto.toString();
      //  Log.d("datepicker", "====  " + date);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        showDatefrom(year, month + 1, day);
        showDateto(year, month + 1, day);

        buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editText_gadgets_requirement.setText("");
                editText_gadgets_description.setText("");

            }
        });
        //Adding click listener
        buttonSend.setOnClickListener(this);


    }


    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.toString().trim();
        //String subject = "from"+tvfrom.getText().toString().trim()+"To"+ tvto.getText().toString().trim();
        String subject = "Leave Application";
        String message = "I want Leave from"+""+tvfrom.getText().toString().trim()+""+"To"+ ""+tvto.getText().toString().trim()+""+"Because"+""+editText_gadgets_description.getText().toString().trim()+""+"Thanks for support";


        Log.d("Subject", "====  " + subject+message);
        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }


    //Date picker

    @SuppressWarnings("deprecation")
    public void from(View view) {
        showDialog(888);
        Toast.makeText(getApplicationContext(), "ca",Toast.LENGTH_SHORT).show();
    }
    @SuppressWarnings("deprecation")
    public void to(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub

        if (id == 888) {
            return new DatePickerDialog(this,myDateListenerfrom, year, month, day);
        }
        if (id == 999) {
            return new DatePickerDialog(this,myDateListenerto, year, month, day);
        }
        return null;
    }



    private DatePickerDialog.OnDateSetListener myDateListenerfrom = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day

                    showDatefrom(arg1, arg2 + 1, arg3);

                }
            };

    private DatePickerDialog.OnDateSetListener myDateListenerto = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day

                    showDateto(arg1, arg2+1, arg3);
                }
            };


    private void showDatefrom(int year, int month, int day) {
        tvfrom.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
    private void showDateto(int year, int month, int day) {
        tvto.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));

    }





}