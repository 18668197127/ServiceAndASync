package com.example.servicetest001;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private MyService.MyCustomBinder myCustomBinder;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: "+name);
            myCustomBinder= (MyService.MyCustomBinder) service;
            myCustomBinder.startDoSomething();
            myCustomBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: "+name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);
        Button button2=findViewById(R.id.button2);
        Button button3=findViewById(R.id.button3);
        Button button4=findViewById(R.id.button4);
        Button button6=findViewById(R.id.button6);
        Button button8=findViewById(R.id.button8);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button6.setOnClickListener(this);
        button8.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.button:
                Intent intent=new Intent(MainActivity.this,MyService.class);
                startService(intent);
                break;
            case R.id.button2:
                Intent intent2=new Intent(MainActivity.this,MyService.class);
                stopService(intent2);
                break;
            case R.id.button3:
                Intent intent3=new Intent(MainActivity.this,MyService.class);
                bindService(intent3,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.button4:
                unbindService(serviceConnection);
                break;
            case R.id.button6:
                finish();
                break;
            case R.id.button8:
//                Intent intent8=new Intent(MainActivity.this,MyService.class);
//                PendingIntent pi=PendingIntent.getActivity(this,0,intent8,0);
//                Notification notification=new NotificationCompat.Builder(this)
//                        .setContentTitle("这是标题")
//                        .setContentText("这是内容这是内容这是内容")
//                        .setWhen(System.currentTimeMillis())
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentIntent(pi)
//                        .build();
                break;
        }
    }
}
