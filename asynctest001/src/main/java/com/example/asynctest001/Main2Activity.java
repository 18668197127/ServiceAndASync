package com.example.asynctest001;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//子线程和子线程通信
public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    private Runnable1 runnable1;
    private Runnable2 runnable2;
    private Handler r1Handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        Log.i(TAG, "onCreate1: "+Process.myPid());
//        Log.i(TAG, "onCreate2: "+Process.myTid());
//        Log.i(TAG, "onCreate3: "+Process.myUid());
//        Log.i(TAG, "onCreate4: "+Thread.currentThread().getId());
//        Log.i(TAG, "onCreate5: "+getMainLooper().getThread().getId());
//        Log.i(TAG, "onCreate6: "+getTaskId());
//        Log.i(TAG, "onCreate7: "+getApplicationInfo().uid);
//        Log.i(TAG, "onCreate8: "+getApplicationInfo().processName);
        runnable1=new Runnable1();
        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //接收信息的线程
                new Thread(runnable1).start();
                //该线程用于两个通信线程之间Handler的传递工作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: 该线程用于两个通信线程之间Handler的传递工作");
                        r1Handler=runnable1.getHandler();
                        if (r1Handler==null){
                            try {
                                Thread.sleep(250);
                                r1Handler=runnable1.getHandler();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        runnable2=new Runnable2(r1Handler);
                    }
                }).start();
            }
        });

        Button button3=findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递信息的线程
                new Thread(runnable2).start();
            }
        });
    }
}
