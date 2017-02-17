package com.citynote.sanky.ut_preliminary.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.citynote.sanky.ut_preliminary.Email.SendMail;
import com.citynote.sanky.ut_preliminary.R;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class PrivacyPolicyActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditText
    String editTextEmail ="sanketleader@gmail.com";
    private EditText editText_gadgets_requirement;
    private EditText editText_gadgets_description;

    //Send button
    private Button buttonSend,buttonclear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gadgets);

        //Initializing the views
        //editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editText_gadgets_requirement = (EditText) findViewById(R.id.gadgets_requirement);
        editText_gadgets_description = (EditText) findViewById(R.id.gadgets_description);

        buttonSend = (Button) findViewById(R.id.gadgets_submit);
        buttonclear = (Button) findViewById(R.id.gadgets_clear);
buttonclear.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        editText_gadgets_requirement.setText("");
        editText_gadgets_description.setText("");

    }
});
        //Adding click listener
        buttonSend.setOnClickListener(this);
    }


    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.toString().trim();
        String subject = editText_gadgets_requirement.getText().toString().trim();
        String message = editText_gadgets_description.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }
}