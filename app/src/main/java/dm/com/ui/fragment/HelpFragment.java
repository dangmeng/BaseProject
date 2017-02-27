package dm.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by m on 2017/2/23.
 * ${describe}
 */

public class HelpFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(HelpFragment.class.getSimpleName());
        return textView;
    }
}
