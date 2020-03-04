package com.dsn.andy.bgmlauncher.http2;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by dell on 2017/9/21.
 */

public interface GetProgramsService {
    public static final String HOST = "http://api.open.qingting.fm";

    public static final String URL = "http://api.open.qingting.fm/v6/media/channelondemands/125260/programs/curpage/#{curpage}/pagesize/30?access_token=#{access_token}";
    @GET("v6/media/channelondemands/{channel_id}/programs/curpage/{curpage}/pagesize/50")
    Call<ResponseBody> getProgram(
            @Path("channel_id") String channel_id,
            @Path("curpage") String curpage,
            @Query("access_token") String access_token);

}
