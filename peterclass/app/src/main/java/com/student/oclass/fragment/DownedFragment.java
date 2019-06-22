package com.student.oclass.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.student.oclass.R;
import com.student.oclass.adapter.SyncTechAdapter;
import com.student.oclass.model.BookEntity;

public class DownedFragment extends BaseFragment {

    private GridView gridView;

    private SyncTechAdapter adapter;

    private int position = 0;

    private List<BookEntity> listBook = new ArrayList<BookEntity>();

    public static DownedFragment newInstance(int position) {
        DownedFragment fragment = new DownedFragment();
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
        initLocalData();
        adapter = new SyncTechAdapter(getActivity(), listBook, true);
        gridView.setAdapter(adapter);
        return contentView;
    }

    void initLocalData() {
        for (int i = 0; i < 2; i++) {
            BookEntity book = new BookEntity();
            listBook.add(book);
        }
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        position = bundle.getInt("position");

    }
}
