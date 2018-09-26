package com.pilab.maxu.calculator.calu.notebook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pilab.maxu.calculator.calu.R;
import com.pilab.maxu.calculator.calu.calu.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import us.feras.mdv.MarkdownView;

public class NotesDetaliActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private String time;
    private String content;
    private String time_b;
    private String content_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_detali);
        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        content = intent.getStringExtra("content");
        time_b = time;
        content_b = content;
        removeNote(time_b, content_b);

        final EditText editText = findViewById(R.id.et_note_detail);
        editText.setText(content);
        final MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdown(content);

        initMarkDownView();
        initButton();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_notebook_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
//        final EditText et_note_detail = findViewById(R.id.et_note_detail);
//        insertNote(et_note_detail.getText().toString(), et_note_detail.getText().toString());
//        Toast.makeText(NotesDetaliActivity.this, "文件已经保存", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        final EditText et_note_detail = findViewById(R.id.et_note_detail);
        removeNote(time_b, content_b);
        insertNote(et_note_detail.getText().toString(), et_note_detail.getText().toString());
        Toast.makeText(NotesDetaliActivity.this, "文件已经保存", Toast.LENGTH_SHORT).show();
        super.onPause();
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

    public void insertNote(String title, String content) {
        DBHelper dbHelper = new DBHelper(NotesDetaliActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time1 = df.format(new Date());// new Date()为获取当前系统时间

        cv.put("title", title);
        cv.put("time", time1);
        cv.put("content", content);
        db.insert("Notes", null, cv);
        Log.i("tag", "insertNote: 插入成功！！！！！！！！！！！！！！！！！");
    }

    void removeNote(String time, String content) {
        DBHelper dbHelper = new DBHelper(NotesDetaliActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String sql = "DELETE FROM Notes WHERE time = '" + time + "' AND content='" + content + "';";
        db.execSQL(sql);
//        Toast.makeText(NotesDetaliActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    private void initButton() {
        final ImageButton bt_note_md_manager = findViewById(R.id.bt_note_md_manager);
        final EditText et_note_detail = findViewById(R.id.et_note_detail);
        final MarkdownView markdownView = findViewById(R.id.markdownView);
        Button bt_note_jinghao = findViewById(R.id.bt_note_jinghao);
        Button bt_note_daima = findViewById(R.id.bt_note_daima);
        Button bt_note_tanhao = findViewById(R.id.bt_note_tanhao);
        Button bt_note_xiaokuohao = findViewById(R.id.bt_note_xiaokuohao);
        Button bt_note_xinghao = findViewById(R.id.bt_note_xinghao);
        Button bt_note_you = findViewById(R.id.bt_note_you);
        Button bt_note_zhongkuohao = findViewById(R.id.bt_note_zhongkuohao);

        bt_note_md_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("tagggg", "onClick: " + markdownView.getHeight());
                if (markdownView.getHeight() > 10) {
                    markdownView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0f));
                    et_note_detail.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 10f));
                    bt_note_md_manager.setImageResource(R.drawable.ic_action_name_up);
                } else {
                    markdownView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 5f));
                    et_note_detail.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 5f));
                    bt_note_md_manager.setImageResource(R.drawable.ic_action_name);
                }
            }
        });
        bt_note_daima.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int index = et_note_detail.getSelectionStart();//获取光标所在位置
                Editable edit = et_note_detail.getEditableText();//获取EditText的文字
                if (index < 0 || index >= edit.length()) {
                    edit.append("```\n\n```");
                } else {
                    edit.insert(index, "```\n\n```");//光标所在位置插入文字
                }
                et_note_detail.setSelection(index + 4);
            }
        });
        bt_note_jinghao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int index = et_note_detail.getSelectionStart();//获取光标所在位置
                Editable edit = et_note_detail.getEditableText();//获取EditText的文字
                if (index < 0 || index >= edit.length()) {
                    edit.append("#");
                } else {
                    edit.insert(index, "#");//光标所在位置插入文字
                }
                et_note_detail.setSelection(index + 1);

            }
        });
        bt_note_tanhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int index = et_note_detail.getSelectionStart();//获取光标所在位置
                Editable edit = et_note_detail.getEditableText();//获取EditText的文字
                if (index < 0 || index >= edit.length()) {
                    edit.append("!");
                } else {
                    edit.insert(index, "!");//光标所在位置插入文字
                }
                et_note_detail.setSelection(index + 1);

            }
        });
        bt_note_xiaokuohao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int index = et_note_detail.getSelectionStart();//获取光标所在位置
                Editable edit = et_note_detail.getEditableText();//获取EditText的文字
                if (index < 0 || index >= edit.length()) {
                    edit.append("()");
                } else {
                    edit.insert(index, "()");//光标所在位置插入文字
                }
                et_note_detail.setSelection(index + 1);

            }
        });
        bt_note_zhongkuohao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int index = et_note_detail.getSelectionStart();//获取光标所在位置
                Editable edit = et_note_detail.getEditableText();//获取EditText的文字
                if (index < 0 || index >= edit.length()) {
                    edit.append("[]");
                } else {
                    edit.insert(index, "[]");//光标所在位置插入文字
                }
                et_note_detail.setSelection(index + 1);

            }
        });
        bt_note_you.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int index = et_note_detail.getSelectionStart();//获取光标所在位置
                Editable edit = et_note_detail.getEditableText();//获取EditText的文字
                if (index < 0 || index >= edit.length()) {
                    edit.append(">");
                } else {
                    edit.insert(index, ">");//光标所在位置插入文字
                }
                et_note_detail.setSelection(index + 1);

            }
        });
        bt_note_xinghao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int index = et_note_detail.getSelectionStart();//获取光标所在位置
                Editable edit = et_note_detail.getEditableText();//获取EditText的文字
                if (index < 0 || index >= edit.length()) {
                    edit.append("*");
                } else {
                    edit.insert(index, "*");//光标所在位置插入文字
                }
                et_note_detail.setSelection(index + 1);

            }
        });
    }

    private void initMarkDownView() {
        final MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        final EditText editText = findViewById(R.id.et_note_detail);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                markdownView.loadMarkdown(editText.getText().toString());
            }
        });
    }
}
