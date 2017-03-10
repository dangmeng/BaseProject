/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dm.com.http;

import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;


/**
 * Created by Yan ZhenJie on 2016/12/17.
 *
 */
public class DefaultResponseListener<T> implements OnResponseListener<Result<T>> {

    private HttpListener<T> httpListener;
    private AbstractRequest<T> abstractRequest;

    public DefaultResponseListener(HttpListener<T> httpListener,
                                   AbstractRequest<T> abstractRequest) {
        this.httpListener = httpListener;
        this.abstractRequest = abstractRequest;
    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSucceed(int what, Response<Result<T>> response) {
        // http层的请求成功，响应码可能是200、400、500。
        if (httpListener != null && !abstractRequest.isCanceled())
            httpListener.onSucceed(what, response.get());
    }

    @Override
    public void onFailed(int what, Response<Result<T>> response) {
        Exception exception = response.getException();

        if (exception instanceof NetworkError) {
//            ToastUtils.showLongToast("请检查网络是否可用！");

        } else if (exception instanceof TimeoutError) { // 超时。
//            ToastUtils.showLongToast("请求超时，请稍后重试！");
        }
        if (httpListener != null && !abstractRequest.isCanceled())
            httpListener.onFailed(what);
    }

    @Override
    public void onFinish(int what) {
        // TODO 关闭请求。
        if (httpListener != null)
            httpListener.onFinish(what);
    }
}
