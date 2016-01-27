package com.peerless2012.qingniantuzhai.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.peerless2012.common.imgcache.ImageCacheManager;
import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.interfaces.IOnItemClickListener;
import com.peerless2012.qingniantuzhai.model.ArticleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleListHolder>{

    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    private List<ArticleItem> articleItems;
    private IOnItemClickListener listener;
    private OnRecycleItemClickListener recycleItemClickListener = new OnRecycleItemClickListener();
    public ArticleListAdapter() {

    }

    public void setOnItemClickListener(IOnItemClickListener l){
        listener = l;
    }

    public ArticleListAdapter(List<ArticleItem> articleItems) {
        this.articleItems = articleItems;
    }

    public void addData(List<ArticleItem> articles){
        if (articleItems == null){
            articleItems = new ArrayList<ArticleItem>();
        }
        articleItems.addAll(articles);
        notifyDataSetChanged();
    }
    public void setData(List<ArticleItem> articles){
        articleItems = articles;
        notifyDataSetChanged();
    }

    @Override
    public ArticleListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        if (imageLoader == null) {
            imageLoader = ImageCacheManager.getInstance().getImageLoader();
        }
        return new ArticleListHolder(layoutInflater.inflate(R.layout.item_home_article_list,parent,false));
    }

    @Override
    public int getItemCount() {
        return articleItems == null ? 0 : articleItems.size();
    }

    public Object getItem(int position){
        return articleItems.get(position);
    }

    public int getId(int position){
        return 0;
    }

    @Override
    public void onBindViewHolder(ArticleListHolder holder, int position) {
        ArticleItem articleItem = articleItems.get(position);
        holder.articleTitle.setText(articleItem.getTitle());
        holder.articlePreViewImg.setImageUrl(articleItem.getPreviewImgUrl(), imageLoader);
        holder.itemView.setOnClickListener(recycleItemClickListener);
        holder.itemView.setTag(R.id.home_adapter_position_tag,position);
    }

    class ArticleListHolder extends RecyclerView.ViewHolder{
        public NetworkImageView articlePreViewImg;
        public TextView articleTitle;
        public ArticleListHolder(View itemView) {
            super(itemView);
            articlePreViewImg = (NetworkImageView) itemView.findViewById(R.id.article_preview_img);
            articleTitle = (TextView) itemView.findViewById(R.id.article_tile);
        }
    }

    class OnRecycleItemClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Integer position = (Integer) v.getTag(R.id.home_adapter_position_tag);
            if (position != null && listener != null) {
                int i = position;
                listener.onItemClick((RecyclerView)v.getParent(),v,i,0);
            }
        }
    }
}
