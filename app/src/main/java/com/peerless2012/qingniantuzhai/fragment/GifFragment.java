package com.peerless2012.qingniantuzhai.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.peerless2012.qingniantuzhai.interfaces.IOnImgDownloadCompleteListener;
import com.peerless2012.qingniantuzhai.model.ArticleDetail;
import java.io.File;
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
        Glide.with(this)
                .load(articleDetail.getImg())
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        displayImgs(resource.getAbsolutePath());
                    }
                });
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
        super.onDestroy();
    }
}
