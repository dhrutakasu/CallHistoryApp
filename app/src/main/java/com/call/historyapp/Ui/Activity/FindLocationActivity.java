package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

public class FindLocationActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private TextView TvTitle;
    private ImageView IvBack;
    private TextView BtnFindLocation;
    private EditText EdtNumber;
    private String splistr;
    private ConstraintLayout ConInput, ConOutput;
    private TextView TvNumber, TvNetwork, TvState;
    private ConstraintLayout ConsCall, ConsContact, ConsMessage, ConsWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        BtnFindLocation = (TextView) findViewById(R.id.BtnFindLocation);
        EdtNumber = (EditText) findViewById(R.id.EdtNumber);
        TvNumber = (TextView) findViewById(R.id.TvNumber);
        TvNetwork = (TextView) findViewById(R.id.TvNetwork);
        TvState = (TextView) findViewById(R.id.TvState);
        ConInput = (ConstraintLayout) findViewById(R.id.ConInput);
        ConOutput = (ConstraintLayout) findViewById(R.id.ConOutput);
        ConsCall = (ConstraintLayout) findViewById(R.id.ConsCall);
        ConsContact = (ConstraintLayout) findViewById(R.id.ConsContact);
        ConsMessage = (ConstraintLayout) findViewById(R.id.ConsMessage);
        ConsWhatsapp = (ConstraintLayout) findViewById(R.id.ConsWhatsapp);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
        BtnFindLocation.setOnClickListener(this);
        ConsCall.setOnClickListener(this);
        ConsContact.setOnClickListener(this);
        ConsMessage.setOnClickListener(this);
        ConsWhatsapp.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenNativeAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(R.string.find_location);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                finish();
                break;
            case R.id.BtnFindLocation:
                GotoFindLocation(view);
                break;
            case R.id.ConsCall:
                GotoCall();
                break;
            case R.id.ConsContact:
                GotoContact();
                break;
            case R.id.ConsMessage:
                GotoMessage();
                break;
            case R.id.ConsWhatsapp:
                GotoWhatsapp();
                break;
        }
    }

    private void GotoCall() {
        Intent in = new Intent(Intent.ACTION_CALL);
        in.setData(Uri.parse("tel:+91" + TvNumber.getText().toString()));
        startActivity(in);
    }

    private void GotoContact() {
        Intent i = new Intent("com.android.contacts.action.SHOW_OR_CREATE_CONTACT", Uri.parse("tel:+91" + TvNumber.getText().toString()));
        i.putExtra("com.android.contacts.action.FORCE_CREATE", true);
        startActivity(i);
    }

    private void GotoMessage() {
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setType("text/plain");
        i.setData(Uri.parse("sms:+91" + TvNumber.getText().toString()));
        startActivity(Intent.createChooser(i, "Complete action using"));
    }

    private void GotoWhatsapp() {
        try {
            getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.whatsapp");
            StringBuilder builder = new StringBuilder();
            builder.append("https://api.whatsapp.com/send?phone=");
            builder.append("+91" + TvNumber.getText());
            builder.append("&text=");
            builder.append(URLEncoder.encode("", "UTF-8"));
            intent.setData(Uri.parse(builder.toString()));
            startActivity(intent);
        } catch (Exception unused) {
            Toast.makeText(context, "Not Install WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }

    private void GotoFindLocation(View view) {
        if (EdtNumber.getText().toString().isEmpty()) {
            Toast.makeText(context, "Please Enter number", Toast.LENGTH_SHORT).show();
        } else if (EdtNumber.getText().toString().length() == 10) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("phNo.txt"), "UTF-8"));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        return;
                    }
                    if (EdtNumber.getText().toString().substring(0, 4).equals(line.split(",")[0])) {
                        String[] strings = line.split(",");
                        String str = strings[1];
                        splistr = strings[2];
                        try {
                            List<Address> addresses = new Geocoder(context,
                                    Locale.getDefault()).getFromLocationName(splistr, 1);
                            if (addresses != null && addresses.size() > 0) {
                                Address address = addresses.get(0);
                                address.getLatitude();
                                address.getLongitude();
                            }
                        } catch (IOException e) {
                            e.getMessage();
                        }
                        TvNumber.setText(EdtNumber.getText().toString());
                        TvNetwork.setText(str);
                        TvState.setText(splistr);
                        ConOutput.setVisibility(View.VISIBLE);
                        ConInput.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e2) {
                e2.getMessage();
            }
        } else {
            Toast.makeText(context, "Please Enter proper number", Toast.LENGTH_SHORT).show();
        }
    }
}