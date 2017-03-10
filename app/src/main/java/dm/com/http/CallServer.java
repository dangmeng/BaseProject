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


import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * Created by Yan Zhenjie on 2016/12/17.
 */
public class CallServer {

    private static CallServer instance;

    /**
     * 请求队列。
     */
    private RequestQueue requestQueue;

    private CallServer() {
        requestQueue = NoHttp.newRequestQueue(5);
    }

    /**
     * 请求队列。
     */
    public synchronized static CallServer getInstance() {
        if (instance == null)
            synchronized (CallServer.class) {
                if (instance == null)
                    instance = new CallServer();
            }
        return instance;
    }

    /**
     * 添加一个请求到请求队列。
     *
     * @param what         用来标志请求, 当多个请求使用同一个Listener时, 在回调方法中会返回这个what。
     * @param request      请求对象。
     * @param httpListener 结果回调对象。
     */
    public <T> void add(int what, AbstractRequest<T> request, HttpListener<T> httpListener) {
        // 在请求入口处统一添加内容。
        // request.addHeader("App_Version", "1.0.0");
        // request.add("token", "token");
        requestQueue.add(what, request, new DefaultResponseListener<>(httpListener, request));
    }

    /**
     * 取消这个sign标记的所有请求。
     *
     * @param sign 请求的取消标志。
     */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);
    }

    /**
     * 取消队列中所有请求。
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }

}
