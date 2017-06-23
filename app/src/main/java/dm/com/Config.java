package dm.com;

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
}
