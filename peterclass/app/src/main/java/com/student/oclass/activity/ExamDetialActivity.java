package com.student.oclass.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.student.oclass.R;
import com.student.oclass.adapter.ChoiceItemAdapter;
import com.student.oclass.adapter.JudgeItemAdapter;
import com.student.oclass.model.BookEntity;
import com.student.oclass.model.ChoiceItem;
import com.student.oclass.model.JudgeItem;
import com.student.oclass.utils.AppConstant;
import com.student.oclass.utils.HToast;
import com.student.oclass.view.ReplaceSpan;
import com.student.oclass.view.SpansManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamDetialActivity extends BaseActivity implements ReplaceSpan.OnClickListener,View.OnClickListener{
    private TextView mTvContent;
    private EditText mEtInput;
    private SpansManager mSpansManager;
    private List<JudgeItem> judgeItemList=new ArrayList<>();
    private ListView judgeListView;
    private List<ChoiceItem> choiceItemList=new ArrayList<>();
    private ListView choiceListView;
    private ProgressBar contentLoading;
    private ScrollView content;
    private Button submitButton;
    private JudgeItemAdapter judgeItemAdapter;
    private ChoiceItemAdapter choiceItemAdapter;
    private String[] fillBlankAnswer;
    private Boolean[] judgeAnswer;
    private Boolean[][] choiceAnswer;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detial);
        iv_title = (ImageView) this.findViewById(R.id.iv_title);
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        iv_title.setImageResource(R.drawable.exam_pressed);
        id=getIntent().getIntExtra("id",0);
        tv_title.setText(getResources().getString(R.string.exam_room)+" 第"+id+"章 满分50分");
        content=findViewById(R.id.content);
        contentLoading=findViewById(R.id.content_loading);
        submitButton=findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        mTvContent = (TextView) findViewById(R.id.tv_content);
        mEtInput = (EditText) findViewById(R.id.et_input);
        judgeListView=findViewById(R.id.judge_list_view);
        judgeItemAdapter=new JudgeItemAdapter(ExamDetialActivity.this,R.layout.judge_item_layout,judgeItemList);
        judgeListView.setAdapter(judgeItemAdapter);
        judgeListView.setDividerHeight(0);

        choiceListView=findViewById(R.id.choice_list_view);
        choiceItemAdapter=new ChoiceItemAdapter(ExamDetialActivity.this,R.layout.choice_item_layout,choiceItemList);
        choiceListView.setAdapter(choiceItemAdapter);
        choiceListView.setDividerHeight(0);

        fillBlankAnswer=new String[AppConstant.FULL_BLANK_SIZE];
        judgeAnswer=new Boolean[AppConstant.JUDGE_SIZE];
        choiceAnswer=new Boolean[AppConstant.CHOICE_SIZE][4];

        new InitDataTask().execute(new Integer[]{id});
    }

    @Override
    public void OnClick(TextView v, int id, ReplaceSpan span) {
        mSpansManager.setData(mEtInput.getText().toString(),null, mSpansManager.mOldSpan);
        mSpansManager.mOldSpan = id;
        //如果当前span身上有值，先赋值给et身上
        mEtInput.setText(TextUtils.isEmpty(span.mText)?"":span.mText);
        mEtInput.setSelection(span.mText.length());
        span.mText = "";
        //通过rf计算出et当前应该显示的位置
        RectF rf = mSpansManager.drawSpanRect(span);
        //设置EditText填空题中的相对位置
        mSpansManager.setEtXY(rf);
        mSpansManager.setSpanChecked(id);
    }

    private String getMyAnswerStr(){
        mSpansManager.setLastCheckedSpanText(mEtInput.getText().toString());
        return mSpansManager.getMyAnswer().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_button:
                int undo=0;
                int score=0;
                String[] fillBlankResult=getMyAnswerStr().substring(1,getMyAnswerStr().length()-1).split(",");
                String fillBlankAnswerHandledString="";
                for (int i=0;i<fillBlankAnswer.length;i++){
                    fillBlankAnswerHandledString+=fillBlankAnswer[i]+"、";
                }
                String[] fillBlankAnswerHandled=fillBlankAnswerHandledString.substring(0,fillBlankAnswerHandledString.length()-1).split("、");
                for (int i=0;i<fillBlankAnswerHandled.length;i++){
//                    Log.d("sdfasdwwqrrq", "onClick: "+fillBlankResult[i]+"=="+fillBlankAnswerHandled[i]);
                    if (fillBlankResult[i].trim().equals("")){
                        undo++;
                    }else if(fillBlankAnswerHandled[i].equals(fillBlankResult[i].trim())){
                        score+=2;
                    }
                }
                for (int i = 0; i < judgeAnswer.length; i++) {
                    if (judgeItemAdapter.result[i]==null){
                        undo++;
                    }else if (judgeAnswer[i]==judgeItemAdapter.result[i]){
                        score+=2;
                    }
                }
                for (int i=0;i<choiceAnswer.length;i++){
//                    Log.d("sdfasdwwqrrq",Arrays.asList(choiceItemAdapter.result[i]).toString());
                    if (Arrays.asList(choiceItemAdapter.result[i]).toString().equals("[null, null, null, null]")){
                        undo++;
                    }else if (Arrays.asList(choiceAnswer).toString().equals(Arrays.asList(choiceItemAdapter.result[i]).toString())){
                        score+=4;
                    }
//                    for (int j=0;j<choiceAnswer[i].length;j++){
//                        if (choiceAnswer[i][j])
//                    }
                }
//                Log.d("sdfasdwwqrrq", "onClick: "+"fill_blank"+getMyAnswerStr()+" judge"+ Arrays.asList(judgeItemAdapter.result).toString()+"choice"+choiceResult);
//                Log.d("sdfasdwwqrrq", "onClick: "+"fill_blank"+Arrays.asList(fillBlankAnswer).toString()+" judge"+ Arrays.asList(judgeAnswer).toString()+"choice"+choiceResult);
                AlertDialog.Builder builder=new AlertDialog.Builder(ExamDetialActivity.this);
                builder.setTitle("测试结果").setMessage("分数: "+score+"\n未做: "+undo).setPositiveButton("确定",null).create().show();
                new SubmitDataTask().execute(new String[]{"2016030604",""+score,""+undo,""+id});
        }
    }


    public class InitDataTask extends AsyncTask<Integer, Void, Boolean> {
        private String msg;
        private String fillBlank="";
        private boolean isSuccess=true;


        @Override
        protected void onPreExecute() {
            contentLoading.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            Connection conn=null;
            Statement stmt=null;
            try {
                Class.forName(AppConstant.DRIVER);
                conn= DriverManager.getConnection(AppConstant.URL,AppConstant.USERNAME,AppConstant.PASSWORD);
                stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery("select * from dbo.EN_ST right join dbo.EN_DA on dbo.EN_ST.stid=dbo.EN_DA.stid where zjid="+params[0]);
                int j=0;
                String[] choiceItem=new String[4];
                int fillBlankI=0;
                int judgeI=0;
                int choiceI=0;
                while (rs.next()){
                    switch (rs.getString("sttype").trim()){
                        case "1":
                            fillBlank+=rs.getString("sttext")+"<br/>";
                            fillBlankAnswer[(fillBlankI++)%AppConstant.FULL_BLANK_SIZE]=rs.getString("datext").substring(2).trim();
                            break;
                        case "2":
                            judgeItemList.add(new JudgeItem(rs.getString("sttext"),false));
                            judgeAnswer[(judgeI++)%AppConstant.JUDGE_SIZE]="1".equals(rs.getString("istrue"))?true:false;
//                            if ("√".equals(rs.getString("datext").trim())){
//                                judgeAnswer[(judgeI++)%AppConstant.JUDGE_SIZE]=true;
//                            }else if ("×".equals(rs.getString("datext").trim())){
//                                judgeAnswer[(judgeI++)%AppConstant.JUDGE_SIZE]=false;
//                            }
//                            Log.d("sdfasdwwqrrq", "doInBackground: "+(rs.getString("datext").trim()=="√"));
                            break;
                        case "3":
                            choiceItem[(j++)%4]=rs.getString("datext");
                            if(j%4==0){
                                choiceItemList.add(new ChoiceItem(rs.getString("sttext"),choiceItem[0],choiceItem[1],choiceItem[2],choiceItem[3]));
                            }
                            choiceAnswer[(choiceI++)%5][j%4]="1".equals(rs.getString("istrue"))?true:false;
//                            Log.d("sdfasdwwqrrq", "doInBackground: "+rs.getString("istrue"));
                    }
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
                mSpansManager = new SpansManager(ExamDetialActivity.this,mTvContent,mEtInput);
                mSpansManager.doFillBlank(fillBlank);

                judgeItemAdapter.notifyDataSetChanged();
                choiceItemAdapter.notifyDataSetChanged();

                contentLoading.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }else {
                HToast.makeToast(ExamDetialActivity.this,msg);
            }
        }
    }

    public class SubmitDataTask extends AsyncTask<String, Void, Boolean> {
        private String msg;
        private boolean isSuccess=true;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            Connection conn=null;
            PreparedStatement pstmt=null;
            try {
                Class.forName(AppConstant.DRIVER);
                conn=DriverManager.getConnection(AppConstant.URL,AppConstant.USERNAME,AppConstant.PASSWORD);
                pstmt=conn.prepareStatement("insert into dbo.EN_STU_RESULT(stuid,score,undo,submit_time,test_chapter) values(?,?,?,getdate(),?)");
                pstmt.setString(1,params[0]);
                pstmt.setInt(2,Integer.valueOf(params[1]));
                pstmt.setInt(3,Integer.valueOf(params[2]));
                pstmt.setInt(4,Integer.valueOf(params[3]));
                isSuccess=pstmt.executeUpdate()==1?true:false;
                pstmt.close();
                conn.close();
                msg="register success.";
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
                    if(pstmt!=null){
                        pstmt.close();
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
            if (!success) {
                Toast.makeText(ExamDetialActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
