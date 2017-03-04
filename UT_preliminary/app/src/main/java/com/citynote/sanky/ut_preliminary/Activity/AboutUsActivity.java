package com.citynote.sanky.ut_preliminary.Activity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.citynote.sanky.ut_preliminary.Attandance_model.DBHelper;
import com.citynote.sanky.ut_preliminary.Attandance_model.HttpHandler;
import com.citynote.sanky.ut_preliminary.R;
import com.citynote.sanky.ut_preliminary.CircleTransform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class AboutUsActivity extends Activity {
    ImageView aboutuslogo;
    TextView aboutus;
    WebView mWebView;
    private static final String urlaboutuslogo = "http://citynote.in/sanket/uohmaclogo.PNG";


    private static final String urlattandance = "http://citynote.in/sanket/attandance.php";
    JSONArray jsonarray;
    JSONObject jsonObject;
    DBHelper db;


    private String TAG = AboutUsActivity.class.getSimpleName();

    Integer id_a;
    Integer Id_a;
    String emp_id,date,status_a;
    String Emp_id,Date,Status_a;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        aboutuslogo = (ImageView) findViewById(R.id.urlaboutuslogo);
        //  aboutus = (TextView) findViewById(R.id.aboutus);
        mWebView = (WebView) findViewById(R.id.webview);

        // Loading profile image
        Glide.with(this).load(urlaboutuslogo)
                .crossFade()
                .thumbnail(1f)
               // .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(aboutuslogo);
        db = new DBHelper(this);
        db.open();
/*

        aboutus.setText("UOhmac is one among the latest generation Information Technologies company who delivers the Product Engineering , Cloud Solutions and Services.  We are a trusted transformation partner and close enough to understand the individual needs of each and every customer. With the experts, we aim to become a leading Solutions and Services integrator, creating the unsurpassed service experience in Information Technology.\n"+"" +"UOhmac Technologies, head quartered in Silicon Valley of India Bangalore, providing Information Technology Hosting Services provides, Product Engineering, Complete Enterprise CRM Automation Solutions on Cloud Computing Salesforce.com CRM Force.com, Innovative Consulting,  Application Hosting and Corporate, Open House School Learning Solutions.  Feel the difference with a decade of Generic Customer Relationship Management expertise Pioneer six plus years of Trusted Cloud Salesforce.com CRM Technology Solutions; i.e. since Salesforce.com stepped in India, we are into " +
                "The company has niche Consultant strengths in building and managing a Business Oriented IT environment with rich experience in Product and Project Engineering, Business Intelligence, Technology Incubation, Data Management, CRM Consulting, Analytics, Data Warehousing. We are here to provide you with supreme SLAs with highly scalable infrastructure available on services.");
*/




        mWebView = (WebView) findViewById(R.id.webview);

                 String text = "<html><body>"
                         + "<p align=\"justify\" >"
                         + getString(R.string.aboutus)
                         + "</p> "
                         + "<p align=\"justify\">"
                         + getString(R.string.aboutus1)
                         + "</p> "
                         + "</body></html>";

                 mWebView.loadData(text, "text/html", "utf-8");




    }


}



