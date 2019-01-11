package com.example.asynctest001;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

public class Runnable1 implements Runnable {
    private static final String TAG = "Runnable1";
    private Handler handler;

    public Handler getHandler() {
        return handler;
    }
    @SuppressLint("HandlerLeak")
    @Override
    public void run() {

        Log.i(TAG, "run: 线程1开始");
        Looper.prepare();
        handler = new Handler(Looper.myLooper()){
            public void handleMessage(Message msg) {
                //这里处理消息
                Log.i(TAG, "handleMessage:收到消息 "+msg.what);
                //只接受一次消息就退出Looper循环
                handler.getLooper().quit();
            };
        };
        Looper.loop();
        Log.i(TAG, "run: "+Process.myTid());
    }
}
