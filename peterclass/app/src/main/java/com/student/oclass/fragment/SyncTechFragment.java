package com.student.oclass.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.student.oclass.R;
import com.student.oclass.activity.CourseListActivity;
import com.student.oclass.adapter.SyncTechAdapter;
import com.student.oclass.model.BookEntity;
import com.student.oclass.utils.FileUtil;
import com.student.oclass.utils.XmlFile;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import static java.lang.Integer.parseInt;

public class SyncTechFragment extends BaseFragment {

    private GridView gridView;

    private SyncTechAdapter adapter;

    private List<BookEntity> listBook = new ArrayList<BookEntity>();

    private int position = 0;

    private String GetRemoteOnlineListXmlURL = "http://120.79.131.208:8009/onlinecourseList.xml";
    /* SD卡根目录 */
    private File rootDie;

    /* 进度条对话框 */
    private ProgressDialog progress;

    private XmlFile OnlineCourseList;
    public static SyncTechFragment newInstance(int position) {
        SyncTechFragment fragment = new SyncTechFragment();
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
        }
        else {
            /* 异步加载在线资源列表 */
            new MyLoadAsyncTask(0,"OnlineCourseList.xml").execute(GetRemoteOnlineListXmlURL);
        }


        //注册grid的item的点击事件
        gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = adapter.getName(position);
                if(position==0 && listBook.get(position).getType() == 0){
                    //如果是本地资源的第一个item
                    Intent intent = new Intent(getActivity(), CourseListActivity.class);
                    intent.putExtra("type","courseList");//传参数 暂时没有做接收处理
                    startActivity(intent);
                }

                if(listBook.get(position).getType() == 1){
                    String FileUrl = listBook.get(position).getFileUrl();
                    String FileName = listBook.get(position).getName();
                    Toast.makeText(getActivity(),FileUrl,Toast.LENGTH_SHORT).show();
                    new MyLoadAsyncTask(1,FileName).execute(FileUrl);
                }
            }
        });
        return contentView;
    }

    void initLocalData() {
        //只要一个item：Android程序课件列表
        BookEntity book = new BookEntity();
        book.setType(0);//0代表本地资源  1代表在线资源
        listBook.add(book);
        adapter = new SyncTechAdapter(getActivity(), listBook, false);
        gridView.setAdapter(adapter);
    }


    void initOnlineData() {
        OnlineCourseList = new XmlFile(rootDie + "/oclass/download/OnlineCourseList.xml",getActivity());
        String[] CourseListName = OnlineCourseList.getAttrList("filename", "/courseList/course");
        String[] CourseListURL = OnlineCourseList.getAttrList("sources", "/courseList/course");
        for (int i = 0; i < CourseListName.length; i++) {
            BookEntity book = new BookEntity();
            book.setType(1);//0代表本地资源  1代表在线资源
            book.setName(CourseListName[i]);
            book.setFileUrl(CourseListURL[i]);
            listBook.add(book);
        }

        adapter = new SyncTechAdapter(getActivity(), listBook, false);
        gridView.setAdapter(adapter);
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        position = bundle.getInt("position");

    }


    /* 检查sdcard并创建目录文件 */
    private void checkAndCreateDir() {
        /* 获取sdcard目录 */
        rootDie = Environment.getExternalStorageDirectory();
        /* 新文件的目录 */
        File newFile = new File(rootDie + "/oclass/download/");
        if (!newFile.exists()) {
            /* 如果文件不存在就创建目录 */
            newFile.mkdirs();
        }
    }

    /* 异步任务，后台处理与更新UI */
    private class MyLoadAsyncTask extends AsyncTask<String, String, String> {

        private int type = 1;//0代表加载xml文件  1代表下载文件
        private String outFileName = "";
        /*构造函数*/
        public MyLoadAsyncTask(int type,String outFileName){
            this.type = type;
            this.outFileName = outFileName;
            checkAndCreateDir();
        }

        /* 后台线程 */
        @Override
        protected String doInBackground(String... params) {
            /* 所下载文件的URL */
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                /* URL属性设置 */
                conn.setRequestMethod("GET");
                /* URL建立连接 */
                conn.connect();
                /* 下载文件的大小 */
                int fileOfLength = conn.getContentLength();
                /* 每次下载的大小与总下载的大小 */
                int totallength = 0;
                int length = 0;
                /* 输入流 */
                InputStream in = conn.getInputStream();
                /* 输出流 */
                FileOutputStream out = new FileOutputStream(new File(rootDie + "/oclass/download/", outFileName));
                /* 缓存模式，下载文件 */
                byte[] buff = new byte[1024 * 1024];
                while ((length = in.read(buff)) > 0) {
                    totallength += length;
                    String str1 = "" + (int) ((totallength * 100) / fileOfLength);
                    publishProgress(str1);
                    out.write(buff, 0, length);
                    //调用publishProgress公布进度,最后onProgressUpdate方法将被执行
                    //为了显示出进度 人为休眠0.25秒
                    Thread.sleep(250);
                }
                /* 关闭输入输出流 */
                in.close();
                out.flush();
                out.close();
            } catch (MalformedURLException e) {
                Toast.makeText(getActivity(),"MalformedURLException",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(getActivity(),"IOException",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (InterruptedException e) {
                Toast.makeText(getActivity(),"InterruptedException",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }


        /* 预处理UI线程 */
        @Override
        protected void onPreExecute() {
            progress=new ProgressDialog(getActivity());
            progress.setIcon(R.drawable.ic_launcher);
            if(type==0){
                progress.setMessage("在线资源列表加载中...");
            }
            else{
                progress.setTitle("提示信息");
                progress.setMessage("正在下载中，请稍候...");
            }
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();
            progress.setProgress(0);
            super.onPreExecute();
        }


        /* 结束时的UI线程 */
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(),"加载远程列表完成",Toast.LENGTH_SHORT).show();
            progress.cancel();
            if(type==0){
                initOnlineData();
            }
            else if(type==1){
                //打开下载好的文件
                Toast.makeText(getActivity(),"下载文件完成:"+outFileName,Toast.LENGTH_SHORT).show();
                openAndroidFile(rootDie + "/oclass/download/"+outFileName);
            }
            super.onPostExecute(result);
        }


        /* 处理UI线程，会被多次调用,触发事件为publicProgress方法 */
        @Override
        protected void onProgressUpdate(String... values) {
            /* 进度显示 */
            super.onProgressUpdate(values);
            progress.setProgress(parseInt(values[0]));
        }


    }


    public void openAndroidFile(String filepath){
        Intent intent = new Intent();
        // 这是比较流氓的方法，绕过7.0的文件权限检查
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        File file = new File(filepath);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);//动作，查看
        intent.setDataAndType(Uri.fromFile(file), new FileUtil().getMIMEType(file));//设置类型
        startActivity(intent);
    }

}