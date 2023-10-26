package com.call.historyapp.Ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;
import com.yesterselga.countrypicker.CountryPicker;
import com.yesterselga.countrypicker.Theme;


public class CallHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack;
    private ImageView IvCountryIcon;
    private TextView TvTitle, TvCountryCode, TvCountryName, BtnSubmit;
    private EditText EdtNumber;
    private ConstraintLayout ConsCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        ConsCountry = (ConstraintLayout) findViewById(R.id.ConsCountry);
        TvCountryName = (TextView) findViewById(R.id.TvCountryName);
        EdtNumber = (EditText) findViewById(R.id.EdtNumber);
        TvCountryCode = (TextView) findViewById(R.id.TvCountryCode);
        BtnSubmit = (TextView) findViewById(R.id.BtnSubmit);
        IvCountryIcon = (ImageView) findViewById(R.id.IvCountryIcon);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
        ConsCountry.setOnClickListener(this);
        BtnSubmit.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenNativeAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(R.string.call_history);
        TvCountryName.setText("India");
        IvCountryIcon.setImageResource(R.drawable.flag_in);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                finish();
                break;
            case R.id.ConsCountry:
                GotoCountryDialog();
                break;
            case R.id.BtnSubmit:
                if (EdtNumber.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (EdtNumber.getText().length() >= 10) {
                    ScreenAdsClass.ShowScreenInterstitialAd(context, new ScreenAdsClass.AdCallback() {
                        @Override
                        public void AppCallback() {
                            Intent intent = new Intent(context, SHowEmailAddressActivity.class);
                            intent.putExtra("phone", EdtNumber.getText().toString());
                            intent.putExtra("code", TvCountryCode.getText().toString());
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(context, "Enter Proper Mobile Number", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void GotoCountryDialog() {
        CountryPicker picker = CountryPicker.newInstance("Select Country", Theme.LIGHT);
        picker.setListener((name, code, dialCode, flagDrawableResID) -> {
            TvCountryName.setText(name);
            IvCountryIcon.setImageResource(flagDrawableResID);
            TvCountryCode.setText(dialCode);

            picker.dismiss();
        });
        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
    }
}