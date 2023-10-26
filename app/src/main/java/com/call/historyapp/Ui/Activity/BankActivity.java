package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.AppConst;
import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.Model.BankModel;
import com.call.historyapp.R;
import com.call.historyapp.Ui.Adapter.BankAdapter;

public class BankActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private RecyclerView RvBankList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        RvBankList = (RecyclerView) findViewById(R.id.RvBankList);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenNativeAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(R.string.bank_list);
        RvBankList.setLayoutManager(new LinearLayoutManager(context));
        BankAdapter bankAdapter = new BankAdapter(context, AppConst.BankList(), new BankAdapter.BankCLick() {
            @Override
            public void BankListen(int position, BankModel bankModel) {
                ScreenAdsClass.ShowScreenInterstitialAd(context, new ScreenAdsClass.AdCallback() {
                    @Override
                    public void AppCallback() {
                        AppConst.BankModels = bankModel;
                        startActivity(new Intent(context, ShowBankDataActivity.class));
                    }
                });
            }
        });
        RvBankList.setAdapter(bankAdapter);
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