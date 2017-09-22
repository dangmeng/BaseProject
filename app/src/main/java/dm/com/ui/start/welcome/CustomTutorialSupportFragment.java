package dm.com.ui.start.welcome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.cleveroad.slidingtutorial.TutorialPageProvider;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;

import dm.com.Config;
import dm.com.R;
import dm.com.ui.start.SplashActivity;
import dm.com.utils.SPUtils;

public class CustomTutorialSupportFragment extends TutorialSupportFragment
        implements OnTutorialPageChangeListener {

    private static final String TAG = "CustomTutorialSFragment";
    private static final int TOTAL_PAGES = 3;
    private static final int ACTUAL_PAGES_COUNT = 3;

    private final View.OnClickListener mOnSkipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Toast.makeText(getContext(), "Skip button clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), SplashActivity.class));
            SPUtils.put(getActivity(), Config.FIRST_OPEN, true);
            getActivity().finish();
        }
    };

    private final TutorialPageOptionsProvider mTutorialPageOptionsProvider = new TutorialPageOptionsProvider() {
        @NonNull
        @Override
        public PageOptions provide(int position) {
            @LayoutRes int pageLayoutResId;
            TransformItem[] tutorialItems;
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0: {
                    pageLayoutResId = R.layout.fragment_page_first;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                    };
                    break;
                }
                case 1: {
                    pageLayoutResId = R.layout.fragment_page_second;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                    };
                    break;
                }
                case 2: {
                    pageLayoutResId = R.layout.fragment_page_third;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                    };
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }

            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    };

    private final TutorialPageProvider<Fragment> mTutorialPageProvider = new TutorialPageProvider<Fragment>() {
        @NonNull
        @Override
        public Fragment providePage(int position) {
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0:
                    return new FirstCustomPageSupportFragment();
                case 1:
                    return new SecondCustomPageSupportFragment();
                case 2:
                    return new ThirdCustomPageSupportFragment();
                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }
        }
    };

    private int[] pagesColors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (pagesColors == null) {
            pagesColors = new int[]{
                    ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_red_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_purple),
                    ContextCompat.getColor(getContext(), android.R.color.holo_green_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark),
                    ContextCompat.getColor(getContext(), android.R.color.darker_gray)
            };
        }
        addOnTutorialPageChangeListener(this);
    }

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getContext())
                .setUseInfiniteScroll(true)
                .setPagesColors(pagesColors)
                .setPagesCount(TOTAL_PAGES)
                .setTutorialPageProvider(mTutorialPageOptionsProvider)
                .setIndicatorOptions(IndicatorOptions.newBuilder(getContext())
                        .setElementSizeRes(R.dimen.indicator_size)
                        .setElementSpacingRes(R.dimen.indicator_spacing)
                        .setElementColorRes(android.R.color.darker_gray)
                        .setSelectedElementColor(Color.LTGRAY)
                        .setRenderer(DrawableRenderer.create(getContext()))
                        .build())
                .onSkipClickListener(mOnSkipClickListener)
                //.setTutorialPageProvider(mTutorialPageProvider)
                .build();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_tutorial_layout;
    }

    @Override
    public void onPageChanged(int position) {
        Log.i(TAG, "onPageChanged: position = " + position);
        if (position == TutorialSupportFragment.EMPTY_FRAGMENT_POSITION) {
            Log.i(TAG, "onPageChanged: Empty fragment is visible");
        }
    }

}
