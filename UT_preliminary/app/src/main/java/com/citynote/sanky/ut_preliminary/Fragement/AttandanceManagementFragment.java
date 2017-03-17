package com.citynote.sanky.ut_preliminary.Fragement;

import android.app.Dialog;
import android.app.ProgressDialog;
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
    TextView d_name,d_email,d_post,d_status,Image;
    Employee employee;
    DBHelper dbHelper;
    String Title2,Image2,Rating2,Post2,ReleaseYear2 ;
    TextView name2,email2,post3,status2;
    ImageView ThumbNail2,d_thumbNail2;
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



        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        dbHelper=new DBHelper(getActivity());


       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String pos = employeeList.get(position).getemloyee_employee_id();
                  Log.e("Idddd", pos);

              if (employeeList.get(position).getemloyee_employee_id().equals("1"))
              {
                  UserdetailsFragment fr = new UserdetailsFragment();
                  if (fr != null) {
                      fm = getFragmentManager();
                      transaction = fm.beginTransaction();

                    //send data to next fragment
                      Bundle args = new Bundle();
                      args.putString("current_user_id", employeeList.get(position).getemloyee_employee_id());
                     // args.putString("emp_name2", employeeList.get(position).getTitle());
                      args.putString("emp_email2", employeeList.get(position).getEmail());
                      args.putString("emp_post2", employeeList.get(position).getGenre());
                      args.putString("emp_status2", employeeList.get(position).getStatus());
                      args.putString("thumbnail2", employeeList.get(position).getThumbnailUrl());


                      fr.setArguments(args);



                      transaction.replace(R.id.attandanceframe, fr, null)
                              .addToBackStack(null)
                              .commit();


                    /*  transaction.replace(R.id.attandanceframe, fr);
                      transaction.addToBackStack(null).commit();*/

                  }


              }
              else {

                  Display_Dialog(position);
              }



              // Display_Dialog(position);


           }
       });





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

                              //  employee.setTitle(obj.getString("title"));
                                employee.setThumbnailUrl(obj.getString("image"));
                                employee.setEmail(obj.getString("rating"));
                                employee.setStatus(obj.getString("releaseYear"));
                                employee.setemloyee_employee_id(obj.getString("id"));

                                employee.setGenre(obj.getString("genre"));
                               /* // Genre is json array
                                JSONArray postArry = obj.getJSONArray("genre");
                                ArrayList<String> post = new ArrayList<String>();
                                for (int j = 0; j < postArry.length(); j++) {
                                    post.add((String) postArry.get(j));
                                }

                                Post11 = post.toString().trim();
                                employee.setPost(post);*/

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

    public void Display_Dialog(int position)
    {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_anotheruserdetails);
        dialog.setTitle("Custom Dialog");
       /* d_thumbNail2 = (ImageView) dialog.findViewById(R.id.thumbnail2);
        d_name = (TextView) dialog.findViewById(R.id.emp_name2);
        d_email = (TextView) dialog.findViewById(R.id.emp_email2);
        d_post = (TextView) dialog.findViewById(R.id.emp_post2);
        d_status = (TextView) dialog.findViewById(R.id.emp_status2);*/

        //if(list.equals(position)) {

           // d_name.setText(employeeList.get(position).getTitle());
            d_email.setText(employeeList.get(position).getEmail());
            d_post.setText(employeeList.get(position).getGenre());
            d_status.setText(employeeList.get(position).getStatus());
           // d_thumbNail2.setImageUrl(employeeList.get(position).getThumbnailUrl(),imageLoader);






        Glide.with(this).load(employeeList.get(position).getThumbnailUrl())
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(d_thumbNail2);

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
