package com.citynote.sanky.ut_preliminary.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.citynote.sanky.ut_preliminary.Activity.MainActivity;
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

/**
 * Created by sanky on 20-01-2017.
 */

public class Login extends MainActivity {
    EditText email,pass;
    Button login,signup;
    String status,user_name,pass_word,response,message;
    JSONObject jsonObject;

    //shared preferance setting+ include sharedpreference pacakge by using "alt+enter"
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "emailkey";
    public static final String Pass = "passkey";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnLogin);
        signup = (Button) findViewById(R.id.btnLinkToRegisterScreen);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")) {
                    email.setError("Write UserName");
                }
                else if(pass.getText().toString().trim().equals("")){
                    pass.setError("Password Required");
                }

                else{
                    user_name = email.getText().toString();
                    pass_word = pass.getText().toString();
                    new loginparser().execute(user_name, pass_word);

                     Intent intent=new Intent(Login.this,MainActivity.class);
                     startActivity(intent);

                    String e  = email.getText().toString();
                    String pa  = pass.getText().toString();
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString(Email, e);
                    editor.putString(Pass, pa);
                    editor.commit();

                    String value3 = sharedpreferences.getString("emailKey", "");
                    Log.d(Email, value3);
                    String value4 = sharedpreferences.getString("passwordKey", "");
                    Log.d(Pass, value4);

                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
            }
        });

    }
    class loginparser extends AsyncTask<String,String,String> {

        ProgressDialog pdLoading = new ProgressDialog(Login.this);
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

                url = new URL("http://blessindia.in/webservice/login.php");
                httpconnection = (HttpURLConnection) url.openConnection();
                httpconnection.setRequestMethod("POST");
                httpconnection.setDoInput(true);
                httpconnection.setDoOutput(true);
                httpconnection.connect();
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                OutputStream os = httpconnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                int response_code = httpconnection.getResponseCode();
                Log.e("rescode", ""+response_code);
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

            super.onPostExecute(res);
            Log.d("showvalue","====  "+res);
            pdLoading.dismiss();



            try {
                jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                Log.e("st",status);
                message = jsonObject.getString("msg");
                Log.e("response", message);

                // Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();

                if(message.equals("Login successfully")){
                    Intent intent = new Intent(Login.this,MainActivity.class);

                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }



        }


    }

}

