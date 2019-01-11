package com.example.servicetest001;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    //初始化自定义的Binder对象
    private MyCustomBinder mBinder=new MyCustomBinder();

    public MyService() {
    }
    //自定义的内部Binder类
    class MyCustomBinder extends Binder{
        public void startDoSomething(){
            Log.i(TAG, "startDoSomething: ");
        }
        public void getProgress(){
            Log.i(TAG, "getProgress: ");
        }
        public void stopDoSomething(){
            Log.i(TAG, "stopDoSomething: ");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i(TAG, "onBind: ");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
//        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
