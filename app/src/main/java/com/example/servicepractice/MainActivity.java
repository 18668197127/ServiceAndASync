package com.example.servicepractice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //初始化对象
    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder= (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startDownload= (Button) findViewById(R.id.start_download);
        Button pauseDownload= (Button) findViewById(R.id.pause_download);
        Button cancelDownload= (Button) findViewById(R.id.cancel_download);
        startDownload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = 26)
            @Override
            public void onClick(View v) {
//                String url="https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
//                String url="https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
//                String url="http://f.hiphotos.baidu.com/image/pic/item/b7fd5266d016092446517fdadd0735fae7cd34ff.jpg";
                String url="https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
                downloadBinder.startDownload(url);
            }
        });
        pauseDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBinder.pauseDownload();
            }
        });
        cancelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBinder.cancelDownload();
            }
        });

        //绑定启动服务
        Intent intent=new Intent(this,DownloadService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);

        //申请写入权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
//        Log.i(TAG, "onCreate: "+Environment.getDataDirectory().getAbsolutePath());
//        Log.i(TAG, "onCreate: "+Environment.getRootDirectory().getAbsolutePath());
//        Log.i(TAG, "onCreate: "+Environment.getDownloadCacheDirectory().getAbsolutePath());
//        Log.i(TAG, "onCreate: "+Environment.getExternalStorageDirectory().getAbsolutePath());
//        Log.i(TAG, "onCreate: "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());
//        Log.i(TAG, "onCreate: "+this.getFileStreamPath("custom1").getAbsolutePath());
//        Log.i(TAG, "onCreate: "+this.getDir("custom2", Context.MODE_PRIVATE).getAbsolutePath());
//        Log.i(TAG, "onCreate: "+this.getObbDir().getAbsolutePath());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String [] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法正常使用",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //断开服务的绑定
        unbindService(connection);
    }
}
