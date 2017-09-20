package dm.com.ui.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;

import dm.com.R;
@Route(path = "/detail/activity")
public class DetailActivity extends BaseActivity {


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

        setTitle(R.string.title_camera);
        ImageView imageView = (ImageView) findViewById(R.id.image_detail);

        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url).asBitmap().into(imageView);
    }

}
