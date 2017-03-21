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

    public static  String search (String search){
        return "http://app.bilibili.com/x/v2/search?appkey=1d8b6e7d45233436&build=501000&duration=0&keyword="+search+"&mobi_app=android&platform=android&pn=1&ps=20";
    }
}
