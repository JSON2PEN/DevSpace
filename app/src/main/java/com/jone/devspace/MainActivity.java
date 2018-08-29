package com.jone.devspace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.json.basewebview.Utils.MyToast;
import com.json.basewebview.widgets.CustomToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvHello=findViewById(R.id.tv_hello);
        tvHello.setOnClickListener((view)->{
            CustomToast.showToast("执行了");
        });
    }
}
