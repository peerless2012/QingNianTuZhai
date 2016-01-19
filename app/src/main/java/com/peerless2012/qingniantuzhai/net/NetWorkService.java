package com.peerless2012.qingniantuzhai.net;

import com.peerless2012.qingniantuzhai.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2016/1/17.
 */
public interface NetWorkService {

    @GET("/qingniantuzhai/user2.json")
    public Call<UserInfo> getUserInfo();
}
