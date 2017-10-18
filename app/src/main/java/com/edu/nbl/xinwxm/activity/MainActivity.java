package com.edu.nbl.xinwxm.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.TextView;


import com.edu.nbl.xinwxm.R;
import com.edu.nbl.xinwxm.fragment.Circle;
import com.edu.nbl.xinwxm.fragment.Collection;
import com.edu.nbl.xinwxm.fragment.Mine;
import com.edu.nbl.xinwxm.fragment.NewsFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_title)
    TextView mTitle;
    @BindView(R.id.main_toolar)
    Toolbar mToolar;
    @BindViews({R.id.main_news, R.id.main_collection, R.id.main_circle, R.id.main_mine})
    TextView[] tvs;
    @BindView(R.id.main_vp)
    ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolar.setTitle("新闻");
        mVp.setAdapter(adapter);
        mVp.setCurrentItem(0);
        //默认显示第一个
        init();
        tvs[0].setSelected(true);
        tvs[0].setTextColor(Color.WHITE);
    }
    private void init() {
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<tvs.length;i++){
                    tvs[i].setSelected(false);
                    tvs[i].setTextColor(Color.BLACK);
                }
                tvs[position].setSelected(true);
                tvs[position].setTextColor(Color.WHITE);
                mTitle.setText(tvs[position].getText().toString());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

   private FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new NewsFragment();
                case 1:
                    return new Collection();
                case 2:
                    return new Circle();
                case 3:
                    return new Mine();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tvs.length;
        }
    };
    

    @OnClick({R.id.main_news, R.id.main_collection, R.id.main_circle, R.id.main_mine})
    public void onViewClicked(TextView view) {
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setSelected(false);
            tvs[i].setTextColor(Color.BLACK);
            tvs[i].setTag(i);
        }
        view.setSelected(true);
        view.setTextColor(Color.WHITE);
        mVp.setCurrentItem((Integer) view.getTag());
    }
}
