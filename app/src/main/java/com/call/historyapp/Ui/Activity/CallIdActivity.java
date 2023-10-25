package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.call.historyapp.R;

public class CallIdActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private TextView TvTitle, BtnLocation, BtnSystemUsage, BtnBattery, BtnDevice, BtnISD, BtnStd, BtnBank;
    private ImageView IvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_id);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        BtnLocation = (TextView) findViewById(R.id.BtnLocation);
        BtnSystemUsage = (TextView) findViewById(R.id.BtnSystemUsage);
        BtnBattery = (TextView) findViewById(R.id.BtnBattery);
        BtnDevice = (TextView) findViewById(R.id.BtnDevice);
        BtnISD = (TextView) findViewById(R.id.BtnISD);
        BtnStd = (TextView) findViewById(R.id.BtnStd);
        BtnBank = (TextView) findViewById(R.id.BtnBank);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
        BtnLocation.setOnClickListener(this);
        BtnSystemUsage.setOnClickListener(this);
        BtnBattery.setOnClickListener(this);
        BtnDevice.setOnClickListener(this);
        BtnISD.setOnClickListener(this);
        BtnStd.setOnClickListener(this);
        BtnBank.setOnClickListener(this);
    }

    private void CallInitActions() {
        TvTitle.setText("Call History");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                finish();
                break;
            case R.id.BtnLocation:
                startActivity(new Intent(context, FindLocationActivity.class));
                break;
            case R.id.BtnSystemUsage:
                startActivity(new Intent(context, SystemUsageActivity.class));
                break;
            case R.id.BtnBattery:
                startActivity(new Intent(context, BatteryActivity.class));
                break;
            case R.id.BtnDevice:
                startActivity(new Intent(context, DeviceActivity.class));
                break;
            case R.id.BtnISD:
                startActivity(new Intent(context, ISDActivity.class));
                break;
            case R.id.BtnStd:
                startActivity(new Intent(context, STDActivity.class));
                break;
            case R.id.BtnBank:
                startActivity(new Intent(context, BankActivity.class));
                break;
        }
    }
}