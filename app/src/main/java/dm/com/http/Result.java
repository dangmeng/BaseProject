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


import com.yanzhenjie.nohttp.Headers;

/**
 *
 * Created by Yan Zhenjie on 2016/12/17.
 */
public class Result<Result> {

    private boolean isSucceed;

    private Result result;

    private Headers headers;

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public Result(boolean isSucceed, Result result, Headers headers, String msg, int code) {
        this.isSucceed = isSucceed;
        this.result = result;
        this.headers = headers;
        this.msg = msg;
        this.code = code;
    }
    public Result() {

    }
    public int getCode() {
        return code;
    }
    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }


}
