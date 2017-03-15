package dm.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import dm.com.Config;
import dm.com.R;
import dm.com.http.CallServer;
import dm.com.http.Result;
import dm.com.http.SimpleHttpListener;
import dm.com.http.StringRequest;
import dm.com.weiget.MultipleStatusView;

/**
 * Created by m on 2017/2/23.
 * ${describe}
 */

public class PositionFragment extends BaseFragment {

    /**
     * 用来标志请求的what, 类似handler的what一样，这里用来区分请求。
     */
    private static final int NOHTTP_WHAT = 0x001;
    private TextView textView;
    private View rootView;
    private MultipleStatusView multipleStatusView;


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.position_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) rootView.findViewById(R.id.content_view);
        multipleStatusView = (MultipleStatusView) rootView.findViewById(R.id.multipleStatusView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        multipleStatusView.showLoading();
        String url = Config.getRequestUrl(pageSize,page);
        Logger.e(url);
        StringRequest request = new StringRequest(url, RequestMethod.GET);

        CallServer.getInstance().add(NOHTTP_WHAT, request, new SimpleHttpListener<String>() {

            @Override
            public void onSucceed(int what, Result<String> t) {
                if (what == NOHTTP_WHAT){
                    String result = t.getResult();
                    if (TextUtils.isEmpty(result)) {
                        multipleStatusView.showEmpty();
                    } else {
                        multipleStatusView.showContent();
                        textView.setText(result);
                    }
                    Logger.e(t.getResult());
                }
            }

            @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
            @Override
            public void onFailed(int what,Response<String> response) {
                // 请求失败
                Throwable exception = response.getException();
                if (exception instanceof NetworkError) {// 网络不好
//                    Toast.makeText(getActivity(), R.string.error_please_check_network,Toast.LENGTH_LONG).show();
                    multipleStatusView.showNoNetwork();
                } else {
                    multipleStatusView.showError();
                }
                Logger.e("错误：" + exception.getMessage());
            }
        });


    }
}
