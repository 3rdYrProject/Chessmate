package com.example.araic.tapley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage1(View view){
        Intent intent = new Intent(this, ButtonActivity.class);
        intent.putExtra("test", 1);
        startActivity(intent);
    }
    public void sendMessage2(View view){
        Intent intent = new Intent(this, ButtonActivity.class);
        intent.putExtra("test", 2);
        startActivity(intent);
    }
    public void sendMessage3(View view){
        Intent intent = new Intent(this, ButtonActivity.class);
        intent.putExtra("test", 3);
        startActivity(intent);
    }
    public void sendMessage4(View view){
        Intent intent = new Intent(this, ButtonActivity.class);
        intent.putExtra("test", 4);
        startActivity(intent);
    }
}