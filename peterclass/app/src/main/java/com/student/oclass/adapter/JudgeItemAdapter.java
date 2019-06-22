package com.student.oclass.adapter;

import android.content.ClipData;
import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.student.oclass.R;
import com.student.oclass.model.JudgeItem;
import com.student.oclass.utils.AppConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JudgeItemAdapter extends ArrayAdapter {
    private int resouseceId;
    public Boolean[] result=new Boolean[AppConstant.JUDGE_SIZE];
    public JudgeItemAdapter(Context context, int textViewResourceId, List<JudgeItem> objects){
        super(context,textViewResourceId,objects);
        resouseceId=textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        JudgeItem judgeItem= (JudgeItem) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view =LayoutInflater.from(getContext()).inflate(resouseceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.title=view.findViewById(R.id.judge_tv);
            viewHolder.radioGroup=view.findViewById(R.id.judge_radioGroup);
            viewHolder.radioButton1=view.findViewById(R.id.judge_true);
            viewHolder.radioButton2=view.findViewById(R.id.judge_false);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.title.setText(judgeItem.getTitle());
//        viewHolder.radioButton1.setChecked(judgeItem.getTrue()?true:false);
//        viewHolder.radioButton2.setChecked(judgeItem.getTrue()?false:true);
        viewHolder.radioGroup.setTag(position);
        viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.judge_true:
                        result[(int)group.getTag()]=true;
                        break;
                    case R.id.judge_false:
                        result[(int)group.getTag()]=false;
                }
            }
        });
        return view;
    }
    class ViewHolder{
        TextView title;
        RadioGroup radioGroup;
        RadioButton radioButton1,radioButton2;
    }
}
