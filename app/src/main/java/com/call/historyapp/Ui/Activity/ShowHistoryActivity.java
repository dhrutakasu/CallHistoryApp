package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;

public class ShowHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack, IvVector;
    private TextView TvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        IvVector = (ImageView) findViewById(R.id.IvVector);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenBannerAds(context,((ProgressBar) findViewById(R.id.progressBarAd)),(RelativeLayout) findViewById(R.id.RlAdvertisement));
        String action = getIntent().getStringExtra("ShowData");
        TvTitle.setText(R.string.call_history);
        if (action.equalsIgnoreCase(getString(R.string.call_history))) {
            IvVector.setImageResource(R.drawable.ic_call_history_vector);
        } else if (action.equalsIgnoreCase(getString(R.string.whatsapp_history))) {
            IvVector.setImageResource(R.drawable.ic_chat);
        } else if (action.equalsIgnoreCase(getString(R.string.sms_history))) {
            IvVector.setImageResource(R.drawable.ic_sms_history);
        } else if (action.equalsIgnoreCase(getString(R.string.all_history))) {
            IvVector.setImageResource(R.drawable.ic_mix);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                finish();
                break;
        }
    }
}