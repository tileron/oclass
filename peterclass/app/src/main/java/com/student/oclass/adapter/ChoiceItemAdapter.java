package com.student.oclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.student.oclass.R;
import com.student.oclass.model.ChoiceItem;
import com.student.oclass.model.JudgeItem;
import com.student.oclass.utils.AppConstant;

import java.util.List;

public class ChoiceItemAdapter extends ArrayAdapter implements CompoundButton.OnCheckedChangeListener{
    public Boolean[][] result=new Boolean[AppConstant.CHOICE_SIZE][4];
    private int resouseceId;
    public ChoiceItemAdapter(Context context, int textViewResourceId, List<ChoiceItem> objects){
        super(context,textViewResourceId,objects);
        resouseceId=textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        ChoiceItem choiceItem= (ChoiceItem) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view =LayoutInflater.from(getContext()).inflate(resouseceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.title=view.findViewById(R.id.choice_tv);
            viewHolder.checkBoxA=view.findViewById(R.id.choiceA);
            viewHolder.checkBoxB=view.findViewById(R.id.choiceB);
            viewHolder.checkBoxC=view.findViewById(R.id.choiceC);
            viewHolder.checkBoxD=view.findViewById(R.id.choiceD);
            viewHolder.checkBoxContain=view.findViewById(R.id.checkbox_contain);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.title.setText(choiceItem.getTitle());
        viewHolder.checkBoxA.setText(choiceItem.getChoiceA());
        viewHolder.checkBoxB.setText(choiceItem.getChoiceB());
        viewHolder.checkBoxC.setText(choiceItem.getChoiceC());
        viewHolder.checkBoxD.setText(choiceItem.getChoiceD());
        viewHolder.checkBoxA.setOnCheckedChangeListener(this);
        viewHolder.checkBoxB.setOnCheckedChangeListener(this);
        viewHolder.checkBoxC.setOnCheckedChangeListener(this);
        viewHolder.checkBoxD.setOnCheckedChangeListener(this);
        viewHolder.checkBoxContain.setTag(position);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        LinearLayout linearLayout=(LinearLayout) buttonView.getParent();
        if (buttonView.getId()==R.id.choiceA){
            result[(int) linearLayout.getTag()][0]=isChecked;
        }else if (buttonView.getId()==R.id.choiceB){
            result[(int) linearLayout.getTag()][1]=isChecked;
        }else if (buttonView.getId()==R.id.choiceC){
            result[(int) linearLayout.getTag()][2]=isChecked;
        }else if(buttonView.getId()==R.id.choiceD){
            result[(int) linearLayout.getTag()][3]=isChecked;
        }
    }

    class ViewHolder{
        TextView title;
        CheckBox checkBoxA;
        CheckBox checkBoxB;
        CheckBox checkBoxC;
        CheckBox checkBoxD;
        LinearLayout checkBoxContain;
    }
}
