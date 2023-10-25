package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.call.historyapp.R;

public class DeviceActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvDeviceName, TvModelName, TvBrandName, TvProduct, TvIMEI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvDeviceName = (TextView) findViewById(R.id.TvDeviceName);
        TvModelName = (TextView) findViewById(R.id.TvModelName);
        TvBrandName = (TextView) findViewById(R.id.TvBrandName);
        TvProduct = (TextView) findViewById(R.id.TvProduct);
        TvIMEI = (TextView) findViewById(R.id.TvIMEI);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
    }

    private void CallInitActions() {
        TvTitle.setText("Battery Info");
        String Device;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Device = Settings.Secure.getString(getContentResolver(), "android_id");
        } else {
            TelephonyManager systemService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (systemService.getDeviceId() != null) {
                Device = systemService.getDeviceId();
            } else {
                Device = Settings.Secure.getString(getContentResolver(), "android_id");
            }
        }
        TvDeviceName.setText(Build.MODEL);
        TvBrandName.setText(Build.BRAND);
        TvProduct.setText(Build.PRODUCT);
        TvIMEI.setText(Device);
        TvModelName.setText(Settings.Secure.getString(getContentResolver(), "bluetooth_name"));
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