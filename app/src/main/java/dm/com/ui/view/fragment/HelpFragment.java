package dm.com.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Response;

import dm.com.Config;
import dm.com.R;
import dm.com.http.CallServer;
import dm.com.http.Result;
import dm.com.http.SimpleHttpListener;
import dm.com.http.StringRequest;
import dm.com.ui.view.base.BaseFragment;

/**
 * Created by m on 2017/2/23.
 * ${describe}
 */

public class HelpFragment extends BaseFragment {

    private static final int NOHTTP_WHAT = 0x002;
    private TextView textView;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) rootView.findViewById(R.id.tv_content);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadData();
    }

    private void loadData() {
        String url = Config.getRequestUrl("all" , pageSize , page);
        Logger.e(url);
        StringRequest request = new StringRequest(url, RequestMethod.GET);
        request.setCancelSign(object);

        CallServer.getInstance().add(NOHTTP_WHAT, request, new SimpleHttpListener<String>() {

            @Override
            public void onSucceed(int what, Result<String> t) {
                if (what == NOHTTP_WHAT){
                    String result = t.getResult();
                    if (!TextUtils.isEmpty(result)) {
                        textView.setText(result);
                    }
                }
            }

            @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
            @Override
            public void onFailed(int what,Response<String> response) {
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
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.help_fragment;
    }

    @Override
    public void initView() {

    }
}
