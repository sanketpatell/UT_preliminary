package com.citynote.sanky.ut_preliminary.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.citynote.sanky.ut_preliminary.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class Signup extends AppCompatActivity {
    EditText emp_id,fname,lname,mobile,email,pass,re_pass;
    Button signup,login;
    String status,Employee_id,Email,Pass_word,Re_pass_word,First_name,Last_name,User_mobile,response,message;
    JSONObject jsonObject;
//String registerurl = "http://blessindia.in/webservice/emp_registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emp_id = (EditText) findViewById(R.id.employee_id);
        fname = (EditText) findViewById(R.id.first_name);
        lname = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.mobile);
        pass = (EditText) findViewById(R.id.password);
        re_pass = (EditText) findViewById(R.id.re_password);
        login = (Button) findViewById(R.id.btnLinkToLoginScreen);
        signup = (Button) findViewById(R.id.btnRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emp_id.getText().toString().trim().equals("")) {
                    emp_id.setError("Write User Employee ID");
                } else if (fname.getText().toString().trim().equals("")) {
                    fname.setError("Write First Name");
                } else if (lname.getText().toString().trim().equals("")) {
                    lname.setError("Write Last Name");
                } else if (email.getText().toString().trim().equals("")) {
                    email.setError("Write Email ID");
                } else if (!email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    email.setError("Enter Valid Email ID");
                    //  Toast.makeText(Signup.this,"Enter Valid Email id",Toast.LENGTH_SHORT).show();
                } else if (mobile.getText().toString().trim().equals("")) {
                    mobile.setError("Write Phone Number");
                } else if (mobile.getText().toString().length() != 10) {
                    mobile.setError("Mobile Number must be ten digit");
                } else if (pass.getText().toString().trim().equals("")) {
                    pass.setError("Write Password");
                } else if (re_pass.getText().toString().trim().equals("")) {
                    re_pass.setError("Write Re-Password");
                } else if (!pass.getText().toString().trim().equals(re_pass.getText().toString().trim())) {
                    // show message password not match
                    pass.setError("Password not Matched");
                    re_pass.setError("Password not Matched");
                    //     Toast.makeText(getBaseContext(), "Password not Matched" , Toast.LENGTH_SHORT ).show();
                } else {

                    Employee_id = emp_id.getText().toString();
                    First_name = fname.getText().toString();
                    Last_name = lname.getText().toString();
                    Email = email.getText().toString();
                    User_mobile = mobile.getText().toString();
                    Pass_word = pass.getText().toString();

                    new signupparser().execute(Employee_id, First_name, Last_name,Email, User_mobile,Pass_word);
                }
            }
        });

    } class signupparser extends AsyncTask<String,String,String> {

        ProgressDialog pdLoading = new ProgressDialog(Signup.this);
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

                url = new URL("http://blessindia.in/webservice/emp_registration.php");
                httpconnection = (HttpURLConnection) url.openConnection();
                httpconnection.setRequestMethod("POST");
                httpconnection.setDoInput(true);
                httpconnection.setDoOutput(true);
                httpconnection.connect();
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("employee_id", params[0]);
                builder.appendQueryParameter("first_name",params[1]);
                builder.appendQueryParameter("last_name", params[2]);
                builder.appendQueryParameter("email", params[3]);
                builder.appendQueryParameter("mobile", params[4])
                        .appendQueryParameter("password", params[5]);
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

                Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();

                if(status.equals("1")){
                    Intent intent = new Intent(Signup.this,Login.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Signup.this,message,Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }}


}

   /* class signupparser extends AsyncTask<String,String,String> {

        ProgressDialog pdLoading = new ProgressDialog(Signup.this);
        URL url = null;
        HttpURLConnection httpconnection;
        RegisterUserClass RegisterUserClass =new RegisterUserClass();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            HashMap<String, String> data = new HashMap<>();

            data.put("employee_id", params[0]);
            data.put("first_name", params[1]);
            data.put("last_name", params[2]);
            data.put("email", params[3]);
            data.put("mobile", params[4]);
            data.put("password", params[5]);

            Log.d("employee_id new", "====  " + params[0]);
            Log.d("first_name new", "====  " + params[1]);
            Log.d("last_name new", "====  " + params[2]);
            Log.d("email new", "====  " + params[3]);
            Log.d("mobile new", "====  " + params[4]);
            Log.d("password new", "====  " + params[5]);


            try {
                result = RegisterUserClass.sendPostRequest(registerurl, data);

                Log.d("result new", "====  " + result);

            } catch (Exception e){
                Log.d("Exception new", "====  " + e.toString());
        }

            return result;
        }
        protected void onPostExecute(String res) {

            pdLoading.dismiss();
            Log.d("showvalue", "====  " + res);

            try {
                jsonObject = new JSONObject(res);
                status = jsonObject.getString("status");
                Log.e("st", status);
                message = jsonObject.getString("msg");
                Log.e("response", message);
                //Log.e("response", res);

                // Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
                if(message.equals("Registered")){
                    Intent intent=new Intent(Signup.this,Login.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



         }



    }


}
*/