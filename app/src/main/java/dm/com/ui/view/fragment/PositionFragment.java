package dm.com.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
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
import dm.com.utils.CommonUtils;
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
    private MultipleStatusView multipleStatusView;

    @Override
    protected int getLayoutRes() {
        return R.layout.position_fragment;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) rootView.findViewById(R.id.content_view);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.position_name);
        setHasOptionsMenu(true);
        multipleStatusView = (MultipleStatusView) rootView.findViewById(R.id.multipleStatusView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        loadData();
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isFastDoubleClick()) {
                    loadData();
                }
            }
        });
    }

    private void loadData() {
        multipleStatusView.showLoading();
        String url = Config.getRequestUrl("Android" , pageSize , page);
        Logger.e(url);
        StringRequest request = new StringRequest(url, RequestMethod.GET);
        request.setCancelSign(object);

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
                }
            }

            @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
            @Override
            public void onFailed(int what,Response<String> response) {
                // 请求失败
                Throwable exception = response.getException();
                if (exception instanceof NetworkError) {// 网络不好
                    multipleStatusView.showNoNetwork();
                } else { //其他错误
                    multipleStatusView.showError();
                }
                Logger.e("错误：" + exception.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消请求
        CallServer.getInstance().cancelBySign(object);
    }
}
