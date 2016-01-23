package com.peerless2012.qingniantuzhai.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peerless2012.qingniantuzhai.model.ArticleDetail;
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
    public static Fragment newInstance(Context context,ArticleDetail articleDetail){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARTICLE_DETAIL,articleDetail);
        return Fragment.instantiate(context, GifFragment.class.getName(),bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        ArticleDetail articleDetail = getArguments().getParcelable(ARTICLE_DETAIL);
        /*ImageLoader.getInstance().loadImage(articleDetail.getImg(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    File file = ImageLoader.getInstance().getDiskCache().get(imageUri);
//                    final GifDrawable gifDrawable = (GifDrawable) GifDrawable.createFromPath(file.getAbsolutePath());

                    try {
                        gifDrawable = new GifDrawable(file.getAbsolutePath());
                        GifImageView gifImageView = new GifImageView(container.getContext());
                        gifImageView.setImageDrawable(gifDrawable);
                        gifDrawable.addAnimationListener(new AnimationListener() {
                            @Override
                            public void onAnimationCompleted() {
                                gifDrawable.start();
                            }
                        });
                        gifDrawable.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });*/

        try {
            gifDrawable = new GifDrawable(Environment.getExternalStorageDirectory().getAbsolutePath()+"/1.gif");
            gifImageView = new GifImageView(container.getContext());
            gifImageView.setImageDrawable(gifDrawable);
            gifDrawable.addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted() {
                    gifDrawable.start();
                }
            });
            gifDrawable.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gifImageView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        super.onDestroy();
    }
}
