package dm.com;

import dm.com.router.Router;

/**
 * Created by m on 2017/3/10.
 * ${describe}
 */

public class Config {

    public static final String REQUEST_URL = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";

    public static final String SERVER_BASEURL =  "http://192.168.2.3:9008/dibs-gw-4/web/dealWebTrade.do";

    public static String getRequestUrl (String type , int pageSize , int page) {
       return  "http://gank.io/api/data/" + type + "/"
                + String.valueOf(pageSize) + "/"
                + String.valueOf(page);
    }

    // #router
    public static final int ROUTER_ANIM_ENTER = Router.RES_NONE;
    public static final int ROUTER_ANIM_EXIT = Router.RES_NONE;

    public static final int MODE_DEFAULT = 0;

    public static final int MODE_SONIC = 1;

    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

    public static final String DEMO_URL = "http://mc.vip.qq.com/demo/indexv3";
}
