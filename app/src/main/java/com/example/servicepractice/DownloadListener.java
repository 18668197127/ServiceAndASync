package com.example.servicepractice;

/**
 * Created by 19057 on 2018/9/5.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
