package com.think.guoyh.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.base.gyh.baselib.base.SupportActivity;
import com.jaeger.library.StatusBarUtil;

import com.think.guoyh.R;

public class SplashActivity extends SupportActivity {

    private boolean isTo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtil.setTranslucent(this,0);
        initView();
    }

    public void startToMain() {
        if(isTo){
            isTo = false;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void initView() {
        TextView jump = (TextView) findViewById(R.id.jump);
        jump.postDelayed(new Runnable() {
            @Override
            public void run() {
                startToMain();
            }
        },3000);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToMain();
            }
        });
    }
}
