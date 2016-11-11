package com.yatuhasiumai.newbomproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Rank extends ActionBarActivity {
    private BgmPlayer bgm;
    private int record1;
    private int record2;
    private int record3;

    private TextView rankView1;
    private TextView rankView2;
    private TextView rankView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rank);
        this.bgm = new BgmPlayer(this);

        //Preferenceの読み込み
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        record1 = pref.getInt("score1", 0);
        record2 = pref.getInt("score2", 0);
        record3 = pref.getInt("score3", 0);

        rankView1 = (TextView)findViewById(R.id.rankView1);
        rankView1.setText("1.    " + String.valueOf(record1));

        rankView2 = (TextView)findViewById(R.id.rankView2);
        rankView2.setText("2.    " + String.valueOf(record2));

        rankView3 = (TextView)findViewById(R.id.rankView3);
        rankView3.setText("3.    " + String.valueOf(record3));

    }
    public void onStartMenu(View view){
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
        bgm.rankBgmStart();
    }

    @Override
    protected void onPause(){
        super.onPause();
        bgm.rankBgmStop();
    }
}
