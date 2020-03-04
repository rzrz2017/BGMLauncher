package com.dsn.andy.bgmlauncher.http2;

import org.json.JSONObject;

/**
 * Created by dell on 2017/9/21.
 */

public class Song {

    public String singername;
    public String filename;
    public String hash;
    public String album_name;

    public String songName;
    public String imgUrl;
    public String url;

    public static Song parse(JSONObject obj){
        Song song = null;
        try {
             song = new Song();
            song.singername = obj.getString("singername");
            song.album_name = obj.getString("album_name");
            song.filename = obj.getString("filename");
            try {
                song.hash = obj.getString("hash");
            }catch (Exception e){

            }

            try {
                if(song.hash == null) {
                    song.hash = obj.getString("320hash");
                }
            }catch (Exception e){

            }

        }catch (Exception e){
            return null;
        }

        return song;


    }


    public void fillSongInfo(JSONObject info){

        try{
            JSONObject extra = info.getJSONObject("extra");
            if(extra == null) return;

            this.songName = info.getString("songName");
            this.imgUrl = info.getString("imgUrl");
            this.url = info.getString("url");
        }catch (Exception e){

        }
    }

}
