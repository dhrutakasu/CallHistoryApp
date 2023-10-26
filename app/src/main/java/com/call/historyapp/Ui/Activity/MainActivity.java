package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.call.historyapp.BuildConfig;
import com.call.historyapp.Const.AppConst;
import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private TextView BtnStart, BtnMoreApps;
    private ConstraintLayout ConsRate, ConsShare, ConsPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        BtnStart = (TextView) findViewById(R.id.BtnStart);
        BtnMoreApps = (TextView) findViewById(R.id.BtnMoreApps);
        ConsRate = (ConstraintLayout) findViewById(R.id.ConsRate);
        ConsShare = (ConstraintLayout) findViewById(R.id.ConsShare);
        ConsPrivacy = (ConstraintLayout) findViewById(R.id.ConsPrivacy);
    }

    private void CallInitListener() {
        BtnStart.setOnClickListener(this);
        BtnMoreApps.setOnClickListener(this);
        ConsRate.setOnClickListener(this);
        ConsShare.setOnClickListener(this);
        ConsPrivacy.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenNativeAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BtnStart:
                GotoStart();
                break;
            case R.id.BtnMoreApps:
                break;
            case R.id.ConsRate:
                GotoRate();
                break;
            case R.id.ConsShare:
                GotoShare();
                break;
            case R.id.ConsPrivacy:
                startActivity(new Intent(context, PolicyActivity.class));
                break;
        }
    }

    private void GotoStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String s = Manifest.permission.CALL_PHONE;
            String s1 = Manifest.permission.READ_PHONE_STATE;
            String s2 = Manifest.permission.READ_MEDIA_IMAGES;
            Dexter.withContext(context)
                    .withPermissions(s, s1, s2)
                    .withListener(new MultiplePermissionsListener() {
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                startActivity(new Intent(context, StartActivity.class));
                            }
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                AppConst.showSettingsDialog(MainActivity.this);
                            }
                        }

                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissionToken) {
                            AppConst.showPermissionDialog(MainActivity.this, permissionToken);
                        }
                    })
                    .onSameThread()
                    .check();
        } else {
            String s = Manifest.permission.CALL_PHONE;
            String s1 = Manifest.permission.READ_PHONE_STATE;
            String s2 = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            String s3 = Manifest.permission.READ_EXTERNAL_STORAGE;
            Dexter.withContext(context)
                    .withPermissions(s, s1, s2, s3)
                    .withListener(new MultiplePermissionsListener() {
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                startActivity(new Intent(context, StartActivity.class));
                            }
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                AppConst.showSettingsDialog(MainActivity.this);
                            }
                        }

                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissionToken) {
                            AppConst.showPermissionDialog(MainActivity.this, permissionToken);
                        }
                    })
                    .onSameThread()
                    .check();
        }
    }

    private void GotoRate() {
        Uri RateUri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, RateUri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    private void GotoShare() {
        try {
            Intent ShareIntent = new Intent(Intent.ACTION_SEND);
            ShareIntent.setType("text/plain");
            ShareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String msg = "\nLet me recommend you this application\n\n";
            msg = msg + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            ShareIntent.putExtra(Intent.EXTRA_TEXT, msg);
            startActivity(Intent.createChooser(ShareIntent, "share via"));
        } catch (Exception e) {
            e.getMessage();
        }
    }
}