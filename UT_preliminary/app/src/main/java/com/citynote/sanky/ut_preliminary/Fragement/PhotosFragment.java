package com.citynote.sanky.ut_preliminary.Fragement;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citynote.sanky.ut_preliminary.Photos.SlideshowDialogFragment;
import com.citynote.sanky.ut_preliminary.R;
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
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import com.citynote.sanky.ut_preliminary.Photos.GalleryAdapter;







public class PhotosFragment extends Fragment {


    private String TAG = PhotosFragment.class.getSimpleName();
    private static final String endpoint = "http://api.androidhive.info/json/glide.json";
    private ArrayList<com.citynote.sanky.ut_preliminary.Photos.Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    View PhotosFragmentView;

    String response;

    JSONObject jsonObject;
    JSONArray jsonarray;

    ProgressDialog pd;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor spt;
    String Employee_id="",Password="";

    private FragmentActivity myContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      PhotosFragmentView = inflater.inflate(R.layout.fragment_photos, container, false);

        recyclerView = (RecyclerView) PhotosFragmentView.findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(getActivity());
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getActivity(), images);


        sharedpreferences = getActivity().getSharedPreferences("uohmac", 0);
        spt = sharedpreferences.edit();

        Employee_id =sharedpreferences.getString("employee_id", null);
        Password=sharedpreferences.getString("password",null);







        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getActivity(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);


                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        new fetchImages().execute(Employee_id,Password);


        return PhotosFragmentView;
    }




    class fetchImages extends AsyncTask<String,String,String> {

        URL url = null;
        HttpURLConnection httpconnection;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {


                url = new URL("http://blessindia.in/webservice/api_img.php");
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
                Log.e("rescode", "" + response_code);
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

            pd.dismiss();

            // images.clear();

            try {
                jsonarray = new JSONArray(response);
                jsonObject = new JSONObject();
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonObject = jsonarray.getJSONObject(i);

                    com.citynote.sanky.ut_preliminary.Photos.Image image = new com.citynote.sanky.ut_preliminary.Photos.Image();

                    image.setName(jsonObject.getString("name"));
                    image.setSmall(jsonObject.getString("image"));
                    image.setMedium(jsonObject.getString("image"));
                    image.setLarge(jsonObject.getString("image"));





                    images.add(image);
                    Log.d("name", jsonObject.getString("name"));
                    Log.d("image", jsonObject.getString("image"));
                    images.size();
                    Log.d("imagesizwarry",""+ images.size());
                }

                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }



}
