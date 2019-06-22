package com.student.oclass.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.student.oclass.R;

public class VoiceFragment extends BaseFragment {

	private int position=0;
	public static VoiceFragment newInstance(int position) {
		VoiceFragment fragment = new VoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_sync_tech, container, false);
       
        return contentView;
    }
    
    private void parseArgument() {
        Bundle bundle = getArguments();
        position=bundle.getInt("position");
       
    }
}
