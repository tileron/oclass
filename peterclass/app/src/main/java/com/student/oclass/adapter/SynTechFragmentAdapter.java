package com.student.oclass.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.student.oclass.R;
import com.student.oclass.fragment.SyncTechFragment;

public class SynTechFragmentAdapter extends FragmentPagerAdapter{
private String[] titleStr = null;

	
	public SynTechFragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public SynTechFragmentAdapter(FragmentManager fm, Context context) {
		super(fm);
		titleStr=context.getResources().getStringArray(R.array.sync_tech_array);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
		case 1:
			return SyncTechFragment.newInstance(position);
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
