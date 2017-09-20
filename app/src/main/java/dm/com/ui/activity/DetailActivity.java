package dm.com.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dm.com.R;
import dm.com.html.BrowserActivity;
import dm.com.html.SonicJavaScriptInterface;
import dm.com.router.Router;
import dm.com.ui.base.BaseActivity;

import static dm.com.Config.MODE_SONIC_WITH_OFFLINE_CACHE;

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


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newIntent(DetailActivity.this)
                        .putString(BrowserActivity.PARAM_URL , "http://www.jianshu.com/u/18132864645d")
                        .putInt(BrowserActivity.PARAM_MODE , MODE_SONIC_WITH_OFFLINE_CACHE)
                        .putLong(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis())
                        .requestCode(-1)
                        .to(BrowserActivity.class)
                        .launch();
            }
        });
    }

}
