package com.example.asynctest001;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;

public class Runnable2 implements Runnable {

    private static final String TAG = "Runnable2";

    private Handler handler;

    public Runnable2(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        Log.i(TAG, "run: 线程2开始");
//        Message message=new Message();
        Message message=Message.obtain();
//        Message message=handler.obtainMessage();
        message.what=1;
        handler.sendMessage(message);
        Log.i(TAG, "run: "+Process.myTid());
//        handler.getLooper().quit();
    }
}
