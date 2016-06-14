package com.peerless2012.qingniantuzhai.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.peerless2012.qingniantuzhai.fragment.GifFragment;
import com.peerless2012.qingniantuzhai.fragment.PhotoFragment;
import com.peerless2012.qingniantuzhai.fragment.VideoFragment;
import com.peerless2012.qingniantuzhai.model.ArticleDetail;
import java.util.List;
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
    public ArticleDetail getItemByPosition(int position){
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
    }
}
