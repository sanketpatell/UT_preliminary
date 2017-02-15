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

import javax.net.ssl.HttpsURLConnection;

public class Signup extends AppCompatActivity {
    EditText fname,lname,mobile,email,pass;
    Button signup,login;
    String status,user_name,pass_word,first_name,last_name,user_mobile,response,message;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fname = (EditText) findViewById(R.id.first_name);
        lname = (EditText) findViewById(R.id.last_name);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnLinkToLoginScreen);
        signup = (Button) findViewById(R.id.btnRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = fname.getText().toString();
                last_name = lname.getText().toString();
                user_mobile = mobile.getText().toString();
                user_name = email.getText().toString();
                pass_word = pass.getText().toString();
                new signupparser().execute(first_name,last_name,user_mobile,user_name, pass_word);
            }
        });

    }
    class signupparser extends AsyncTask<String,String,String> {

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

                url = new URL("http://blessindia.in/webservice/register.php");
                httpconnection = (HttpURLConnection) url.openConnection();
                httpconnection.setRequestMethod("POST");
                httpconnection.setDoInput(true);
                httpconnection.setDoOutput(true);
                httpconnection.connect();
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("first_name", params[0]);
                builder.appendQueryParameter("last_name",params[1]);
                builder.appendQueryParameter("mobile", params[2]);
                builder.appendQueryParameter("email", params[3])
                        .appendQueryParameter("password", params[4]);
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
                Log.e("response", res);

                // Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();

                       /* Intent intent=new Intent(MainHome.this,ma.class);
                        startActivity(intent);*/
                Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
            }


        }}


}
