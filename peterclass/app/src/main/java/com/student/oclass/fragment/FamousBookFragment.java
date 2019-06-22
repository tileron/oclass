package com.student.oclass.fragment;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.AdapterView;

import com.student.oclass.R;
import com.student.oclass.activity.webView;
import com.student.oclass.adapter.VideoAdapter;
import com.student.oclass.model.BookEntity;
import com.student.oclass.utils.AppConstant;

public class FamousBookFragment extends BaseFragment {

    private GridView gridView;

    private VideoAdapter adapter;

    private int position = 0;

    private List<BookEntity> listBook = new ArrayList<BookEntity>();

    public static FamousBookFragment newInstance(int position) {
        FamousBookFragment fragment = new FamousBookFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_sync_tech, container, false);
        gridView = (GridView) contentView.findViewById(R.id.gridview);
        parseArgument();
        if (position == 0) {
            initLocalData();
        } else {
            initOnlineData();
        }
        adapter = new VideoAdapter(getActivity(), listBook, true);
        gridView.setAdapter(adapter);

        //注册grid的item的点击事件

        gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = adapter.getName(position);
                String url = null;
                switch (position){
                    case  0:
                        url = "https://www.bilibili.com/video/av22836860/";
                        break;
                    case  1:
                        url = "https://www.bilibili.com/video/av52811495";
                        break;
                    case  2:
                        url = "https://www.bilibili.com/video/av41442740/";
                        break;
                    case  3:
                        url= "https://www.bilibili.com/video/av22836860/";
                        break;
                    case  4:
                        url = "https://www.bilibili.com/video/av48828477?from=search&seid=17041494753277985080";
                        break;
                    case  5:
                        url = "https://www.bilibili.com/video/av36617479/";
                        break;
                }
//                Intent it = new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(it);
                //if(position==0){
                //Uri uri =Uri.parse("http://www.baidu.com");
                //Intent it = new Intent(Intent.ACTION_VIEW,uri);
                //startActivity(it);
                //如果是第一个item
                Intent intent = new Intent(getActivity(), webView.class);
                intent.putExtra("url",url);//传参数 暂时没有做接收处理
                startActivity(intent);
                //}

                //Toast.makeText(getActivity(),"name:"+name+",position:"+position+",id:"+id,Toast.LENGTH_SHORT).show();
            }
        });

        return contentView;
    }



    void initLocalData() {
        for (int i = 0; i < 6; i++) {
            BookEntity book = new BookEntity();
            book.setType(AppConstant.FAMOUS_BOOK);
            listBook.add(book);
        }
    }

    void initOnlineData() {
        for (int i = 0; i < 6; i++) {
            BookEntity book = new BookEntity();
            book.setType(AppConstant.FAMOUS_BOOK);
            listBook.add(book);
        }
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        position = bundle.getInt("position");

    }
}
