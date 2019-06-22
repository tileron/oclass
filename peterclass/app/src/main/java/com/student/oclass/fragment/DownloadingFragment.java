package com.student.oclass.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.student.oclass.MainActivity;
import com.student.oclass.R;
import com.student.oclass.adapter.DownloadAdapter;
import com.student.oclass.adapter.DownloadFragmentAdapter;
import com.student.oclass.model.BookEntity;
import com.student.oclass.utils.AppConstant;
import com.student.oclass.view.NumberProgressBar;
import com.student.oclass.view.pagerindicator.TabPageIndicator;


public class DownloadingFragment extends BaseFragment{

	private List<BookEntity> listBook=new ArrayList<BookEntity>();
	private DownloadAdapter adapter;
	private ListView mListView;
	private int position=0;
	private Handler mHandler=new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch(msg.what) {
			case 0:
				View view = mListView.getChildAt(msg.arg1);
				if(view!=null) {
				NumberProgressBar numPbar=(NumberProgressBar) view.findViewById(R.id.progress);
				int progress=numPbar.getProgress()+5;
				if(progress>100) {
					progress=0;
				}
				numPbar.setProgress(progress);
				}
				break;
			}
		}
	};
	public static DownloadingFragment newInstance(int position) {
		DownloadingFragment fragment = new DownloadingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_view_list, container, false);   
        parseArgument();
        initDownlodingData();
        mHandler.post(myRunnable);      
        mListView=(ListView)contentView.findViewById(R.id.list);
        adapter=new DownloadAdapter(getActivity(), listBook);
        mListView.setAdapter(adapter);
        return contentView;
    }
    Runnable myRunnable= new Runnable() {  
        public void run() {
        	Message msg=mHandler.obtainMessage();
        	msg.what=0;
        	mHandler.sendMessage(msg); 
        	mHandler.postDelayed(this, 1000);                                                      
        }
    };
    void initDownlodingData() {
    	for(int i=0;i<4;i++) {
    		BookEntity book=new BookEntity();
    		listBook.add(book);
    	}
    }   
    @Override
	public void onDestroy() {
		super.onDestroy();
		if(myRunnable!=null)
			mHandler.removeCallbacks(myRunnable);
	}
    private void parseArgument() {
        Bundle bundle = getArguments();
        position=bundle.getInt("position");
       
    }
}
