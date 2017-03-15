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

import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by Yan Zhenjie on 2016/12/17.
 *
 */
public abstract class SimpleHttpListener<T> implements HttpListener<T> {
    @Override
    public void onSucceed(int what, Result<T> t) {
    }

    @Override
    public void onFailed(int what,Response<T> response) {

    }

    @Override
    public void onFinish(int what) {

    }
}
