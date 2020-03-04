package com.dsn.andy.bgmlauncher.fragment2;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Album;

import java.util.ArrayList;

/**
 * Created by dell on 2018/9/22.
 */

public class RecommendUtils {



    public static ArrayList<Album> makeData() {
        final ArrayList<Album> data = new ArrayList<Album>();
        Album album = new Album();
        album.resId = R.drawable.ic_recommend1;
       // album.imgUrl = "https://y.gtimg.cn/music/photo_new/T001R300x300M000003NThQh3ujqIo.jpg?max_age=2592000";
        album.musicList = Album.createAlbum2(Album.album1);
        album.name = "沉醉的旋律";
        data.add(album);

        album = new Album();
        album.resId = R.drawable.ic_recommend2;
        album.musicList = Album.createAlbum2(Album.album2);
      //  album.imgUrl = "https://p.qpic.cn/music_cover/qH8rLHHhL8O8Iibm56uPzJ9ia5TwehMggWeiaKN98ttulzzzVPwevAztg/300?n=1";
        album.name = "金色古典";
        data.add(album);

        album = new Album();
        album.resId = R.drawable.ic_recommend3;
        album.musicList = Album.createAlbum2(Album.album3);
       // album.imgUrl = "https://p.qpic.cn/music_cover/eQ8Bxpy4MCiawehucE6EHG5Qfp2kx7etWp9x2FYWaBKk6E5B5HKs8Mw/300?n=1";
        album.name = "夜的钢琴曲";
        data.add(album);

        album = new Album();
        album.resId = R.drawable.ic_recommend4;
        album.musicList = Album.createAlbum2(Album.album4);
       // album.imgUrl = "https://p.qpic.cn/music_cover/ZbA5Sic4n7UaHENs3oPiaLiadqH378lcmodRuRVVdibk3s0SRkukOic0QHA/300?n=1";
        album.name = "英文精选";
        data.add(album);

        album = new Album();
        album.resId = R.drawable.ic_recommend5;
        album.musicList = Album.createAlbum2(Album.album5);
        //album.imgUrl = "https://p.qpic.cn/music_cover/ULH0NLW4u55E7T2PEnniavcrHzoxDJOBc5XgbEJZc58w6icNFX5jtuFw/300?n=1";
        album.name = "珍藏歌曲";
        data.add(album);

        album = new Album();
        album.resId = R.drawable.ic_recommend6;
        album.musicList = Album.createAlbum2(Album.album6);
       // album.imgUrl = "https://p.qpic.cn/music_cover/vQcJ7UZxNjMicEvtoTjyV1mepJbWrTuB3xf6PeD5oDIRywAbtFnQjqA/300?n=1";
        album.name = "渡";
        data.add(album);
/*
        album = new Album();
        album.resId = R.drawable.ic_alumb_7;
        album.musicList = Album.createAlbum2(Album.album7);
        album.imgUrl = "https://p.qpic.cn/music_cover/z8wAFqicQ1qhImeiajkrgiaRxTUQYhcbpgRXIn5ApEAYAm6myAGv8VwsA/300?n=1";
        album.name = "倾听安静";
        data.add(album);

        album = new Album();
        album.resId = R.drawable.ic_alumb_8;
        album.musicList = Album.createAlbum2(Album.album8);
        album.imgUrl = "https://p.qpic.cn/music_cover/hSqULWEBYJEo7iaPyicuhTvbkvicckqzEYicicR1vkYXPEAFm4bVX3UAMBQ/300?n=1";
        album.name = "甜蜜经过";
        data.add(album);

        album = new Album();
        album.resId = R.drawable.ic_alumb_9;
        album.musicList = Album.createAlbum2(Album.album9);
        album.imgUrl = "https://p.qpic.cn/music_cover/y0Elj3m25QhKsCrLguvkx7wlDOc8rzS8icAHGPEbia3KXFJV3c673weA/300?n=1";
        album.name = "心情节奏";
        data.add(album);

        album = new Album();
        album.resId = R.drawable.ic_alumb_a;
        album.musicList = Album.createAlbum2(Album.albuma);
        album.imgUrl = "https://p.qpic.cn/music_cover/RQdsTJS6m65iavgS5kqMwUFWww0E5DaXCNBzFgyKicEpEdm8e8LiccfUQ/300?n=1";
        album.name = "一缕晨光";
        data.add(album);

        album = new Album();
        album.resId = R.drawable.ic_alumb_b;
        album.musicList = Album.createAlbum2(Album.albumb);
        album.imgUrl = "https://p.qpic.cn/music_cover/ViaoILzwFRibvcYKjaWG2ico93mbKiaMicejh15ib06YC9pyQoNicMnLK4bGg/300?n=1";
        album.name = "城市中的一些人";
        data.add(album);
*/
        return data;
    }






}
