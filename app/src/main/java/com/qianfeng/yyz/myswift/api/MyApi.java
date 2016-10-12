package com.qianfeng.yyz.myswift.api;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class MyApi {


    public static final String BASE_URL = "http://www.1688wan.com";

    public static class GiftApi{
        //礼包列表api
        public static final String GIFT_LIST = "http://www.1688wan.com/majax.action?method=getGiftList&pageno=%s";
        //礼包详情
        public static final String GIFT_DETAL = "http://www.1688wan.com/majax.action?method=getGiftInfo&id=%s";
        //领取礼包码
        //uid
        public static final String GIFT_CODE = "http://www.1688wan.com/majax.action?method=GiftCheck&uid=%s&gid=%s";
        public static final String MYGIFT = "http://www.1688wan.com/majax.action?method=getMyGift&uid=%s";
        public static final String TAOHAO = "http://www.1688wan.com/majax.action?method=taohao&uid=%s&gid=%s";
    }

    public static class Open{

        public static final String OPEN_CE = "http://www.1688wan.com/majax.action?method=getWebfutureTest";

        public static final String OPEN_FU = "http://www.1688wan.com/majax.action?method=getJtkaifu";

        public static final String OPEN_DETAL = "http://www.1688wan.com/majax.action?method=getAppInfo&id=%s";

    }

    public static class Hote{

        public static final String HOT_LIST = "http://www.1688wan.com//majax.action?method=hotpushForAndroid";

    }

    public static class Special{
        //暴打星期三
        public static final String SP_LEFT = "http://www.1688wan.com/majax.action?method=bdxqs&pageNo=0";
        public static final String SP_LEFT_DT = "http://www.1688wan.com/majax.action?method=bdxqschild&id=%s";
        //新游周刊
        public static final String SP_RIGHT = "http://www.1688wan.com/majax.action?method=getWeekll&pageNo=0";
        public static final String SP_RIGHT_DT = "http://www.1688wan.com/majax.action?method=getWeekllChid&id=%s";
    }

    public static class Search{
        public static final String SEARCH = "http://www.1688wan.com/majax.action?method=searchGift";
    }

    public static class Login{
        public static final String SINGIN = "http://www.1688wan.com//webmember.action?method=userRegisterForMobile";
        public static final String LOGIN = "http://www.1688wan.com//majax.action?method=mobileUserLogin";
    }

    public static class Send{
        //desc	t4546577
        //contact	5688727284944554
        public static final String SEND = "http://www.1688wan.com//webmember.action?method=member_advise";
    }
}
