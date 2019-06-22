package com.student.oclass.fragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.student.oclass.R;
import com.student.oclass.adapter.ExamScoreAdapter;
import com.student.oclass.model.ExamScoreEntity;
import com.student.oclass.utils.AppConstant;
import com.student.oclass.utils.HToast;

public class ExamScoreFragment extends BaseFragment {

    private List<ExamScoreEntity> scoreRank=new ArrayList<ExamScoreEntity>();
    private ExamScoreAdapter adapter;
    private ListView mListView;
    public static ExamScoreFragment newInstance(int position) {
        ExamScoreFragment fragment = new ExamScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_view_list, container, false);
        mListView=(ListView)contentView.findViewById(R.id.list);
        initData();
        adapter=new ExamScoreAdapter(getActivity(), scoreRank);
        mListView.setAdapter(adapter);
        new InitDataTask().execute();
        return contentView;
    }
    void initData() {
        ExamScoreEntity score=new ExamScoreEntity("正在初始化...","",0);
        scoreRank.add(score);
    }
    private void parseArgument() {
        Bundle bundle = getArguments();
        int position=bundle.getInt("position");

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
                ResultSet rs=stmt.executeQuery("select score,submit_time,test_chapter from [oclass].[dbo].[EN_STU_RESULT]");
                while (rs.next()){
                    ExamScoreEntity score=new ExamScoreEntity("<Android应用程序开发>第"+rs.getString("test_chapter")+"章",rs.getString("submit_time"),rs.getInt("score"));
                    scoreRank.add(score);
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
                scoreRank.remove(0);
            }else {
                scoreRank.get(0).setTitle(msg);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
