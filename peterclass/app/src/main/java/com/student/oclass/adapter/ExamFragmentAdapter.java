package com.student.oclass.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.student.oclass.R;
import com.student.oclass.fragment.ExamFagment;
import com.student.oclass.fragment.ExamScoreFragment;
import com.student.oclass.fragment.GameFragment;
import com.student.oclass.fragment.RankFragment;
import com.student.oclass.fragment.WinRecordFragment;
import com.student.oclass.utils.HToast;

public class ExamFragmentAdapter extends FragmentPagerAdapter {

	private String[] titleStr = null;
	private Context context;


	public ExamFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public ExamFragmentAdapter(FragmentManager fm, Context context) {
		super(fm);
		titleStr = context.getResources().getStringArray(R.array.exam_array);
		this.context=context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
			case 2:
//                HToast.makeToast(context,"2");
				return ExamFagment.newInstance(position);
			case 1:
//                HToast.makeToast(context,"1");
				return ExamScoreFragment.newInstance(position);
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
