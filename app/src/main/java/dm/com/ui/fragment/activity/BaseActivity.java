package dm.com.ui.fragment.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import dm.com.R;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by m on 2017/2/24.
 * ${describe}
 */

public abstract class BaseActivity extends SupportActivity{

    protected Toolbar toolbar;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int getLayoutResource();

    protected  void setActionIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }
}
