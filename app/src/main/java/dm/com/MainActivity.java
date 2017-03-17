package dm.com;

import android.os.Bundle;

import dm.com.ui.fragment.BaseFragment;
import dm.com.ui.fragment.CameraFragment;
import dm.com.ui.fragment.HelpFragment;
import dm.com.ui.fragment.PositionFragment;
import dm.com.ui.fragment.SearchFragment;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    private PagerBottomTabLayout tabLayout;
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOUR = 3;
    private BaseFragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
        initTab();
    }

    private void initView(Bundle savedInstanceState) {
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
                        break;
                    case SECOND:
                        showHideFragment(mFragments[SECOND]);
                        break;
                    case THIRD:
                        showHideFragment(mFragments[THIRD]);
                        break;
                    case FOUR:
                        showHideFragment(mFragments[FOUR]);
                        break;
                }
            }
            @Override
            public void onRepeatClick(int index, Object tag) {}
        };
        controller.addTabItemClickListener(listener);
    }


}
