package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.AppConst;
import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.R;

public class ShowBankDataActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack, IvBankIcon;
    private TextView TvTitle, TvBankName, TvCheckBalanceOutput, TvCheckCustomerOutput;
    private ConstraintLayout ConsBankBalance, ConsBankCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bank_data);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvBankName = (TextView) findViewById(R.id.TvBankName);
        TvCheckBalanceOutput = (TextView) findViewById(R.id.TvCheckBalanceOutput);
        TvCheckCustomerOutput = (TextView) findViewById(R.id.TvCheckCustomerOutput);
        IvBankIcon = (ImageView) findViewById(R.id.IvBankIcon);
        ConsBankBalance = (ConstraintLayout) findViewById(R.id.ConsBankBalance);
        ConsBankCustomer = (ConstraintLayout) findViewById(R.id.ConsBankCustomer);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
        ConsBankBalance.setOnClickListener(this);
        ConsBankCustomer.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenBannerAds(context,((ProgressBar) findViewById(R.id.progressBarAd)),(RelativeLayout) findViewById(R.id.RlAdvertisement));
        TvTitle.setText(AppConst.BankModels.getBankName());
        IvBankIcon.setImageResource(AppConst.BankModels.getBankIcon());
        TvBankName.setText(AppConst.BankModels.getBankName());
        TvCheckBalanceOutput.setText(AppConst.BankModels.getBankBalance());
        TvCheckCustomerOutput.setText(AppConst.BankModels.getBankCustomer());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                finish();
                break;
            case R.id.ConsBankBalance:
                GotoCall(AppConst.BankModels.getBankBalance());
                break;
            case R.id.ConsBankCustomer:
                GotoCall(AppConst.BankModels.getBankCustomer());
                break;
        }
    }

    private void GotoCall(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }
}