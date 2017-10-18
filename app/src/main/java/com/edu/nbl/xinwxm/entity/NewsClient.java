package com.edu.nbl.xinwxm.entity;

import com.edu.nbl.xinwxm.network.EasyShopApi;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by ydy on 2017/10/11.
 */

public class NewsClient {

    /**
     *
     * @param subid 新闻类别
     * @param dir 方向--下拉(1)or上拉(2)
     * @param nid 如果是上拉，那么最后一条新闻id，否则0
     * @return
     */
    public Call getNews(int subid,int dir,int nid){
        String url = "news_list?ver=2&subid=" + subid +"&dir=" + dir + "&nid=" + nid + "&stamp=null&cnt=2";
        Request mRequest=new Request
                .Builder()
                .url(EasyShopApi.APP_URL+url)
                .build();
        return null;
    }
}
