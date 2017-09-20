package dm.com.html;

import android.os.Bundle;
import android.webkit.WebView;

import com.tencent.sonic.sdk.SonicSessionClient;

import java.util.HashMap;

/***********************************************
 *  * * * * *     *       *
 *  *       *    * *     * *  
 *  *       *   * * *   * * *   Describe:
 *  *       *  *  _  * *  _  *  Time:2017/9/20 16:11
 *  * * * * * *       *       * Created by m
 *************************************************/
public class SonicSessionClientImpl extends SonicSessionClient {

    private WebView webView;
    public void bindWebView(WebView webView) {
        this.webView = webView;
    }

    @Override
    public void loadUrl(String url, Bundle extraData) {
        webView.loadUrl(url);
    }

    @Override
    public void loadDataWithBaseUrl(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        webView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    @Override
    public void loadDataWithBaseUrlAndHeader(String baseUrl, String data, String mimeType, String encoding, String historyUrl, HashMap<String, String> headers) {
        loadDataWithBaseUrl(baseUrl, data, mimeType, encoding, historyUrl);
    }

    public WebView getWebView() {
        return webView;
    }
    public void destroy() {
        if (null != webView) {
            webView.destroy();
            webView = null;
        }
    }
}
