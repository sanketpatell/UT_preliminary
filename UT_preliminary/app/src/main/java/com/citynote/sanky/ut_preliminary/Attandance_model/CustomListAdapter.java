package com.citynote.sanky.ut_preliminary.Attandance_model;


import com.bumptech.glide.Glide;
import com.citynote.sanky.ut_preliminary.R;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Employee> employeeItems;
   // ImageLoader imageLoader = AppController.getInstance().getImageLoader();

Context context;

    public CustomListAdapter(Activity activity, List<Employee> employeeItems) {
        this.activity = activity;
        this.employeeItems = employeeItems;
    }

    @Override
    public int getCount() {
        return employeeItems.size();
    }

    @Override
    public Object getItem(int location) {
        return employeeItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.attandance_manage_list, null);

       /* if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();*/
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumbnail);
        TextView id = (TextView) convertView.findViewById(R.id.emp_id);
        TextView name = (TextView) convertView.findViewById(R.id.emp_name);
        TextView email = (TextView) convertView.findViewById(R.id.emp_email);
        TextView post = (TextView) convertView.findViewById(R.id.emp_post);
      //  TextView status = (TextView) convertView.findViewById(R.id.emp_status);


        // getting employee data for the row
        Employee m = employeeItems.get(position);

        // thumbnail image
        Picasso.with(context).load(m.getemloyee_Image()).into(thumbNail);
       // thumbNail.setImageUrl(m.getemloyee_Image(), imageLoader);
        /*Glide.with(context)
                .load(m.getemloyee_Image())
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .crossFade()
                .into(thumbNail);*/
        // emp_id
        id.setText(m.getemloyee_employee_id());

        // emp_name
        name.setText(m.getemloyee_first_name()+"  "+m.getemloyee_last_name());

        // email
        email.setText("Email: " + String.valueOf(m.getemloyee_email()));


        post.setText(m.getemloyee_designation());

       /* // post
        String postStr = "";
        for (String str : m.getPost()) {
            postStr += str + ", ";
        }
        postStr = postStr.length() > 0 ? postStr.substring(0,
                postStr.length() - 2) : postStr;
        post.setText(postStr);*/

        // release year
       // status.setText(String.valueOf(m.getStatus()));

        return convertView;



    }

}
