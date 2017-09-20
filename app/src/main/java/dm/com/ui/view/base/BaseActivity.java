package dm.com.ui.view.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import dm.com.R;
import dm.com.http.CallServer;
import dm.com.utils.BarUtils;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by m on 2017/2/24.
 * ${describe}
 */

public abstract class BaseActivity extends SwipeBackActivity {

    protected Toolbar toolbar;
    private TextView mTvTitle;
    private View mBaseStatusBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_base_top_bar);
        if (getLayoutResource() > 0) {
            initContentView(getLayoutResource());
            // 初始化View注入
            ButterKnife.bind(this);
        }

        initData(savedInstanceState);


    }

    @SuppressWarnings("ConstantConditions")
    private void initContentView(int layoutResource) {
        toolbar = (Toolbar) findViewById(R.id.tb_base_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //设置不显示自带的 Title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mTvTitle = (TextView) findViewById(R.id.tv_base_title);
        FrameLayout mViewContent = (FrameLayout) findViewById(R.id.fl_base_viewContent);
        initStatusBarView();

        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout 里面
        LayoutInflater.from(this).inflate(layoutResource, mViewContent);
    }

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

    protected abstract int getLayoutResource();

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    protected  void setActionIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        if(item.getItemId() == android.R.id.home)  {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    protected void onDestroy() {
        super.onDestroy();
        //取消所有的网络请求
        if (isCancelAllNetRequest()) {
            CallServer.getInstance().cancelAll();
        }
    }

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

    protected void showOrHiddenToolBar(boolean show) {
        if (show) {
            mBaseStatusBar.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.VISIBLE);
        } else {
            mBaseStatusBar.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
        }
    }
}
