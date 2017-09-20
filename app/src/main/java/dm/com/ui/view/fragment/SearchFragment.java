package dm.com.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import dm.com.Config;
import dm.com.R;
import dm.com.ui.view.base.BaseFragment;

/**
 * Created by m on 2017/2/23.
 * ${describe}
 */

public class SearchFragment extends BaseFragment {

    /**
     * 用来标志请求的what, 类似handler的what一样，这里用来区分请求。
     */
    private static final int NOHTTP_WHAT = 0x002;
    private RequestQueue queue;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }




    /**nohttp post请求测试案例*/
    private void pushBody() {

        Request<String> request = NoHttp.createStringRequest(Config.SERVER_BASEURL, RequestMethod.POST);

        request.add("accName","啦啦啦");
        request.add("accNo","6236216609000063505");
        request.add("accSts","AS01");
        request.add("brf", "电子账号下子账户信息查询");
        request.add("busType", "----");
        request.add("chnlDate", "20170320");
        request.add( "chnlType", "0061");
        request.add("chrgAccNo", "6236216609000063505");
        request.add("cifNo", "110000001931");
        request.add("cityCode", "233");
        request.add("deductType", "DT02");
        request.add("deviceNo", "862873024790260");
        request.add("transCode", "105129");
        request.add("transTime", "2017-03-20 15:28:21");
        request.add("txBrNo", "461910");
        request.add("txIp", "172.27.35.12");
        request.add("versionId", "1.0");

        queue.add(NOHTTP_WHAT, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {}

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (what == NOHTTP_WHAT)
                   Logger.d(response.get());
                   Logger.e(response.getHeaders());
            }

            @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Throwable exception = response.getException();
                if (exception instanceof NetworkError) {// 网络不好
                    Toast.makeText(getActivity(), R.string.error_please_check_network,Toast.LENGTH_LONG).show();
                } else if (exception instanceof TimeoutError) {// 请求超时
                    Toast.makeText(getActivity(), R.string.error_timeout,Toast.LENGTH_LONG).show();
                } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                    Toast.makeText(getActivity(), R.string.error_not_found_server,Toast.LENGTH_LONG).show();
                } else if (exception instanceof URLError) {// URL是错的
                    Toast.makeText(getActivity(), R.string.error_url_error,Toast.LENGTH_LONG).show();
                }
                Logger.e("错误：" + exception.getMessage());
            }
            @Override
            public void onFinish(int what) {}
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search_layout;
    }

    @Override
    public void initView() {

    }
}
