package com.student.oclass.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.student.oclass.R;
import com.student.oclass.model.BookEntity;
import com.student.oclass.utils.AppConstant;

public class SyncTechAdapter extends BaseAdapter {

    private List<BookEntity> listBook = null;

    private Context context;

    private boolean isFamousBook = false;

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

    //获取title
    public String getName(int position) {
        // TODO Auto-generated method stub
        return listBook.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (contentView == null) {
            holder = new ViewHolder();
            if (isFamousBook) {
                contentView = LayoutInflater.from(context).inflate(R.layout.view_famous_book_item, null);
            } else {
                contentView = LayoutInflater.from(context).inflate(R.layout.sync_tech_item, null);
            }
            holder.iv_book = (ImageView) contentView.findViewById(R.id.iv_book);
            holder.layout_bg = (RelativeLayout) contentView.findViewById(R.id.layout_book);
            holder.tv_name = (TextView) contentView.findViewById(R.id.tv_name);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }
        BookEntity book = listBook.get(position);
        if (book.getType() == 0) {
            //本地资源
            holder.iv_book.setImageResource(R.drawable.english_default);
            holder.tv_name.setText("Android程序\n课件列表");

        } else if(book.getType() == 1){
            //在线资源
            holder.iv_book.setImageResource(R.drawable.book_default);
            holder.tv_name.setText(book.getName());
        }
        return contentView;
    }

    public SyncTechAdapter(Context context, List<BookEntity> listBook, boolean isFamousBook) {
        this.context = context;
        this.listBook = listBook;
        this.isFamousBook = isFamousBook;
    }

    class ViewHolder {
        ImageView iv_book;

        TextView tv_name;

        RelativeLayout layout_bg;
    }
}