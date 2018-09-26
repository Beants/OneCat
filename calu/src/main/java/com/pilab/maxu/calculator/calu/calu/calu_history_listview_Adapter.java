package com.pilab.maxu.calculator.calu.calu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pilab.maxu.calculator.calu.R;

import java.util.List;

/**
 * Created by andersen on 2018/9/15.
 */

public class calu_history_listview_Adapter extends ArrayAdapter<calu_history_item> {
    private int layoutId;
    private String TAG = "mycalu";

    public calu_history_listview_Adapter(Context context, int layoutId, List<calu_history_item> list) {
        super(context, layoutId, list);
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        calu_history_item item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        TextView textView1 = (TextView) view.findViewById(R.id.calu_history_time);
        TextView textView2 = (TextView) view.findViewById(R.id.calu_history_formula);
        textView1.setText("Time:"+item.getTime());
        textView2.setText("      Formula:"+item.getFormula());
//        Log.i(TAG, "getView: " + item.getTime() + " -----------------:" + item.getFormula());


        return view;
    }

}
