package dm.com.ui.view.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Unbinder;
import dm.com.R;
import dm.com.http.CallServer;
import dm.com.utils.BarUtils;
import me.yokeyword.fragmentation.SupportActivity;


public abstract class XBaseActivity extends SupportActivity implements UiCallback {
    protected Activity context;
    protected UiDelegate uiDelegate;
    private Unbinder unbinder;
    FrameLayout mViewContent;

    //加载对话框
    protected Toolbar mToolbar;
    private View mBaseStatusBar;
    private TextView mTvTitle;

    // icon 图标id
    private int mMenuSecondRightResId;
    private String mMenuSecondRightStr;
    private int mMenuRightResId;
    private String mMenuRightStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_top_bar);
        this.context = this;
        if (getLayoutId() > 0) {
            initContentView(getLayoutId());
//            unbinder = KnifeKit.bind(this);
        }
        initData(savedInstanceState);
        setConfig();
    }

    private void initContentView(@LayoutRes int layoutResID) {
        mToolbar = (Toolbar) findViewById(R.id.tb_base_toolbar);
        mTvTitle = (TextView) findViewById(R.id.tv_base_title);
        mViewContent = (FrameLayout) findViewById(R.id.fl_base_viewContent);
        initStatusBarView();
        setSupportActionBar(mToolbar);
        setRightDrawable(R.drawable.fragmentation_ic_right);
        setSecondRightDrawable(R.drawable.fragmentation_ic_expandable);
        //设置不显示自带的 Title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setLeftDrawable(R.drawable.ic_launcher);
        //设置NavigationIcon的点击事件,需要放在setSupportActionBar之后设置才会生效,
        //因为setSupportActionBar里面也会setNavigationOnClickListener
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLeft();
            }
        });
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout 里面
        LayoutInflater.from(this).inflate(layoutResID, mViewContent);
    }

    /**
     * 状态栏沉浸
     * http://www.jianshu.com/p/a44c119d6ef7
     */
    protected void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //状态栏透明
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * 生成一个与状态栏高度一样的View,实现沉浸式
     */
    private void initStatusBarView() {
        mBaseStatusBar = findViewById(R.id.v_base_status);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mBaseStatusBar.setVisibility(View.VISIBLE);
            int statusHeight = BarUtils.getStatusBarHeight(this);
            if (statusHeight != -1) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        mBaseStatusBar.getLayoutParams();
                params.height = statusHeight;
                mBaseStatusBar.setLayoutParams(params);
            }
        } else {
            mBaseStatusBar.setVisibility(View.GONE);
        }
    }



    protected UiDelegate getUiDelegate() {
        if (uiDelegate == null) {
            uiDelegate = UiDelegateBase.create(this);
        }
        return uiDelegate;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isRegisterEventBusOnStart()) {
//            BusFactory.getBus().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isUnRegisterEventBusOnStop()) {
//            BusFactory.getBus().unregister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUiDelegate().resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getUiDelegate().pause();
    }

    @Override
    public boolean isRegisterEventBusOnStart() {
        return true;
    }

    @Override
    public boolean isUnRegisterEventBusOnStop() {
        return true;
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onWsUserEventDeal(WsUserEvent event) {
//        handleWsUserEvent(event);
//    }
//
//    protected void handleWsUserEvent(WsUserEvent event) {
//        String eventMasterCode = event.getMasterCode();
//        String eventMsg = event.getMsg();
//        //如果是设置默认，且与当前主机编号相同则不处理
//        if (TextUtils.equals(event.getOper(), "CHANGE") &&
//                TextUtils.equals(event.getMasterCode(), sGlobalUserInfo.getMasterCode())) {
//            return;
//        }
//        //重新设置默认主机和是否是管理员
//        sGlobalUserInfo.setMasterCode(eventMasterCode);
//        sGlobalUserInfo.setIsAdmin(event.getIsAdmin());
//        if (!(this instanceof MainActivity)) {
//            new HintDialog
//                    .Builder(this)
//                    .setMessage(eventMsg)
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            //要dismiss掉，不然会报has leaked window com.android.internal.policy.PhoneWindow$DecorView
//                            dialog.dismiss();
//                            Router.newIntent()
//                                    .from(context)
//                                    .to(MainActivity.class)
//                                    .putString(MainActivity.MAIN_ON_NEWINTENT_MSG, "MasterCode_WebSocket")
//                                    .launch();
//                        }
//                    })
//                    .setOutSideCancelable(false)
//                    .setNegativeButton("", null)
//                    .create()
//                    .show();
//        } else {
//            MainActivity.updateWebSocket();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getUiDelegate().destory();
        unbinder.unbind();
        //取消所有的网络请求
        if (isCancelAllNetRequest()) {
            CallServer.getInstance().cancelAll();
        }
    }

    /**
     * 是否取消所有的网络请求。
     *
     * @return 默认返回false
     */
    protected boolean isCancelAllNetRequest() {
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (mMenuSecondRightResId != 0) {
//            menu.findItem(R.id.action_second_right).setIcon(mMenuSecondRightResId);
//        }
//        if (!TextUtils.isEmpty(mMenuSecondRightStr)) {
//            menu.findItem(R.id.action_second_right).setTitle(mMenuSecondRightStr);
//        }
//        if (mMenuRightResId != 0) {
//            menu.findItem(R.id.action_right).setIcon(mMenuRightResId);
//        }
//        if (!TextUtils.isEmpty(mMenuRightStr)) {
//            menu.findItem(R.id.action_right).setTitle(mMenuRightStr);
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_right:
//                    onClickRight();
//                    break;
//                case R.id.action_second_right:
//                    onClickSecondRight();
//                    break;
//            }
            return true;
        }
    };

    protected void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }
    }

    public void setTitle(@StringRes int titleId) {
        mTvTitle.setText(titleId);
    }

    public void setTitleVisibility(int visibility) {
        mTvTitle.setVisibility(visibility);
    }

    public void hiddenRightCtv() {
        mMenuRightStr = null;
        mMenuRightResId = 0;
    }

    protected void showOrHiddenToolBar(boolean show) {
        if (show) {
            mBaseStatusBar.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.VISIBLE);
        } else {
            mBaseStatusBar.setVisibility(View.GONE);
            mToolbar.setVisibility(View.GONE);
        }
    }

    protected void setLeftDrawable(@DrawableRes int resId) {
//        mTitleBar.setLeftDrawable(getResources().getDrawable(resId));
        mToolbar.setNavigationIcon(resId);
    }

    protected void setRightDrawable(@DrawableRes int resId) {
//        mTitleBar.setRightDrawable(getResources().getDrawable(resId));
        mMenuRightResId = resId;
        mMenuRightStr = null;
    }

    protected void setRightText(String rightText) {
        mMenuRightStr = rightText;
        mMenuRightResId = 0;
    }

    protected void setSecondRightDrawable(@DrawableRes int resId) {
        //  mTitleBar.setRightSecondaryDrawable(getResources().getDrawable(resId));
        mMenuSecondRightResId = resId;
        mMenuSecondRightStr = null;
    }


    protected void onClickLeft() {
//        ActivityLifecycleHelper.build().popOneActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

//            ActivityLifecycleHelper.build().popOneActivity(this);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}