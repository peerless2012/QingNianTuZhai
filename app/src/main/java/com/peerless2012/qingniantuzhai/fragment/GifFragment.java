package com.peerless2012.qingniantuzhai.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lazylibrary.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peerless2012.qingniantuzhai.activity.DetailActivity;
import com.peerless2012.qingniantuzhai.interfaces.IOnImgDownloadCompleteListener;
import com.peerless2012.qingniantuzhai.model.ArticleDetail;
import com.peerless2012.qingniantuzhai.service.DownloadImgsService;
import java.io.File;
import java.io.IOException;
import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/1/23 21:59
 * @Version V1.0
 * @Description
 */
public class GifFragment extends BaseFragment{
    private final static String ARTICLE_DETAIL = "ARTICLE_DETAIL";
    private GifDrawable gifDrawable = null;
    private GifImageView gifImageView = null;
    private ArticleDetail articleDetail;
    private IOnImgDownloadCompleteListener listener;
    public static Fragment newInstance(Context context,ArticleDetail articleDetail){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARTICLE_DETAIL, articleDetail);
        return Fragment.instantiate(context, GifFragment.class.getName(),bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        gifImageView = new GifImageView(container.getContext());
        gifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity != null && activity instanceof DetailActivity) {
                    ((DetailActivity)activity).inFullScreenMode();
                }
            }
        });
        return gifImageView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        articleDetail = getArguments().getParcelable(ARTICLE_DETAIL);
        String fileNameByUrl = getFileNameByUrl(articleDetail.getImg());
        if (isFileExists(fileNameByUrl)){
            displayImgs(new File(cacheDir,fileNameByUrl).getAbsolutePath());
        }else {
            listener = new IOnImgDownloadCompleteListener() {
                @Override
                public void onImgDownloadComplete(String downloadUrl,String path) {
                    if (path != null){
                        displayImgs(path);
                    }else {
                        ToastUtils.showToast(getActivity(),"加载失败");
                    }
                }
            };
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    DownloadImgsService.DownloadBinder downloadBinder = (DownloadImgsService.DownloadBinder) service;
                    downloadBinder.add(articleDetail.getImg(),listener);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
            bindService();
        }
    }

    private void displayImgs(String path){
        try {
            gifDrawable = new GifDrawable(path);
            gifImageView.setImageDrawable(gifDrawable);
            gifDrawable.addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted() {
                    gifDrawable.start();
                }
            });
            gifDrawable.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (gifDrawable != null) {
            gifDrawable.stop();
            gifDrawable = null;
        }
        if (gifImageView != null) {
            gifImageView.setImageDrawable(null);
        }
        if (serviceConnection != null){
            unBindService();
        }
        super.onDestroy();
    }

    private ServiceConnection serviceConnection;

    private void bindService(){
        Intent intent = new Intent(getContext(), DownloadImgsService.class);
        getContext().bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    private void unBindService(){
        getContext().unbindService(serviceConnection);
    }

    private boolean isFileExists (String path){
        File file = new File(cacheDir,path);
        return file.exists();
    }

    private String getFileNameByUrl(String url){
        int indexOf = url.lastIndexOf("/");
        return url.substring(indexOf + 1);
    }
}
