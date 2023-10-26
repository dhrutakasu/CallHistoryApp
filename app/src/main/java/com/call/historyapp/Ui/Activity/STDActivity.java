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

public class STDActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvCity, TvSTD, BtnSearch, TvOutPutOne, TvOutPutSec;
    private AutoCompleteTextView AutoSTD, AutoCity;
    private HashMap<String, String> stringStringHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdactivity);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvCity = (TextView) findViewById(R.id.TvCity);
        TvSTD = (TextView) findViewById(R.id.TvSTD);
        TvOutPutOne = (TextView) findViewById(R.id.TvOutPutOne);
        TvOutPutSec = (TextView) findViewById(R.id.TvOutPutSec);
        AutoSTD = (AutoCompleteTextView) findViewById(R.id.AutoSTD);
        AutoCity = (AutoCompleteTextView) findViewById(R.id.AutoCity);
        BtnSearch = (TextView) findViewById(R.id.BtnSearch);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
        TvCity.setOnClickListener(this);
        TvSTD.setOnClickListener(this);
        BtnSearch.setOnClickListener(this);
        stringStringHashMap = new HashMap<>();
        for (int i = 0; i < AppConst.CityList.length; i++) {
            stringStringHashMap.put(AppConst.CityList[i].toString(), AppConst.STDCode[i].toString());
        }
        AutoCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppConst.OutputOne = "City Name : " + adapterView.getItemAtPosition(i).toString();
                AppConst.OutputSec = "STD Code : " + "0" + stringStringHashMap.get(adapterView.getItemAtPosition(i)).toString();
            }
        });
        AutoSTD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppConst.OutputSec = "City Name : " + stringStringHashMap.get(adapterView.getItemAtPosition(i)).toString();
                AppConst.OutputOne = "STD Code : " + "0" + adapterView.getItemAtPosition(i).toString();
            }
        });
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenNativeBannerAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(R.string.std_code);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, AppConst.CityList);
        AutoCity.setThreshold(1);
        AutoCity.setAdapter(adapter);
        ArrayAdapter<String> adapterSTD = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, AppConst.STDCode);
        AutoSTD.setThreshold(1);
        AutoSTD.setAdapter(adapterSTD);
        GotoBgChange(TvCity, AutoCity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                finish();
                break;
            case R.id.TvCity:
                stringStringHashMap = new HashMap<>();
                for (int i = 0; i < AppConst.CityList.length; i++) {
                    stringStringHashMap.put(AppConst.CityList[i].toString(), AppConst.STDCode[i].toString());
                }
                GotoBgChange(TvCity, AutoCity);
                break;
            case R.id.TvSTD:
                stringStringHashMap = new HashMap<>();
                for (int i = 0; i < AppConst.STDCode.length; i++) {
                    stringStringHashMap.put(AppConst.STDCode[i].toString(), AppConst.CityList[i].toString());
                }
                GotoBgChange(TvSTD, AutoSTD);
                break;
            case R.id.BtnSearch:
                TvOutPutOne.setText(AppConst.OutputOne);
                TvOutPutSec.setText(AppConst.OutputSec);
                TvOutPutOne.setVisibility(View.VISIBLE);
                TvOutPutSec.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void GotoBgChange(TextView view, AutoCompleteTextView autoView) {
        TvCity.setBackgroundResource(R.drawable.ic_view_unpressed);
        TvCity.setTextColor(getResources().getColor(R.color.black));
        TvSTD.setBackgroundResource(R.drawable.ic_view_unpressed);
        TvSTD.setTextColor(getResources().getColor(R.color.black));
        view.setBackgroundResource(R.drawable.ic_view_pressed);
        view.setTextColor(getResources().getColor(R.color.white));
        AutoCity.setText("");
        AutoSTD.setText("");
        TvOutPutOne.setText("");
        TvOutPutSec.setText("");
        TvOutPutOne.setVisibility(View.GONE);
        TvOutPutSec.setVisibility(View.GONE);
        AutoCity.setVisibility(View.GONE);
        AutoSTD.setVisibility(View.GONE);
        autoView.setVisibility(View.VISIBLE);
    }
}