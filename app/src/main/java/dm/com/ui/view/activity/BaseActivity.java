package dm.com.ui.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import dm.com.R;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by m on 2017/2/24.
 * ${describe}
 */

public abstract class BaseActivity extends SwipeBackActivity {

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
            setActionIcon(R.mipmap.ic_launcher);
        }
    }

    protected abstract int getLayoutResource();

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
}
