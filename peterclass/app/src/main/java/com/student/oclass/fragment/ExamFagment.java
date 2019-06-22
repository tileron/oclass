package com.student.oclass.fragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.student.oclass.R;
import com.student.oclass.activity.ExamDetialActivity;
import com.student.oclass.adapter.ExamAdapter;
import com.student.oclass.model.BookEntity;
import com.student.oclass.model.ExamScoreEntity;
import com.student.oclass.utils.AppConstant;
import com.student.oclass.utils.HToast;

public class ExamFagment extends BaseFragment {

    private GridView gridView;

    private ExamAdapter adapter;

    private int position = 0;

    private List<BookEntity> listBook = new ArrayList<BookEntity>();

    public static ExamFagment newInstance(int position) {
        ExamFagment fragment = new ExamFagment();
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
            initExamData();
            new InitDataTask().execute();
        } else {
            initQuestionData();
        }
        adapter = new ExamAdapter(getActivity(), listBook, false);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ExamDetialActivity.class);
                intent.putExtra("id",listBook.get(position).getId());
                startActivity(intent);
            }
        });
        return contentView;
    }

    void initExamData() {
        BookEntity book = new BookEntity();
        book.setType(AppConstant.EXAM);
        book.setTitle("<Android>\n正在初始化\n...");
        listBook.add(book);
    }

    void initQuestionData() {
        for (int i = 0; i < 2; i++) {
            BookEntity book = new BookEntity();
            book.setType(AppConstant.EXAM);
            listBook.add(book);
        }
    }




    private void parseArgument() {
        Bundle bundle = getArguments();
        position = bundle.getInt("position");
    }


    public class InitDataTask extends AsyncTask<Void, Void, Boolean> {
        private String msg;
        private boolean isSuccess=true;


        @Override
        protected void onPreExecute() {
//            HToast.makeToast(getActivity(),"正在拉取信息...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Connection conn=null;
            Statement stmt=null;
            try {
                Class.forName(AppConstant.DRIVER);
                conn= DriverManager.getConnection(AppConstant.URL,AppConstant.USERNAME,AppConstant.PASSWORD);
                stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery("select distinct zjid from [oclass].[dbo].[EN_ST]");
                while (rs.next()){
                    BookEntity book = new BookEntity();
                    book.setType(AppConstant.EXAM);
                    String chapter=rs.getString("zjid").trim();
                    book.setTitle("<Android>\n第"+chapter+"章");
                    book.setId(Integer.parseInt(chapter));
                    listBook.add(book);
                }
                rs.close();
                stmt.close();
                conn.close();
                msg="pull score message success.";
            } catch (ClassNotFoundException e) {
                msg=e.getMessage();
                e.printStackTrace();
                isSuccess=false;
            } catch (SQLException e) {
                msg=e.getMessage();
                e.printStackTrace();
                isSuccess=false;
            }finally {
                try {
                    if(stmt!=null){
                        stmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if(conn!=null){
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return isSuccess;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(isSuccess){
                listBook.remove(0);
            }else {
                listBook.get(0).setTitle(msg);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
