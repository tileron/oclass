package com.student.oclass.adapter;

import java.util.List;

import com.student.oclass.R;
import com.student.oclass.adapter.SyncTechAdapter.ViewHolder;
import com.student.oclass.model.BookEntity;
import com.student.oclass.utils.AppConstant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GameAdapter extends BaseAdapter {

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
	public View getView(int position, View contentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(contentView==null) {
			holder=new ViewHolder();
			contentView=LayoutInflater.from(context).inflate(R.layout.view_game_item, null);
			holder.iv_book=(ImageView) contentView.findViewById(R.id.iv_book);
			holder.tv_name=(TextView) contentView.findViewById(R.id.tv_name);
			contentView.setTag(holder);
		}else {
			holder=(ViewHolder) contentView.getTag();
		}
		BookEntity book=listBook.get(position);
		if(position%2==0) {
			holder.iv_book.setImageResource(R.drawable.game_default);
			holder.tv_name.setText(book.getName());

		}else {
			holder.iv_book.setImageResource(R.drawable.g_default);
			holder.tv_name.setText(book.getName());
		}
		return contentView;
	}
	public GameAdapter(Context context,List<BookEntity> listBook) {
		this.context=context;
		this.listBook=listBook;
	}
	class ViewHolder{
		ImageView iv_book;
		TextView tv_name;
	}
}
