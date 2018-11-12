package com.pilab.maxu.calculator.calu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pilab.maxu.calculator.calu.calu.CaluActivity;
import com.pilab.maxu.calculator.calu.notebook.NoteBookActivity;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_1);


    }

    public void change(View view) {
        textView = findViewById(R.id.tv_1);
        if (textView.getText() == "sssss") {
            Log.i("myTag", "change: tv_1.text has be changed to " + "hhhhh");
            textView.setText("hhhhh");
        } else {
            textView.setText("sssss");
            Log.i("myTag", "change: tv_1.text has be changed to " + "sssss");

        }

    }

    public void callcalu(View view) {
        Intent intent = new Intent(MainActivity.this, CaluActivity.class);
        startActivity(intent);

    }

    public void WordBook(View view) {
        Intent intent = new Intent(MainActivity.this, NoteBookActivity.class);
        startActivity(intent);

    }

}






