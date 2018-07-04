package com.example.ckc.aopdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.libaop.annotation.polling.Polling;
import com.example.libaop.annotation.timing.EndTime;
import com.example.libaop.annotation.timing.StartTime;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        test();
//        try {
//            start();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        new Test().test();

        System.out.println("oncreate");
        new ResponseListener() {
            @EndTime
            @Override
            public void onResponse() {

            }

            @Override
            public void onErrorResponse() {

            }

            @StartTime
            @Override
            public void onAsyncResponse() {
                System.out.println("时间间隔开始");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onResponse();
            }
        }.onAsyncResponse();

    }

    public int url=1;

    @Polling(expiry = 1)
    public void test() {
        Log.i("aoptest", "----------test-------------");
    }


    public int getUrl() {
        return 3;
    }

    @StartTime
    public void start() throws InterruptedException {
        Thread.sleep(3000);
        end();
    }

    @EndTime
    public void end() {

    }


    public class Test {
        @StartTime
        public Test() {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        @EndTime
        public void test() {

        }
    }


    public interface ResponseListener {

        void onResponse();

        void onErrorResponse();


        void onAsyncResponse();
    }
}
