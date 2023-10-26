package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.call.historyapp.Const.AppConst;
import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.Model.ShowAdsModel;
import com.call.historyapp.R;

public class PolicyActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvNotFOund;
    private WebView WebPolicy;
    private ProgressBar ProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvNotFOund = (TextView) findViewById(R.id.TvNotFOund);
        WebPolicy = (WebView) findViewById(R.id.WebPolicy);
        ProgressDialog = (ProgressBar) findViewById(R.id.ProgressDialog);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
    }

    private void CallInitActions() {
        ScreenAdsClass.ShowScreenBannerAds(context, ((ProgressBar) findViewById(R.id.progressBarAd)), (RelativeLayout) findViewById(R.id.RlAdvertisement));
        IvBack.setVisibility(View.VISIBLE);
        ProgressDialog.setVisibility(View.VISIBLE);
        WebPolicy.setVisibility(View.GONE);
        TvNotFOund.setVisibility(View.GONE);
        TvTitle.setText("Privacy Policy");
        if (ScreenAdsClass.SetInternetOn(this)) {
            AppConst.LoadAdsData(PolicyActivity.this, new AppConst.LoadAdsId() {
                @Override
                public void getAdsIds(ShowAdsModel showAdsModel) {
                    if (!showAdsModel.getAppPrivacyPolicyLink().equalsIgnoreCase("")) {
                        WebPolicy.setInitialScale(100);
                        WebSettings webPrivacySettings = WebPolicy.getSettings();
                        webPrivacySettings.setJavaScriptEnabled(true);
                        webPrivacySettings.setTextZoom(webPrivacySettings.getTextZoom() + 70);
                        WebPolicy.loadUrl(showAdsModel.getAppPrivacyPolicyLink());
                        WebPolicy.setWebViewClient(new AppWebViewClients(ProgressDialog));
                    } else {
                        ProgressDialog.setVisibility(View.GONE);
                        TvNotFOund.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
        }
    }

    private class AppWebViewClients extends WebViewClient {

        private final View progressBar;

        public AppWebViewClients(View progressBar) {
            this.progressBar = progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            WebPolicy.setVisibility(View.VISIBLE);
        }
    }
}