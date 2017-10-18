package com.edu.nbl.xinwxm.entity;

import java.io.Serializable;

/**
 * Created by ydy on 2017/8/30.
 */

public class News implements Serializable {
   public String summary;
   public String icon;
   public String stamp;
   public String title;
   public String link;
    public int nid;
    public int type;

    public News(String summary, String icon, String stamp, String title, String link, int nid, int type) {
        this.summary = summary;
        this.icon = icon;
        this.stamp = stamp;
        this.title = title;
        this.link = link;
        this.nid = nid;
        this.type = type;
    }

    public News(String summary, String icon, String stamp, String title, String link, int nid) {
        this.summary = summary;
        this.icon = icon;
        this.stamp = stamp;
        this.title = title;
        this.link = link;
        this.nid = nid;
    }

    @Override
    public String toString() {
        return "News{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", stamp='" + stamp + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", nid=" + nid +
                ", type=" + type +
                '}';
    }
}
