package com.alibaba.bean;

/**
 * @author zwq
 * @date 2019/11/4 11:30
 */
public class AppActive_background {
    private String active_source;//1=upgrade,2=download(下载),3=plugin_upgrade

    public String getActive_source() {
        return active_source;
    }

    public void setActive_source(String active_source) {
        this.active_source = active_source;
    }
}
