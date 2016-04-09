package com.peerless2012.qingniantuzhai.net;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/4/16 10:28
 * @Version V1.0
 * @Description
 */
public class NetControl {
    private static volatile NetControl mInstance;
    private Retrofit retrofit;

    public static NetControl getIns(){
        if (mInstance == null){
            synchronized (NetControl.class){
                if (mInstance == null) mInstance = new NetControl();
            }
        }
        return mInstance;
    }

    private NetControl(){

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(EasyApplication.getInstance().gson))
                .client(okHttpClient)
                .build();*/
    }

    public  <T> T createService(Class<T> clz){
        return retrofit.create(clz);
    }
}
