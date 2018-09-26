package com.pilab.maxu.calculator.calu.calu;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pilab.maxu.calculator.calu.R;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CaluActivity extends AppCompatActivity {


    String TAG = "myCalu";
    public String RESULT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_clau);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }


    //    字符串中有几个字符
    private int pattern(String text, String a) {
        int res = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Objects.equals(a, String.valueOf(text.charAt(i)))) {
                res++;
            }
        }
        return res;

    }


    @SuppressLint("ShowToast")
/*  isNum 是不是数字
*  isSign  是不是加减乘除
* */
    private String getAttribute(String a) {
        // 定义 a 的属性
        String attribute = "";
        if (Objects.equals(a, "+") || Objects.equals(a, "-") || Objects.equals(a, "*") || Objects.equals(a, "/")) {
            attribute = "SIGN";
        } else if (Objects.equals(a, "1") || Objects.equals(a, "2") || Objects.equals(a, "3") || Objects.equals(a, "4") || Objects.equals(a, "5") || Objects.equals(a, "6") || Objects.equals(a, "7") || Objects.equals(a, "8") || Objects.equals(a, "9") || Objects.equals(a, "0") || Objects.equals(a, ".")) {
            attribute = "NUM";
        } else if (Objects.equals(a, "del")) {
            attribute = "DEL";
        } else if (Objects.equals(a, "ac")) {
            attribute = "AC";
            // = ac
        } else if (Objects.equals(a, "="))
            attribute = "RES";
        else attribute = "OTHERS";
        return attribute;
    }

    @SuppressLint("SetTextI18n")
    public void changetv(String a) {
        TextView textView = findViewById(R.id.tv_result);
        String temp = textView.getText().toString();
        if (temp.contains("=") && Objects.equals(getAttribute(a), "NUM")) {
            changetv("ac");
        } else {
            String word_now_attr = getAttribute(a);
            if (temp.length() >= 1) {
                String word_bofore = String.valueOf(temp.charAt(temp.length() - 1));
                String word_bofore_attr = getAttribute(word_bofore);
                // 得出结果后不能继续操作
                if (temp.contains("=") && (Objects.equals(getAttribute(a), "SIGN"))) {
                    temp = temp.substring(temp.lastIndexOf("=") + 1);
                    Log.i(TAG, "changetv: temp:" + temp);
                    textView.setText(temp + a);
                    Log.i(TAG, "changetv: temp" + textView.getText().toString());
                    return;
                }
                // 得出结果后不能继续操作
                if (temp.contains("=") && (Objects.equals(getAttribute(a), "RES"))) {
                    temp = temp.substring(temp.lastIndexOf("=") + 1);
                    Log.i(TAG, "changetv: temp:" + temp);
                    textView.setText(temp);
                    Log.i(TAG, "changetv: temp" + textView.getText().toString());
                    return;
                }

                //            不能连续输入多个加减乘除符号

                if (Objects.equals(word_now_attr, "SIGN") && Objects.equals(word_bofore_attr, "SIGN")) {
                    changetv("del");
                    changetv(a);
                    Toast.makeText(CaluActivity.this, "已自动变为最后输入的符号。", Toast.LENGTH_SHORT).show();

                    return;
                }

//            不能连续输入多个等号
                if (Objects.equals(word_now_attr, "RES") && Objects.equals(word_bofore_attr, "RES")) {
                    Toast.makeText(CaluActivity.this, "不能连续输入多个等号。", Toast.LENGTH_SHORT).show();

                    return;
                }

                // 不能连续输入多个点
                //前面不是数字不能输入点
                if (Objects.equals(a, ".")) {

//                获取最后一段
                    int last_index = 0;
                    if (temp.lastIndexOf("+") > last_index) {
                        last_index = temp.lastIndexOf("+");
                    }
                    if (temp.lastIndexOf("-") > last_index) {
                        last_index = temp.lastIndexOf("-");
                    }
                    if (temp.lastIndexOf("*") > last_index) {
                        last_index = temp.lastIndexOf("*");
                    }
                    if (temp.lastIndexOf("/") > last_index) {
                        last_index = temp.lastIndexOf("/");
                    }

                    String last_nums = temp.substring(last_index, temp.length());

                    int point_size = pattern(last_nums, ".");


                    if (point_size >= 1) {
                        Toast.makeText(CaluActivity.this, "在此处不能输入点。", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (Objects.equals(word_bofore, ".")) {
                        Toast.makeText(CaluActivity.this, "不能连续输入多个点。", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!Objects.equals(word_bofore_attr, "NUM")) {
                        Toast.makeText(CaluActivity.this, "在此处不能输入点。", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

            } else {
//            开头不能输入符号
                if (Objects.equals(word_now_attr, "SIGN")) {
                    Toast.makeText(CaluActivity.this, "开头不能输入符号", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

//      对tv的内容进行增加或者删除操作
            String str;
            if (Objects.equals(a, "del")) {
                str = textView.getText().toString();
                try {
                    str = str.substring(0, str.length() - 1);
                } catch (StringIndexOutOfBoundsException e) {
                    Toast.makeText(CaluActivity.this, "ERROR：当前显示内容不可以删减！", Toast.LENGTH_SHORT);
                }
            } else if (Objects.equals(a, "ac")) {
                str = "";
            } else {
                str = textView.getText().toString() + a;
            }
            textView.setText(str);
            Log.i(TAG, "changetv:getResult_list: " + getResult_list());


        }
    }

    private void initView() {

        Button sign_jia = findViewById(R.id.sign_jia);
        Button sign_jian = findViewById(R.id.sign_jian);
        Button sign_cheng = findViewById(R.id.sign_cheng);
        Button sign_chu = findViewById(R.id.sign_chu);
        Button sign_point = findViewById(R.id.sign_point);
        Button sign_result = findViewById(R.id.result);
        Button sign_del = findViewById(R.id.sign_del);

        Button num0 = findViewById(R.id.num0);
        Button num1 = findViewById(R.id.num1);
        Button num2 = findViewById(R.id.num2);
        Button num3 = findViewById(R.id.num3);
        Button num4 = findViewById(R.id.num4);
        Button num5 = findViewById(R.id.num5);
        Button num6 = findViewById(R.id.num6);
        Button num7 = findViewById(R.id.num7);
        Button num8 = findViewById(R.id.num8);
        Button num9 = findViewById(R.id.num9);

        sign_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changetv("+");
            }
        });
        sign_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changetv("-");

            }
        });
        sign_cheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changetv("*");

            }
        });
        sign_chu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changetv("/");

            }
        });
        sign_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.tv_result);
                String temp = textView.getText().toString();
                if (temp.contains("=")) {
                    textView.setText(RESULT);
                } else {
                    try {
                        List<String> res = new ArrayList<>();
                        res = getResult_list();
                        getResult(res);
                        Log.i(TAG, "onClick: temp:" + temp);
                        changetv("=" + RESULT);
                    } catch (NumberFormatException e) {
                        Toast.makeText(CaluActivity.this, "错误！请检查你的输入！", Toast.LENGTH_SHORT).show();
                    }
                }

                DBHelper dbHelper = new DBHelper(CaluActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String TABLE_NAME = "Calculator_History";
                String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key, formula text , time text,beizhu text)";
                db.execSQL(sql);
                ContentValues cv = new ContentValues();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String time_ = df.format(new Date());// new Date()为获取当前系统时间
                cv.put("formula", textView.getText().toString());
                cv.put("time", time_);
                cv.put("beizhu", "");
                db.insert("Calculator_History", null, cv);


//
//                String aa = db.query("Calculator_History", null, null, null, null, null, null).toString();


            }
        });
        sign_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changetv("del");
            }
        });
//        长按效果
        sign_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                changetv("ac");
                RESULT = "";
                return false;
            }
        });
        sign_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changetv(".");
            }
        });

        {
            num0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("0");
                }
            });
            num1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("1");
                }
            });
            num2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("2");
                }
            });
            num3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("3");
                }
            });
            num4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("4");
                }
            });
            num5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("5");
                }
            });
            num6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("6");
                }
            });
            num7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("7");
                }
            });
            num8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("8");
                }
            });
            num9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changetv("9");
                }
            });
        }
    }


    //    右上角按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calu_tb_menu, menu);
        return true;
    }

    //右上角按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        //返回主页
        if (i == android.R.id.home) {
            onBackPressed();
            return true;
//            历史记录
        } else if (i == R.id.menu_calu_history) {
            Intent intent = new Intent(CaluActivity.this, CaluHistoryActivity.class);
            startActivity(intent);
            return true;
//            汇率转化
        } else if (i == R.id.menu_calu_huilv) {
            //待修改'

            Intent intent = new Intent(CaluActivity.this, ExchangeRateActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List getResult_list() {
        TextView textView = findViewById(R.id.tv_result);
        String temp = textView.getText().toString();

        List<String> res = new ArrayList<>();

        int first_index = temp.length() - 1;

        while (temp.contains("+") || temp.contains("-") || temp.contains("*") || temp.contains("/")) {
            first_index = temp.length() - 1;

            //获取到最前面的符号
            if (temp.contains("+")) {
                if (temp.indexOf("+") < first_index) {
                    first_index = temp.indexOf("+");
                }
            }
            if (temp.contains("-")) {
                if (temp.indexOf("-") < first_index) {
                    first_index = temp.indexOf("-");
                }
            }
            if (temp.contains("*")) {
                if (temp.indexOf("*") < first_index) {
                    first_index = temp.indexOf("*");
                }
            }
            if (temp.contains("/")) {
                if (temp.indexOf("/") < first_index) {
                    first_index = temp.indexOf("/");
                }
            }

            String first_nums = temp.substring(0, first_index);

            res.add(first_nums);
            temp = temp.substring(first_nums.length());

            res.add(String.valueOf(temp.charAt(0)));
            temp = temp.substring(1);


        }
        res.add(temp);
//      [1111, /, 2225, *, 59]

        return res;
    }

    public void getResult(List<String> res) {

        int indexof;

        if (res.contains("/")) {
            indexof = res.indexOf("/");
            String temp_result = String.valueOf(Double.parseDouble(res.get(indexof - 1)) / Double.parseDouble(res.get(indexof + 1)));
            res.set(indexof, temp_result);
            res.remove(indexof - 1);
            //元素下标改变了
            res.remove(indexof);
            getResult(res);
        } else if (res.contains("*")) {
            indexof = res.indexOf("*");
            res.set(indexof, String.valueOf(Double.parseDouble(res.get(indexof - 1)) * Double.parseDouble(res.get(indexof + 1))));
            res.remove(indexof - 1);
            res.remove(indexof);
            getResult(res);
        } else if (res.contains("+")) {
            indexof = res.indexOf("+");
            res.set(indexof, String.valueOf(Double.parseDouble(res.get(indexof - 1)) + Double.parseDouble(res.get(indexof + 1))));
            res.remove(indexof - 1);
            res.remove(indexof);
            getResult(res);
        } else if (res.contains("-")) {
            indexof = res.indexOf("-");
            res.set(indexof, String.valueOf(Double.parseDouble(res.get(indexof - 1)) - Double.parseDouble(res.get(indexof + 1))));
            res.remove(indexof - 1);
            res.remove(indexof);
            getResult(res);
        } else
            RESULT = res.get(0);
    }


}





