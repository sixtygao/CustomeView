package com.murphy.china.testannonation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.murphy.china.testannonation.view.MyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnStart)
    Button mBtnStart;
    @BindView(R.id.btnStop)
    Button mBtnStop;

    private MyView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mView = (MyView) findViewById(R.id.view);
    }

    @OnClick(R.id.btnStart)
    public void start() {
        mView.start();
    }

    @OnClick(R.id.btnStop)
    public void stop() {
        mView.stop();
    }
}
