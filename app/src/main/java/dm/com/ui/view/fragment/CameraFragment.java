package dm.com.ui.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dm.com.R;
import dm.com.ui.view.activity.DetailActivity;

/**
 * Created by m on 2017/2/23.
 * ${describe}
 */

public class CameraFragment extends BaseFragment {

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imageUrl = (String) view.getTag();
                startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("url",imageUrl));
            }
        });
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.camera_fragment;
    }

    private static class GridViewAdapter extends BaseAdapter {

        private Context mContext;

        GridViewAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return "Item " + String.valueOf(i + 1);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.grid_item, viewGroup, false);
            }

            String imageUrl = "http://lorempixel.com/800/600/sports/" + String.valueOf(i + 1);
            view.setTag(imageUrl);

            ImageView image = (ImageView) view.findViewById(R.id.image_pic);
            Glide.with(mContext)
                    .load(imageUrl)
                    .asBitmap()
                    .into(image);
            Log.i("url",imageUrl+"");
            return view;
        }
    }


}
