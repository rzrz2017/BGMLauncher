package com.dsn.andy.bgmlauncher.http;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by dell on 2017/10/13.
 */

public interface GetLatestVersionService {
    @GET("common/update.jsp")
    Call<ResponseBody> getLatest();

}
