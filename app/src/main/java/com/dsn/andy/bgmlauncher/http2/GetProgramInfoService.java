package com.dsn.andy.bgmlauncher.http2;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by dell on 2017/9/21.
 */

public interface GetProgramInfoService {
    public static final String HOST = "http://api.open.qingting.fm";

    public static final String URL = "http://api.open.qingting.fm/v6/media/programs/#{program_id}?access_token=#{access_token}";
    @GET("v6/media/programs/{program_id}")
    Call<ResponseBody> getProgram(
            @Path("program_id") String program_id,
            @Query("access_token") String access_token);

}
