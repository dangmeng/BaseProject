package dm.com.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import dm.com.R;
import dm.com.html.BrowserActivity;
import dm.com.html.SonicJavaScriptInterface;
import dm.com.router.Router;
import dm.com.ui.base.BaseActivity;
import skin.support.SkinCompatManager;

import static dm.com.Config.DEMO_URL;
import static dm.com.Config.MODE_SONIC_WITH_OFFLINE_CACHE;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.choose_color)
    Button btn_choose;


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
                        .putString(BrowserActivity.PARAM_URL , DEMO_URL)
                        .putInt(BrowserActivity.PARAM_MODE , MODE_SONIC_WITH_OFFLINE_CACHE)
                        .putLong(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis())
                        .requestCode(-1)
                        .to(BrowserActivity.class)
                        .launch();
            }
        });

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinCompatManager.getInstance().loadSkin("pink", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
            }
        });



    }

}
