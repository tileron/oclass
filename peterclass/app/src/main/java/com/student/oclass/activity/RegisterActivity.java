package com.student.oclass.activity;

import com.student.oclass.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 注册
 * @author lanyj
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{

	private RadioGroup radioGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}
	private void initView(){
		radioGroup=(RadioGroup)this.findViewById(R.id.radiogroup);
		radioGroup.check(R.id.radiogirl);
		iv_title=(ImageView)this.findViewById(R.id.iv_title);
		tv_title=(TextView)this.findViewById(R.id.tv_title);
		btn_back=(Button)this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.btn_back:
			finish();
			break;	
		}
	}
}
