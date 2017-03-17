package com.citynote.sanky.ut_preliminary.Fragement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.citynote.sanky.ut_preliminary.Attandance_model.DBHelper;
import com.citynote.sanky.ut_preliminary.CircleTransform;
import com.citynote.sanky.ut_preliminary.Employees_Details.Utils.Constandapi;
import com.citynote.sanky.ut_preliminary.Employees_Details.api.Demoapi;
import com.citynote.sanky.ut_preliminary.R;
import com.citynote.sanky.ut_preliminary.Attandance_model.AppController;
import com.citynote.sanky.ut_preliminary.Attandance_model.CustomListAdapter;
import com.citynote.sanky.ut_preliminary.Attandance_model.Employee;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit.Callback;

public class EmployessFragment extends Fragment {
        // Log tag
        TextView another_emp_id,another_emp_fname,another_emp_lname,another_emp_email,another_emp_mobile,another_emp_emg_mobile,another_emp_dob,another_emp_bg,another_emp_jod,another_emp_designation,another_emp_work_exp,another_emp_technical_skill;

        Employee employee;
        DBHelper dbHelper;


        ImageView ThumbNail;

        private ProgressDialog pDialog;
        private List<Employee> employeeList = new ArrayList<Employee>();
        private ListView listView;
        private CustomListAdapter adapter;
        Dialog dialog;


        SharedPreferences sharedpreferences;
        SharedPreferences.Editor spt;
        String Employee_id="",Password="";

    JSONObject jsonObject;
    JSONArray jsonarray;

    String emloyee_employee_id,emloyee_first_name,emloyee_last_name,emloyee_email,emloyee_mobile,emloyee_emer_contact,emloyee_dob,emloyee_blood_groop,emloyee_joining_date,emloyee_designation,emloyee_work_exp,emloyee_technical_skill,Image,asd,response;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View EmployessFragmentView = inflater.inflate(R.layout.fragment_attandance, container, false);


            sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", 0);
            spt = sharedpreferences.edit();

            Employee_id =sharedpreferences.getString("employee_id", null);
            Password=sharedpreferences.getString("password",null);


            listView = (ListView) EmployessFragmentView.findViewById(R.id.list);


            dbHelper = new DBHelper(getActivity());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                  //  Toast.makeText(getActivity(), "Stop Clicking me", Toast.LENGTH_SHORT).show();


                    Display_Dialog(position);


                }
            });
            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String pos = employeeList.get(position).getemloyee_employee_id();
                    Log.e("Idddd", pos);

                    Display_Dialog(position);

                }
            });*/

            new allemployeeparser().execute(Employee_id,Password);
            return EmployessFragmentView;

        }

    class allemployeeparser extends AsyncTask<String,String,String> {

        URL url = null;
        HttpURLConnection httpconnection;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            try {


                url = new URL("http://blessindia.in/webservice/all_employee_details.php");
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
                        Log.d("elseresooo","////   "+response.toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("exection","***  "+e.toString());

            }
            // Log.e("reslogin", response);

            return response;


        }

        protected void onPostExecute(String res) {

            super.onPostExecute(res);
            Log.d("showvalueresponc", "====  " + res);
           // pdLoading.dismiss();

            try {
                jsonarray = new JSONArray(response);

                for (int i = 0; i < jsonarray.length(); i++) {
                        jsonObject = jsonarray.getJSONObject(i);
                        employee = new Employee();

                        employee.setemloyee_employee_id(jsonObject.getString("employee_id"));
                        employee.setemloyee_first_name(jsonObject.getString("first_name"));
                        employee.setemloyee_last_name(jsonObject.getString("last_name"));
                        employee.setemloyee_email(jsonObject.getString("email"));
                        employee.setemloyee_mobile(jsonObject.getString("mobile"));
                        employee.setemloyee_emer_contact(jsonObject.getString("emer_contact"));
                        employee.setemloyee_dob(jsonObject.getString("dob"));
                        employee.setemloyee_blood_groop(jsonObject.getString("blood_groop"));
                        employee.setemloyee_joining_date(jsonObject.getString("joining_date"));
                        employee.setemloyee_designation(jsonObject.getString("designation"));
                        employee.setemloyee_work_exp(jsonObject.getString("work_exp"));
                        employee.setemloyee_technical_skill(jsonObject.getString("technical_skill"));
                        employee.setemloyee_Image(jsonObject.getString("image"));


                        employeeList.add(employee);
                        Log.d("emloyee_first_name", "====  " +(employeeList.get(i).getemloyee_first_name()));
                        Log.d("emloyee_designation", "====  " + (employeeList.get(i).getemloyee_designation()));
                        Log.d("emloyee_email", "====  " + (employeeList.get(i).getemloyee_email()));
                        Log.d("emloyee_mobile", "====  " + (employeeList.get(i).getemloyee_mobile()));

                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            adapter = new CustomListAdapter(getActivity(), employeeList);
            listView.setAdapter(adapter);

        }
    }
        public void Display_Dialog(int position)
        {
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.fragment_anotheruserdetails);
            dialog.setTitle("Custom Dialog");

            ThumbNail = (ImageView) dialog.findViewById(R.id.another_user_image);
            another_emp_id = (TextView) dialog.findViewById(R.id.another_emp_id);
            another_emp_fname = (TextView) dialog.findViewById(R.id.another_emp_fname);
            another_emp_lname = (TextView) dialog.findViewById(R.id.another_emp_lname);
            another_emp_email = (TextView) dialog.findViewById(R.id.another_emp_email);
            another_emp_mobile = (TextView) dialog.findViewById(R.id.another_emp_mobile);
            another_emp_emg_mobile = (TextView) dialog.findViewById(R.id.another_emp_emg_mobile);
            another_emp_dob = (TextView) dialog.findViewById(R.id.another_emp_dob);
            another_emp_bg = (TextView) dialog.findViewById(R.id.another_emp_bg);
            another_emp_jod = (TextView) dialog.findViewById(R.id.another_emp_jod);
            another_emp_designation = (TextView) dialog.findViewById(R.id.another_emp_designation);
            another_emp_work_exp = (TextView) dialog.findViewById(R.id.another_emp_work_exp);
            another_emp_technical_skill = (TextView) dialog.findViewById(R.id.another_emp_technical_skill);


            //if(list.equals(position)) {


            another_emp_id.setText((employeeList.get(position).getemloyee_employee_id()));
            another_emp_fname.setText(employeeList.get(position).getemloyee_first_name());
            another_emp_lname.setText(employeeList.get(position).getemloyee_last_name());
            another_emp_email.setText(employeeList.get(position).getemloyee_email());
            another_emp_mobile.setText(employeeList.get(position).getemloyee_mobile());
            another_emp_emg_mobile.setText(employeeList.get(position).getemloyee_emer_contact());
            another_emp_dob.setText(employeeList.get(position).getemloyee_dob());
            another_emp_bg.setText(employeeList.get(position).getemloyee_blood_groop());
            another_emp_jod.setText(employeeList.get(position).getemloyee_joining_date());
            another_emp_designation.setText(employeeList.get(position).getemloyee_designation());
            another_emp_work_exp.setText(employeeList.get(position).getemloyee_work_exp());
            another_emp_technical_skill.setText(employeeList.get(position).getemloyee_technical_skill());
            Picasso.with(getActivity()).load(employeeList.get(position).getemloyee_Image()).into(ThumbNail);



            Log.d("emp_id", employeeList.get(position).getemloyee_employee_id());
            Log.d("fname",employeeList.get(position).getemloyee_first_name());
            Log.d("lname",employeeList.get(position).getemloyee_last_name());

            dialog.show();
        }

        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }


        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

    }
