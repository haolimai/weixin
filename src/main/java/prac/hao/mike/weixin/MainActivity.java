package prac.hao.mike.weixin;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    private TextView tv_chat;
    private TextView tv_frd;
    private TextView tv_contact;


    private BadgeView mBadgeView;
    private LinearLayout ll_chat;
    private LinearLayout ll_frd;
    private LinearLayout ll_contact;

    private TextView iv_indicator;

    private int mCurrentPageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        tv_chat = (TextView) findViewById(R.id.tv_chat);
        tv_frd = (TextView) findViewById(R.id.tv_frd);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        ll_chat = (LinearLayout) findViewById(R.id.ll_chat);
        ll_frd = (LinearLayout) findViewById(R.id.ll_frd);
        ll_contact = (LinearLayout) findViewById(R.id.ll_contact);
        iv_indicator = (TextView) findViewById(R.id.iv_indicator);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iv_indicator.getLayoutParams();
        lp.width = getScreenWidth() / 3;
        iv_indicator.setLayoutParams(lp);

        ChatFragment chatFragment = new ChatFragment();
        FrdFragment frdFragment = new FrdFragment();
        ContactFragment contactFragment = new ContactFragment();

        mFragments = new ArrayList<>();
        mFragments.add(chatFragment);
        mFragments.add(frdFragment);
        mFragments.add(contactFragment);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("hlm", position + ", " + positionOffset + ", " + positionOffsetPixels);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iv_indicator.getLayoutParams();

                lp.leftMargin = (int) ((getScreenWidth() / 3) * position + (getScreenWidth() / 3) * positionOffset);
                iv_indicator.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                tabUnselected();
                switch (position) {
                    case 0:
                        if (mBadgeView != null) {
                            ll_chat.removeView(mBadgeView);
                        }

                        mBadgeView = new BadgeView(MainActivity.this);
                        mBadgeView.setBadgeCount(7);
                        ll_chat.addView(mBadgeView);
                        tv_chat.setTextColor(Color.parseColor("#90caaf"));
                        break;
                    case 1:
                        tv_frd.setTextColor(Color.parseColor("#90caaf"));
                        break;
                    case 2:
                        tv_contact.setTextColor(Color.parseColor("#90caaf"));
                        break;
                }

                mCurrentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ll_chat.setOnClickListener(this);
        ll_frd.setOnClickListener(this);
        ll_contact.setOnClickListener(this);

    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private void tabUnselected() {
        tv_chat.setTextColor(Color.parseColor("#625c52"));
        tv_frd.setTextColor(Color.parseColor("#625c52"));
        tv_contact.setTextColor(Color.parseColor("#625c52"));
    }

    @Override
    public void onClick(View v) {
        tabUnselected();
        switch (v.getId()) {
            case R.id.ll_chat:
                tv_chat.setTextColor(Color.parseColor("#90caaf"));
                break;
            case R.id.ll_frd:
                tv_frd.setTextColor(Color.parseColor("#90caaf"));
                break;
            case R.id.ll_contact:
                tv_contact.setTextColor(Color.parseColor("#90caaf"));
                break;
        }
    }
}
