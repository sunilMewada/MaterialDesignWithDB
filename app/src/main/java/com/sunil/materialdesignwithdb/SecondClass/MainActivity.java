package com.sunil.materialdesignwithdb.SecondClass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sunil.materialdesignwithdb.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Thread object  = new Thread(){
            public void run(){
                super.run();
                try {
                    Thread.sleep(3000);
                    Intent h = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(h);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        object.start();
    }
}
