package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;

public class DeviceActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_READ_PHONE_STATE = 101;
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
        ScreenAdsClass.ShowScreenNativeBannerAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(R.string.battery_info_none);
        String Device;
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
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
                break;

            default:
                break;
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