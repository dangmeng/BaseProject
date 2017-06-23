package dm.com.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        rootView = inflater.inflate(getLayoutRes(), container, false);
        return rootView;
    }

    protected abstract int getLayoutRes ();
}
