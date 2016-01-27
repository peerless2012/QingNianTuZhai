package com.peerless2012.qingniantuzhai.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
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
public class DownloadImgsService extends Service{
    protected String cacheDir;
    private Looper downloadLooper;
    private Handler downloadHandle;
    private Handler handle;
    private HashMap<String,IOnImgDownloadCompleteListener> listenerMap;
    private OkHttpClient okHttpClient;
    @Override
    public IBinder onBind(Intent intent) {
        return new DownloadBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        File externalCache = getExternalCacheDir();
        if (externalCache != null) {
            cacheDir = externalCache.getAbsolutePath();
        }else {
            cacheDir = getCacheDir().getAbsolutePath();
        }

        listenerMap = new HashMap<String,IOnImgDownloadCompleteListener>();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(5000, TimeUnit.SECONDS);
        builder.readTimeout(10000,TimeUnit.SECONDS);
        okHttpClient = builder.build();

        //创建下载子线程
        HandlerThread handlerThread = new HandlerThread("DownloadThread");
        handlerThread.start();
        downloadLooper = handlerThread.getLooper();
        downloadHandle = new Handler(downloadLooper){
            @Override
            public void handleMessage(Message msg) {
                downloadImgs((String)msg.obj);
            }
        };

        handle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                DownloadBean downloadBean = (DownloadBean) msg.obj;
                IOnImgDownloadCompleteListener listener = listenerMap.get(downloadBean.getUrl());
                if (listener != null) {
                    listener.onImgDownloadComplete(downloadBean.getUrl(),downloadBean.getPath());
                }

            }
        };
    }

    private void downloadImgs(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response != null && response.isSuccessful()){
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    InputStream inputStream = responseBody.byteStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                    String fileName = getFileNameByUrl(url);
                    File file = new File(cacheDir,fileName+".temp");
                    if (file.exists()) file.delete();
                    OutputStream outputStream = new FileOutputStream(file);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = bufferedInputStream.read(buffer)) != -1){
                        bufferedOutputStream.write(buffer,0,len);
                        bufferedOutputStream.flush();
                    }
                    File newPath = new File(cacheDir, fileName);
                    file.renameTo(newPath);
                    DownloadBean downloadBean = new DownloadBean();
                    downloadBean.setIsSuccessful(true);
                    downloadBean.setUrl(url);
                    downloadBean.setPath(newPath.getAbsolutePath());
                    Message message = handle.obtainMessage();
                    message.obj = downloadBean;
                    handle.sendMessage(message);
                }else {
                    //出错
                }
            }else {
                //出错
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                InputStream inputStream = response.body().byteStream();
//            }
//        });

    }

    private void addDownload(String url, IOnImgDownloadCompleteListener l){
        listenerMap.put(url,l);
        Message message = downloadHandle.obtainMessage();
        message.obj = url;
        downloadHandle.sendMessage(message);
    }

    private void removeDownloadListener(String url, IOnImgDownloadCompleteListener l){
        listenerMap.remove(url);
    }

    private String getFileNameByUrl(String url){
        int indexOf = url.lastIndexOf("/");
        return url.substring(indexOf + 1);
    }

    @Override
    public void onDestroy() {
        if (downloadLooper != null){
            downloadLooper.quit();
        }
        if (downloadHandle != null){
            downloadHandle.removeCallbacksAndMessages(null);
        }
        if (handle != null){
            handle.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }


    public class DownloadBinder extends Binder implements IDownloadService{

        @Override
        public void add(String url, IOnImgDownloadCompleteListener l) {
            addDownload(url,l);
        }

        @Override
        public void removeListener(String url, IOnImgDownloadCompleteListener l) {
            removeDownloadListener(url, l);
        }
    }

    private class DownloadBean{
        private String url;
        private String path;
        private boolean isSuccessful;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }

        public void setIsSuccessful(boolean isSuccessful) {
            this.isSuccessful = isSuccessful;
        }
    }
}
