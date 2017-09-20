package dm.com.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by m on 2017/2/23.
 * ${describe}
 */

public abstract class BaseFragment extends SwipeBackFragment {

    protected int page = 1;
    protected int pageSize = 30;
    /**
     * 用来标记取消
     */
    protected Object object = new Object();
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //20160727 修复该方法多次调用 bug
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } else {
            rootView = getCreateView(inflater, container);
            ButterKnife.bind(this, rootView);
            initView();     //初始化布局
        }

        return rootView;
    }

    protected abstract int getLayoutRes ();

    /**
     * 获取Fragment布局文件的View
     *
     * @param inflater
     * @param container
     * @return
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    /**
     * 初始化视图
     */
    public abstract void initView();
}
