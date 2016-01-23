package com.peerless2012.qingniantuzhai.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peerless2012.qingniantuzhai.model.ArticleDetail;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/1/23 21:59
 * @Version V1.0
 * @Description
 */
public class VideoFragment extends BaseFragment{
    private final static String ARTICLE_DETAIL = "ARTICLE_DETAIL";

    public static Fragment newInstance(Context context,ArticleDetail articleDetail){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARTICLE_DETAIL,articleDetail);
        return Fragment.instantiate(context, VideoFragment.class.getName(),bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText("暂无内容");
        return textView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
