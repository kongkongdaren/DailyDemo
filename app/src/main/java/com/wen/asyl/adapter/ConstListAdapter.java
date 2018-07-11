package com.wen.asyl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wen.asyl.dailydemo.R;
import com.wen.asyl.entity.ConstBean;

import java.util.List;


public class ConstListAdapter extends BaseAdapter {

    private List<ConstBean> mList;
    private Context mcontext;
    private LayoutInflater mLyoutInflater;

    public ConstListAdapter(Context context,List<ConstBean> mList) {
        mcontext=context;
        this.mList = mList;
        mLyoutInflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        ViewHolder holder = null;
        if (view == null) {
            view = mLyoutInflater.inflate(R.layout.list_item, null); //加载布局
            holder = new ViewHolder();

            holder.mTvConstTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.mTvConstDate = (TextView) view.findViewById(R.id.tv_date);
            holder.mTvConstMoney = (TextView) view.findViewById(R.id.tv_cost);

            view.setTag(holder);
        } else { //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) view.getTag();
        }

        ConstBean bean = mList.get(i);
        holder.mTvConstTitle.setText(bean.constTitle);
        holder.mTvConstDate.setText(bean.constDate);
        holder.mTvConstMoney.setText(bean.constMoney+"元");

        return view;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView mTvConstTitle;
        TextView mTvConstDate;
        TextView mTvConstMoney;
    }
}
