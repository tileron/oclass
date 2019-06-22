package com.student.oclass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.student.oclass.activity.AboutActivity;
import com.student.oclass.activity.BaseActivity;
import com.student.oclass.activity.BestHeadActivity;
import com.student.oclass.activity.ExamActivity;
import com.student.oclass.activity.FamousBookActivity;
import com.student.oclass.activity.LoginActivity;
import com.student.oclass.activity.MyDownloadActivity;
import com.student.oclass.activity.PayAccountActivity;
import com.student.oclass.activity.SettingActivity;
import com.student.oclass.activity.SyncTechActivity;
import com.student.oclass.fragment.BaseFragment;
import com.student.oclass.utils.AppConstant;
import com.student.oclass.utils.HProgress;
import com.student.oclass.utils.HToast;
import com.student.oclass.view.SlideImageLayout;
import com.zdp.aseo.content.AseoZdpAseo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnClickListener {

    // 滑动图片的集合
    private ArrayList<View> imagePageViews = null;

    private ViewPager viewPager = null;

    // 当前ViewPager索引
    private int pageIndex = 0;

    // 包含圆点图片的View
    private ViewGroup imageCircleView = null;

    private ImageView[] imageCircleViews = null;

    private SlideImageLayout slideLayout = null;

    List<TextView> titleViews = new ArrayList<TextView>();

    Handler handler;

    // 更新自动广告
    private static final int H_UPDATE_AUTO_SELECTED = 1;

    private static final int H_RESET_LIST = 2;

    private static final int H_LOAD_DATA = 3;

    boolean adAuto = true;

    // 广告正在滑动
    boolean adScrolling = false;

    List<String[]> types;

    private ImageView btn_number, btn_english, btn_chinese;
    private ImageView iv_game, iv_record, iv_rank;
    private ImageView iv_contact, iv_about, iv_about_me;
    private LinearLayout layout_famous;
    private LinearLayout layout_user;
    private LinearLayout layout_exam;
    private LinearLayout layout_pay;
    private LinearLayout layout_download;
    private LinearLayout layout_setting;
    private SharedPreferences sp;

    private RelativeLayout layout_study;
    private RelativeLayout layout_online;
    private RelativeLayout layout_about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = MainActivity.this.getSharedPreferences("ThemeColor", MODE_PRIVATE);
        initHandler();
        InitHeadAd();
        initView();
        adAuto = true;
        adThread.start();
    }

    public void initView() {
        layout_study = (RelativeLayout) this.findViewById(R.id.study);
        layout_exam = (LinearLayout) this.findViewById(R.id.layout_exam);
        layout_famous = (LinearLayout) this.findViewById(R.id.layout_famous);
        layout_online = (RelativeLayout) this.findViewById(R.id.online);
        layout_user = (LinearLayout) this.findViewById(R.id.layout_user);
        layout_pay = (LinearLayout) this.findViewById(R.id.layout_pay);
        layout_download=(LinearLayout)this.findViewById(R.id.layout_download);
        layout_setting=(LinearLayout)this.findViewById(R.id.layout_setting);
        layout_about = (RelativeLayout) this.findViewById(R.id.about);

        btn_number = (ImageView) this.findViewById(R.id.btn_number);
        btn_english = (ImageView) this.findViewById(R.id.btn_english);
        btn_chinese = (ImageView) this.findViewById(R.id.btn_chinese);
        iv_game = (ImageView) this.findViewById(R.id.iv_game);
        iv_record = (ImageView) this.findViewById(R.id.iv_record);
        iv_rank = (ImageView) this.findViewById(R.id.iv_rank);
        iv_contact = (ImageView) this.findViewById(R.id.iv_contact);
        iv_about = (ImageView) this.findViewById(R.id.iv_about);
        iv_about_me = (ImageView) this.findViewById(R.id.iv_aboutme);
        btn_number.setOnClickListener(this);
        btn_english.setOnClickListener(this);
        btn_chinese.setOnClickListener(this);
        layout_famous.setOnClickListener(this);
        layout_user.setOnClickListener(this);
        layout_exam.setOnClickListener(this);
        iv_game.setOnClickListener(this);
        iv_record.setOnClickListener(this);
        iv_rank.setOnClickListener(this);
        layout_pay.setOnClickListener(this);
        layout_download.setOnClickListener(this);
        iv_contact.setOnClickListener(this);
        iv_about.setOnClickListener(this);
        iv_about_me.setOnClickListener(this);
        layout_setting.setOnClickListener(this);


    }

    public void refreshThemeColor(){
        layout_study.setBackgroundColor(sp.getInt("layout_study", Color.parseColor("#ff6678")));
        layout_exam.setBackgroundColor(sp.getInt("layout_exam", Color.parseColor("#ffcc33")));
        layout_famous.setBackgroundColor(sp.getInt("layout_video", Color.parseColor("#9755f6")));
        layout_online.setBackgroundColor(sp.getInt("layout_online", Color.parseColor("#fe864b")));
        layout_user.setBackgroundColor(sp.getInt("layout_user", Color.parseColor("#0fc4d9")));
        layout_pay.setBackgroundColor(sp.getInt("layout_pay", Color.parseColor("#84d018")));
        layout_download.setBackgroundColor(sp.getInt("layout_download", Color.parseColor("#c8d900")));
        layout_setting.setBackgroundColor(sp.getInt("layout_settings", Color.parseColor("#c8d900")));
        layout_about.setBackgroundColor(sp.getInt("layout_about", Color.parseColor("#22ad38")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    protected void replaceFragment(int viewId, BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case R.id.ui_show_dialog:
                    HProgress.show(MainActivity.this, null);
                    break;
                case R.id.ui_dismiss_dialog:
                    HProgress.dismiss();
                    break;
                case R.id.ui_show_text:
                    HToast.showShortText(MainActivity.this, msg.arg1);
                    break;
                case R.id.ui_update_ui:
                    // updateUI(msg.arg1);
                    break;
                case H_UPDATE_AUTO_SELECTED:
                    updateAddSelecedStatus();
                    viewPager.setCurrentItem(pageIndex);
                    break;
                case H_RESET_LIST:
                    break;
                case H_LOAD_DATA:
                    break;
                default:
                    break;
                }
            }
        };
    }

    Thread adThread = new Thread() {
        public void run() {
            while (adAuto) {
                try {
                    sleep(6000);
                    if (adScrolling == false) {
                        pageIndex++;
                        if (pageIndex > imagePageViews.size() - 1)
                            pageIndex = 0;
                        handler.sendEmptyMessage(H_UPDATE_AUTO_SELECTED);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    };

    /**
     * 加载头部广告
     */
    private void InitHeadAd() {
        // 滑动图片区域
        imagePageViews = new ArrayList<View>();
        viewPager = (ViewPager) this.findViewById(R.id.image_slide_page);
        // 圆点图片区域
        int length = AppConstant.imgsUrl.length;
        imageCircleViews = new ImageView[length];
        imageCircleView = (ViewGroup) this.findViewById(R.id.layout_circle_images);
        slideLayout = new SlideImageLayout(this);
        slideLayout.setCircleImageLayout(length);
        for (int i = 0; i < length; i++) {
            int defId = R.drawable.a01;
            switch (i) {
            case 1:
                defId = R.drawable.a02;
                break;
            case 2:
                defId = R.drawable.a03;
                break;
            case 3:
                defId = R.drawable.a04;
                break;
            }
            View ImageView = slideLayout.getSlideImageLayout(AppConstant.imgsUrl[i], i, defId);
            imagePageViews.add(ImageView);
            imageCircleViews[i] = slideLayout.getCircleImageLayout(i);
            imageCircleView.addView(slideLayout.getLinearLayout(imageCircleViews[i], 10, 10));
        }
        viewPager.setAdapter(new SlideImageAdapter());
        viewPager.setOnPageChangeListener(new ImagePageChangeListener());
    }

    // 滑动图片数据适配器
    private class SlideImageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            // adScrolling = true;
            return imagePageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;

        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(imagePageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imagePageViews.get(arg1));

            return imagePageViews.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    // 滑动页面更改事件监听器
    private class ImagePageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
            pageIndex = index;
            updateAddSelecedStatus();
        }
    }

    /**
     * 更新广告选项中状态
     */
    private void updateAddSelecedStatus() {
        slideLayout.setPageIndex(pageIndex);
        for (int i = 0; i < imageCircleViews.length; i++) {
            if (i == pageIndex) {
                imageCircleViews[pageIndex].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageCircleViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		AseoZdpAseo.initFinalTimer(this);
		startActivity(intent);
	}
	
	
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int resId = R.drawable.number_pressed;
        switch (v.getId()) {
        case R.id.btn_number:
        case R.id.btn_english:
        case R.id.btn_chinese:
        	if(v.getId()==R.id.btn_number) {
        		resId = R.drawable.number_pressed;
        	}else if(v.getId()==R.id.btn_english) {
        		resId = R.drawable.english_pressed;
        	}else {
        		resId = R.drawable.chinese_pressed;
        	}
            Intent intent = new Intent(MainActivity.this, SyncTechActivity.class);
            intent.putExtra("resId", resId);
            startActivity(intent);
            break;
        case R.id.layout_famous:
            intent = new Intent(MainActivity.this, FamousBookActivity.class);
            startActivity(intent);
            break;
        case R.id.layout_user:
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            break;
        case R.id.layout_pay:
            intent = new Intent(MainActivity.this, PayAccountActivity.class);
            startActivity(intent);
            break;
        case R.id.iv_game:
//        case R.id.iv_record:
//        case R.id.iv_rank:
        	if(v.getId()==R.id.iv_game) {
        		resId = R.drawable.best_head1_pressed;
        	}else if(v.getId()==R.id.iv_record) {
        		resId = R.drawable.best_head2_pressed;
        	}else {
        		resId = R.drawable.best_head3_pressed;
        	}
            intent = new Intent(MainActivity.this, BestHeadActivity.class);
            intent.putExtra("resId", resId);
            startActivity(intent);
            break;    
        case R.id.layout_download:
        	intent = new Intent(MainActivity.this, MyDownloadActivity.class);
            startActivity(intent);
        	break;
        case R.id.layout_exam:
        	intent = new Intent(MainActivity.this, ExamActivity.class);
            startActivity(intent);
        	break;
        case R.id.iv_about:
        	intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        	break;
        case R.id.layout_setting:
        	intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        	break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshThemeColor();
    }
}
