package com.wen.asyl.dailydemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wen.asyl.adapter.ConstListAdapter;
import com.wen.asyl.db.DataBaseHelper;
import com.wen.asyl.entity.ConstBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<ConstBean> mConstBeanList;
    private DataBaseHelper dataBaseHelper;
    private ConstListAdapter adapter;
    private ListView costList;
    private TextView mTvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(this);
        mConstBeanList = new ArrayList<>();
        initDatas();
        initViews();

    }

    private void initViews() {
        mTvNoData = (TextView) findViewById(R.id.tv_no_data);
        costList = (ListView) findViewById(R.id.lv_main);
        adapter = new ConstListAdapter(this,mConstBeanList);
        costList.setAdapter(adapter);
        costList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("账单详情")
                        .setMessage("说明：" + mConstBeanList.get(position).constTitle + "\n"
                                + "时间：" + mConstBeanList.get(position).constDate + "\n"
                                + "金额：" + mConstBeanList.get(position).constMoney+"元")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //数据库的也删除
                                dataBaseHelper.deleteData(mConstBeanList.get(position).constDate);
                                mConstBeanList.remove(position);
                                adapter.notifyDataSetChanged();

                                //没有数据
                                if (mConstBeanList.size() == 0) {
                                    costList.setVisibility(View.GONE);
                                    mTvNoData.setVisibility(View.VISIBLE);
                                }

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                View viewDialog=inflater.inflate(R.layout.new_cost_data,null);
                final EditText title=(EditText)viewDialog.findViewById(R.id.et_cost_title);
                final EditText money=(EditText)viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker dp=(DatePicker)viewDialog.findViewById(R.id.dp_cost_date);
                builder.setView(viewDialog);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConstBean constBean=new ConstBean();
                        constBean.constTitle=   title.getText().toString().trim();
                        constBean.constMoney=   money.getText().toString().trim();
                        constBean.constDate=dp.getYear()+"-"+(dp.getMonth()-1)+"-"+dp.getDayOfMonth();
                        dataBaseHelper.insertCost(constBean);
                        //虽然已插入数据库，但读取数据库文件是在初始化时读取的，并且只读取一次，所以需要插入到集合中
                        mConstBeanList.add(constBean);
                        adapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
            }
        });
    }

    private void initDatas() {
        Cursor cursor = dataBaseHelper.getAllCursorData();
        if (cursor!=null){
            while (cursor.moveToNext()){
                ConstBean constBean=new ConstBean();
                constBean.constTitle=  cursor.getString(cursor.getColumnIndex("cost_title"));
                constBean.constMoney=  cursor.getString(cursor.getColumnIndex("cost_money"));
                constBean.constDate=  cursor.getString(cursor.getColumnIndex("cost_data"));
                mConstBeanList.add(constBean);
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_charts:
                Intent intent=new Intent(MainActivity.this,ChartsActivity.class);
                intent.putExtra("cost_list", (Serializable) mConstBeanList);
                startActivity(intent);
                break;
            case R.id.action_setting:
                Intent intentSetting=new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.action_clear:
                if (mConstBeanList.size() != 0) {
                    new AlertDialog.Builder(this)
                            .setTitle("警告")
                            .setMessage("如果点击确定，将删除所有账单数据且不能恢复")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dataBaseHelper.deleteAllData();
                                    mConstBeanList.clear();
                                    adapter.notifyDataSetChanged();
                                    //没有数据
                                    if (mConstBeanList.size() == 0) {
                                       costList .setVisibility(View.GONE);
                                        mTvNoData.setVisibility(View.VISIBLE);
                                    }
                                }
                            })
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    Toast.makeText(this, "对不起，您都还没有记账！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
