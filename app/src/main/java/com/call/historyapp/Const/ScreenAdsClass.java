package com.call.historyapp.Const;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.call.historyapp.Model.ShowAdsModel;
import com.call.historyapp.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.BuildConfig;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ScreenAdsClass {

    public static InterstitialAd interstitialAd;
    public static AdCallback adCallback;
    public static NativeAd nativeAd;
    private static int Counter = 1;

    public static void showFacebookBanner(Context context, com.facebook.ads.AdSize size, RelativeLayout relativeLayout, String id, String show, String bannerAd) {
        com.facebook.ads.AdView view = new com.facebook.ads.AdView(context, id, size);
        relativeLayout.addView(view);
        com.facebook.ads.AdListener listener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                if (((Activity) context).findViewById(R.id.NativeBannerAdContainer) != null) {
                    ((Activity) context).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.GONE);
                }
                ScreenAdsClass.showGoogleAdMobBanner(context, com.google.android.gms.ads.AdSize.BANNER, relativeLayout, bannerAd, show, id);
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        view.loadAd(view.buildLoadAdConfig().withAdListener(listener).build());
        if (show.equalsIgnoreCase("t")) {
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.INVISIBLE);
        }
    }

    public static void showGoogleAdMobBanner(Context context, AdSize size, RelativeLayout relativeLayout, String id, String show, String facebookbannerad) {
        AdView view = new AdView(context);
        view.setAdSize(size);
        view.setAdUnitId(id);

        view.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdClosed() {
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                if (((Activity) context).findViewById(R.id.NativeBannerAdContainer) != null) {
                    ((Activity) context).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.VISIBLE);
                }
                Initialize(context);
                ScreenAdsClass.showFacebookBanner(context, com.facebook.ads.AdSize.BANNER_HEIGHT_50, relativeLayout, facebookbannerad, show, id);
            }

            @Override
            public void onAdImpression() {
            }

            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdOpened() {
            }
        });
        view.loadAd(new AdRequest.Builder().build());
        relativeLayout.addView(view);
        if (show.equalsIgnoreCase("t")) {
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.INVISIBLE);
        }
    }

    public static void ShowScreenBannerAds(Context con, ProgressBar bar, RelativeLayout relativeLayout) {
        if (ScreenAdsClass.SetInternetOn(con)) {
            bar.setVisibility(View.VISIBLE);
            AppConst.LoadAdsData(con, new AppConst.LoadAdsId() {
                @Override
                public void getAdsIds(ShowAdsModel showAdsModel) {
                    bar.setVisibility(View.GONE);
                    if (showAdsModel.getBtype().equalsIgnoreCase("1")) {
                        if (((Activity) con).findViewById(R.id.NativeBannerAdContainer) != null) {
                            ((Activity) con).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.GONE);
                        }
                        ScreenAdsClass.showGoogleAdMobBanner(con, com.google.android.gms.ads.AdSize.BANNER, relativeLayout, showAdsModel.getBad(), showAdsModel.getLogin(), showAdsModel.getFbad());
                    } else {
                        if (((Activity) con).findViewById(R.id.NativeBannerAdContainer) != null) {
                            ((Activity) con).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.VISIBLE);
                        }
                        Initialize(con);
                        ScreenAdsClass.showFacebookBanner(con, com.facebook.ads.AdSize.BANNER_HEIGHT_50, relativeLayout, showAdsModel.getFbad(), showAdsModel.getLogin(), showAdsModel.getBad());
                    }
                }
            });
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public interface AdCallback {
        void AppCallback();

    }

    public static void ShowGoogleAdMobInterstitialAd(Activity activity, String id, String show, String facebookiad, AdCallback adCallback) {
        MobileAds.initialize(activity, initializationStatus -> {
        });

        interstitialAd.load(activity, id, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            public void onAdLoaded(InterstitialAd interstitialAd) {
                ScreenAdsClass.interstitialAd = interstitialAd;
                if (show.equalsIgnoreCase("t")) {
                    if (interstitialAd != null) {
                        interstitialAd.show(activity);
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                ScreenAdsClass.interstitialAd = null;
                                ScreenAdsClass.adCallback = adCallback;
                                if (ScreenAdsClass.adCallback != null) {
                                    ScreenAdsClass.adCallback.AppCallback();
                                    ScreenAdsClass.adCallback = null;
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError error) {
                                ScreenAdsClass.interstitialAd = null;
                            }
                        });
                        return;
                    }
                }

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                if (((Activity) activity).findViewById(R.id.NativeBannerAdContainer) != null) {
                    ((Activity) activity).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.VISIBLE);
                }
                Initialize(activity);
                ScreenAdsClass.ShowFacebookInterstitialAd((Activity) activity, facebookiad, id, show, adCallback);

                interstitialAd = null;
            }
        });
    }

    public static void ShowFacebookInterstitialAd(Activity activity, String id, String GoogleId, String show, AdCallback adCallback) {
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {

            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(activity, id);
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                ScreenAdsClass.adCallback = adCallback;
                if (ScreenAdsClass.adCallback != null) {
                    ScreenAdsClass.adCallback.AppCallback();
                    ScreenAdsClass.adCallback = null;
                }
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                if (((Activity) activity).findViewById(R.id.NativeBannerAdContainer) != null) {
                    ((Activity) activity).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.GONE);
                }
                ScreenAdsClass.ShowGoogleAdMobInterstitialAd((Activity) activity, GoogleId, show, id, adCallback);

            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (show.equalsIgnoreCase("t")) {
                    if (interstitialAd != null) {
                        interstitialAd.show();
                        return;
                    }
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };

        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                .build());

    }

    public static void ShowScreenInterstitialAd(Context context, AdCallback adCallback) {
        int click = ScreenAdsClass.Counter++;
        if (ScreenAdsClass.SetInternetOn(context)) {
            AppConst.LoadAdsData(context, new AppConst.LoadAdsId() {
                @Override
                public void getAdsIds(ShowAdsModel adsModel) {
                    if (click % Integer.parseInt(adsModel.getAdscount()) == 0) {
                        if (adsModel.getItype().equalsIgnoreCase("1")) {
                            if (!adsModel.getIad().equalsIgnoreCase("")) {
                                if (((Activity) context).findViewById(R.id.NativeBannerAdContainer) != null) {
                                    ((Activity) context).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.GONE);
                                }
                                ScreenAdsClass.ShowGoogleAdMobInterstitialAd((Activity) context, adsModel.getIad(), adsModel.getLogin(), adsModel.getFiad(), adCallback);
                            } else {
                                if (((Activity) context).findViewById(R.id.NativeBannerAdContainer) != null) {
                                    ((Activity) context).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.VISIBLE);
                                }
                                Initialize(context);
                                ScreenAdsClass.ShowFacebookInterstitialAd((Activity) context, adsModel.getFiad(), adsModel.getIad(), adsModel.getLogin(), adCallback);
                            }
                        } else {
                            if (!adsModel.getFiad().equalsIgnoreCase("")) {
                                if (((Activity) context).findViewById(R.id.NativeBannerAdContainer) != null) {
                                    ((Activity) context).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.VISIBLE);
                                }
                                Initialize(context);
                                ScreenAdsClass.ShowFacebookInterstitialAd((Activity) context, adsModel.getFiad(), adsModel.getIad(), adsModel.getLogin(), adCallback);
                            } else {
                                if (((Activity) context).findViewById(R.id.NativeBannerAdContainer) != null) {
                                    ((Activity) context).findViewById(R.id.NativeBannerAdContainer).setVisibility(View.GONE);
                                }
                                ScreenAdsClass.ShowGoogleAdMobInterstitialAd((Activity) context, adsModel.getIad(), adsModel.getLogin(), adsModel.getFiad(), adCallback);
                            }
                        }
                    } else {
                        adCallback.AppCallback();
                    }
                }
            });
        } else {
            adCallback.AppCallback();
        }
    }

    public static boolean SetInternetOn(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        if (connectivityManager.getNetworkInfo(0).getState() != NetworkInfo.State.DISCONNECTED) {
            connectivityManager.getNetworkInfo(1).getState();
            NetworkInfo.State state = NetworkInfo.State.DISCONNECTED;
        }
        return false;
    }

    public static void showGoogleAdMobNativeBanner(Activity activity, String id, RelativeLayout relativeLayout, String show, String appAdsButtonTextColor, String appAdsButtonColor, String backgroundcolor, String fnad) {
        AdLoader loader = new AdLoader.Builder(activity, id)
                .forNativeAd(nativeAd -> {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (nativeAd != null) {
                                NativeAdView nativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.layout_ad_mob_native_banner, (ViewGroup) null);
                                UnifiedNativeBannerAdView(nativeAd, nativeAdView, appAdsButtonColor, appAdsButtonTextColor, backgroundcolor);
                                relativeLayout.removeAllViews();

                                if (show.equalsIgnoreCase("t")) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    relativeLayout.addView(nativeAdView);
                                } else {
                                    relativeLayout.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }, 1000);
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        ScreenAdsClass.showFacebookNativeBanner((Activity) activity, ((NativeAdLayout) ((Activity) activity).findViewById(R.id.NativeBannerAdContainer)), fnad, relativeLayout, show, appAdsButtonTextColor, appAdsButtonColor, backgroundcolor, id);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build())
                .build();
        loader.loadAd(new AdManagerAdRequest.Builder().build());
    }

    @SuppressLint("WrongConstant")
    private static void UnifiedNativeBannerAdView(NativeAd ad, NativeAdView nativeAdView, String appAdsButtonColor, String appAdsButtonTextColor, String backgroundcolor) {
        nativeAdView.setMediaView((MediaView) nativeAdView.findViewById(R.id.MvAdMedia));
        nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.TvAdHeadline));
        nativeAdView.setBodyView(nativeAdView.findViewById(R.id.TvAdBody));
        Button button = nativeAdView.findViewById(R.id.BtnAdCallToAction);
        button.setSelected(true);
        nativeAdView.setCallToActionView(button);
        nativeAdView.setIconView(nativeAdView.findViewById(R.id.IvAdIcon));
        nativeAdView.setPriceView(nativeAdView.findViewById(R.id.TvAdPrice));
        nativeAdView.setStarRatingView(nativeAdView.findViewById(R.id.RbAdStars));
        nativeAdView.setStoreView(nativeAdView.findViewById(R.id.TvAdStore));
        nativeAdView.setAdvertiserView(nativeAdView.findViewById(R.id.TvAdAdvertiser));
        button.setBackgroundColor(Color.parseColor(appAdsButtonColor));
        button.setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((CardView) nativeAdView.findViewById(R.id.CardNative)).setCardBackgroundColor(Color.parseColor(backgroundcolor));
        ((TextView) nativeAdView.findViewById(R.id.TvAdBody)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) nativeAdView.findViewById(R.id.TvAdAdvertiser)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) nativeAdView.findViewById(R.id.TvAdHeadline)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) nativeAdView.findViewById(R.id.TvAdBody)).setSelected(true);
        ((TextView) nativeAdView.findViewById(R.id.TvAdAdvertiser)).setSelected(true);
        ((TextView) nativeAdView.findViewById(R.id.TvAdHeadline)).setSelected(true);
        ((TextView) nativeAdView.findViewById(R.id.TvAdPrice)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) nativeAdView.findViewById(R.id.TvAdStore)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) nativeAdView.findViewById(R.id.TvAd)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) nativeAdView.getHeadlineView()).setText(ad.getHeadline());
        if (ad.getBody() == null) {
            nativeAdView.getBodyView().setVisibility(4);
        } else {
            nativeAdView.getBodyView().setVisibility(0);
            ((TextView) nativeAdView.getBodyView()).setText(ad.getBody());
        }
        if (ad.getCallToAction() == null) {
            nativeAdView.getCallToActionView().setVisibility(4);
        } else {
            nativeAdView.getCallToActionView().setVisibility(0);
            ((Button) nativeAdView.getCallToActionView()).setText(ad.getCallToAction());
        }
        if (ad.getIcon() == null) {
            nativeAdView.getIconView().setVisibility(8);
        } else {
            ((ImageView) nativeAdView.getIconView()).setImageDrawable(ad.getIcon().getDrawable());
            nativeAdView.getIconView().setVisibility(0);
        }
        if (ad.getPrice() == null) {
            nativeAdView.getPriceView().setVisibility(4);
        } else {
            nativeAdView.getPriceView().setVisibility(0);
            ((TextView) nativeAdView.getPriceView()).setText(ad.getPrice());
        }
        if (ad.getStore() == null) {
            nativeAdView.getStoreView().setVisibility(4);
        } else {
            nativeAdView.getStoreView().setVisibility(0);
            ((TextView) nativeAdView.getStoreView()).setText(ad.getStore());
        }
        if (ad.getStarRating() == null) {
            nativeAdView.getStarRatingView().setVisibility(4);
        } else {
            ((RatingBar) nativeAdView.getStarRatingView()).setRating(ad.getStarRating().floatValue());
            nativeAdView.getStarRatingView().setVisibility(0);
        }
        if (ad.getAdvertiser() == null) {
            nativeAdView.getAdvertiserView().setVisibility(4);
        } else {
            ((TextView) nativeAdView.getAdvertiserView()).setText(ad.getAdvertiser());
            nativeAdView.getAdvertiserView().setVisibility(0);
        }
        nativeAdView.setNativeAd(ad);
    }

    public static void showFacebookNativeBanner(Activity activity, NativeAdLayout adLayout, String id, RelativeLayout relativeLayout, String show, String appAdsButtonTextColor, String appAdsButtonColor, String backgroundcolor, String nad) {
        NativeBannerAd nativeBannerAd = new NativeBannerAd(activity, id);
        NativeAdListener nativeAdListener = new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                ScreenAdsClass.showGoogleAdMobNativeBanner((Activity) activity, nad, relativeLayout, show, appAdsButtonTextColor, appAdsButtonColor, backgroundcolor, id);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeBannerAd == null || nativeBannerAd != ad) {
                    return;
                }
                FacebookNativeBannerAd(activity, adLayout, nativeBannerAd, appAdsButtonTextColor, appAdsButtonColor, backgroundcolor);
                relativeLayout.removeAllViews();
                if (show.equalsIgnoreCase("t")) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    relativeLayout.addView(adLayout);
                } else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };
        nativeBannerAd.loadAd(nativeBannerAd.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build());
    }

    private static void FacebookNativeBannerAd(Activity activity, NativeAdLayout nativeAdLayout, NativeBannerAd nativeBannerAd, String appAdsButtonTextColor, String appAdsButtonColor, String backgroundcolor) {
        if (nativeAdLayout != null) {
            nativeBannerAd.unregisterView();
            LayoutInflater inflater = LayoutInflater.from(activity);
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.layout_fb_native_banner_ad, nativeAdLayout, false);
            nativeAdLayout.addView(linearLayout);

            RelativeLayout adChoicesContainer = linearLayout.findViewById(R.id.RlAdChoicesContainer);
            AdOptionsView adOptionsView = new AdOptionsView(activity, nativeBannerAd, nativeAdLayout);
            adChoicesContainer.removeAllViews();
            adChoicesContainer.addView(adOptionsView, 0);
            LinearLayout ad_unit = linearLayout.findViewById(R.id.ad_unit);
            TextView nativeAdTitle = linearLayout.findViewById(R.id.TxtNativeAdTitle);
            TextView nativeAdSocialContext = linearLayout.findViewById(R.id.TxtNativeAdSocialContext);
            TextView sponsoredLabel = linearLayout.findViewById(R.id.TxtNativeAdSponsoredLabel);
            com.facebook.ads.MediaView nativeAdIconView = linearLayout.findViewById(R.id.MediaNativeIconView);
            Button nativeAdCallToAction = linearLayout.findViewById(R.id.BtnNativeAdCallAction);
            nativeAdCallToAction.setSelected(true);
            nativeAdTitle.setSelected(true);
            nativeAdSocialContext.setSelected(true);
            sponsoredLabel.setSelected(true);
            ad_unit.setBackgroundColor(Color.parseColor(backgroundcolor));
            nativeAdTitle.setTextColor(Color.parseColor(appAdsButtonTextColor));
            nativeAdSocialContext.setTextColor(Color.parseColor(appAdsButtonTextColor));
            sponsoredLabel.setTextColor(Color.parseColor(appAdsButtonTextColor));
            nativeAdCallToAction.setTextColor(Color.parseColor(appAdsButtonTextColor));
            nativeAdCallToAction.setBackgroundColor(Color.parseColor(appAdsButtonColor));

            nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
            nativeAdCallToAction.setVisibility(nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
            nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
            sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(nativeAdTitle);
            clickableViews.add(nativeAdCallToAction);
            nativeBannerAd.registerViewForInteraction(linearLayout, nativeAdIconView, clickableViews);
        }
    }

    public static void ShowScreenNativeBannerAds(Context context, ProgressBar progressBar, RelativeLayout relativeLayout) {
        if (ScreenAdsClass.SetInternetOn(context)) {
            progressBar.setVisibility(View.VISIBLE);
            AppConst.LoadAdsData(context, new AppConst.LoadAdsId() {
                @Override
                public void getAdsIds(ShowAdsModel showAdsModel) {
                    progressBar.setVisibility(View.GONE);
                    if (showAdsModel.getBtype().equalsIgnoreCase("1")) {
                        if (!showAdsModel.getNad().equalsIgnoreCase("")) {
                            ScreenAdsClass.showGoogleAdMobNativeBanner((Activity) context, showAdsModel.getNad(), relativeLayout, showAdsModel.getLogin(), showAdsModel.getAppAdsButtonTextColor(), showAdsModel.getAppAdsButtonColor(), showAdsModel.getBackgroundcolor(), showAdsModel.getFnad());
                        } else {
                            Initialize(context);
                            ScreenAdsClass.showFacebookNativeBanner((Activity) context, ((NativeAdLayout) ((Activity) context).findViewById(R.id.NativeBannerAdContainer)), showAdsModel.getFnbad(), relativeLayout, showAdsModel.getLogin(), showAdsModel.getAppAdsButtonTextColor(), showAdsModel.getAppAdsButtonColor(), showAdsModel.getBackgroundcolor(), showAdsModel.getNad());
                        }
                    } else {
                        if (!showAdsModel.getFnbad().equalsIgnoreCase("")) {
                            Initialize(context);
                            ScreenAdsClass.showFacebookNativeBanner((Activity) context, ((NativeAdLayout) ((Activity) context).findViewById(R.id.NativeBannerAdContainer)), showAdsModel.getFnbad(), relativeLayout, showAdsModel.getLogin(), showAdsModel.getAppAdsButtonTextColor(), showAdsModel.getAppAdsButtonColor(), showAdsModel.getBackgroundcolor(), showAdsModel.getNad());
                        } else {
                            ScreenAdsClass.showGoogleAdMobNativeBanner((Activity) context, showAdsModel.getNad(), relativeLayout, showAdsModel.getLogin(), showAdsModel.getAppAdsButtonTextColor(), showAdsModel.getAppAdsButtonColor(), showAdsModel.getBackgroundcolor(), showAdsModel.getFnad());
                        }
                    }
                }
            });
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public static void showGoogleAdMobNative(Activity activity, String id, RelativeLayout relativeLayout, String show, String appAdsButtonTextColor, String appAdsButtonColor, String backgroundcolor, String fnad) {
        AdLoader adLoader = new AdLoader.Builder(activity, id)
                .forNativeAd(nativeAd -> {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (nativeAd != null) {
                                NativeAdView nativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.layout_ad_mob_native, (ViewGroup) null);
                                UnifiedNativeAdView(nativeAd, nativeAdView, appAdsButtonColor, appAdsButtonTextColor, backgroundcolor);
                                relativeLayout.removeAllViews();

                                if (show.equalsIgnoreCase("t")) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    relativeLayout.addView(nativeAdView);
                                } else {
                                    relativeLayout.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }, 1000);
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        ScreenAdsClass.showFacebookNative((Activity) activity, ((NativeAdLayout) ((Activity) activity).findViewById(R.id.NativeBannerAdContainer)), fnad, relativeLayout, show, appAdsButtonTextColor, appAdsButtonColor, backgroundcolor, id);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build())
                .build();
        adLoader.loadAd(new AdManagerAdRequest.Builder().build());
    }

    public static void showFacebookNative(Activity activity, NativeAdLayout adLayout, String id, RelativeLayout RlNativeAd, String show, String appAdsButtonTextColor, String appAdsButtonColor, String backgroundcolor, String nad) {
        com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(activity, id);
        NativeAdListener nativeAdListener = new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                ScreenAdsClass.showGoogleAdMobNative((Activity) activity, nad, RlNativeAd, show, appAdsButtonTextColor, appAdsButtonColor, backgroundcolor, id);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                FacebookNativeAd(activity, adLayout, nativeAd, appAdsButtonTextColor, appAdsButtonColor, backgroundcolor);
                RlNativeAd.removeAllViews();
                if (show.equalsIgnoreCase("t")) {
                    RlNativeAd.setVisibility(View.VISIBLE);
                    RlNativeAd.addView(adLayout);
                } else {
                    RlNativeAd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };
        nativeAd.loadAd(nativeAd.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build());
    }

    private static void FacebookNativeAd(Activity activity, NativeAdLayout adLayout, com.facebook.ads.NativeAd ad, String appAdsButtonTextColor, String appAdsButtonColor, String backgroundcolor) {
        ad.unregisterView();
        LayoutInflater inflater = LayoutInflater.from(activity);
        LinearLayout inflate = (LinearLayout) inflater.inflate(R.layout.layout_fb_native_ad, adLayout, false);
        inflate.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, 850));
        adLayout.addView(inflate, new RelativeLayout.LayoutParams(MATCH_PARENT, 850));

        LinearLayout adChoicesContainer = inflate.findViewById(R.id.RlAdChoicesContainer);
        AdOptionsView adOptionsView = new AdOptionsView(activity, ad, adLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        LinearLayout ad_unit = inflate.findViewById(R.id.ad_unit);
        TextView nativeAdTitle = inflate.findViewById(R.id.TxtNativeAdTitle);
        TextView nativeAdSocialContext = inflate.findViewById(R.id.TxtNativeAdSocialContext);
        TextView sponsoredLabel = inflate.findViewById(R.id.TxtNativeAdSponsoredLabel);
        TextView nativeAdBody = inflate.findViewById(R.id.TxtNativeAdBody);
        com.facebook.ads.MediaView nativeAdIconView = inflate.findViewById(R.id.MediaNativeIconView);
        com.facebook.ads.MediaView MediaNativeAd = inflate.findViewById(R.id.MediaNativeAd);
        MediaNativeAd.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 500));
        Button nativeAdCallToAction = inflate.findViewById(R.id.BtnNativeAdCallAction);
        nativeAdCallToAction.setSelected(true);
        nativeAdTitle.setSelected(true);
        nativeAdSocialContext.setSelected(true);
        sponsoredLabel.setSelected(true);
        ad_unit.setBackgroundColor(Color.parseColor(backgroundcolor));
        nativeAdTitle.setTextColor(Color.parseColor(appAdsButtonTextColor));
        nativeAdSocialContext.setTextColor(Color.parseColor(appAdsButtonTextColor));
        sponsoredLabel.setTextColor(Color.parseColor(appAdsButtonTextColor));
        nativeAdCallToAction.setTextColor(Color.parseColor(appAdsButtonTextColor));
        nativeAdBody.setTextColor(Color.parseColor(appAdsButtonTextColor));
        nativeAdCallToAction.setBackgroundColor(Color.parseColor(appAdsButtonColor));

        nativeAdCallToAction.setText(ad.getAdCallToAction());
        nativeAdBody.setText(ad.getAdBodyText());
        nativeAdCallToAction.setVisibility(ad.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(ad.getAdvertiserName());
        nativeAdSocialContext.setText(ad.getAdSocialContext());
        sponsoredLabel.setText(ad.getSponsoredTranslation());

        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        ad.registerViewForInteraction(inflate, MediaNativeAd, nativeAdIconView, clickableViews);
    }

    public static void ShowScreenNativeAds(Context context, ProgressBar progressBar, RelativeLayout relativeLayout) {
        if (ScreenAdsClass.SetInternetOn(context)) {
            progressBar.setVisibility(View.VISIBLE);
            AppConst.LoadAdsData(context, new AppConst.LoadAdsId() {
                @Override
                public void getAdsIds(ShowAdsModel showAdsModel) {
                    progressBar.setVisibility(View.GONE);
                    if (showAdsModel.getBtype().equalsIgnoreCase("1")) {
                        if (!showAdsModel.getNad().equalsIgnoreCase("")) {
                            ScreenAdsClass.showGoogleAdMobNative((Activity) context, showAdsModel.getNad(), relativeLayout, showAdsModel.getLogin(), showAdsModel.getAppAdsButtonTextColor(), showAdsModel.getAppAdsButtonColor(), showAdsModel.getBackgroundcolor(), showAdsModel.getFnad());
                        } else {
                            Initialize(context);
                            ScreenAdsClass.showFacebookNative((Activity) context, ((NativeAdLayout) ((Activity) context).findViewById(R.id.NativeBannerAdContainer)), showAdsModel.getFnad(), relativeLayout, showAdsModel.getLogin(), showAdsModel.getAppAdsButtonTextColor(), showAdsModel.getAppAdsButtonColor(), showAdsModel.getBackgroundcolor(), showAdsModel.getNad());
                        }
                    } else {
                        if (showAdsModel.getFnad().equalsIgnoreCase("")) {
                            ScreenAdsClass.showGoogleAdMobNative((Activity) context, showAdsModel.getNad(), relativeLayout, showAdsModel.getLogin(), showAdsModel.getAppAdsButtonTextColor(), showAdsModel.getAppAdsButtonColor(), showAdsModel.getBackgroundcolor(), showAdsModel.getFnad());
                        } else {
                            Initialize(context);
                            ScreenAdsClass.showFacebookNative((Activity) context, ((NativeAdLayout) ((Activity) context).findViewById(R.id.NativeBannerAdContainer)), showAdsModel.getFnad(), relativeLayout, showAdsModel.getLogin(), showAdsModel.getAppAdsButtonTextColor(), showAdsModel.getAppAdsButtonColor(), showAdsModel.getBackgroundcolor(), showAdsModel.getNad());
                        }
                    }
                }
            });
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public static void Initialize(Context context) {
        AudienceNetworkAds.InitListener InitListener = new AudienceNetworkAds.InitListener() {
            @Override
            public void onInitialized(AudienceNetworkAds.InitResult initResult) {
                if (!AudienceNetworkAds.isInitialized(context)) {
                    if (BuildConfig.DEBUG) {
                        AdSettings.setTestMode(true);
                    }


                }
            }
        };
        Runnable logcatMonitor = new Runnable() {
            @Override
            public void run() {
                // Read logcat to find the test device hash
                try {
                    Process process = Runtime.getRuntime().exec("logcat -d");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.contains("FacebookAds") && line.contains("test mode device hash")) {
                            String testDeviceHash = line.substring(line.indexOf("test mode device hash:") + "test mode device hash:".length()).trim();
                            Log.d("FacebookAds", "Test device hash: " + testDeviceHash);
                            AdSettings.addTestDevice(testDeviceHash);
//                            AdSettings.addTestDevice("0fc7d369-b31e-4337-a907-697421dd2ddb");

                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(logcatMonitor).start();
        AudienceNetworkAds
                .buildInitSettings(context)
                .withInitListener(InitListener)
                .initialize();
    }
    @SuppressLint("WrongConstant")
    private static void UnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView, String appAdsButtonColor, String appAdsButtonTextColor, String backgroundcolor) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.MvAdMedia));
        adView.setHeadlineView(adView.findViewById(R.id.TvAdHeadline));
        adView.setBodyView(adView.findViewById(R.id.TvAdBody));
        Button button = adView.findViewById(R.id.BtnAdCallToAction);
        adView.setCallToActionView(button);
        adView.setIconView(adView.findViewById(R.id.IvAdIcon));
        adView.setPriceView(adView.findViewById(R.id.TvAdPrice));
        adView.setStarRatingView(adView.findViewById(R.id.RbAdStars));
        adView.setStoreView(adView.findViewById(R.id.TvAdStore));
        adView.setAdvertiserView(adView.findViewById(R.id.TvAdAdvertiser));
        button.setBackgroundColor(Color.parseColor(appAdsButtonColor));
        button.setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((CardView) adView.findViewById(R.id.CardNative)).setCardBackgroundColor(Color.parseColor(backgroundcolor));
        ((TextView) adView.findViewById(R.id.TvAdBody)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) adView.findViewById(R.id.TvAdHeadline)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) adView.findViewById(R.id.TvAdPrice)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) adView.findViewById(R.id.TvAdStore)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) adView.findViewById(R.id.TvAdAdvertiser)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) adView.findViewById(R.id.TvAd)).setTextColor(Color.parseColor(appAdsButtonTextColor));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(4);
        } else {
            adView.getBodyView().setVisibility(0);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(4);
        } else {
            adView.getCallToActionView().setVisibility(0);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(8);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(0);
        }
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(4);
        } else {
            adView.getPriceView().setVisibility(0);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(4);
        } else {
            adView.getStoreView().setVisibility(0);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(4);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(0);
        }
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(4);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(0);
        }
        adView.setNativeAd(nativeAd);
    }
}