package com.citynote.sanky.ut_preliminary.Activity;

import com.citynote.sanky.ut_preliminary.Fragement.Attandance_management;
import com.citynote.sanky.ut_preliminary.Test.CustomListAdapter;
import com.citynote.sanky.ut_preliminary.Test.AppController;
import com.citynote.sanky.ut_preliminary.Test.Employee;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.citynote.sanky.ut_preliminary.R;


public class Test extends Activity {
    // Log tag
    private static final String TAG = Test.class.getSimpleName();

    // Movies json url   http://citynote.in/sanket/uohemploye.json
    private static final String url = "http://citynote.in/sanket/uohemploye.php";
    //private static final String url = "http://api.androidhive.info/json/movies.json";
    private ProgressDialog pDialog;
    private List<Employee> employeeList = new ArrayList<Employee>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, employeeList);
        listView.setAdapter(adapter);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListClickHandler());

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


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
                                Employee employee = new Employee();
                                employee.setTitle(obj.getString("title"));
                                employee.setThumbnailUrl(obj.getString("image"));
                                employee.setEmail(obj.getString("rating"));
                                employee.setStatus(obj.getString("releaseYear"));

                                // Genre is json array
                                JSONArray postArry = obj.getJSONArray("genre");
                                ArrayList<String> post = new ArrayList<String>();
                                for (int j = 0; j < postArry.length(); j++) {
                                    post.add((String) postArry.get(j));
                                }
                                employee.setPost(post);

                                // adding movie to movies array
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public class ListClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {

            Intent intent = new Intent(Test.this, Attandance_management.class);

            startActivity(intent);
            }
    }
}
