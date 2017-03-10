package dm.com;

import android.app.Application;

import com.yanzhenjie.nohttp.*;
import com.yanzhenjie.nohttp.BuildConfig;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

/**
 * Created by m on 2017/2/23.
 * ${describe}
 */

public class App  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.setDebug(BuildConfig.DEBUG);
        Logger.setTag("==dm==");
        NoHttp.initialize(this,new NoHttp.Config()
                .setConnectTimeout(5 * 1000)
                .setReadTimeout(5 * 1000)
                .setCacheStore(new DBCacheStore(this).setEnable(false))
                .setCookieStore(new DBCookieStore(this).setEnable(false))
                .setNetworkExecutor(new OkHttpNetworkExecutor()));

    }
}
