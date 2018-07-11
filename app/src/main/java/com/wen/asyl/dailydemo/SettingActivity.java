package com.wen.asyl.dailydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;


import com.wen.asyl.adapter.ListDataAdapter;

import java.util.ArrayList;


/**
 * Description：xx <br/>
 * Copyright (c) 2018<br/>
 * This program is protected by copyright laws <br/>
 * Date:2018-07-11 10:03
 *
 * @author 姜文莒
 * @version : 1.0
 */
public class SettingActivity extends Activity {
    private ListView mListView;
    private ArrayList<String> mList = new ArrayList<>();
    private ListDataAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();

    }

    private void initView() {

        ImageButton mIbBack= (ImageButton) findViewById(R.id.ib_back);
        mIbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
      ListView mListView=(ListView)  findViewById(R.id.lv_setting);
        mList.add("作者:姜文莒");
        mList.add("如有建议请联系QQ：1322702546");
        mList.add("博客:https://blog.csdn.net/wen_haha");
        mList.add("GitHub:https://github.com/kongkongdaren");
        mList.add("学习自姜文莒：GitHub:https://github.com/kongkongdaren/DailyDemo");

        mAdapter = new ListDataAdapter(this, mList);
        mListView.setAdapter(mAdapter);
    }
}
