package com.citynote.sanky.ut_preliminary.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class LeaveActivity extends AppCompatActivity {

    //Declaring EditText
    String editTextEmail = "sanketleader@gmail.com";
    String date;


    String status, Startdate, Enddate, Leavereason, message, response;
    JSONObject jsonObject;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor spt;
    String Employee_id="",Password="";

    private EditText editText_leave_reason;

    //Send button
    private Button buttonSend, buttonclear;

    //date picker
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView tvfrom, tvto;
    private String tvfrom_db, tvto_db;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        //Initializing the views
        //editTextEmail = (EditText) findViewById(R.id.editTextEmail);


        editText_leave_reason = (EditText) findViewById(R.id.leave_reason);

        buttonSend = (Button) findViewById(R.id.leave_submit);
        buttonclear = (Button) findViewById(R.id.leave_clear);

        sharedpreferences = getSharedPreferences("MyPREFERENCES", 0);
        spt = sharedpreferences.edit();

        Employee_id =sharedpreferences.getString("employee_id", null);
        Password=sharedpreferences.getString("password",null);

        Log.d("employee_id","1   "+ Employee_id);
        Log.d("password","2 "+ Password);
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
                editText_leave_reason.setText("");

            }
        });
        //Adding click listener
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText_leave_reason.getText().toString().trim().equals("")) {
                    editText_leave_reason.setError("Write Leave Reason");


                } else {

                    Startdate = tvfrom.getText().toString().trim();
                    Enddate = tvto.getText().toString().trim();
                    Leavereason = editText_leave_reason.getText().toString();
                    /*for (int i=Startdate.length();i>0;i--)
                    {
                        Startdate.charAt(i);

                    }*/
                    new leveparser().execute(Employee_id,Password,Startdate, Enddate, Leavereason);

                    Log.d("Employee_id", "====  " + Employee_id);
                    Log.d("Password", "====  " + Password);
                    Log.d("Startdate", "====  " + Startdate );
                    Log.d("Enddate", "====  " + Enddate );
                    Log.d("Leavereason", "====  " + Leavereason );


                    editText_leave_reason.setText("");
                }
            }
        });

    }

   /*//Getting content for email
        String email = editTextEmail.toString().trim();
        //String subject = "from"+tvfrom.getText().toString().trim()+"To"+ tvto.getText().toString().trim();
        String subject = "Leave Application";
        String message = "I want Leave from"+""+tvfrom.getText().toString().trim()+""+"To"+ ""+tvto.getText().toString().trim()+""+"Because"+""+editText_gadgets_description.getText().toString().trim()+""+"Thanks for support";


        Log.d("Subject", "====  " + subject+message);
        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();*/





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
        tvfrom.setText(new StringBuilder().append(year).append("/")
                .append(month).append("/").append(day));

       /* tvfrom_db.setText(new StringBuilder().append(year).append("/")
                .append(month).append("/").append(day));
*/
    }
    private void showDateto(int year, int month, int day) {
        tvto.setText(new StringBuilder().append(year).append("/")
                .append(month).append("/").append(day));

       /* tvto_db.setText(new StringBuilder().append(year).append("/")
                .append(month).append("/").append(day));
*/
    }

    class leveparser extends AsyncTask<String,String,String> {

        ProgressDialog pdLoading = new ProgressDialog(LeaveActivity.this);
        URL url = null;
        HttpURLConnection httpconnection;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                url = new URL("http://blessindia.in/webservice/leave_request.php");
                httpconnection = (HttpURLConnection) url.openConnection();
                httpconnection.setRequestMethod("POST");
                httpconnection.setDoInput(true);
                httpconnection.setDoOutput(true);
                httpconnection.connect();
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("employee_id", params[0]);
                builder.appendQueryParameter("password", params[1]);
                builder.appendQueryParameter("start_date", params[2]);
                builder.appendQueryParameter("end_date", params[3]);
                builder.appendQueryParameter("reason", params[4]);

                String query = builder.build().getEncodedQuery();

                OutputStream os = httpconnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                int response_code = httpconnection.getResponseCode();
                Log.e("rescode", "" + response_code);
                if (response_code == HttpURLConnection.HTTP_OK) {

                    if (response_code == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpconnection.getInputStream()));
                        response = br.readLine();



                    } else {
                        response = "Error ";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            // Log.e("reslogin", response);

            return response;


        }
        protected void onPostExecute(String res) {
            Log.d("showvalue", "====  " + res);
            pdLoading.dismiss();


            try {
                jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                Log.e("st", status);
                message = jsonObject.getString("msg");
                Log.e("response", message);

                Toast.makeText(LeaveActivity.this, message, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
            }


        }}



}