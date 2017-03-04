package com.citynote.sanky.ut_preliminary.Fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.citynote.sanky.ut_preliminary.Attandance_model.AppController;
import com.citynote.sanky.ut_preliminary.Attandance_model.DBHelper;
import com.citynote.sanky.ut_preliminary.Attandance_model.Employee;
import com.citynote.sanky.ut_preliminary.Attandance_model.HttpHandler;
import com.citynote.sanky.ut_preliminary.CircleTransform;
import com.citynote.sanky.ut_preliminary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnExpDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthScrollListener;
import sun.bob.mcalendarview.utils.CurrentCalendar;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;

public class UserdetailsFragment extends Fragment {
    List<String> date_list;
    List<String> date_list1;
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

    String Id,Title,Image,Rating,Post,ReleaseYear ;
    TextView id,name,email,post2,status;
    ImageView ThumbNail,thumbNail;
   // ImageLoader imageLoader = AppController.getInstance().getImageLoader();





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View UserdetailsFragmentView =  inflater.inflate(R.layout.fragment_userdetails, container, false);


        dbHelper=new DBHelper(getActivity());
         thumbNail = (ImageView) UserdetailsFragmentView.findViewById(R.id.thumbnail1);
         id = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_id1);
         name = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_name1);
         email = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_email1);
         post2 = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_post1);
         status = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_status1);
        Button TravelToClick = (Button) UserdetailsFragmentView.findViewById(R.id.TravelToClick);

        String current_user_id = getArguments().getString("current_user_id");
        String emp_name2 = getArguments().getString("emp_name2");
        String emp_email2 = getArguments().getString("emp_email2");
        String emp_post2 = getArguments().getString("emp_post2");
        String emp_status2 = getArguments().getString("emp_status2");
        String thumbnail2 = getArguments().getString("thumbnail2");
//present dates
        date_list=new ArrayList<String>();
        date_list=dbHelper.getpresent_date();

        Log.e("date_listdate_list", "" + date_list);

       //absent dates
        date_list1=new ArrayList<String>();
        date_list1=dbHelper.getabsent_date();





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
                    .into(thumbNail);

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
            Log.e("original_date", "" + original_date);
            split_date = original_date.split("/");
            Log.e("splited_date", "" + split_date);
            year = Integer.parseInt(split_date[2]);
            Log.e("year", "" + year);
            month = Integer.parseInt(split_date[1]);
            Log.e("month", "" + month);
            date = Integer.parseInt(split_date[0]);
            Log.e("day", "" + date);

            expCalendarView.setMarkedStyle(MarkStyle.RIGHTSIDEBAR)
                    .markDate(new DateData(year, month, date).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.GREEN)));
        }


        //absent date
        split_date1=new String[]{};
        for (int k = 0; k < date_list1.size(); k++) {

            original_date = date_list1.get(k);
            Log.e("original_date", "" + original_date);
            split_date1 = original_date.split("/");
            Log.e("splited_date", "" + split_date1);
            year = Integer.parseInt(split_date1[2]);
            Log.e("year", "" + year);
            month = Integer.parseInt(split_date1[1]);
            Log.e("month", "" + month);
            date = Integer.parseInt(split_date1[0]);
            Log.e("day", "" + date);

            expCalendarView.setMarkedStyle(MarkStyle.RIGHTSIDEBAR)
                    .markDate(new DateData(year, month, date).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.RED)));
        }



        return UserdetailsFragmentView;
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
