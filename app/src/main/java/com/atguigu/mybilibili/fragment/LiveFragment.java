package com.atguigu.mybilibili.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: 直播
 */

public class LiveFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = new TextView(getActivity());
        view.setText("直播");
        view.setGravity(Gravity.CENTER);
        return view;
    }
}
