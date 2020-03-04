package com.dsn.andy.bgmlauncher.http2;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by dell on 2017/9/21.
 */

public interface GetWeatherService {
    public static final String URL = "http://wthrcdn.etouch.cn/weather_mini?city=北京市";
    @GET("weather_mini")
    Call<ResponseBody> getWeather(@Query("city") String cityKey);

}
