package com.peerless2012.qingniantuzhai.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.StringRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.signature.StringSignature;
import com.github.lazylibrary.util.FileUtils;
import com.github.lazylibrary.util.ToastUtils;
import com.peerless2012.qingniantuzhai.App;
import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.interfaces.IDownloadService;
import com.peerless2012.qingniantuzhai.interfaces.IOnImgDownloadCompleteListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/1/27 23:25
 * @Version V1.0
 * @Description
 */
public class SaveImgsService extends Service{
    /**
     * 退出
     */
    private final static int FLAG_QUITE = 0;

    /**
     * 保存成功
     */
    private final static int FLAG_SAVE_IMG_SUCCESSFUL = 1;
    /**
     * 保存失败
     */
    private final static int FLAG_SAVE_IMG_FAIL = 2;
    protected File cacheDir = null;
    protected File saveDir = null;
    private Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case FLAG_SAVE_IMG_FAIL:
                    mUrlSet.remove(msg.obj.toString());
                    ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.save_failed));
                    break;
                case FLAG_SAVE_IMG_SUCCESSFUL:
                    mUrlSet.remove(msg.obj.toString());
                    ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.save_successfully));
                    break;
                case FLAG_QUITE:
                    mExecutorService.shutdownNow();
                    stopSelf();
                    break;
            }


        }
    };

    private DownloadBinder mDownloadBinder;

    private ExecutorService mExecutorService;

    private Set<String> mUrlSet;

    @Override
    public void onCreate() {
        super.onCreate();
        cacheDir = ((App)getApplication()).getAppCacheDir();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            saveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            saveDir = new File(saveDir,"QingNianTuZhai");
        }
        mDownloadBinder = new DownloadBinder();
        mExecutorService = Executors.newFixedThreadPool(1);
        mUrlSet = new HashSet<String>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mDownloadBinder;
    }

    private String getFileNameByUrl(String url){
        int indexOf = url.lastIndexOf("/");
        return url.substring(indexOf + 1);
    }

    @Override
    public void onDestroy() {
        if (handle != null){
            handle.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private void saveImg(String url){
        //检查是否有sd卡
        if (saveDir == null) {
            showToast(R.string.save_not_sd);
            return;
        }

        // 检查本地是否有
        String fileName = getFileNameByUrl(url);
        File localFile = new File(saveDir,fileName);
        if (localFile.exists()){
            showToast(R.string.save_exist);
            return;
        }

        // 检查glide的disk是否有
        DiskCache diskCache = DiskLruCacheWrapper.get(cacheDir, App.DEFAULT_DISK_CACHE_SIZE);
        File diskTempFile = diskCache.get(new StringSignature(url));
        if (diskTempFile == null || !diskTempFile.exists()){
            showToast(R.string.save_not_downloaded);
            return;
        }

        // 检查是否在队列中

        if (mUrlSet.contains(url)){
            showToast(R.string.save_saving);
            return;
        }

        mExecutorService.execute(new SaveImgRunnable(url,diskTempFile.getAbsolutePath(),localFile.getAbsolutePath()));
        mUrlSet.add(url);
        showToast(R.string.save_start);
    }

    private void showToast(String toast){
            ToastUtils.showToast(getApplicationContext(), toast);
    }

    private void showToast(@StringRes int toastRes){
        showToast(getString(toastRes));
    }

    private void quiteApp(){
        mExecutorService.execute(new QuiteRunnable());
    }

    public class DownloadBinder extends Binder implements IDownloadService{

        @Override
        public void save(String url) {
            saveImg(url);
        }

        @Override
        public void quite() {
            quiteApp();
        }
    }

    public class QuiteRunnable implements Runnable{

        @Override
        public void run() {
            handle.sendEmptyMessage(FLAG_QUITE);
        }
    }

    public class SaveImgRunnable implements Runnable{

        private String url;

        private String destPath;

        private String srcPath;

        public SaveImgRunnable(String url , String srcPath, String destPath) {
            this.url = url;
            this.srcPath = srcPath;
            this.destPath = destPath;
        }

        @Override
        public void run() {
            boolean saveResult = false;
            try {
                saveResult = FileUtils.copyFile(srcPath, destPath);
            }catch (Exception e){
                e.printStackTrace();
            }
            Message message = handle.obtainMessage();
            message.what = saveResult ? FLAG_SAVE_IMG_SUCCESSFUL : FLAG_SAVE_IMG_FAIL;
            message.obj = url;
            message.sendToTarget();
        }
    }
}
