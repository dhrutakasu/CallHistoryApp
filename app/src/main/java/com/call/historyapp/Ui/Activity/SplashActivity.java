package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.call.historyapp.Const.AppConst;
import com.call.historyapp.Const.ScreenAdsClass;
import com.call.historyapp.Model.ShowAdsModel;
import com.call.historyapp.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressSplash = findViewById(R.id.progressSplash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CallInitViews();
    }

    private void CallInitViews() {
        if (ScreenAdsClass.SetInternetOn(this)) {
            progressSplash.setVisibility(View.VISIBLE);

            AppConst.LoadAdsData(SplashActivity.this, new AppConst.LoadAdsId() {
                @Override
                public void getAdsIds(ShowAdsModel showAdsModel) {
                    if (!showAdsModel.getOad().equalsIgnoreCase("")) {
                        fetchAd(showAdsModel);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressSplash.setVisibility(View.GONE);
                                ScreenAdsClass.ShowScreenInterstitialAd(SplashActivity.this, new ScreenAdsClass.AdCallback() {
                                    @Override
                                    public void AppCallback() {
                                        GotoActivity();
                                    }
                                });
                            }
                        }, 2000L);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Please Turn Your Internet..", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void fetchAd(ShowAdsModel showAdsModel) {
        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            private AppOpenAd openAd;

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                openAd = null;
                GotoActivity();
            }

            @Override
            public void onAdLoaded(AppOpenAd ad) {
                openAd = ad;
                if (openAd != null) {
                    openAd.show(SplashActivity.this);
                    openAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            progressSplash.setVisibility(View.GONE);
                            openAd = null;
                            GotoActivity();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError error) {
                            openAd = null;
                            GotoActivity();
                            progressSplash.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                        }
                    });
                }
            }
        };
        AdRequest build = new AdRequest.Builder().build();
        AppOpenAd.load(this, showAdsModel.getOad(), build, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    private void GotoActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}