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
import com.citynote.sanky.ut_preliminary.R;

public class GadgetsFragment extends Fragment {

    Button Submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View GadgetsFragmentView = inflater.inflate(R.layout.fragment_gadgets, container, false);

        Submit = (Button) GadgetsFragmentView.findViewById(R.id.gadgets_submit);
        return GadgetsFragmentView;

    }

    public void sendEmail(View button) {
        /*final EditText formName = (EditText) findViewById(R.id.formName);
        String clientName = formName.getText().toString();
        final EditText formEmail = (EditText) findViewById(R.id.formEmail);
        String clientEmail = formEmail.getText().toString();
        final EditText formDetails = (EditText) findViewById(R.id.formDetails);
        String clientDetails = formDetails.getText().toString();*/

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"example@email.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "New Private Service Request");
        i.putExtra(Intent.EXTRA_TEXT, "TODO: compose message body");
        try {
            startActivity(Intent.createChooser(i, "Send email with...?"));
        } catch (android.content.ActivityNotFoundException exception) {
            Toast.makeText(GadgetsFragment.this.getActivity(), "No email clients installed on device!", Toast.LENGTH_LONG).show();
        }

    }
}
