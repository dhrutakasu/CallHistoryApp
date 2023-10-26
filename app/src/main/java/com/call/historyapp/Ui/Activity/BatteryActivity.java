package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;

public class BatteryActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvBatteryLevel, TvBatteryStatus, TvBatteryTech, TvBatteryHealth, TvBatteryTemp, TvBatteryUptime, TvBatteryVoltage;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvBatteryLevel = (TextView) findViewById(R.id.TvBatteryLevel);
        TvBatteryStatus = (TextView) findViewById(R.id.TvBatteryStatus);
        TvBatteryHealth = (TextView) findViewById(R.id.TvBatteryHealth);
        TvBatteryTemp = (TextView) findViewById(R.id.TvBatteryTemp);
        TvBatteryTech = (TextView) findViewById(R.id.TvBatteryTech);
        TvBatteryVoltage = (TextView) findViewById(R.id.TvBatteryVoltage);
        TvBatteryUptime = (TextView) findViewById(R.id.TvBatteryUptime);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenNativeAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(R.string.battery_info_none);
        intent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        StringBuilder builder = new StringBuilder();
        int level = intent.getIntExtra("level", -1);
        int scale = intent.getIntExtra("scale", -1);
        builder.append((level == -1 || scale == -1) ? 50.0f : (((float) level) / ((float) scale)) * 100.0f);
        builder.append("%");
        int plugg = intent.getIntExtra("plugged", -1);
        int health = intent.getIntExtra("health", 1);
        long realtime = SystemClock.elapsedRealtime();

        StringBuilder stringBuilder = new StringBuilder();
        if (realtime > 86400000) {
            stringBuilder.append(realtime / 86400000);
            stringBuilder.append(" days ");
            realtime %= 86400000;
        }
        if (realtime > 3600000) {
            stringBuilder.append(realtime / 3600000);
            stringBuilder.append(" hours ");
            realtime %= 3600000;
        }
        if (realtime > 60000) {
            stringBuilder.append(realtime / 60000);
            stringBuilder.append(" min. ");
            realtime %= 60000;
        }
        if (realtime > 1000) {
            stringBuilder.append(realtime / 1000);
            stringBuilder.append(" sec.");
        }
        TvBatteryLevel.setText(builder.toString());
        TvBatteryStatus.setText(plugg == 4 ? "WIRELESS Charging" : plugg != 1 ? plugg != 2 ? "NOT Charging" : "USB Charging" : "AC Charging");
        TvBatteryTech.setText(intent.getExtras().getString("technology"));
        TvBatteryHealth.setText(health == 2 ? "Good" : health == 3 ? "Over Heat" : health == 4 ? "Dead" : health == 5 ? "Over Voltage" : health == 6 ? "Unspecified Failure" : "Unknown");
        TvBatteryVoltage.setText(String.valueOf(intent.getIntExtra("voltage", -1)));
        TvBatteryTemp.setText((((float) intent.getIntExtra("temperature", -1)) / 10.0f) + " *C");
        TvBatteryUptime.setText(stringBuilder.toString());
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