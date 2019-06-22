package com.student.oclass.fragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.student.oclass.R;
import com.student.oclass.adapter.FamousBookFragmentAdapter;
import com.student.oclass.adapter.GameAdapter;
import com.student.oclass.adapter.SyncTechAdapter;
import com.student.oclass.model.BookEntity;
import com.student.oclass.utils.AppConstant;

public class GameFragment extends BaseFragment {

    private GridView gridView;
    private GameAdapter adapter;
    private List<BookEntity> listBook=new ArrayList<BookEntity>();
    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg){
            if(msg.what==12){
                adapter.notifyDataSetChanged();
                for(BookEntity b:listBook)
                {
                    Log.d("aaa", "handleMessage: "+b.getName()+"id"+b.getId());
                }
            }
        }
    };

    public static GameFragment newInstance(int position) {
        GameFragment fragment = new GameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_sync_tech, container, false);
        gridView=(GridView) contentView.findViewById(R.id.gridview);
        initData();
        adapter=new GameAdapter(getActivity(), listBook);
        gridView.setAdapter(adapter);
        return contentView;
    }
    void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection con = null;
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + "120.79.131.208" + ":1433/" + "oclass" + ";charset=utf8", "oclass","oclass666");
                    ResultSet rs =null;
                    String sql_student = "select * from STU_INFO";
                    Statement stmt = con.createStatement();
                    rs = stmt.executeQuery(sql_student);
                    while (rs.next()){
                        BookEntity book=new BookEntity();
                        book.setName(rs.getString("stu_name"));
                        listBook.add(book);
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                } catch (SQLException e) {
                    Log.d("bbb", "run: "+e.getMessage());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                handler.obtainMessage(12).sendToTarget();
            }
        }).start();
    }
    private void parseArgument() {
        Bundle bundle = getArguments();
        int position=bundle.getInt("position");

    }

}
