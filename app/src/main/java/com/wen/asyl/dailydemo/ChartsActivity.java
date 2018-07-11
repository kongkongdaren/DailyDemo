package com.wen.asyl.dailydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.wen.asyl.entity.ConstBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by wen on 2018/7/10.
 */

public class ChartsActivity extends Activity {

    private LineChartView mchart;
    private Map<String,Integer> table=new TreeMap<>();
    private LineChartData mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mchart =(LineChartView)findViewById(R.id.lcv_chart);
        ImageButton  mIbBack =(ImageButton)findViewById(R.id.ib_back);
        mIbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         List<ConstBean> mConstBeanList= (List<ConstBean>) getIntent().getSerializableExtra("cost_list");
        generateValues(mConstBeanList);
        generateData();
    }

    private void generateValues(List<ConstBean> list) {
        if (list!=null){
            for (int i=0;i<list.size();i++){
                ConstBean constBean = list.get(i);
                String constDate = constBean.constDate;
                int money = Integer.parseInt(constBean.constMoney);
                if (!table.containsKey(constDate)){
                    table.put(constDate,money);
                }else{
                    table.put(constDate,money+( table.get(constDate)));
                }
            }
        }
    }
    private void generateData() {
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();
        int index=0;
        for (Integer value:table.values()) {
            values.add(new PointValue(index,value));
            index++;
        }
        Line line=new Line(values);
        //设置线条的颜色
        line.setColor(ChartUtils.COLORS[0]);
        //设置点的形状
        line.setShape(ValueShape.CIRCLE);
        //设置点的颜色
        line.setPointColor(ChartUtils.COLORS[1]);
        //添加
        lines.add(line);

        mData=new LineChartData(lines);

        mchart.setLineChartData(mData);
    }
}
