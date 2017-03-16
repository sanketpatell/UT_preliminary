package com.citynote.sanky.ut_preliminary.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Login extends AppCompatActivity {
    EditText emp_id,pass;
    Button login,signup;
    String status,empl_id,pass_word,response,message,employee_id,password;
    JSONObject jsonObject;

    //shared preferance setting+ include sharedpreference pacakge by using "alt+enter"
    public static final String MyPREFERENCES = "uohmac" ;
    public static final String Status = "status_key";
    public static final String Emp_id = "em_idkey";
    public static final String Pass = "passkey";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor spt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences("MyPREFERENCES", 0);
        spt = sharedpreferences.edit();

        emp_id = (EditText) findViewById(R.id.emp_id);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnLogin);
        signup = (Button) findViewById(R.id.btnLinkToRegisterScreen);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emp_id.getText().toString().trim().equals("")) {
                    emp_id.setError("Write UserName");
                }
                else if(pass.getText().toString().trim().equals("")){
                    pass.setError("Password Required");
                }

                else{
                    empl_id = emp_id.getText().toString();
                    pass_word = pass.getText().toString();
                    new loginparser().execute(empl_id, pass_word);


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

                url = new URL("http://blessindia.in/webservice/login_uohmac.php");
                httpconnection = (HttpURLConnection) url.openConnection();
                httpconnection.setRequestMethod("POST");
                httpconnection.setDoInput(true);
                httpconnection.setDoOutput(true);
                httpconnection.connect();
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("employee_id", params[0])
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
                employee_id = jsonObject.getString("employee_id");
                password = jsonObject.getString("password");

                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();

                if(status.equals("1")){


                    Intent intent = new Intent(Login.this,MainActivity.class);
                    spt.putString("status", status);
                    spt.putString("success", "succesfully");
                    //  editor.putString(Pass, pa);
                    spt.putString("employee_id", employee_id);
                    spt.putString("password", password);

                    spt.commit();

                    startActivity(intent);
                    Log.e("status_preference", status);
                }else{
                    Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }



        }


    }

}

