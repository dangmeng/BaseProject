package dm.com.ui.view.base;

import android.os.Bundle;


public interface UiCallback {
    void initData(Bundle savedInstanceState);

    void setConfig();

    int getLayoutId();

    boolean isRegisterEventBusOnStart();

    boolean isUnRegisterEventBusOnStop();
}
