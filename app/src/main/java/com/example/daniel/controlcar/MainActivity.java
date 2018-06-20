package com.example.daniel.controlcar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.daniel.common.HttpUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnTouchListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button goWBtn = (Button) findViewById(R.id.go_w);
        goWBtn.setOnTouchListener(this);
        Button goABtn = (Button) findViewById(R.id.go_a);
        goABtn.setOnTouchListener(this);
        Button goSBtn = (Button) findViewById(R.id.go_s);
        goSBtn.setOnTouchListener(this);
        Button goDBtn = (Button) findViewById(R.id.go_d);
        goDBtn.setOnTouchListener(this);
        Button goPLeftBtn = (Button) findViewById(R.id.p_left);
        goPLeftBtn.setOnTouchListener(this);
        Button goPRightBtn = (Button) findViewById(R.id.p_right);
        goPRightBtn.setOnTouchListener(this);
    }


    public void sendPost(final String key) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil httpUtil = new HttpUtil();
                String response = httpUtil.doPost("http://192.168.31.242:8888?key=" + key, "{}");
                Log.d("returnValue", response);
            }
        }).start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            action(view);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            stop();
        }
        return false;
    }

    private ScheduledExecutorService scheduledExecutor;

    /**
     * 开始计划任务，定时每100ms执行一次
     * @param view
     */
    private void action(final View view){
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                switch (view.getId()) {
                    case R.id.go_w:
                        sendPost("w");
                        break;
                    case R.id.go_a:
                        sendPost("a");
                        break;
                    case R.id.go_s:
                        sendPost("s");
                        break;
                    case R.id.go_d:
                        sendPost("d");
                        break;
                    case R.id.p_left:
                        sendPost("pl");
                        break;
                    case R.id.p_right:
                        sendPost("pr");
                        break;
                }
            }
        }, 0, 300, TimeUnit.MILLISECONDS);    //每间隔100ms发送Message
    }

    /**
     * 停止计划任务
     */
    private void stop() {
        if (scheduledExecutor != null) {
            scheduledExecutor.shutdownNow();
            scheduledExecutor = null;
        }
    }

}
