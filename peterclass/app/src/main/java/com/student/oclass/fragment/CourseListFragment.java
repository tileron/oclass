package com.student.oclass.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.student.oclass.R;
import com.student.oclass.adapter.CourseListAdapter;
import com.student.oclass.model.BookEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import com.student.oclass.utils.FileUtil;

public class CourseListFragment extends BaseFragment {

    private List<BookEntity> listBook=new ArrayList<BookEntity>();
    private CourseListAdapter adapter;
    private ListView mListView;
    private String[] courseArray = null;
    /* SD卡根目录 */
    private File rootDie = Environment.getExternalStorageDirectory();

    public static CourseListFragment newInstance(int position) {
        CourseListFragment fragment = new CourseListFragment();
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
        //实例化一个CourseListAdapter课程列表适配器
        adapter=new CourseListAdapter(getActivity(), listBook);
        //列表控件绑定一个适配器
        mListView.setAdapter(adapter);

        //注册list的item的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String courseName = adapter.getName(position);
                String outFilePath = rootDie + "/oclass/";
                File fi = new File(outFilePath);
                if(!fi.exists()){
                    fi.mkdir();
                }
                String strOutFileName = outFilePath + courseName;

                //复制文件到/oclass目录下并打开
                try {
                    copyBigDataToSD("ppt/"+courseName,strOutFileName);
                    openAndroidFile(strOutFileName);
                } catch (IOException e) {
                    Toast.makeText(getActivity(),"打开文件发生错误!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return contentView;
    }
    void initData() {
        try {
            courseArray = getActivity().getAssets().list("ppt");
            for(int i=0;i<courseArray.length;i++) {
                BookEntity book=new BookEntity();
                book.setName(courseArray[i]);
                listBook.add(book);
            }
        } catch (IOException e) {
            Toast.makeText(getActivity(),"获取文件列表错误!",Toast.LENGTH_SHORT).show();
        }
    }
    private void parseArgument() {
        Bundle bundle = getArguments();
        int position=bundle.getInt("position");

    }

    private void copyBigDataToSD(String assetName,String strOutFileName) throws IOException
    {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(strOutFileName);
        myInput = getActivity().getAssets().open(assetName);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
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

