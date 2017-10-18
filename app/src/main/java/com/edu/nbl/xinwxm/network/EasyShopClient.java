package com.edu.nbl.xinwxm.network;


import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/7/17 0017.
 * okhttp3网络框架构建
 */
public class EasyShopClient {
    private static EasyShopClient easyShopClient;
    private OkHttpClient okHttpClient;
    private Gson gson;

    //单例模式 懒汉式
    public static EasyShopClient getInstance() {
        if (easyShopClient == null) {
            easyShopClient = new EasyShopClient();
        }
        return easyShopClient;
    }

    private EasyShopClient() {//构造方法私有化
        //设置拦截器级别
        okHttpClient = new OkHttpClient.Builder()
                .build();
        gson = new Gson();
    }

    public Call getNewsType() {
        Request mRequest = new Request.Builder()
                .url(EasyShopApi.APP_URL + EasyShopApi.NEWS_TYPE)
                .build();
        return okHttpClient.newCall(mRequest);
    }

}
