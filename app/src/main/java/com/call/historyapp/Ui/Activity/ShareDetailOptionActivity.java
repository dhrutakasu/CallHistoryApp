package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;

public class ShareDetailOptionActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, BtnCall, BtnWhatsApp, BtnSMS, BtnAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail_option);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        BtnCall = (TextView) findViewById(R.id.BtnCall);
        BtnWhatsApp = (TextView) findViewById(R.id.BtnWhatsApp);
        BtnSMS = (TextView) findViewById(R.id.BtnSMS);
        BtnAll = (TextView) findViewById(R.id.BtnAll);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
        BtnCall.setOnClickListener(this);
        BtnWhatsApp.setOnClickListener(this);
        BtnSMS.setOnClickListener(this);
        BtnAll.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenNativeAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(R.string.call_history);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                finish();
                break;
            case R.id.BtnCall:
                GotoData(BtnCall.getText().toString());
                break;
            case R.id.BtnWhatsApp:
                GotoData(BtnWhatsApp.getText().toString());
                break;
            case R.id.BtnSMS:
                GotoData(BtnSMS.getText().toString());
                break;
            case R.id.BtnAll:
                GotoData(BtnAll.getText().toString());
                break;
        }
    }

    private void GotoData(String toString) {
        ScreenAdsClass.ShowScreenInterstitialAd(context, new ScreenAdsClass.AdCallback() {
            @Override
            public void AppCallback() {
                Intent intent = new Intent(context, ShowHistoryActivity.class);
                intent.putExtra("ShowData", toString);
                startActivity(intent);
            }
        });
    }
}