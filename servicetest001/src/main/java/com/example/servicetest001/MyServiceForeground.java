package com.example.servicetest001;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyServiceForeground extends Service {
    public MyServiceForeground() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent8=new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent8,0);
        //后面一个参数为渠道参数,Android8.0新增要求
        Notification notification=new NotificationCompat.Builder(this,"foreground")
                .setContentTitle("这是标题")
                .setContentText("这是内容这是内容这是内容")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
                .build();
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = null;
        //Android8.0要求设置通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("foreground", "foregroundName", NotificationManager.IMPORTANCE_HIGH);
//            channel.enableLights(true);
//            channel.setVibrationPattern();
//            channel.enableVibration(true);
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            channel.setLightColor(Color.RED);
//            channel.setShowBadge(true);
//            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//            // 创建一个渠道组
//            NotificationChannelGroup channelGroup = new NotificationChannelGroup("测试组ID", "渠道组名");
//            // 绑定渠道组
//            channel.setGroup("测试组ID");
//            notificationManager.createNotificationChannelGroup(channelGroup);
//            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
//            // 查询所有当前用户的所有渠道
//            notificationManager.getNotificationChannels();
//            // 查询所有当前用户的所有渠道组
//            notificationManager.getNotificationChannelGroups();
//            // 根据ID删除渠道
//            notificationManager.deleteNotificationChannel("渠道ID");
//            // 根据ID删除渠道组
//            notificationManager.deleteNotificationChannel("测试组ID");
        }
        startForeground(1,notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
