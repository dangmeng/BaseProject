package dm.com.ui.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dm.com.R;

public class DetailActivity extends BaseActivity {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (toolbar != null) {
            getSupportActionBar().setTitle(R.string.detail_name);
        }

        ImageView imageView = (ImageView) findViewById(R.id.image_detail);

        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url).asBitmap().into(imageView);

        // 给其下Fragment的默认背景
        // (默认使用Fragment根布局的background属性,如若没有则使用Theme的windowBackground属性)
//        setDefaultFragmentBackground(android.R.color.white);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

}
