package com.example.ckc.aopdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.libaop.annotation.Polling;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    @Polling(expiry = 3)
    public void test(){
        Log.i("aoptest","----------test-------------");
    }
}
