package com.dsn.andy.bgmlauncher.util;

import android.util.Log;

import com.dsn.andy.bgmlauncher.http2.GetWeatherService;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dell on 2018/9/27.
 */

public class WeatherManager {

    private static final String HOST_WEATHER = "http://wthrcdn.etouch.cn/";

    public interface GetWeatherCallback {
        void onGetWeather(String type,String temp,String wind);
    }


    public static void request(String city, final GetWeatherCallback callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_WEATHER)
                .build();

        GetWeatherService service = retrofit.create(GetWeatherService.class);
        Call<ResponseBody> call = service.getWeather(city);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    String str = response.body().string();

                    JSONObject json = new JSONObject(str);

                    Log.e("andy","get weather:"+str);

                    JSONObject data = json.getJSONObject("data");
                    JSONArray forecast = data.getJSONArray("forecast");

                    if(forecast!=null && forecast.length()>0){
                        JSONObject obj = forecast.getJSONObject(0);

                        String type = obj.getString("type");
                        String fengxiang = obj.getString("fengxiang");
                        String fengliFull = obj.getString("fengli");
                        int begin = "<![CDATA[".length();
                        int end = fengliFull.length()-"]]>".length();
                        String fengli = fengliFull.substring(begin,end);
                        String tempHigh = obj.getString("high").substring(2);
                        String tempLow = obj.getString("low").substring(2);
                        String temp = tempLow+"-"+tempHigh;
                        String wind = fengxiang+" "+fengli;

                        Log.e("andy","temp="+temp+",wind="+wind+",type="+type);

                        if(callback != null){
                            callback.onGetWeather(type,temp,wind);
                        }

                    }


                }catch (JSONException ex){

                }catch (IOException ex){

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }




}
