package com.citynote.sanky.ut_preliminary.Fragement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;

public class EmployessFragment extends Fragment {
    // Log tag
    TextView another_emp_id,another_emp_fname,another_emp_lname,another_emp_email,another_emp_mobile,another_emp_emg_mobile,another_emp_dob,another_emp_bg,another_emp_jod,another_emp_designation,another_emp_work_exp,another_emp_technical_skill;
    String another_employee_id,another_first_name,another_last_name,another_email,another_mobile,another_emer_contact,another_dob,another_blood_groop,another_joining_date,another_designation,another_work_exp,another_technical_skill,Image,response;

    Employee employee;
    DBHelper dbHelper;
    String Title2,Image2,Rating2,Post2,ReleaseYear2 ;
    TextView name2,email2,post3,status2;

    ImageView ThumbNail,d_thumbNail2;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String Title,Image22,Rating,ReleaseYear,Post11,Post;
    private static final String TAG = AttandanceManagementFragment.class.getSimpleName();

    // Movies json url   http://citynote.in/sanket/uohemploye.json
    private static final String url = "http://citynote.in/sanket/uohemploye.php";
    //private static final String url = "http://api.androidhive.info/json/movies.json";
    private ProgressDialog pDialog;
    private List<Employee> employeeList = new ArrayList<Employee>();
    private ListView listView;
    private CustomListAdapter adapter;

    String title = "Sanket Patel";
    Dialog dialog;
    ProgressDialog progressDialog;

    android.support.v4.app.FragmentTransaction transaction;
    android.support.v4.app.FragmentManager fm;


    SharedPreferences sharedpreferences;
    SharedPreferences.Editor spt;
    String Employee_id="",Password="";


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
        adapter = new CustomListAdapter(getActivity(), employeeList);
        listView.setAdapter(adapter);


        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        dbHelper = new DBHelper(getActivity());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = employeeList.get(position).getId();
                Log.e("Idddd", pos);

                Display_Dialog(position);

               /* if (employeeList.get(position).getId().equals("1"))
                {
                    UserdetailsFragment fr = new UserdetailsFragment();
                    if (fr != null) {
                        fm = getFragmentManager();
                        transaction = fm.beginTransaction();

                        //send data to next fragment
                        Bundle args = new Bundle();
                        args.putString("current_user_id", employeeList.get(position).getId());
                        args.putString("emp_name2", employeeList.get(position).getTitle());
                        args.putString("emp_email2", employeeList.get(position).getEmail());
                        args.putString("emp_post2", employeeList.get(position).getGenre());
                        args.putString("emp_status2", employeeList.get(position).getStatus());
                        args.putString("thumbnail2", employeeList.get(position).getThumbnailUrl());


                        fr.setArguments(args);



                        transaction.replace(R.id.attandanceframe, fr, null)
                                .addToBackStack(null)
                                .commit();


                    *//*  transaction.replace(R.id.attandanceframe, fr);
                      transaction.addToBackStack(null).commit();*//*

                    }


                }
                else {

                    Display_Dialog(position);
                }



                // Display_Dialog(position);
*/

            }
        });




/*

        // Creating volley request obj
        JsonArrayRequest employeeReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                employee = new Employee();

                                employee.setTitle(obj.getString("title"));
                                employee.setThumbnailUrl(obj.getString("image"));
                                employee.setEmail(obj.getString("rating"));
                                employee.setStatus(obj.getString("releaseYear"));
                                employee.setId(obj.getString("id"));

                                employee.setGenre(obj.getString("genre"));

                                employeeList.add(employee);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(employeeReq);

*/

        return EmployessFragmentView;

    }


    public void Allemployee(String employee_id, String password) {
       /* progressDialog = new ProgressDialog(EmployessFragment.getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Constandapi.ROOT_URL) //Setting the Root URL
                .build();
        Demoapi sign_up  = adapter.create(Demoapi.class);


        sign_up.reg_signup(employee_id,password,new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {

                BufferedReader reader = null;
                String result = "";

                try {
                    //Initializing buffered reader
                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                    //Reading the output in the string
                    result = reader.readLine();


                } catch (IOException e) {
                    Log.d("IOException", "signupdetails" + e.toString());
                }

                Log.d("signupdetails_responce", "signupdetails" + result.toString());
                progressDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("signs_errorfailure", "signupdetails " + error.toString());
               // progressDialog.dismiss();
            }
        });


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


        another_emp_id.setText(another_employee_id);
        another_emp_fname.setText(another_first_name);
        another_emp_lname.setText(another_last_name);
        another_emp_email.setText(another_email);
        another_emp_mobile.setText(another_mobile);
        another_emp_emg_mobile.setText(another_emer_contact);
        another_emp_dob.setText(another_dob);
        another_emp_bg.setText(another_blood_groop);
        another_emp_jod.setText(another_joining_date);
        another_emp_designation.setText(another_designation);
        another_emp_work_exp.setText(another_work_exp);
        another_emp_technical_skill.setText(another_technical_skill);
        Picasso.with(getActivity()).load(Image).into(ThumbNail);


        dialog.show();

          /*  for (int n = 0; n < employeeList.size(); n++) {



            }*/
        // }


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
