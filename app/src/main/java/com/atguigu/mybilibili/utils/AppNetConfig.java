package com.atguigu.mybilibili.utils;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class AppNetConfig {
    //TAG
    public static final String DISCOVER_TAG="http://app.bilibili.com/x/v2/search/hot?appkey=1d8b6e7d45233436&build=501000&limit=50&mobi_app=android&platform=android&ts=1490014710000&sign=e5ddf94fa9a0d6876cb85756c37c4adc";
    //话题
    public static final String DISCOVER_HUATI="http://api.bilibili.com/topic/getlist?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&page=1&pageSize=20&platform=android&ts=1490015740000&sign=be68382cdc99c168ef87f2fa423dd280";
    //活动
    public static final String DISCOVER_HUODONG="http://api.bilibili.com/event/getlist?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&page=1&pageSize=20&platform=android&ts=1490015812000&sign=0d9d37f01da5a7d425c10cee0cf3a5f4";
    //原创排行榜----原创
    public static final String DISCOVER_ORIGINAL="http://app.bilibili.com/x/v2/rank?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&order=origin&platform=android&pn=1&ps=20&ts=1490015891000&sign=1a5a1c73e3b23be37fb13ee0178ceef0";

    public static final String LIVE="http://live.bilibili.com/AppNewIndex/common?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=hdpi&ts=1490013188000&sign=92541a11ed62841120e786e637b9db3b";

    public static  String search (String search){
        return "http://app.bilibili.com/x/v2/search?appkey=1d8b6e7d45233436&build=501000&duration=0&keyword="+search+"&mobi_app=android&platform=android&pn=1&ps=20";
    }
    //推荐
    public static final String RECOMMEND ="http://app.bilibili.com/x/feed/index?appkey=1d8b6e7d45233436&build=501000&idx=1490013261&mobi_app=android&network=wifi&platform=android&pull=true&style=2&ts=1490015599000&sign=af4edc66aef7e443c98c28de2b660aa4";
    //分区首页
    public static final String PARTITION ="http://app.bilibili.com/x/v2/show/region?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&ts=1490014674000&sign=93edb7634f38498a60e5c3ad0b8b0974";
    //追番
    public static final String CARTOON ="http://bangumi.bilibili.com/api/app_index_page_v4?build=3940&device=phone&mobi_app=iphone&platform=ios";
    //video 评论
    public static final String VIDEO ="http://api.bilibili.com/x/v2/reply?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&oid=9270287&plat=2&platform=android&pn=1&ps=20&sort=0&type=1&sign=d4a5c3858fb6e820661f4083f9d12614";
    public static final String MAIL ="http://bmall.bilibili.com/api/product/detail.do?skuId=90";

}
