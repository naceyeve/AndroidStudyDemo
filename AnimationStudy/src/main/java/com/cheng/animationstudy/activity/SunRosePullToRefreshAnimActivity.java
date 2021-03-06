package com.cheng.animationstudy.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.cheng.animationstudy.C;
import com.cheng.animationstudy.R;
import com.cheng.animationstudy.customview.sunrefresh.SunRosePullToRefreshView;
import com.cheng.utils.ViewFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SunRosePullToRefreshAnimActivity extends AppCompatActivity implements SunRosePullToRefreshView.OnRefreshListener {

    private SunRosePullToRefreshView mSunRosePullToRefreshView;
    private ListView mSunRoseLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunrosepulltorefreshanim);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        this.mSunRosePullToRefreshView = ViewFinder.findViewById(this, R.id.ptrv_sunrose);
        this.mSunRoseLV = ViewFinder.findViewById(this, R.id.lv_sunrose);
    }

    private void initListener() {
        this.mSunRosePullToRefreshView.setOnRefreshListener(this);
    }

    private void initData() {
        Map<String, Integer> map;
        List<Map<String, Integer>> sampleList = new ArrayList<>();
        int [] icons = {
            R.mipmap.icon_1,
            R.mipmap.icon_2,
            R.mipmap.icon_3
        };
        int [] colors = {
            R.color.saffron,
            R.color.eggplant,
            R.color.sienna
        };

        for (int i=0; i<icons.length; i++) {
            map = new HashMap<>();
            map.put(SampleAdapter.KEY_ICON, icons[i]);
            map.put(SampleAdapter.KEY_COLOR, colors[i]);
            sampleList.add(map);
        }

        this.mSunRoseLV.setAdapter(new SampleAdapter(this, R.layout.item_sunroselist, sampleList));
    }

    @Override
    public void onRefresh() {
        this.mSunRosePullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSunRosePullToRefreshView.setRefreshing(false);
            }
        }, C.Int.IMITATE_NET_DELAYED * 4);
    }

    class SampleAdapter extends ArrayAdapter<Map<String, Integer>> {

        public static final String KEY_ICON = "icon";
        public static final String KEY_COLOR = "color";

        private final LayoutInflater mInflater;
        private final List<Map<String, Integer>> mData;

        public SampleAdapter(Context context, int layoutResourceId, List<Map<String, Integer>> data) {
            super(context, layoutResourceId, data);
            mData = data;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_sunroselist, parent, false);
                viewHolder.imageViewIcon = (ImageView) convertView.findViewById(R.id.image_view_icon);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.imageViewIcon.setImageResource(mData.get(position).get(KEY_ICON));
            convertView.setBackgroundResource(mData.get(position).get(KEY_COLOR));

            return convertView;
        }

        class ViewHolder {
            ImageView imageViewIcon;
        }
    }

}
