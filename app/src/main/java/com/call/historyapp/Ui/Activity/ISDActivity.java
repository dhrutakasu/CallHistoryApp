package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.AppConst;
import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;

import java.util.HashMap;

public class ISDActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvCountry, TvISD, BtnSearch, TvOutPutOne, TvOutPutSec;
    private AutoCompleteTextView AutoISD, AutoCountry;
    private HashMap<String, String> stringStringHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isdactivity);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvCountry = (TextView) findViewById(R.id.TvCountry);
        TvISD = (TextView) findViewById(R.id.TvISD);
        TvOutPutOne = (TextView) findViewById(R.id.TvOutPutOne);
        TvOutPutSec = (TextView) findViewById(R.id.TvOutPutSec);
        AutoISD = (AutoCompleteTextView) findViewById(R.id.AutoISD);
        AutoCountry = (AutoCompleteTextView) findViewById(R.id.AutoCountry);
        BtnSearch = (TextView) findViewById(R.id.BtnSearch);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
        TvCountry.setOnClickListener(this);
        TvISD.setOnClickListener(this);
        BtnSearch.setOnClickListener(this);

        stringStringHashMap = new HashMap<>();
        for (int i = 0; i < AppConst.CountryList.length; i++) {
            stringStringHashMap.put(AppConst.CountryList[i].toString(), AppConst.ISDCode[i].toString());
        }
        AutoCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppConst.OutputOne = "Country Name : " + adapterView.getItemAtPosition(i).toString();
                AppConst.OutputSec = "ISD Code : " + stringStringHashMap.get(adapterView.getItemAtPosition(i)).toString();
            }
        });
        AutoISD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppConst.OutputSec = "Country Name : " + stringStringHashMap.get(adapterView.getItemAtPosition(i)).toString();
                AppConst.OutputOne = "ISD Code : " + adapterView.getItemAtPosition(i).toString();
            }
        });
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenNativeBannerAds(context,((ProgressBar) findViewById(R.id.progressBarAd)),(RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(R.string.isd_code);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, AppConst.CountryList);
        AutoCountry.setThreshold(1);
        AutoCountry.setAdapter(adapter);
        ArrayAdapter<String> adapterISd = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, AppConst.ISDCode);
        AutoISD.setThreshold(1);
        AutoISD.setAdapter(adapterISd);
        GotoBgChange(TvCountry, AutoCountry);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                finish();
                break;
            case R.id.TvCountry:
                stringStringHashMap = new HashMap<>();
                for (int i = 0; i < AppConst.CountryList.length; i++) {
                    stringStringHashMap.put(AppConst.CountryList[i].toString(), AppConst.ISDCode[i].toString());
                }
                GotoBgChange(TvCountry, AutoCountry);
                break;
            case R.id.TvISD:
                stringStringHashMap = new HashMap<>();
                for (int i = 0; i < AppConst.ISDCode.length; i++) {
                    stringStringHashMap.put(AppConst.ISDCode[i].toString(), AppConst.CountryList[i].toString());
                }
                GotoBgChange(TvISD, AutoISD);
                break;
            case R.id.BtnSearch:
                TvOutPutOne.setVisibility(View.VISIBLE);
                TvOutPutSec.setVisibility(View.VISIBLE);
                TvOutPutOne.setText(AppConst.OutputOne);
                TvOutPutSec.setText(AppConst.OutputSec);
                break;
        }
    }

    private void GotoBgChange(TextView view, AutoCompleteTextView autoView) {
        TvCountry.setBackgroundResource(R.drawable.ic_view_unpressed);
        TvCountry.setTextColor(getResources().getColor(R.color.black));
        TvISD.setBackgroundResource(R.drawable.ic_view_unpressed);
        TvISD.setTextColor(getResources().getColor(R.color.black));
        view.setBackgroundResource(R.drawable.ic_view_pressed);
        view.setTextColor(getResources().getColor(R.color.white));
        AutoCountry.setText("");
        AutoISD.setText("");
        TvOutPutOne.setText("");
        TvOutPutSec.setText("");
        AutoCountry.setVisibility(View.GONE);
        AutoISD.setVisibility(View.GONE);
        TvOutPutOne.setVisibility(View.GONE);
        TvOutPutSec.setVisibility(View.GONE);
        autoView.setVisibility(View.VISIBLE);
    }
}