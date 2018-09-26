package com.pilab.maxu.calculator.calu.calu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.pilab.maxu.calculator.calu.R;

import java.util.ArrayList;
import java.util.List;

public class CaluHistoryActivity extends AppCompatActivity {
    String TAG = "CaluHistoryActivity";
    String PREFS_NAME = "CaluHistory";
    private List<calu_history_item> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calu_history);


        Toolbar toolbar = findViewById(R.id.tb_clau_history);
        toolbar.setTitle("计算器历史记录");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initList();
        Log.i(TAG, "onCreate: ____________________________________________" + list.toString());
        calu_history_listview_Adapter itemAdapter = new calu_history_listview_Adapter(CaluHistoryActivity.this, R.xml.listview_calu_history, list);
        ListView listView = (ListView) findViewById(R.id.list_view_calu_history);
        listView.setAdapter(itemAdapter);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        //返回主页
        if (i == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void initList() {
        DBHelper dbHelper = new DBHelper(CaluHistoryActivity.this);
        SQLiteDatabase db;

        db = dbHelper.getReadableDatabase();
// select count(Id) from Orders where Country = 'China'
        Cursor cursor = db.query("Calculator_History", null, null, null, null, null, null);
        Log.i(TAG, "onClick: 共查询到：" + cursor.getCount() + "条数据\n---------------");
        cursor.move(0);
        while (cursor.moveToNext()) {
            //获得表中值
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String formula = cursor.getString(cursor.getColumnIndex("formula"));
            String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));

            calu_history_item item = new calu_history_item(time, formula);
            Log.i(TAG, "----" + item.getTime() + "-----formula:" + item.getFormula() + "----beizhu:" + beizhu);
            list.add(item);
        }

    }


}
