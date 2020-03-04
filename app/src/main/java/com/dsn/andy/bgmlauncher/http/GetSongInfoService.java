package com.dsn.andy.bgmlauncher.http;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by dell on 2017/9/21.
 */

public interface GetSongInfoService {
    public static final String URL = "http://m.kugou.com/app/i/getSongInfo.php?hash=457e5004e2bfd294e3aab8e9173be39f&cmd=playInfo";
    @GET("app/i/getSongInfo.php")
    Call<ResponseBody> getMusicInfo(@Query("hash") String hash, @Query("cmd") String cmd);

}
