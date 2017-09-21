package dm.com;

import android.app.Application;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import skin.support.SkinCompatManager;

/**
 * Created by m on 2017/2/23.
 * ${describe}
 */

public class App  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.setDebug(true);
        Logger.setTag("==BaseProject==");
        NoHttp.initialize(this,new NoHttp.Config()
                .setConnectTimeout(5 * 1000)
                .setReadTimeout(5 * 1000)
                .setCacheStore(new DBCacheStore(this).setEnable(false))
                .setCookieStore(new DBCookieStore(this).setEnable(false))
                .setNetworkExecutor(new OkHttpNetworkExecutor()));

        initSkin();

    }

    private void initSkin() {
        SkinCompatManager.withoutActivity(this)        // 基础控件换肤初始化
                .setSkinStatusBarColorEnable(false)    // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)  // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }
}
