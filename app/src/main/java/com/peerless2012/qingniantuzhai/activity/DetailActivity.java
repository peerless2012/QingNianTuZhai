package com.peerless2012.qingniantuzhai.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.model.ArticleDetail;
import com.peerless2012.qingniantuzhai.model.ArticleItem;
import com.peerless2012.qingniantuzhai.utils.FileUtils;
import com.peerless2012.qingniantuzhai.view.adapter.ArticleDetailPagerAdapter;
import com.peerless2012.qingniantuzhai.view.widget.PhotoViewPager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016/1/20 23:14
* @Version V1.0
* @Description: 计划单独一个图片,单独一个gif显示一个,一组多图的整页显示一组
*/
public class DetailActivity extends BaseActivity{
    private String TAG = "DetailActivity";
    private final static String DETAIL = "detail";
    private DetailOnPageChangeListener listener;
    private PhotoViewPager articlePager;
    private TextView articleItemDesc;
    private ArticleDetailPagerAdapter articleDetailPagerAdapter;
    private Subscription subscribe;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        articlePager = getView(R.id.article_detail_pager);
        articleItemDesc = getView(R.id.article_detail_desc);
    }

    @Override
    protected void initListener() {
        listener = new DetailOnPageChangeListener();
        articlePager.addOnPageChangeListener(listener);
    }

    @Override
    protected void initData() {
        articleDetailPagerAdapter = new ArticleDetailPagerAdapter(this,getSupportFragmentManager());
        articlePager.setAdapter(articleDetailPagerAdapter);
        ArticleItem articleItem = getIntent().getParcelableExtra(DETAIL);
        toolbar.setTitle(articleItem.getTitle());
        setTitle(articleItem.getTitle());
        subscribe = Observable.just(articleItem)
                .map(new Func1<ArticleItem, List<ArticleDetail>>() {
                    @Override
                    public List<ArticleDetail> call(ArticleItem item) {
                        List<ArticleDetail> articleDetails = null;
                        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                        String readJson = FileUtils.readJson(cacheDir, item.getUrl());
                        if (readJson != null) {
                            return gson.fromJson(readJson,new TypeToken<List<ArticleDetail>>(){}.getType());
                        }
                        try{
                            URL url = new URL(item.getUrl());
                            Document document = Jsoup.parse(url, 5000);
                            Element postContent = document.getElementsByClass("post-content").first();

                            Elements elementsByTag = postContent.getElementsByTag("p");
                            Element pElement = null;
                            ArticleDetail articleDetail = null;
                            articleDetails = new ArrayList<ArticleDetail>();
                           for (int i = 0; i < elementsByTag.size(); i++){
                               pElement = elementsByTag.get(i);
                               Elements imgs = pElement.getElementsByTag("img");
                               if (imgs == null || imgs.size() == 0) {
                                   if (articleDetail == null) {
                                       articleDetail = new ArticleDetail();
                                   }
                                   articleDetail.setDesc(pElement.text());
                               }else {
                                   Element img = imgs.first();
                                   if (articleDetail == null) {
                                       articleDetail = new ArticleDetail();
                                   }
                                   articleDetail.setImg(img.attr("data-src"));
                                   articleDetails.add(articleDetail);
                                   articleDetail = null;
                               }
                           }
                            FileUtils.saveJson(cacheDir,gson.toJson(articleDetails,new TypeToken<List<ArticleDetail>>(){}.getType()), url.toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return articleDetails;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ArticleDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ArticleDetail> articleDetails) {
                        articleDetailPagerAdapter.addData(articleDetails);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (!subscribe.isUnsubscribed()) subscribe.unsubscribe();
        articlePager.removeOnPageChangeListener(listener);
        super.onDestroy();
    }

    class DetailOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ArticleDetail articleDetail = (ArticleDetail) articleDetailPagerAdapter.getItemByPosition(position);
            articleItemDesc.setText(articleDetail.getDesc());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }



    public static void launch(Context context,ArticleItem item){
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra(DETAIL,item);
        context.startActivity(intent);
    }
}
