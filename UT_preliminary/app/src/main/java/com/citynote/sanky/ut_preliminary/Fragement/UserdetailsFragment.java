package com.citynote.sanky.ut_preliminary.Fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.citynote.sanky.ut_preliminary.Attandance_model.AppController;
import com.citynote.sanky.ut_preliminary.Attandance_model.Employee;
import com.citynote.sanky.ut_preliminary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserdetailsFragment extends Fragment {

    private static final String TAG = AttandanceManagementFragment.class.getSimpleName();

    // Movies json url   http://citynote.in/sanket/uohemploye.json
    private static final String url = "http://citynote.in/sanket/uohemploye.php";
    //private static final String url = "http://api.androidhive.info/json/movies.json";
    private ProgressDialog pDialog;

    String Title,Image,Rating,Post,ReleaseYear ;
    TextView name,email,post2,status;
    NetworkImageView ThumbNail,thumbNail;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View UserdetailsFragmentView =  inflater.inflate(R.layout.fragment_userdetails, container, false);

         ThumbNail = (NetworkImageView) UserdetailsFragmentView.findViewById(R.id.thumbnail1);
         name = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_name1);
         email = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_email1);
         post2 = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_post1);
         status = (TextView) UserdetailsFragmentView.findViewById(R.id.emp_status1);



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

                                JSONObject obj = response.getJSONObject(0);
                                //Employee employee = new Employee();

                                Title= obj.getString("title");
                                Image=obj.getString("image");
                                Rating=obj.getString("rating");
                                ReleaseYear=obj.getString("releaseYear");

                                // Genre is json array
                                JSONArray postArry = obj.getJSONArray("genre");
                                ArrayList<String> post = new ArrayList<String>();
                                for (int j = 0; j < postArry.length(); j++) {
                                    post.add((String) postArry.get(j));
                                }
                                    Post = post.toString().trim();




                                name.setText(Title);
                                email.setText(Rating);
                                post2.setText(Post);
                                status.setText(ReleaseYear);
                                ThumbNail.setImageUrl(Image,imageLoader);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


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
