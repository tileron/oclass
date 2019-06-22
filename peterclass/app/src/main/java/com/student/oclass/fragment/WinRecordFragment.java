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
import android.widget.ListView;
import com.student.oclass.R;
import com.student.oclass.adapter.GameRecordAdapter;
import com.student.oclass.model.BookEntity;

public class WinRecordFragment extends BaseFragment {

    private List<BookEntity> listBook=new ArrayList<BookEntity>();
    private GameRecordAdapter adapter;
    private ListView mListView;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==12){
                adapter.notifyDataSetChanged();
                for (BookEntity b:listBook
                ) {
                    Log.d("sdfadsfadsf", "handleMessage: "+b.getName()+"  id "+b.getId());
                }
            }
        }
    };
    public static WinRecordFragment newInstance(int position) {
        WinRecordFragment fragment = new WinRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_view_list, container, false);
        initData();
        mListView=(ListView)contentView.findViewById(R.id.list);
        adapter=new GameRecordAdapter(getActivity(), listBook);
        mListView.setAdapter(adapter);
        return contentView;
    }

    void  initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection con = null;
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + "120.79.131.208" + ":1433/" + "oclass" + ";charset=utf8", "oclass","oclass666");
                    ResultSet rs =null;
                    String sql = "select * from TE_INFO";
                    Statement stmt = con.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()){
                        BookEntity book=new BookEntity();
                        book.setName(rs.getString("name"));
                        book.setTitle(rs.getString("id"));
                        listBook.add(book);
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                } catch (SQLException e) {
                    Log.d("safwefqqw", "run: "+e.getMessage());
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
