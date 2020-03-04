package com.dsn.andy.bgmlauncher.http2;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by dell on 2017/9/21.
 */

public interface SearchMusicListService {
    public static final String URL = "http://mobilecdn.kugou.com/api/v3/search/song?iscorrect=1&keyword=%E5%A4%B4%E9%A1%B6%E4%B8%80%E7%89%87%E5%A4%A9&page=1&pagesize=20";
    @GET("api/v3/search/song")
    Call<ResponseBody> getMusicList(@Query("iscorrect") int iscorrent, @Query("keyword") String keyword, @Query("page") int page, @Query("pagesize") int pagesize);

}
