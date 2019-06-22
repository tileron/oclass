package com.student.oclass.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 *	lanyj
 */
public abstract class BaseActivity extends FragmentActivity {
    protected ActionBar actionBar;
    public Button btn_back;
    public Button btn_home;
    public Button btn_search;
    public ImageView iv_title;
    public TextView tv_title;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
      
    }

    
}
