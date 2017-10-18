package com.edu.nbl.xinwxm.entity;

/**
 * Created by ydy on 2017/8/30.
 */

public class NewsType {
  public String subgroup;
    public int  subid;

    public NewsType(String subgroup, int subid) {
        this.subgroup = subgroup;
        this.subid = subid;
                }

@Override
public String toString() {
        return "NewssTape{" +
        "subgroup='" + subgroup + '\'' +
        ", subid=" + subid +
        '}';
        }
        }
