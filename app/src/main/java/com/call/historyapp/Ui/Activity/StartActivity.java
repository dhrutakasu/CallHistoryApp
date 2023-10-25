package com.call.historyapp.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.call.historyapp.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private TextView TCallId, TCallHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        CallInitViews();
        CallInitListener();
        CallInitActions();
    }

    private void CallInitViews() {
        context = this;
        TCallId = (TextView) findViewById(R.id.TCallId);
        TCallHistory = (TextView) findViewById(R.id.TCallHistory);
    }

    private void CallInitListener() {
        TCallId.setOnClickListener(this);
        TCallHistory.setOnClickListener(this);
    }

    private void CallInitActions() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TCallHistory:
                startActivity(new Intent(context, CallHistoryActivity.class));
                break;
            case R.id.TCallId:
                startActivity(new Intent(context, CallIdActivity.class));
                break;
        }
    }
}