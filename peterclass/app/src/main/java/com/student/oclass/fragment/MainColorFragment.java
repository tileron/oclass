package com.student.oclass.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.student.oclass.R;
import com.student.oclass.utils.ColorPickerDialog;

import static android.content.Context.MODE_PRIVATE;

public class MainColorFragment extends BaseFragment implements View.OnClickListener {

    private int position = 0;
    private ColorPickerDialog dialog;
    private Button layout_study,layout_exam,layout_video,layout_online,layout_user,layout_pay,layout_download,layout_settings,layout_about;
    private TextView tv_com;
    private  SharedPreferences sp;
    public static MainColorFragment newInstance(int position) {
        MainColorFragment fragment = new MainColorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_theme_color, container, false);
        sp = getActivity().getSharedPreferences("ThemeColor", MODE_PRIVATE);

        layout_study = (Button)contentView.findViewById(R.id.layout_study);
        layout_exam = (Button)contentView.findViewById(R.id.layout_exam);
        layout_video = (Button)contentView.findViewById(R.id.layout_video);
        layout_online = (Button)contentView.findViewById(R.id.layout_online);
        layout_user = (Button)contentView.findViewById(R.id.layout_user);
        layout_pay = (Button)contentView.findViewById(R.id.layout_pay);
        layout_download = (Button)contentView.findViewById(R.id.layout_download);
        layout_settings = (Button)contentView.findViewById(R.id.layout_settings);
        layout_about = (Button)contentView.findViewById(R.id.layout_about);
        tv_com = (TextView)contentView.findViewById(R.id.tv_com);

        layout_study.setOnClickListener(this);
        layout_exam.setOnClickListener(this);
        layout_video.setOnClickListener(this);
        layout_online.setOnClickListener(this);
        layout_user.setOnClickListener(this);
        layout_pay.setOnClickListener(this);
        layout_download.setOnClickListener(this);
        layout_settings.setOnClickListener(this);
        layout_about.setOnClickListener(this);
        tv_com.setOnClickListener(this);

        refreshThemeColor();
        return contentView;
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        position = bundle.getInt("position");

    }

    @Override
    public void onClick(View view) {
        String pickLayout = "";
        switch(view.getId()) {
            case R.id.layout_study:
                pickLayout = "layout_study";
                break;
            case R.id.layout_exam:
                pickLayout = "layout_exam";
                break;
            case R.id.layout_video:
                pickLayout = "layout_video";
                break;
            case R.id.layout_online:
                pickLayout = "layout_online";
                break;
            case R.id.layout_user:
                pickLayout = "layout_user";
                break;
            case R.id.layout_pay:
                pickLayout = "layout_pay";
                break;
            case R.id.layout_download:
                pickLayout = "layout_download";
                break;
            case R.id.layout_settings:
                pickLayout = "layout_settings";
                break;
            case R.id.layout_about:
                pickLayout = "layout_about";
                break;
            case R.id.tv_com:
                defaultThemeColor();
                refreshThemeColor();
                break;
        }

        if(pickLayout == ""){
            return;
        }
        pickColor(pickLayout);
    }


    public void pickColor(String pickLayout){
        final String finalPickLayout = pickLayout;
        //Toast.makeText(getActivity(), "pickLayout:" + pickLayout,Toast.LENGTH_SHORT).show();
        dialog = new ColorPickerDialog(getActivity(),"请选择颜色:选择颜色后点击内圆即可。", new ColorPickerDialog.OnColorChangedListener() {
            @Override
            public void colorChanged(int color) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(finalPickLayout,color);
                editor.commit();
                refreshThemeColor();
            }
        });
        dialog.show();
    }


    public void refreshThemeColor(){
        layout_study.setBackgroundColor(sp.getInt("layout_study", Color.parseColor("#ff6678")));
        layout_exam.setBackgroundColor(sp.getInt("layout_exam", Color.parseColor("#ffcc33")));
        layout_video.setBackgroundColor(sp.getInt("layout_video", Color.parseColor("#9755f6")));
        layout_online.setBackgroundColor(sp.getInt("layout_online", Color.parseColor("#ffcc33")));
        layout_user.setBackgroundColor(sp.getInt("layout_user", Color.parseColor("#0fc4d9")));
        layout_pay.setBackgroundColor(sp.getInt("layout_pay", Color.parseColor("#84d018")));
        layout_download.setBackgroundColor(sp.getInt("layout_download", Color.parseColor("#84d018")));
        layout_settings.setBackgroundColor(sp.getInt("layout_settings", Color.parseColor("#c8d900")));
        layout_about.setBackgroundColor(sp.getInt("layout_about", Color.parseColor("#22ad38")));
    }

    public void defaultThemeColor(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("layout_study",Color.parseColor("#ff6678"));
        editor.putInt("layout_exam",Color.parseColor("#ffcc33"));
        editor.putInt("layout_video",Color.parseColor("#9755f6"));
        editor.putInt("layout_online",Color.parseColor("#fe864b"));
        editor.putInt("layout_user",Color.parseColor("#0fc4d9"));
        editor.putInt("layout_pay",Color.parseColor("#ffcc33"));
        editor.putInt("layout_download",Color.parseColor("#84d018"));
        editor.putInt("layout_settings",Color.parseColor("#c8d900"));
        editor.putInt("layout_about",Color.parseColor("#22ad38"));
        editor.commit();
        Toast.makeText(getActivity(), "默认主题色设置成功!",Toast.LENGTH_SHORT).show();
    }
}