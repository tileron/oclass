package com.student.oclass.adapter;

import java.util.List;

import com.student.oclass.R;
import com.student.oclass.adapter.GameRecordAdapter.ViewHolder;
import com.student.oclass.model.BookEntity;
import com.student.oclass.view.NumberProgressBar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DownloadAdapter extends BaseAdapter {

	private List<BookEntity> listBook=null;
	private Context context;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listBook.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listBook.get(position);
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
			convertView=LayoutInflater.from(context).inflate(R.layout.view_download_item, null);
			holder.iv_pause=(ImageView) convertView.findViewById(R.id.iv_pause);
			holder.iv_del=(ImageView) convertView.findViewById(R.id.iv_del);
			holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_author=(TextView) convertView.findViewById(R.id.tv_author);
			holder.tv_size=(TextView) convertView.findViewById(R.id.tv_size);
			holder.numPbar=(NumberProgressBar) convertView.findViewById(R.id.progress);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		BookEntity book=listBook.get(position);
		if(book.getType()==1) {
			holder.numPbar.setVisibility(View.INVISIBLE);
		}else {
			holder.numPbar.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	
	public DownloadAdapter(Context context,List<BookEntity> listBook) {
		this.context=context;
		this.listBook=listBook;
	}
	class ViewHolder{
		ImageView iv_pause;
		ImageView iv_del;
		TextView tv_title;
		TextView tv_author;
		TextView tv_size;
		NumberProgressBar numPbar;
	}
}
