package com.citynote.sanky.ut_preliminary.Fragement;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.citynote.sanky.ut_preliminary.R;
import com.citynote.sanky.ut_preliminary.Attandance_model.AppController;
import com.citynote.sanky.ut_preliminary.Attandance_model.CustomListAdapter;
import com.citynote.sanky.ut_preliminary.Attandance_model.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttandanceManagementFragment extends Fragment {
    // Log tag
    private static final String TAG = AttandanceManagementFragment.class.getSimpleName();

    // Movies json url   http://citynote.in/sanket/uohemploye.json
    private static final String url = "http://citynote.in/sanket/uohemploye.php";
    //private static final String url = "http://api.androidhive.info/json/movies.json";
    private ProgressDialog pDialog;
    private List<Employee> employeeList = new ArrayList<Employee>();
    private ListView listView;
    private CustomListAdapter adapter;
    String title = "Sanket Patel";

    Fragment fr=null;
    android.support.v4.app.FragmentTransaction transaction;
    android.support.v4.app.FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View AttandanceFragmentView = inflater.inflate(R.layout.fragment_attandance, container, false);


        listView = (ListView) AttandanceFragmentView.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), employeeList);
        listView.setAdapter(adapter);


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                if(title.equals("Sanket Patel")) {
                    fr = new UserdetailsFragment();
                    if (fr != null) {
                        fm = getFragmentManager();
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.attandanceframe, fr, null)
                                .addToBackStack(null)
                               .commit();

                       /* transaction.replace(R.id.attandanceframe, fr);
                        transaction.addToBackStack(null).commit();*/
                    } else {
                        Log.e("AttandaceFragment", "Error in creating fragment");
                           }
                    }
                    else {
                        Log.e("AttandaceFragment", " Another user Details   ");
                         }
             }
        });




        pDialog = new ProgressDialog(getActivity());
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


        return AttandanceFragmentView;
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
