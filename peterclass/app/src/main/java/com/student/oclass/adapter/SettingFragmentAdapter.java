package com.student.oclass.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.student.oclass.R;
import com.student.oclass.fragment.FontFagment;
import com.student.oclass.fragment.HelpFagment;
import com.student.oclass.fragment.MainColorFragment;

public class SettingFragmentAdapter extends FragmentPagerAdapter {
    private String[] titleStr = null;

    public SettingFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public SettingFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        titleStr = context.getResources().getStringArray(R.array.setting_array);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
        // case 0:
        // return VoiceFragment.newInstance(position);
        case 0:
            return MainColorFragment.newInstance(position);
        case 1:
            return FontFagment.newInstance(position);
        case 2:
            return HelpFagment.newInstance(position);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        return titleStr[position];
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titleStr.length;
    }

}
