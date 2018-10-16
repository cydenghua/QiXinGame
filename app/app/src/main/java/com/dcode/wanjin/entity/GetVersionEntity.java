package com.dcode.wanjin.entity;

public class GetVersionEntity {

//
//    {
//        "update": "Yes",//有新版本
//            "new_version": "0.8.3",//新版本号
//            "apk_file_url": "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/app-debug.apk", //apk下载地址
//            "update_log": "1，添加删除信用卡接口\r\n2，添加vip认证\r\n3，区分自定义消费，一个小时不限制。\r\n4，添加放弃任务接口，小时内不生成。\r\n5，消费任务手动生成。",//更新内容
//            "target_size": "5M",//apk大小
//            "new_md5":"A818AD325EACC199BC62C552A32C35F2",
//            "constraint": false//是否强制更新
//    }

    private String update;
    private String new_version;
    private String apk_file_url;
    private String update_log;
    private String target_size;
    private String new_md5;
    private boolean constraint;

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }

    public String getApk_file_url() {
        return apk_file_url;
    }

    public void setApk_file_url(String apk_file_url) {
        this.apk_file_url = apk_file_url;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getTarget_size() {
        return target_size;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }

    public String getNew_md5() {
        return new_md5;
    }

    public void setNew_md5(String new_md5) {
        this.new_md5 = new_md5;
    }

    public boolean isConstraint() {
        return constraint;
    }

    public void setConstraint(boolean constraint) {
        this.constraint = constraint;
    }


}
