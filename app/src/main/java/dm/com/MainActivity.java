package dm.com;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import dm.com.ui.base.BaseActivity;
import dm.com.ui.base.BaseFragment;
import dm.com.ui.fragment.CameraFragment;
import dm.com.ui.fragment.HelpFragment;
import dm.com.ui.fragment.PositionFragment;
import dm.com.ui.fragment.SearchFragment;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends BaseActivity {

    private PagerBottomTabLayout tabLayout;
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOUR = 3;
    private BaseFragment[] mFragments;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        setTitle("相机");
        toolbar.setNavigationIcon(new ColorDrawable(Color.parseColor("#00000000")));
        // (默认使用Fragment根布局的background属性,如若没有则使用Theme的windowBackground属性)
        setDefaultFragmentBackground(android.R.color.white);
//        int defaultFragmentBackground = getDefaultFragmentBackground();
        tabLayout = (PagerBottomTabLayout) findViewById(R.id.main_tab);
        mFragments = new BaseFragment[4];
        if (savedInstanceState == null) {

            mFragments[FIRST] = new CameraFragment();
            mFragments[SECOND] = new PositionFragment();
            mFragments[THIRD] = new SearchFragment();
            mFragments[FOUR] = new HelpFragment();

            loadMultipleRootFragment(R.id.main_container,FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复工作，不需要额外的处理
            // 这里我们需要拿到mFragments的引用，用下面的方法查找更方便些
            // 也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些)
            mFragments[FIRST] = findFragment(CameraFragment.class);
            mFragments[SECOND] = findFragment(PositionFragment.class);
            mFragments[THIRD] = findFragment(SearchFragment.class);
            mFragments[FOUR] = findFragment(HelpFragment.class);
        }

        initTab();

    }

    private void initTab() {

        Controller controller = tabLayout.builder()
                .addTabItem(R.mipmap.icon_camera, "相机", 0xFF00796B)
                .addTabItem(R.mipmap.icon_position, "位置", 0xFF5B4947)
                .addTabItem(R.mipmap.icon_search, "搜索", 0xFF607D8B)
                .addTabItem(R.mipmap.icon_help, "帮助", 0xFFF57C00)
                .build();

        OnTabItemSelectListener listener = new OnTabItemSelectListener() {
            @Override
            public void onSelected(int index, Object tag) {
                switch (index) {
                    case FIRST:
                        showHideFragment(mFragments[FIRST]);
                        setTitle("相机");
                        break;
                    case SECOND:
                        showHideFragment(mFragments[SECOND]);
                        setTitle("位置");
                        break;
                    case THIRD:
                        showHideFragment(mFragments[THIRD]);
                        setTitle("搜索");
                        break;
                    case FOUR:
                        showHideFragment(mFragments[FOUR]);
                        setTitle("帮助");
                        break;
                }
            }
            @Override
            public void onRepeatClick(int index, Object tag) {}
        };
        controller.addTabItemClickListener(listener);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
