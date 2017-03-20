package dm.com.ui.fragment.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dm.com.R;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class DetailActivity extends SwipeBackActivity {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.detail_name);
        }

        ImageView imageView = (ImageView) findViewById(R.id.image_detail);

        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url).asBitmap().into(imageView);

        // 给其下Fragment的默认背景
        // (默认使用Fragment根布局的background属性,如若没有则使用Theme的windowBackground属性)
        setDefaultFragmentBackground(android.R.color.white);
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
