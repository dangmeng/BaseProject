package dm.com.ui.start;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import dm.com.Config;
import dm.com.MainActivity;
import dm.com.R;
import dm.com.ui.start.welcome.WelcomeActivity;
import dm.com.utils.SPUtils;
import dm.com.utils.StateBarTranslucentUtils;

public class SplashActivity extends AppCompatActivity implements SplashView {

    private SplashPresenter mPresenter;
    private ImageView mKenBurns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        mPresenter = new SplashPresenterImpl(this);

        // 判断是否是第一次开启应用
        boolean isFirstOpen = (boolean) SPUtils.get(this, Config.FIRST_OPEN, false);
        mPresenter.isFirstOpen(isFirstOpen);
    }

    //=======================动态权限的申请===========================================================<

    //=======================动态权限的申请===========================================================>


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_splash);
        //设置状态栏透明
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        mKenBurns = (ImageView) findViewById(R.id.ken_burns_images);
        mKenBurns.setImageResource(R.drawable.startbg);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
        }
    }


    ObjectAnimator alphaAnimation;

    private void animation() {
        alphaAnimation = ObjectAnimator.ofFloat(mKenBurns, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }
    /**
     * 跳转到welcome
     * @param context 当前上下文
     */
    public static void startWelcomActivity(Context context) {
        SPUtils.put(context, Config.FIRST_OPEN, true);
        context.startActivity(new Intent(context, WelcomeActivity.class));
    }

    @Override
    public void startWelcomeGuideActivity() {
        startWelcomActivity(this);
        finish();
    }

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
