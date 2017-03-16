package com.citynote.sanky.ut_preliminary.Fragement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.citynote.sanky.ut_preliminary.Activity.MainActivity;
import com.citynote.sanky.ut_preliminary.Attandance_model.AppController;
import com.citynote.sanky.ut_preliminary.Attandance_model.DBHelper;
import com.citynote.sanky.ut_preliminary.Attandance_model.Employee;
import com.citynote.sanky.ut_preliminary.Attandance_model.HttpHandler;
import com.citynote.sanky.ut_preliminary.CircleTransform;
import com.citynote.sanky.ut_preliminary.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnExpDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthScrollListener;
import sun.bob.mcalendarview.utils.CurrentCalendar;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;

public class UserdetailsFragment extends Fragment {
    List<String> date_list;  //present
    List<String> date_list1;//absent
    private TextView YearMonthTv;
    private ExpCalendarView expCalendarView;
    DBHelper dbHelper;

   String original_date;
    int year,month,date;
    String []split_date;
    String []split_date1;
    // Movies json url   http://citynote.in/sanket/uohemploye.json
  //  private static final String url = "http://citynote.in/sanket/uohemploye.php";
    //private static final String url = "http://api.androidhive.info/json/movies.json";
    private ProgressDialog pDialog;

    String Id,Title,Rating,Post,ReleaseYear ;
    TextView mobile,name,email,designation,status;
    ImageView ThumbNail,thumbNail;
   ImageLoader imageLoader = AppController.getInstance().getImageLoader();

//employee details
    String curr_employee_id,curr_first_name,curr_last_name,curr_email,curr_mobile,curr_emer_contact,curr_dob,curr_blood_groop,curr_joining_date,curr_designation,curr_work_exp,curr_technical_skill,Image,response;
    JSONObject jsonObject;
    RelativeLayout relativeLayout;
//dilogbox

    Dialog dialog;
TextView curr_emp_id,curr_emp_fname,curr_emp_lname,curr_emp_email,curr_emp_mobile,curr_emp_emg_mobile,curr_emp_dob,curr_emp_bg,curr_emp_jod,curr_emp_designation,curr_emp_work_exp,curr_emp_technical_skill;


    SharedPreferences sharedpreferences;
    SharedPreferences.Editor spt;
    String Employee_id="",Password="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View UserdetailsFragmentView =  inflater.inflate(R.layout.fragment_userdetails, container, false);


        sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", 0);
        spt = sharedpreferences.edit();

        Employee_id =sharedpreferences.getString("employee_id", null);
        Password=sharedpreferences.getString("password",null);

        Log.d("employee_id","1   "+ Employee_id);
        Log.d("password","2 "+ Password);



        dbHelper=new DBHelper(getActivity());
        thumbNail = (ImageView) UserdetailsFragmentView.findViewById(R.id.thumbnail1);
        name = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_name1);
        designation = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_designation);
        email = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_email1);
        mobile = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_mobile);
        relativeLayout=(RelativeLayout) UserdetailsFragmentView.findViewById(R.id.current_userdetails);
         //status = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_status1);
        Button TravelToClick = (Button) UserdetailsFragmentView.findViewById(R.id.TravelToClick);

      /*  String current_user_id = getArguments().getString("current_user_id");
        String emp_name2 = getArguments().getString("emp_name2");
        String emp_email2 = getArguments().getString("emp_email2");
        String emp_post2 = getArguments().getString("emp_post2");
        String emp_status2 = getArguments().getString("emp_status2");
        String thumbnail2 = getArguments().getString("thumbnail2");*/
//present dates
        date_list=new ArrayList<String>();
        date_list=dbHelper.getpresent_date();

      //  Log.e("date_listdate_list", "" + date_list);

       //absent dates
        date_list1=new ArrayList<String>();
        date_list1=dbHelper.getabsent_date();


        //current employee details
        new loginparser().execute(Employee_id,Password);







/*
            id.setText(current_user_id);
            name.setText(emp_name2);
            email.setText(emp_email2);
            post2.setText(emp_post2);
            status.setText(emp_status2);
            // thumbNail.setImageUrl(thumbnail2, imageLoader);


            Glide.with(this).load(thumbnail2)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(getActivity()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(thumbNail);*/

            TravelToClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expCalendarView.travelTo(CurrentCalendar.getCurrentDateData().setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.BLUE)));
                }
            });

        expCalendarView = ((ExpCalendarView) UserdetailsFragmentView.findViewById(R.id.calendar_exp));
            YearMonthTv = (TextView) UserdetailsFragmentView.findViewById(R.id.main_YYMM_Tv);
            YearMonthTv.setText(Calendar.getInstance().get(Calendar.MONTH) + 1 + "  " + (Calendar.getInstance().get(Calendar.YEAR)));

//      Set up listeners.
            expCalendarView.setOnDateClickListener(new OnExpDateClickListener()).setOnMonthScrollListener(new OnMonthScrollListener() {
                @Override
                public void onMonthChange(int year, int month) {
                    YearMonthTv.setText(String.format("%d" + " " + "%d", month, year));
                }

                @Override
                public void onMonthScroll(float positionOffset) {
//                Log.i("listener", "onMonthScroll:" + positionOffset);
                }
            });

           /* expCalendarView.setMarkedStyle(MarkStyle.RIGHTSIDEBAR)
                *//*.markDate(2017, 3, 5)
                .markDate(2017, 3, 4)
                .markDate(2017, 3, 25)*//*
                    // .markDate(new DateData(2017, 3, 6).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.GREEN)))
                    .markDate(new DateData(year, month, date).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.GREEN)))
                    .hasTitle(false);*/
        split_date=new String[]{};
        for (int k = 0; k < date_list.size(); k++) {

            original_date = date_list.get(k);
          //  Log.e("original_date", "" + original_date);
            split_date = original_date.split("/");
          //  Log.e("splited_date", "" + split_date);
            year = Integer.parseInt(split_date[2]);
          //  Log.e("year", "" + year);
            month = Integer.parseInt(split_date[1]);
          //  Log.e("month", "" + month);
            date = Integer.parseInt(split_date[0]);
         //   Log.e("day", "" + date);

            expCalendarView.setMarkedStyle(MarkStyle.RIGHTSIDEBAR)
                    .markDate(new DateData(year, month, date).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.GREEN)));
        }


        //absent date
        split_date1=new String[]{};
        for (int k = 0; k < date_list1.size(); k++) {

            original_date = date_list1.get(k);
          //  Log.e("original_date", "" + original_date);
            split_date1 = original_date.split("/");
          //  Log.e("splited_date", "" + split_date1);
            year = Integer.parseInt(split_date1[2]);
         //   Log.e("year", "" + year);
            month = Integer.parseInt(split_date1[1]);
         //   Log.e("month", "" + month);
            date = Integer.parseInt(split_date1[0]);
         //   Log.e("day", "" + date);

            expCalendarView.setMarkedStyle(MarkStyle.RIGHTSIDEBAR)
                    .markDate(new DateData(year, month, date).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.RED)));
        }

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Display_Dialog();
            }
        });






        return UserdetailsFragmentView;
    }




    class loginparser extends AsyncTask<String,String,String> {

       // ProgressDialog pdLoading = new ProgressDialog(UserdetailsFragment.this);
        URL url = null;
        HttpURLConnection httpconnection;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();*/

        }

        @Override
        protected String doInBackground(String... params) {
            try {


                url = new URL("http://blessindia.in/webservice/get_emp_detail.php");
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
            //pdLoading.dismiss();


            try {
                jsonObject = new JSONObject(response);
                //id = jsonObject.getString("id");
                curr_employee_id = jsonObject.getString("employee_id");
                curr_first_name = jsonObject.getString("first_name");
                curr_last_name = jsonObject.getString("last_name");
                curr_email = jsonObject.getString("email");
                curr_mobile = jsonObject.getString("mobile");
                curr_emer_contact = jsonObject.getString("emer_contact");
                curr_dob = jsonObject.getString("dob");
                curr_blood_groop = jsonObject.getString("blood_groop");
                curr_joining_date = jsonObject.getString("joining_date");
                curr_designation = jsonObject.getString("designation");
                curr_work_exp = jsonObject.getString("work_exp");
                curr_technical_skill  = jsonObject.getString("technical_skill");
                Image = jsonObject.getString("image");

                name.setText(curr_first_name+"  "+curr_last_name);
                designation.setText(curr_designation);
                email.setText(curr_email);
                mobile.setText(curr_mobile);


                //UrlImageViewHelper.setUrlDrawable(thumbNail, Image);


                  Picasso.with(getActivity()).load(Image).into(thumbNail);

                Log.d("curr_first_name", "====  " + curr_first_name);
                Log.d("curr_designation", "====  " + curr_designation);
                Log.d("curr_email", "====  " + curr_email);
                Log.d("curr_mobile", "====  " + curr_mobile);

                //  Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();


            }catch (Exception e){
                e.printStackTrace();
            }






        }


    }

    public void Display_Dialog()
    {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_full_userdetails);
        dialog.setTitle("Custom Dialog");
        ThumbNail = (ImageView) dialog.findViewById(R.id.current_user_image);
        curr_emp_id = (TextView) dialog.findViewById(R.id.curr_emp_id);
        curr_emp_fname = (TextView) dialog.findViewById(R.id.curr_emp_fname);
        curr_emp_lname = (TextView) dialog.findViewById(R.id.curr_emp_lname);
        curr_emp_email = (TextView) dialog.findViewById(R.id.curr_emp_email);
        curr_emp_mobile = (TextView) dialog.findViewById(R.id.curr_emp_mobile);
        curr_emp_emg_mobile = (TextView) dialog.findViewById(R.id.curr_emp_emg_mobile);
        curr_emp_dob = (TextView) dialog.findViewById(R.id.curr_emp_dob);
        curr_emp_bg = (TextView) dialog.findViewById(R.id.curr_emp_bg);
        curr_emp_jod = (TextView) dialog.findViewById(R.id.curr_emp_jod);
        curr_emp_designation = (TextView) dialog.findViewById(R.id.curr_emp_designation);
        curr_emp_work_exp = (TextView) dialog.findViewById(R.id.curr_emp_work_exp);
        curr_emp_technical_skill = (TextView) dialog.findViewById(R.id.curr_emp_technical_skill);


        //if(list.equals(position)) {


        curr_emp_id.setText(curr_employee_id);
        curr_emp_fname.setText(curr_first_name);
        curr_emp_lname.setText(curr_last_name);
        curr_emp_email.setText(curr_email);
        curr_emp_mobile.setText(curr_mobile);
        curr_emp_emg_mobile.setText(curr_emer_contact);
        curr_emp_dob.setText(curr_dob);
        curr_emp_bg.setText(curr_blood_groop);
        curr_emp_jod.setText(curr_joining_date);
        curr_emp_designation.setText(curr_designation);
        curr_emp_work_exp.setText(curr_work_exp);
        curr_emp_technical_skill.setText(curr_technical_skill);
        Picasso.with(getActivity()).load(Image).into(ThumbNail);







        /*Glide.with(this).load(employeeList.get(position).getThumbnailUrl())
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(d_thumbNail2);
*/
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
