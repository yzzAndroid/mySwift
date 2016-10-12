package com.qianfeng.yyz.myswift.bean.second;

import java.util.List;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class SpecialRightSecondBean {


    /**
     * id : 882
     * fid : 20161007
     * appid : 1468573795
     * appname : 崩坏3
     * typename : 角色扮演
     * appsize : 1.52 GB
     * adimg : /allimgs/img_iapp/201610/_1475909662953.jpg
     * appkfs : mihoyo
     * iconurl : /allimgs/img_iapp/201607/_1468571440613.jpg
     * addtime : 2016-10-08
     * descs : 《崩坏3》作为一款国内厂商开发的ACG手游，游戏的质量毋庸置疑，同时目标锁定国内二次元群体，并且以出色的人设和精美的动作画面作为卖点也取得了不错的效果，全中文语音支持免去了玩家的语言障碍。虽然1.5G的容量让本作在一定程度上成为了一个比较“吃配置”的存在，但毕竟体验非常不错，感兴趣的玩家千万不要错过。
     * critique : 崩坏3是由mihoyo(米忽悠)开发制作的一款3D萌娘动作手游，延续了mihoyo的经典(唯一)IP崩坏系列的剧情，原作为崩坏3rd漫画，本作延续长空市崩坏发生后，讲述了主角琪亚娜，雷电芽衣，布洛妮娅成为女武神之后的故事。前作崩坏学园2相信了解崩坏3的玩家都有接触，不过不同的则是崩坏学园2是一款横板动作类游戏，到了崩坏3就成为了一款3D游戏，崩坏学园2中在打僵尸，崩坏3中打高达。
     * iszq : 0
     * typeid : 0
     * istop : 1
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int id;
        private int fid;
        private String appid;
        private String appname;
        private String typename;
        private String appsize;
        private String adimg;
        private String appkfs;
        private String iconurl;
        private String addtime;
        private String descs;
        private String critique;
        private int iszq;
        private int typeid;
        private int istop;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getAppsize() {
            return appsize;
        }

        public void setAppsize(String appsize) {
            this.appsize = appsize;
        }

        public String getAdimg() {
            return adimg;
        }

        public void setAdimg(String adimg) {
            this.adimg = adimg;
        }

        public String getAppkfs() {
            return appkfs;
        }

        public void setAppkfs(String appkfs) {
            this.appkfs = appkfs;
        }

        public String getIconurl() {
            return iconurl;
        }

        public void setIconurl(String iconurl) {
            this.iconurl = iconurl;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getDescs() {
            return descs;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }

        public String getCritique() {
            return critique;
        }

        public void setCritique(String critique) {
            this.critique = critique;
        }

        public int getIszq() {
            return iszq;
        }

        public void setIszq(int iszq) {
            this.iszq = iszq;
        }

        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }

        public int getIstop() {
            return istop;
        }

        public void setIstop(int istop) {
            this.istop = istop;
        }
    }
}
