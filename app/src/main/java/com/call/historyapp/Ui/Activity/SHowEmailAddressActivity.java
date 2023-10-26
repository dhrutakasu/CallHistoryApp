package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;

import java.util.regex.Pattern;

public class SHowEmailAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvNumber, BtnSubmit;
    private EditText EdtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_email_address);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvNumber = (TextView) findViewById(R.id.TvNumber);
        EdtMail = (EditText) findViewById(R.id.EdtMail);
        BtnSubmit = (TextView) findViewById(R.id.BtnSubmit);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
        BtnSubmit.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenBannerAds(context,((ProgressBar) findViewById(R.id.progressBarAd)),(RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(R.string.call_history);
        TvNumber.setText(getIntent().getStringExtra("code") + " " + getIntent().getStringExtra("phone"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                finish();
                break;
            case R.id.BtnSubmit:
                ScreenAdsClass.ShowScreenInterstitialAd(context, new ScreenAdsClass.AdCallback() {
                    @Override
                    public void AppCallback() {
                        String trim = EdtMail.getText().toString().trim();
                        if (Pattern.compile(".+@.+\\.[a-z]+").matcher(trim).matches()) {
                            startActivity(new Intent(context, ShareDetailOptionActivity.class));
                            return;
                        }
                    }
                });
                break;
        }
    }
}