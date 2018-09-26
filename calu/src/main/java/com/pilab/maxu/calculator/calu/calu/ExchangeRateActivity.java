package com.pilab.maxu.calculator.calu.calu;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pilab.maxu.calculator.calu.R;

import org.json.JSONObject;

import java.net.*;
import java.io.*;
import java.util.Objects;

public class ExchangeRateActivity extends AppCompatActivity {

    private String sp1_selected;
    private String sp2_selected;
    private double now_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        Toolbar toolbar = findViewById(R.id.tb_exchange_rate);
        toolbar.setTitle("汇率换算");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] kindsofmoney = getResources().getStringArray(R.array.kindsofmoney);
                sp1_selected = kindsofmoney[pos];
                try {
                    getRate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] kindsofmoney = getResources().getStringArray(R.array.kindsofmoney);
                sp2_selected = kindsofmoney[pos];
                try {
                    Log.i("````````", "onItemSelected: 开始获取");
                    getRate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        EditText editText = findViewById(R.id.et_1);
        editText.addTextChangedListener(textWatcher);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int i = item.getItemId();
//        //返回主页
//        if (i == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            TextView textView = findViewById(R.id.rate_res);
            EditText editText = findViewById(R.id.et_1);
            String money = editText.getText().toString();

            textView.setText("结果：" + String.valueOf(Double.valueOf(money) * Double.valueOf(now_rate)));
        }
    };


    public void getRate() throws Exception {

//        EditText editText = findViewById(R.id.et_1);
//        editText.setText("");
        String AppKey = "36741";
        String SCUR = sp1_selected.split("---")[0];
        String TCU = sp2_selected.split("---")[0];
        String Sign = "0dc93de2c4d502f67e2823ae00df38ca";
        URL u = new URL("http://api.k780.com/?app=finance.rate&scur=" + SCUR + "&tcur=" + TCU + "&appkey=" + AppKey + "&sign=" + Sign + "&format=json");
        Log.i("url", "getRate: " + u);
        InputStream in = u.openStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte buf[] = new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        byte b[] = out.toByteArray();

        String res_json = new String(b, "utf-8");
        Log.i("····························", "getRate: 实时汇率:" + new String(b, "utf-8"));


        JSONObject jsonObject = new JSONObject(res_json);
        Log.i("····························", "getRate: 实时汇率:" + jsonObject.toString());

        if (Objects.equals(jsonObject.getString("success"), "1")) {
            Toast.makeText(ExchangeRateActivity.this, "查询汇率成功！", Toast.LENGTH_SHORT).show();
            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("result"));
            now_rate = Double.valueOf(jsonObject1.getString("rate"));


        } else {
            Toast.makeText(ExchangeRateActivity.this, "查询概率失败！", Toast.LENGTH_SHORT).show();
            now_rate = 0;

        }

//        Log.i("mytag", "getRate: " + jsonObject.getString("result"));
//        now_rate = Double.valueOf(jsonObject.getString("rate"));

        TextView textView = findViewById(R.id.now_rate);
//        Log.i("hahahsdhhashdhad", "getRate: 实时汇率:" + new String(b, "utf-8"));

//        now_rate = Double.valueOf(new String(b, "utf-8"));
        textView.setText("实时汇率:" + String.valueOf(now_rate));
//        Log.i("hahahsdhhashdhad", "getRate: 实时汇率:" + new String(b, "utf-8"));

    }


}
