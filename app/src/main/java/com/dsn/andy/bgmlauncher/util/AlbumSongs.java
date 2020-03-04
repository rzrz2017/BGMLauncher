package com.dsn.andy.bgmlauncher.util;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Music;

import java.util.ArrayList;

/**
 * Created by dell on 2017/10/2.
 */

public class AlbumSongs {

    //不老情歌
    static  String[] songArray1 = {
        // 歌名  歌手名
        "你把我灌醉","邓紫棋","https://y.gtimg.cn/music/photo_new/T001R300x300M000001fNHEf1SFEFN.jpg?max_age=2592000",
         "我的滑板鞋","华晨宇","https://y.gtimg.cn/music/photo_new/T001R300x300M000002Vcz8F2hpBQj.jpg?max_age=2592000",
            "十年","陈奕迅","https://y.gtimg.cn/music/photo_new/T001R300x300M000003Nz2So3XXYek.jpg?max_age=2592000",
            "特别的人","方大同","https://y.gtimg.cn/music/photo_new/T001R300x300M000003zHcYF44FVEV.jpg?max_age=2592000",
            "微微一笑很倾城","杨洋","https://y.gtimg.cn/music/photo_new/T001R300x300M000004coWV04C5FCV.jpg?max_age=2592000",
            "告白气球","周杰伦","https://y.gtimg.cn/music/photo_new/T001R300x300M0000025NhlN2yWrP4.jpg?max_age=2592000",
            "演员","薛之谦","https://y.gtimg.cn/music/photo_new/T001R300x300M000002J4UUk29y8BY.jpg?max_age=2592000",
            "时间煮雨","郁可唯","https://y.gtimg.cn/music/photo_new/T001R300x300M000000NUoMp2WAEpO.jpg?max_age=2592000",
            "一次就好","杨宗纬","https://y.gtimg.cn/music/photo_new/T001R300x300M000003tMm0y0TuewY.jpg?max_age=2592000",
            "一个人","张艺兴","https://y.gtimg.cn/music/photo_new/T001R300x300M000002MfEld3FWp0R.jpg?max_age=2592000",
            "烟火","陈翔","https://y.gtimg.cn/music/photo_new/T001R300x300M000004EyqQS2hMS6V.jpg?max_age=2592000",
            "K歌之王","陈奕迅","https://y.gtimg.cn/music/photo_new/T001R300x300M000003Nz2So3XXYek.jpg?max_age=2592000",
            "认真","阿杜","https://y.gtimg.cn/music/photo_new/T001R300x300M0000022VtZd19rZpi.jpg?max_age=2592000",
            "酷","李宇春","https://y.gtimg.cn/music/photo_new/T001R300x300M000002ZOuVm3Qn20Y.jpg?max_age=2592000",
            "童话","光良","https://y.gtimg.cn/music/photo_new/T001R300x300M000004TXjLD41LEZI.jpg?max_age=2592000",
            "当你老了","莫文蔚","https://y.gtimg.cn/music/photo_new/T001R300x300M000000cISVf2QqLc6.jpg?max_age=2592000",
            "我的歌声里","曲婉婷","https://y.gtimg.cn/music/photo_new/T001R300x300M0000030RkE50nmpWC.jpg?max_age=2592000",
            "独家记忆","陈小春","https://y.gtimg.cn/music/photo_new/T001R300x300M000004DFS271osAwp.jpg?max_age=2592000",
            "遇见","孙燕姿","https://y.gtimg.cn/music/photo_new/T001R300x300M000001pWERg3vFgg8.jpg?max_age=2592000",
            "红豆" ,"王菲","https://y.gtimg.cn/music/photo_new/T001R300x300M000000GDDuQ3sGQiT.jpg?max_age=2592000",
            "曾经的你","许巍","https://y.gtimg.cn/music/photo_new/T001R300x300M00000235pCx2tYjlq.jpg?max_age=2592000",
            "歌和老街","王祖蓝","https://y.gtimg.cn/music/photo_new/T001R300x300M000003V1SaZ2sQMLZ.jpg?max_age=2592000",
            "你是我心内的一首歌","王力宏","https://y.gtimg.cn/music/photo_new/T001R300x300M000001JDzPT3JdvqK.jpg?max_age=2592000",
            "逝去的爱","欧阳菲菲","https://y.gtimg.cn/music/photo_new/T001R300x300M000000picYu1j83L2.jpg?max_age=2592000",
            "我好想你","陈学冬","https://y.gtimg.cn/music/photo_new/T001R300x300M000003vyHwp0iVQZp.jpg?max_age=2592000",
            "有多少爱可以重来","迪克牛仔","https://y.gtimg.cn/music/photo_new/T001R300x300M000002qHYM50JQl7V.jpg?max_age=2592000",
            "咱们结婚吧","齐晨","https://y.gtimg.cn/music/photo_new/T001R300x300M000000H4xDG3heHtr.jpg?max_age=2592000",
            "笑忘书","王菲","https://y.gtimg.cn/music/photo_new/T001R300x300M000000GDDuQ3sGQiT.jpg?max_age=2592000",
            "第七感" ,"张靓颖","https://y.gtimg.cn/music/photo_new/T001R300x300M000000aw4WC2EQYTv.jpg?max_age=2592000",
            "我怀念的","孙燕姿","https://y.gtimg.cn/music/photo_new/T001R300x300M000001pWERg3vFgg8.jpg?max_age=2592000",
            "不可说","霍建华","https://y.gtimg.cn/music/photo_new/T001R300x300M000002wcdcE3xE1Bl.jpg?max_age=2592000",
            "宁夏","梁静茹","https://y.gtimg.cn/music/photo_new/T001R300x300M000000GGDys0yA0Nk.jpg?max_age=2592000",
            "新贵妃醉酒","李玉刚","https://y.gtimg.cn/music/photo_new/T001R300x300M000000Qm9wO1bcM6J.jpg?max_age=2592000",
            "天黑黑","孙燕姿","https://y.gtimg.cn/music/photo_new/T001R300x300M000001pWERg3vFgg8.jpg?max_age=2592000",
            "修炼爱情","林俊杰","https://y.gtimg.cn/music/photo_new/T001R300x300M000001BLpXF2DyJe2.jpg?max_age=2592000",
            "青花瓷","周杰伦","https://y.gtimg.cn/music/photo_new/T001R300x300M0000025NhlN2yWrP4.jpg?max_age=2592000",
            "你的背包","陈奕迅","https://y.gtimg.cn/music/photo_new/T001R300x300M000003Nz2So3XXYek.jpg?max_age=2592000",
            "爱一点" ,"王力宏","https://y.gtimg.cn/music/photo_new/T001R300x300M000001JDzPT3JdvqK.jpg?max_age=2592000",
            "后会无期","邓紫棋","https://y.gtimg.cn/music/photo_new/T001R300x300M000001fNHEf1SFEFN.jpg?max_age=2592000",
            "如果这都不算爱","张学友","https://y.gtimg.cn/music/photo_new/T001R300x300M000004Be55m1SJaLk.jpg?max_age=2592000",
            "我们说好的","张靓颖","https://y.gtimg.cn/music/photo_new/T001R300x300M000000aw4WC2EQYTv.jpg?max_age=2592000",
            "但愿人长久","王菲","https://y.gtimg.cn/music/photo_new/T001R300x300M000000GDDuQ3sGQiT.jpg?max_age=2592000",
            "听海","张惠妹","https://y.gtimg.cn/music/photo_new/T001R300x300M000003JGrNQ3RjelA.jpg?max_age=2592000",
            "放生","范逸臣","https://y.gtimg.cn/music/photo_new/T001R300x300M000003DJJIO1UHlgc.jpg?max_age=2592000",
            "走在冷风中","刘思涵","https://y.gtimg.cn/music/photo_new/T001R300x300M0000030tOaw01vdLW.jpg?max_age=2592000",
            "花火","汪峰","https://y.gtimg.cn/music/photo_new/T001R300x300M000001adLDR1SS40P.jpg?max_age=2592000",
            "匆匆那年" ,"王菲","https://y.gtimg.cn/music/photo_new/T001R300x300M000000GDDuQ3sGQiT.jpg?max_age=2592000",
            "明天过后","张杰","https://y.gtimg.cn/music/photo_new/T001R300x300M000002azErJ0UcDN6.jpg?max_age=2592000",
            "离歌","信乐团","https://y.gtimg.cn/music/photo_new/T001R300x300M000002m2KUd0283YC.jpg?max_age=2592000",
            "痒","黄龄","https://y.gtimg.cn/music/photo_new/T001R300x300M000003AKgLE4SfjsK.jpg?max_age=2592000",
            "三天三夜","张惠妹","https://y.gtimg.cn/music/photo_new/T001R300x300M000003JGrNQ3RjelA.jpg?max_age=2592000",
            "离人","张学友","https://y.gtimg.cn/music/photo_new/T001R300x300M000004Be55m1SJaLk.jpg?max_age=2592000",
            "不将就","李荣浩","https://y.gtimg.cn/music/photo_new/T001R300x300M000000aHmbL2aPXWH.jpg?max_age=2592000",
            "Melody","陶喆","https://y.gtimg.cn/music/photo_new/T001R300x300M000002cK0F12szD9T.jpg?max_age=2592000",
            "舞娘","蔡依林","https://y.gtimg.cn/music/photo_new/T001R300x300M0000027pdHE4STooO.jpg?max_age=2592000",
            "王妃" ,"萧敬腾","https://y.gtimg.cn/music/photo_new/T001R300x300M000004bsIDK0awMOv.jpg?max_age=2592000",
            "简单爱","周杰伦","https://y.gtimg.cn/music/photo_new/T001R300x300M0000025NhlN2yWrP4.jpg?max_age=2592000",
            "不搭","李荣浩","https://y.gtimg.cn/music/photo_new/T001R300x300M000000aHmbL2aPXWH.jpg?max_age=2592000",
            "眼泪","范晓萱","https://y.gtimg.cn/music/photo_new/T001R300x300M000000CLIfb03b9k7.jpg?max_age=2592000",
            "背对背拥抱","林俊杰","https://y.gtimg.cn/music/photo_new/T001R300x300M000001BLpXF2DyJe2.jpg?max_age=2592000",
            "花房姑娘","崔健","https://y.gtimg.cn/music/photo_new/T001R300x300M000003VWf5147Vr5U.jpg?max_age=2592000",
            "一念之间","张杰","https://y.gtimg.cn/music/photo_new/T001R300x300M000002azErJ0UcDN6.jpg?max_age=2592000",
            "认真的雪","薛之谦","https://y.gtimg.cn/music/photo_new/T001R300x300M000002J4UUk29y8BY.jpg?max_age=2592000",
            "最熟悉的陌生人","萧亚轩","https://y.gtimg.cn/music/photo_new/T001R300x300M000002tkdEU4gLVqO.jpg?max_age=2592000",
            "一场游戏一场梦" ,"王杰","https://y.gtimg.cn/music/photo_new/T001R300x300M000003NKwHr46UKeR.jpg?max_age=2592000",
            "画心","张靓颖","https://y.gtimg.cn/music/photo_new/T001R300x300M000000aw4WC2EQYTv.jpg?max_age=2592000",
            "也许明天","张惠妹","https://y.gtimg.cn/music/photo_new/T001R300x300M000003JGrNQ3RjelA.jpg?max_age=2592000",
            "倒带","蔡依林","https://y.gtimg.cn/music/photo_new/T001R300x300M0000027pdHE4STooO.jpg?max_age=2592000",
            "城里的月光","许美静","https://y.gtimg.cn/music/photo_new/T001R300x300M000000jiXJP0fMEbc.jpg?max_age=2592000",
            "燃点","胡夏","https://y.gtimg.cn/music/photo_new/T001R300x300M000002mze3U0NYXOM.jpg?max_age=2592000",
            "大城小爱","王力宏","https://y.gtimg.cn/music/photo_new/T001R300x300M000001JDzPT3JdvqK.jpg?max_age=2592000",
            "勇敢的心","汪峰","https://y.gtimg.cn/music/photo_new/T001R300x300M000001adLDR1SS40P.jpg?max_age=2592000",
            "模特","李荣浩","https://y.gtimg.cn/music/photo_new/T001R300x300M000000aHmbL2aPXWH.jpg?max_age=2592000",
            "我们的爱" ,"飞儿乐团","https://y.gtimg.cn/music/photo_new/T001R300x300M0000010PLKl2Wgolz.jpg?max_age=2592000",
            "下一站天后","Twins","https://y.gtimg.cn/music/photo_new/T001R300x300M000000BwYzf1pWCFB.jpg?max_age=2592000",
            "孤独患者","陈奕迅","https://y.gtimg.cn/music/photo_new/T001R300x300M000003Nz2So3XXYek.jpg?max_age=2592000",
            "你最珍贵","张学友","https://y.gtimg.cn/music/photo_new/T001R300x300M000004Be55m1SJaLk.jpg?max_age=2592000",
            "爱情转移","陈奕迅","https://y.gtimg.cn/music/photo_new/T001R300x300M000003Nz2So3XXYek.jpg?max_age=2592000",
            "绿光","孙燕姿","https://y.gtimg.cn/music/photo_new/T001R300x300M000001pWERg3vFgg8.jpg?max_age=2592000",
            "开不了口","周杰伦","https://y.gtimg.cn/music/photo_new/T001R300x300M0000025NhlN2yWrP4.jpg?max_age=2592000",
            "绅士","薛之谦","https://y.gtimg.cn/music/photo_new/T001R300x300M000002J4UUk29y8BY.jpg?max_age=2592000",
            "三人游","方大同","https://y.gtimg.cn/music/photo_new/T001R300x300M000003zHcYF44FVEV.jpg?max_age=2592000",
            "丑八怪" ,"薛之谦","https://y.gtimg.cn/music/photo_new/T001R300x300M000002J4UUk29y8BY.jpg?max_age=2592000",
            "两个人的烟火","黎明","https://y.gtimg.cn/music/photo_new/T001R300x300M000001wYy5s2mM3Tq.jpg?max_age=2592000",
            "如果你也听说","张惠妹","https://y.gtimg.cn/music/photo_new/T001R300x300M000003JGrNQ3RjelA.jpg?max_age=2592000",
            "你的眼睛背叛你的心","郑中基","https://y.gtimg.cn/music/photo_new/T001R300x300M000001XOTno2nFjJP.jpg?max_age=2592000",
            "空城","杨坤","https://y.gtimg.cn/music/photo_new/T001R300x300M0000043OO7j3TsLnO.jpg?max_age=2592000",
            "值得","郑秀文","https://y.gtimg.cn/music/photo_new/T001R300x300M000002yjHfE3aJX69.jpg?max_age=2592000",
            "心如刀割","张学友","https://y.gtimg.cn/music/photo_new/T001R300x300M000004Be55m1SJaLk.jpg?max_age=2592000",
            "偏爱","张芸京","https://y.gtimg.cn/music/photo_new/T001R300x300M000000acPsp0cfUZr.jpg?max_age=2592000",
            "天后","陈势安","https://y.gtimg.cn/music/photo_new/T001R300x300M0000049u0DR3G9Rgv.jpg?max_age=2592000",
            "只想一生跟你走" ,"张学友","https://y.gtimg.cn/music/photo_new/T001R300x300M000004Be55m1SJaLk.jpg?max_age=2592000",
            "现在已夜深","周柏豪","https://y.gtimg.cn/music/photo_new/T001R300x300M00000180Rk61RE4qh.jpg?max_age=2592000",
            "爱我的人和我爱的人","游鸿明","https://y.gtimg.cn/music/photo_new/T001R300x300M000002qezg71e59Kf.jpg?max_age=2592000",
            "我们的明天","鹿晗","https://y.gtimg.cn/music/photo_new/T001R300x300M000001SqkF53OEhdO.jpg?max_age=2592000",
            "我","张国荣","https://y.gtimg.cn/music/photo_new/T001R300x300M0000001v4XU1cZxPy.jpg?max_age=2592000",
            "太傻","巫启贤","https://y.gtimg.cn/music/photo_new/T001R300x300M000003EL2So3NxH6K.jpg?max_age=2592000",
            "解脱","张惠妹","https://y.gtimg.cn/music/photo_new/T001R300x300M000003JGrNQ3RjelA.jpg?max_age=2592000",
            "快乐崇拜","潘玮柏","https://y.gtimg.cn/music/photo_new/T001R300x300M000000iM9BB2TXFqu.jpg?max_age=2592000",
            "往日时光","谭维维","https://y.gtimg.cn/music/photo_new/T001R300x300M000000we89k1hzBcA.jpg?max_age=2592000",
            "曹操" ,"林俊杰","https://y.gtimg.cn/music/photo_new/T001R300x300M000001BLpXF2DyJe2.jpg?max_age=2592000",
            "春天里","汪峰","https://y.gtimg.cn/music/photo_new/T001R300x300M000001adLDR1SS40P.jpg?max_age=2592000",
            "至少还有你","林忆莲","https://y.gtimg.cn/music/photo_new/T001R300x300M000002u0TJy47WWOj.jpg?max_age=2592000",
            "趁早","张宇","https://y.gtimg.cn/music/photo_new/T001R300x300M0000044wQXL0ElWF1.jpg?max_age=2592000",
            "爱多一次痛多一次","谭咏麟","https://y.gtimg.cn/music/photo_new/T001R300x300M0000040D7gK4aI54k.jpg?max_age=2592000",
            "剑心","张杰","https://y.gtimg.cn/music/photo_new/T001R300x300M000002azErJ0UcDN6.jpg?max_age=2592000",
            "勇气","梁静茹","https://y.gtimg.cn/music/photo_new/T001R300x300M000000GGDys0yA0Nk.jpg?max_age=2592000",
            "李白","李荣浩","https://y.gtimg.cn/music/photo_new/T001R300x300M000000aHmbL2aPXWH.jpg?max_age=2592000",
            "欧若拉","张韶涵","https://y.gtimg.cn/music/photo_new/T001R300x300M000002raUWw3PXdkT.jpg?max_age=2592000",
            "心墙" ,"郭静","https://y.gtimg.cn/music/photo_new/T001R300x300M0000043Zxw10txf5O.jpg?max_age=2592000",
            "冲动的惩罚","刀郎","https://y.gtimg.cn/music/photo_new/T001R300x300M000000iZroE1VWLJG.jpg?max_age=2592000",
            "友情岁月","郑伊健","https://y.gtimg.cn/music/photo_new/T001R300x300M000003bdcMg1ML7YC.jpg?max_age=2592000",
            "祝福","张学友","https://y.gtimg.cn/music/photo_new/T001R300x300M000004Be55m1SJaLk.jpg?max_age=2592000",
            "相见恨晚","彭佳慧","https://y.gtimg.cn/music/photo_new/T001R300x300M000002sStNs4QWLyA.jpg?max_age=2592000",
            "我的骄傲","容祖儿","https://y.gtimg.cn/music/photo_new/T001R300x300M000001uXFgt1kpLyI.jpg?max_age=2592000",
            "爱的魔法","金莎","https://y.gtimg.cn/music/photo_new/T001R300x300M000003MDXPR3tcGSm.jpg?max_age=2592000",
            "男人不该让女人流泪","苏永康","https://y.gtimg.cn/music/photo_new/T001R300x300M000001eTuff0uRGAW.jpg?max_age=2592000",
            "好心情","李玟","https://y.gtimg.cn/music/photo_new/T001R300x300M000003gNFzb0MYOnM.jpg?max_age=2592000",
            "算你狠" ,"陈小春","https://y.gtimg.cn/music/photo_new/T001R300x300M000004DFS271osAwp.jpg?max_age=2592000",
            "情歌王","古巨基","https://y.gtimg.cn/music/photo_new/T001R300x300M000000NFT2p1GbnPB.jpg?max_age=2592000",
            "过火","张信哲","https://y.gtimg.cn/music/photo_new/T001R300x300M0000000mFvh1jtLcz.jpg?max_age=2592000",
            "洋葱","杨宗纬","https://y.gtimg.cn/music/photo_new/T001R300x300M000003tMm0y0TuewY.jpg?max_age=2592000",
            "一笑而过","那英","https://y.gtimg.cn/music/photo_new/T001R300x300M000003LCFXH0eodXv.jpg?max_age=2592000",
            "入戏太深","马旭东","https://y.gtimg.cn/music/photo_new/T001R300x300M000003wWQBU0fHBcj.jpg?max_age=2592000",
            "情非得已","庾澄庆","https://y.gtimg.cn/music/photo_new/T001R300x300M000001G1w4p3Dk92Q.jpg?max_age=2592000",
            "征服","那英","https://y.gtimg.cn/music/photo_new/T001R300x300M000003LCFXH0eodXv.jpg?max_age=2592000",
            "如果这就是爱情","张靓颖","https://y.gtimg.cn/music/photo_new/T001R300x300M000000aw4WC2EQYTv.jpg?max_age=2592000",
            "春泥" ,"庾澄庆","https://y.gtimg.cn/music/photo_new/T001R300x300M000001G1w4p3Dk92Q.jpg?max_age=2592000",
            "死了都要爱","信乐团","https://y.gtimg.cn/music/photo_new/T001R300x300M000002m2KUd0283YC.jpg?max_age=2592000",
            "月亮惹的祸","张宇","https://y.gtimg.cn/music/photo_new/T001R300x300M0000044wQXL0ElWF1.jpg?max_age=2592000",
            "爱如潮水","张信哲","https://y.gtimg.cn/music/photo_new/T001R300x300M0000000mFvh1jtLcz.jpg?max_age=2592000",
            "黑色毛衣","周杰伦","https://y.gtimg.cn/music/photo_new/T001R300x300M0000025NhlN2yWrP4.jpg?max_age=2592000",
            "暖暖","梁静茹","https://y.gtimg.cn/music/photo_new/T001R300x300M000000GGDys0yA0Nk.jpg?max_age=2592000",
            "练习","刘德华","https://y.gtimg.cn/music/photo_new/T001R300x300M000003aQYLo2x8izP.jpg?max_age=2592000",
            "今夜你会不会来","黎明","https://y.gtimg.cn/music/photo_new/T001R300x300M000001wYy5s2mM3Tq.jpg?max_age=2592000",
            "我是不是该安静的走开","郭富城","https://y.gtimg.cn/music/photo_new/T001R300x300M000003yM0OT1EPY0y.jpg?max_age=2592000",
            "吻别" ,"张学友","https://y.gtimg.cn/music/photo_new/T001R300x300M000004Be55m1SJaLk.jpg?max_age=2592000",
     };

     //经典珍藏
     static String[] songArray2 ={
        //歌名 歌手名
          "上海滩","叶丽仪",
             "铁血丹心","罗文",
             "雪中情","杨庆煌",
             "醉","方大同",
             "风马","郑钧",
             "难念的经","周华健",
             "世间始终你好","罗文",
             "月光爱人","李玟",
             "滚滚红尘","罗大佑",
             "浅醉一生","叶倩文",
             "像梦一样自由","汪峰",
             "潇洒走一回","叶倩文",
             "谢谢你的爱","谢霆锋",
             "红日","李克勤",
             "一起走过的日子","刘德华",
             "有心人","张国荣",
             "野孩子","杨千嬅",
             "搜神记","容祖儿",
             "终身美丽","郑秀文",
             "漫步人生路","邓丽君",
             "春夏秋冬","张国荣",
             "千千阙歌","陈慧娴",
             "只愿一生爱一人","张学友",
             "光辉岁月","BEYOND",

             "不浪漫罪名","王杰",
             "让一切随风","钟镇涛",
             "富士山下","陈奕迅",
             "就算世界无童话","卫兰",
             "似水流年","张国荣",
             "不如不见","陈奕迅",

             "命硬","侧田",
             "珍重","叶倩文",
             "一生所爱","卢冠廷",
             "晚秋","黄凯芹",
             "生还者","余文乐",
             "浮夸","陈奕迅",

             "慕容雪","薛凯琪",
             "睡公主","陈慧娴",
             "深深深","李克勤",
             "想得太远","容祖儿",
             "偏偏喜欢你","陈百强",
             "情人知己","叶倩文",

             "人生何处不相逢","陈慧娴",
             "涟漪","陈百强",
             "生命树","吴雨霏",
             "谁明浪子心","王杰",
             "总有你鼓励","李克勤",
             "流浪花","吕方",

             "最爱","周慧敏",
             "敢爱敢做","林子祥",
             "再见亦是泪","谭咏麟",
             "再见理想","BEYOND",
             "冷雨夜","BEYOND",
             "17岁","刘德华",

             "皇后大道东","罗大佑",
             "等你等到我心痛","张学友",
             "心跳呼吸正常","张国荣",
             "讲不出再见","谭咏麟",
             "好心分手","卢巧音",
             "大男人","许志安",

             "爱的呼唤","郭富城",
             "爱的挽歌","郑秀文",
             "天命最高","古天乐",
             "藏身","郑中基",
             "天气的错","郑伊健",
             "从不放弃","郑少秋",

             "笑看风云","郑少秋",
             "大时代过客","郑少秋",
             "当年情","张国荣",
             "飞沙风中转","周润发",
             "叱咤红人","陈小春",
             "少女的祈祷","张智霖",

             "女人花","梅艳芳",
             "孤身走我路","梅艳芳",
             "三暝三日","吴宗宪",
             "是不是这样的夜晚你才会这样的想起我","吴宗宪",
             "新鸳鸯蝴蝶梦","黄安",
             "样样红","黄安",
             "东南西北风","黄安",

             "隐形的翅膀","张韶涵",
             "一千年以前","林俊杰",
             "发如雪","周杰伦",
             "一路向北","周杰伦",
             "一千年以后","林俊杰",
             "大城小爱","王力宏",
             "天空","蔡依林",
             "许愿池的少女","蔡依林",
             "花田错","王力宏",
             "大舌头","吴克群",
             "我会好好的","王心凌",
             "断点","张敬轩",

             "爱我还是他","陶喆",
             "嘻唰唰","花儿乐队",
             "美丽的童话","孙楠",
             "夜曲","周杰伦",
             "莫斯科没有眼泪","TWINS",



    };

     public static ArrayList<Music> getSongs(int resId){
         switch (resId)
         {
             case R.drawable.ic_pop_album1:

                 return getMusicList(songArray1);
             case R.drawable.ic_pop_album2:
                 return getMusicList(songArray2);
         }

         return null;
     }

     public static  ArrayList<Music> getMusicList(String[]  strs){
         ArrayList<Music> musics = new ArrayList<Music>();
         for(int i=0;i<strs.length;i+=3){
             Music m = new Music();
             m.name = strs[i];
             m.singer = strs[i+1];
             m.imgUrl = strs[i+2];
             musics.add(m);
         }

         return musics;
     }

}
