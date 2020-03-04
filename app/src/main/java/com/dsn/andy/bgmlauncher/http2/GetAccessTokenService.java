package com.dsn.andy.bgmlauncher.http2;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by dell on 2017/9/21.
 */

public interface GetAccessTokenService {
    public static final String HOST = "http://api.open.qingting.fm";

    public static final String URL = "http://api.open.qingting.fm/access?&grant_type=client_credentials&client_id=NWE1ODgzMWMtOWRmNC0xMWU3LTkyM2YtMDAxNjNlMDAyMGFk&client_secret=NjdjZGRkYjctNGU1ZS0zZWUzLTkxZjEtODI3ZDQyMGNlNmY5";
    @POST("access?&grant_type=client_credentials")
    Call<ResponseBody> getAccessToken(@Query("client_id") String client_id, @Query("client_secret") String client_secret);

}
