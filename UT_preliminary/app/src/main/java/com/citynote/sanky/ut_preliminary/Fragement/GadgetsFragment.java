package com.citynote.sanky.ut_preliminary.Fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import javax.net.ssl.HttpsURLConnection;

public class GadgetsFragment extends Fragment {

    //Declaring EditText
    String editTextEmail ="sanketleader@gmail.com";
    private EditText editText_gadgets_requirement;
    private EditText editText_gadgets_description;

    String status, gadgets_requirement,gadgets_description, Leavereason, message, response;
    JSONObject jsonObject;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor spt;
    String Employee_id="",Password="";




    //Send button
    private Button buttonSend,buttonclear;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View GadgetsFragmentView = inflater.inflate(R.layout.fragment_gadgets, container, false);

        //Initializing the views
        //editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editText_gadgets_requirement = (EditText) GadgetsFragmentView.findViewById(R.id.gadgets_requirement);
        editText_gadgets_description = (EditText) GadgetsFragmentView.findViewById(R.id.gadgets_description);



        sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", 0);
        spt = sharedpreferences.edit();

        Employee_id =sharedpreferences.getString("employee_id", null);
        Password=sharedpreferences.getString("password",null);




        buttonSend = (Button) GadgetsFragmentView.findViewById(R.id.gadgets_submit);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText_gadgets_requirement.getText().toString().trim().equals("")) {
                    editText_gadgets_requirement.setError("Write Gadgets Name");
                } else if (editText_gadgets_description.getText().toString().trim().equals("")) {
                    editText_gadgets_description.setError("Write Gadgets Description");
                }else {

                    gadgets_requirement = editText_gadgets_requirement.getText().toString().trim();
                    gadgets_description = editText_gadgets_description.getText().toString().trim();

                    new gedgetparser().execute(Employee_id,Password,gadgets_requirement, gadgets_description);

                    Log.d("Employee_id", "====  " + Employee_id);
                    Log.d("Password", "====  " + Password );
                    Log.d("gadgets_requirement", "====  " + gadgets_requirement );
                    Log.d("gadgets_description", "====  " + gadgets_description );


                    editText_gadgets_requirement.setText("");
                    editText_gadgets_description.setText("");

                }
            }
        });

        buttonclear = (Button) GadgetsFragmentView.findViewById(R.id.gadgets_clear);
        buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_gadgets_requirement.setText("");
                editText_gadgets_description.setText("");

            }
        });
       /* //Adding click listener
        buttonSend.setOnClickListener((View.OnClickListener) GadgetsFragmentView);*/

        return GadgetsFragmentView;
    }
    class gedgetparser extends AsyncTask<String,String,String> {


        URL url = null;
        HttpURLConnection httpconnection;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            try {

                url = new URL("http://blessindia.in/webservice/gedgets.php");
                httpconnection = (HttpURLConnection) url.openConnection();
                httpconnection.setRequestMethod("POST");
                httpconnection.setDoInput(true);
                httpconnection.setDoOutput(true);
                httpconnection.connect();
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("employee_id", params[0]);
                builder.appendQueryParameter("password", params[1]);
                builder.appendQueryParameter("r_gedgets", params[2]);
                builder.appendQueryParameter("s_gedgets", params[3]);


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



            try {
                jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                Log.e("st", status);
                message = jsonObject.getString("msg");
                Log.e("response", message);

                //  Toast.makeText(GadgetsFragment.this, message, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
            }


        }}

}

