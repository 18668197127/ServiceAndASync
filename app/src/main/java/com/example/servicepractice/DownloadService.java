package com.example.servicepractice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.io.File;

//这是一个服务
public class DownloadService extends Service {

    final String CHANNEL_ID = "channel_id_1";
    final String CHANNEL_NAME = "channel_name_1";

    private DownloadTask downloadTask;
    private String downloadUrl;

    private DownloadListener listener = new DownloadListener() {
        @RequiresApi(api = 26)
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @RequiresApi(api = 26)
        @Override
        public void onSuccess() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @RequiresApi(api = 26)
        @Override
        public void onFailed() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            //这里是个问题点,我觉得不能=null
            // 重新点击start需要null
            downloadTask = null;
            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        @RequiresApi(api = 26)
        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener,getApplication());
                downloadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {

            //这里又有问题了,只有暂停没有恢复开始吗?
            // 恢复开始要点开始按钮
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            }
            if (downloadUrl != null) {
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStorageDirectory().getPath();
                File file = new File(directory + fileName);
                if (file.exists()) {
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @RequiresApi(api = 26)
    private Notification getNotification(String s, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivities(this, 0, new Intent[]{intent}, 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            //通知才能正常弹出
            getNotificationManager().createNotificationChannel(notificationChannel);
        }
        Notification.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID);
        }else {
            builder = new Notification.Builder(this);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(s);
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}
