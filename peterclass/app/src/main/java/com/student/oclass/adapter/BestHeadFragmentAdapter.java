package com.student.oclass.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.student.oclass.R;
import com.student.oclass.fragment.GameFragment;
import com.student.oclass.fragment.RankFragment;
import com.student.oclass.fragment.WinRecordFragment;

public class BestHeadFragmentAdapter extends FragmentPagerAdapter{
private String[] titleStr = null;

	
	public BestHeadFragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public BestHeadFragmentAdapter(FragmentManager fm, Context context) {
		super(fm);
		titleStr=context.getResources().getStringArray(R.array.best_head_array);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return GameFragment.newInstance(position);
		case 1:
			return WinRecordFragment.newInstance(position);
		case 2:
			return RankFragment.newInstance(position);
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
