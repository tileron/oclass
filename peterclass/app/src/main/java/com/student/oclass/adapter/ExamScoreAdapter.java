package com.student.oclass.adapter;

import java.util.List;

import com.student.oclass.R;
import com.student.oclass.model.ExamScoreEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExamScoreAdapter extends BaseAdapter {

	private List<ExamScoreEntity> scoreRank=null;
	private Context context;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return scoreRank.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return scoreRank.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null) {
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.game_record_item, null);
			holder.iv_tag=(ImageView) convertView.findViewById(R.id.iv_tag);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_score=(TextView) convertView.findViewById(R.id.tv_score);
			holder.tv_class=(TextView) convertView.findViewById(R.id.tv_class);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		ExamScoreEntity score=scoreRank.get(position);
		holder.iv_tag.setVisibility(View.GONE);
		holder.tv_class.setVisibility(View.VISIBLE);
		holder.tv_class.setText(score.getTitle());
		holder.tv_name.setText(score.getTime());
		holder.tv_score.setText(score.getScore()+"");
		return convertView;
	}
	public ExamScoreAdapter(Context context,List<ExamScoreEntity> scoreRank) {
		this.context=context;
		this.scoreRank=scoreRank;
	}
	class ViewHolder{
		ImageView iv_tag;
		TextView tv_name;
		TextView tv_score;
		TextView tv_class;
	}

}
