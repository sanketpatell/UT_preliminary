package com.citynote.sanky.ut_preliminary.Fragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.citynote.sanky.ut_preliminary.Email.SendMail;
import com.citynote.sanky.ut_preliminary.R;

public class GadgetsFragment extends Fragment {

    //Declaring EditText
    String editTextEmail ="sanketleader@gmail.com";
    private EditText editText_gadgets_requirement;
    private EditText editText_gadgets_description;

    //Send button
    private Button buttonSend,buttonclear;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View GadgetsFragmentView = inflater.inflate(R.layout.fragment_gadgets, container, false);

        //Initializing the views
        //editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editText_gadgets_requirement = (EditText) GadgetsFragmentView.findViewById(R.id.gadgets_requirement);
        editText_gadgets_description = (EditText) GadgetsFragmentView.findViewById(R.id.gadgets_description);

        buttonSend = (Button) GadgetsFragmentView.findViewById(R.id.gadgets_submit);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting content for email
                String email = editTextEmail.toString().trim();
                String subject = editText_gadgets_requirement.getText().toString().trim();
                String message = editText_gadgets_description.getText().toString().trim();

                //Creating SendMail object
                SendMail sm = new SendMail(getActivity(), email, subject, message);

                //Executing sendmail to send email
                sm.execute();
            }
        });

        buttonclear = (Button) GadgetsFragmentView.findViewById(R.id.gadgets_clear);
        buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_gadgets_requirement.setText("");
                editText_gadgets_description.setText("");

            }
        });
       /* //Adding click listener
        buttonSend.setOnClickListener((View.OnClickListener) GadgetsFragmentView);*/

        return GadgetsFragmentView;
    }


}

