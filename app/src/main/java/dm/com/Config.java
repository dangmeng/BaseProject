package dm.com;

/**
 * Created by m on 2017/3/10.
 * ${describe}
 */

public class Config {

    public static final String REQUEST_URL = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";

    public static String getRequestUrl (int pageSize , int page) {
       return  "http://gank.io/api/data/all" + "/"
                + String.valueOf(pageSize) + "/"
                + String.valueOf(page);
    }
}