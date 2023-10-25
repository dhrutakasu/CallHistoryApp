package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.call.historyapp.Const.AppConst;
import com.call.historyapp.R;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemUsageActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvTotalRam, TvUsedRam, TvFreeRam, TvTotalInternal, TvFreeInernal, TvTotalExternal, TvFreeExternal;
    private long StrTotalRam, StrFreeRam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_usage);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvTotalRam = (TextView) findViewById(R.id.TvTotalRam);
        TvUsedRam = (TextView) findViewById(R.id.TvUsedRam);
        TvFreeRam = (TextView) findViewById(R.id.TvFreeRam);
        TvTotalInternal = (TextView) findViewById(R.id.TvTotalInternal);
        TvFreeInernal = (TextView) findViewById(R.id.TvFreeInernal);
        TvTotalExternal = (TextView) findViewById(R.id.TvTotalExternal);
        TvFreeExternal = (TextView) findViewById(R.id.TvFreeExternal);
    }

    private void CallInitListener() {
        IvBack.setOnClickListener(this);
    }

    private void CallInitActions() {
        TvTitle.setText("System Usage");
        Pattern compile = Pattern.compile("([a-zA-Z]+):\\s*(\\d+)");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/proc/meminfo", "r");
            while (true) {
                String readLine = randomAccessFile.readLine();
                if (readLine == null) {
                    break;
                }
                Matcher matcher = compile.matcher(readLine);
                if (matcher.find()) {
                    String group = matcher.group(1);
                    String group2 = matcher.group(2);
                    if (group.equalsIgnoreCase("MemTotal")) {
                        StrTotalRam = Long.parseLong(group2);
                    } else if (group.equalsIgnoreCase("MemFree") || group.equalsIgnoreCase("SwapFree")) {
                        StrFreeRam = Long.parseLong(group2);
                    }
                }
            }
            randomAccessFile.close();
            StrTotalRam *= 1024;
            StrFreeRam *= 1024;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                TvTotalRam.setText(AppConst.GetStorage((double) StrTotalRam));
                TvFreeRam.setText(AppConst.GetStorage((double) StrFreeRam));
                TvUsedRam.setText(AppConst.GetStorage((double) (StrTotalRam - StrFreeRam)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TvTotalInternal.setText(AppConst.GetStorage((double) (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()))));
            TvFreeInernal.setText(AppConst.GetStorage((double) (((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize()))));
            String str2 = "error";
            String strTotal;
            if (!Environment.getExternalStorageState().equals("mounted")) {
                strTotal = str2;
            } else {
                strTotal = AppConst.GetStorage((double) (((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())));
            }
            TvTotalExternal.setText(strTotal);
            TvFreeExternal.setText(AppConst.GetStorage((double) (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()))));
        }
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