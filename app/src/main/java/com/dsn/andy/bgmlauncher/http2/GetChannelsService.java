package com.dsn.andy.bgmlauncher.http2;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by dell on 2017/9/21.
 */

public interface GetChannelsService {
    public static final String HOST = "http://api.open.qingting.fm";

    public static final String URL = "http://api.open.qingting.fm/v6/media/categories?access_token=#{access_token}";
    @GET("v6/media/categories/{category_id}/channels/order/0/curpage/{curpage}/pagesize/30")
    Call<ResponseBody> getDomain(@Path("category_id") int category_id, @Path("curpage") int curpage,

                                 @Query("access_token") String access_token);

}
