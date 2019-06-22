package com.student.oclass.activity;

import com.student.oclass.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 登陆
 * @author lanyj
 *
 */
public class LoginActivity extends Activity implements OnClickListener{

	private TextView tv_register,find_password;
	private Button btn_home;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		tv_register=(TextView)this.findViewById(R.id.tv_register);
		find_password=(TextView)this.findViewById(R.id.find_password);
		btn_home=(Button)this.findViewById(R.id.btn_home);
		tv_register.setOnClickListener(this);
		find_password.setOnClickListener(this);
		btn_home.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.tv_register:
			Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.find_password:
			intent=new Intent(LoginActivity.this,FindPwdActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_home:
			finish();
			break;
		}
	}
}
