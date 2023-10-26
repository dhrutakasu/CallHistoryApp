package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private TextView TCallId, TCallHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        TCallId = (TextView) findViewById(R.id.TCallId);
        TCallHistory = (TextView) findViewById(R.id.TCallHistory);
    }

    private void CallInitListener() {
        TCallId.setOnClickListener(this);
        TCallHistory.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenNativeAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TCallHistory:
                ScreenAdsClass.ShowScreenInterstitialAd(context, new ScreenAdsClass.AdCallback() {
                    @Override
                    public void AppCallback() {
                        startActivity(new Intent(context, CallHistoryActivity.class));
                    }
                });
                break;
            case R.id.TCallId:
                ScreenAdsClass.ShowScreenInterstitialAd(context, new ScreenAdsClass.AdCallback() {
                    @Override
                    public void AppCallback() {
                        startActivity(new Intent(context, CallIdActivity.class));
                    }
                });
                break;
        }
    }
}