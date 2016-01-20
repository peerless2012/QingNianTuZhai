package com.peerless2012.qingniantuzhai.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.interfaces.OnItemClickListener;
import com.peerless2012.qingniantuzhai.model.ArticleDetail;
import com.peerless2012.qingniantuzhai.model.ArticleItem;
import com.peerless2012.qingniantuzhai.utils.FileUtils;
import com.peerless2012.qingniantuzhai.view.adapter.ArticleListAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private RecyclerView homeRecycleView;

    private ArticleListAdapter articleListAdapter;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        homeRecycleView = getView(R.id.article_list);
        homeRecycleView.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL,false));
        homeRecycleView.setHasFixedSize(true);
        articleListAdapter = new ArticleListAdapter();
        homeRecycleView.setAdapter(articleListAdapter);
    }

    @Override
    protected void initListener() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        articleListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View parent, View view, int position, long id) {
                ArticleItem articleItem = (ArticleItem) articleListAdapter.getItem(position);
                DetailActivity.launch(HomeActivity.this,articleItem);
            }
        });
    }

    private String baseUrl = "http://www.qingniantuzhai.com/home";
    private Subscription subscribe;
    @Override
    protected void initData() {
        subscribe = Observable.just(baseUrl)
                .map(new Func1<String, List<ArticleItem>>() {
                    @Override
                    public List<ArticleItem> call(String urlStr) {
                        ArrayList<ArticleItem> list = new ArrayList<ArticleItem>();
                        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                        String readJson = FileUtils.readJson(cacheDir, urlStr);
                        if (readJson != null) {
                            return gson.fromJson(readJson,new TypeToken<List<ArticleItem>>(){}.getType());
                        }
                        try {
                            URL url = new URL(urlStr);

                            Document document = Jsoup.parse(url, 5000);
                            Elements rowElements = document.getElementsByClass("row");
                            for (int i = 1; i < rowElements.size(); i++) {
                                Element rowElement = rowElements.get(i);
                                ArticleItem item = new ArticleItem();

                                Element imgElement = rowElement.getElementsByClass("col-md-5").first();
                                Element img = imgElement.select("img").first();
                                item.setPreviewImgUrl(img.attr("data-src"));

                                Element articleElement = rowElement.getElementsByClass("col-md-7").first();
                                Element articleLink = articleElement.select("a").first();
                                item.setUrl(articleLink.attr("href"));
                                String title = articleLink.html();
                                item.setTitle(title.substring(12, title.length() - 14));

                                list.add(item);
                            }
                            FileUtils.saveJson(cacheDir,gson.toJson(list,new TypeToken<List<ArticleItem>>(){}.getType()), url.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ArticleItem>>() {
                    @Override
                    public void call(List<ArticleItem> articleItems) {
                        articleListAdapter.addData(articleItems);
                    }
                });
    }

    @Override
    protected boolean isHome() {
        return true;
    }

    @Override
    protected void onDestroy() {
        subscribe.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_clean) {
            // Handle the camera action
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_day) {

        } else if (id == R.id.nav_night) {

        } else if (id == R.id.nav_color) {

        } else if (id == R.id.nav_view) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
//        OkHttpClient client = new OkHttpClient();
//        Retrofit retrofit = new Retrofit.Builder()
//                        .client(client)
//                        .baseUrl(baseUrl)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//        NetWorkService rottenTomatoesService = retrofit.create(NetWorkService.class);
//        Call<UserInfo> call = rottenTomatoesService.getUserInfo();
//        call.enqueue(new Callback<UserInfo>() {
//            @Override
//            public void onResponse(Response<UserInfo> response) {
////                Toast.makeText(HomeActivity.this,response.toString(),Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
////                Toast.makeText(HomeActivity.this,"出错了!",Toast.LENGTH_LONG).show();
//            }
//        });

//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                OkHttpClient client = new OkHttpClient();
//                Retrofit retrofit = new Retrofit.Builder()
//                                .client(client)
//                                .baseUrl(baseUrl)
//                                .addConverterFactory(GsonConverterFactory.create())
//                                .build();
//
//                NetWorkService rottenTomatoesService = retrofit.create(NetWorkService.class);
//                Call<UserInfo> call = rottenTomatoesService.getUserInfo();
//                        try {
//                            Response<UserInfo> execute = call.execute();
//                            Log.i("Home",execute.body().toString());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//        }.start();

        //jsoup test
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    /*
                    * <hr/>
                        <div class="row">
                        <div class="col-md-5">
                            <a href="http://www.qingniantuzhai.com/posts/2836.html">
                                <img data-src="http://ww1.sinaimg.cn/large/862c3c36jw1ezzekdwnqnj2078055t9b.jpg"
                                     class="img-rounded img-responsive img-full lazy" src="http://www.qingniantuzhai.com/images/loading.gif">
                            </a>
                        </div>
                        <div class="col-md-7">
                            <a href="http://www.qingniantuzhai.com/posts/2836.html">
                                <h5><strong>青年图摘0115！技术高超的电焊大师是这样工作的</strong></h5>
                            </a>

                            <p class="info">
                                <a href="#">小雪</a>
                                <span> • </span>
                                2016-01-15
                                <span> • </span>
                                <label class="text-warning">10351</label>次阅读
                            </p>

                            <p class="subline hidden-xs">【1】这拍照角度66的



            【2】后面的那位好可怜...

            【3】二哈拍照总是很帅

            【4】这面包一定烤熟了

            【5】大哥汽车开出了新水平啊

            【6】不想当西瓜的橘子一定不是好草莓

            【...</p>
                            <p class="info">栏目：<a href="http://www.qingniantuzhai.com/cat/1">青年图摘</a></p>
                        </div>
                    </div>
                    *
                    * */
                    URL url = new URL("http://www.qingniantuzhai.com/home");
                    Document document = Jsoup.parse(url, 5000);
                    Elements rowElements = document.getElementsByClass("row");
                    ArrayList<ArticleItem> list = new ArrayList<ArticleItem>();
                    for (int i = 0; i < rowElements.size(); i++) {
                        Element rowElement = rowElements.get(i);
                        ArticleItem item = new ArticleItem();

                        Element imgElement = rowElement.getElementsByClass("col-md-5").first();
                        Element img = imgElement.select("img").first();
                        item.setPreviewImgUrl(img.attr("data-src"));

                        Element articleElement = rowElement.getElementsByClass("col-md-7").first();
                        Element articleLink = articleElement.select("a").first();
                        item.setUrl(articleLink.attr("href"));
                        String title = articleLink.html();
                        item.setTitle(title.substring(12, title.length() - 14));
                        Gson gson = new Gson();
                        FileUtils.saveJson(cacheDir,gson.toJson(list),url.toString());
                        list.add(item);
                    }
                    for (ArticleItem articleItem:list) {
                        //ArticleItem{dateTime=0, url='http://www.qingniantuzhai.com/posts/2818.html', title='青年图摘1228！笑死我了，放生的奇葩', previewImgUrl='http://ww3.sinaimg.cn/large/862c3c36jw1ezeoub9g2dj2078055weg.jpg'}
                        Log.i("JSOUP", articleItem.toString());
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }.start();
    }
}
