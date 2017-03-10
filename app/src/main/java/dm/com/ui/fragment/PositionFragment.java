package dm.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

/**
 * Created by m on 2017/2/23.
 * ${describe}
 */

public class PositionFragment extends BaseFragment {

    private RequestQueue mQueue;
    /**
     * 用来标志请求的what, 类似handler的what一样，这里用来区分请求。
     */
    private static final int NOHTTP_WHAT_TEST = 0x001;
    private TextView textView;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mQueue = NoHttp.newRequestQueue();
        textView = new TextView(getActivity());
        textView.setText(PositionFragment.class.getSimpleName());
        return textView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Request<String> request = NoHttp.createStringRequest(Config.REQUEST_URL, RequestMethod.GET);
        mQueue.add(NOHTTP_WHAT_TEST,request,responseListener);

//        StringRequest request = new StringRequest(Config.REQUEST_URL,RequestMethod.GET);
//
//        CallServer.getInstance().add(NOHTTP_WHAT_TEST, request, new SimpleHttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Result<String> t) {
//                if (what == NOHTTP_WHAT_TEST)
//                    textView.setText(t.getResult());
//                Logger.e(t.getResult());
//                Logger.e(t.getMsg());
//            }
//
//            @Override
//            public void onFailed(int what) {
//                super.onFailed(what);
//            }
//        });


    }

    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == NOHTTP_WHAT_TEST)
                textView.setText(response.get());
                Logger.e(response.get());
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
        public void onFinish(int what) {

        }
    };
}
