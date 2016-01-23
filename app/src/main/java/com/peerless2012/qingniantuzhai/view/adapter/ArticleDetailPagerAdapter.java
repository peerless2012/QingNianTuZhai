package com.peerless2012.qingniantuzhai.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Movie;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.fragment.GifFragment;
import com.peerless2012.qingniantuzhai.fragment.PhotoFragment;
import com.peerless2012.qingniantuzhai.fragment.VideoFragment;
import com.peerless2012.qingniantuzhai.model.ArticleDetail;
import com.peerless2012.qingniantuzhai.view.widget.GifView;
import com.peerless2012.qingniantuzhai.view.widget.NetworkPhotoView;

import java.io.File;
import java.util.List;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016/1/21 23:19
* @Version V1.0
* @Description:
*/
public class ArticleDetailPagerAdapter extends FragmentPagerAdapter {
    private List<ArticleDetail> articleDetails;
    private Context context;
    public ArticleDetailPagerAdapter(Context context,FragmentManager fm) {
        this(context,fm,null);
    }

    public ArticleDetailPagerAdapter(Context context,FragmentManager fm,List<ArticleDetail> articleDetails) {
        super(fm);
        this.context = context;
        this.articleDetails = articleDetails;
    }

    public void addData(List<ArticleDetail> details){
        if (articleDetails == null) {
            articleDetails = details;
        }else {
            articleDetails.addAll(details);
        }
        notifyDataSetChanged();
    }
    public Object getItemByPosition(int position){
        return articleDetails.get(position);
    }
    @Override
    public int getCount() {
        return articleDetails == null ? 0 : articleDetails.size();
    }

    @Override
    public Fragment getItem(int position) {
        ArticleDetail articleDetail = articleDetails.get(position);
        if (articleDetail.getType() == ArticleDetail.TYPE_IMG){
            return PhotoFragment.newInstance(context,articleDetail);
        }else if (articleDetail.getType() == ArticleDetail.TYPE_GIF){
            return GifFragment.newInstance(context, articleDetail);
        }else {
            return VideoFragment.newInstance(context, articleDetail);
        }
//
//            PhotoView photoView = new PhotoView(container.getContext());
//            ImageLoader.getInstance().displayImage(articleDetail.getImg(), photoView);
//            result = photoView;
//            //TODO ......
//        }else if (articleDetail.getType() == ArticleDetail.TYPE_GIF) {
//            final GifImageView gifView1 = new GifImageView(container.getContext());
//            ImageLoader.getInstance().loadImage(articleDetail.getImg(),new SimpleImageLoadingListener(){
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    File file = ImageLoader.getInstance().getDiskCache().get(imageUri);
//                    gifView1.setImageURI(Uri.fromFile(file));
////                    gifView1.setMovieResource(R.raw.logo);
////                    gifView1.setPaused(false);
////                    File file = ImageLoader.getInstance().getDiskCache().get(imageUri);
////                    gifView1.setMovie(Movie.decodeFile(file.getAbsolutePath()));
//                }
//            });
//
//            result = gifView1;
//        }else if (articleDetail.getType() == ArticleDetail.TYPE_VIDEO) {
//
//        }else {
//            throw new IllegalArgumentException("UnSupport Tpye Exception !");
//        }
    }
}
