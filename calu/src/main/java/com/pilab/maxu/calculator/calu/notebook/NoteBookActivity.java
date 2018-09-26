package com.pilab.maxu.calculator.calu.notebook;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pilab.maxu.calculator.calu.R;
import com.pilab.maxu.calculator.calu.calu.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteBookActivity extends AppCompatActivity {
    public String TAG = "notebook";
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_book);


        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_notebook);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        initRCview();

        // 悬浮按钮
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_add_note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                insertNote();
                Intent intent = new Intent(NoteBookActivity.this, NotesDetaliActivity.class);
                startActivity(intent);
//                Toast.makeText(NoteBookActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
                //  刷新界面
                initRCview();

            }
        });


    }

    //初始化rcview
    private void initRCview() {
        //获取rc的id
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_note_list);
//        如果您确定RecyclerView的(item)数目不会改变，您可以添加以下内容来改善性能：
//        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(NoteBookActivity.this);
        rv.setLayoutManager(llm);
//        insertNote();
        initializeData();
        RVAdapter adapter = new RVAdapter(notes);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(NoteBookActivity.this, NotesDetaliActivity.class);
                intent.putExtra("time", notes.get(position).time);
                intent.putExtra("title", notes.get(position).title);
                intent.putExtra("content", notes.get(position).content);
                startActivity(intent);
//                finish();
            }



            @Override
            public void onItemLongClick(View view, final int position) {
                Log.i(TAG, "onItemLongClick: 长摁");
//                Toast.makeText(NoteBookActivity.this, "长摁", Toast.LENGTH_SHORT).show();
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(NoteBookActivity.this);
                builder.setTitle("是否要删除数据？");
                builder.setMessage("您将要删除\ntime:" + notes.get(position).time + "\ncontent:" + notes.get(position).content + "的便签");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NoteBookActivity.this, "取消删除", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeNote(notes.get(position).time, notes.get(position).content);
                        initRCview();
                    }
                });
                builder.show();

            }
        });
        rv.setAdapter(adapter);


    }

    void removeNote(String time, String content) {
        DBHelper dbHelper = new DBHelper(NoteBookActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String sql = "DELETE FROM Notes WHERE time = '" + time + "' AND content='" + content + "';";
        db.execSQL(sql);
        Log.i("tag", "删除成功！！！！！！！！！！！！！！！！！");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notebook_tb_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        initializeData();
        initRCview();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        //返回主页
        if (i == android.R.id.home) {
//            onBackPressed();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    class Note {
        String time;
        String title;
        String content;

        Note(String time, String title, String content) {
            this.time = time;
            this.title = title;
            this.content = content;
        }
    }


    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData() {
        notes = new ArrayList<>();
        NoteDBHelper dbHelper = new NoteDBHelper(NoteBookActivity.this);
        SQLiteDatabase db;

        db = dbHelper.getReadableDatabase();
// select count(Id) from Orders where Country = 'China'
        Cursor cursor = db.query("Notes", null, null, null, null, null, "time DESC");
        Log.i(TAG, "onClick: 共查询到：" + cursor.getCount() + "条数据\n---------------");
        cursor.move(0);
        while (cursor.moveToNext()) {
            //获得表中值
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));

            Note item = new Note(time, title, content);
            Log.i(TAG, "----" + time + "-----title:" + title + "----content:" + content);
            notes.add(item);
        }
        ;


    }


}

