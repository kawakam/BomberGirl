package com.yatuhasiumai.newbomproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Result extends ActionBarActivity {
    private BgmPlayer bgm;
    private TextView scoreView;
    public int score;
    public int record1;
    public int record2;
    public int record3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.result);
        this.bgm = new BgmPlayer(this);

        Intent intent = getIntent();
        score = (1000 - intent.getIntExtra("score", 0) * 5);

        scoreView = (TextView)findViewById(R.id.scoreTextView);
        scoreView.setText(String.valueOf(score));

        //Preferenceへのアクセス
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        //スコアの更新
        record1 = pref.getInt("score1", 0);
        record2 = pref.getInt("score2", 0);
        record3 = pref.getInt("score3", 0);

        if(record1 < score) {
            record3 = record2;
            record2 = record1;
            record1 = score;
        } else if(record2 < score) {
            record3 = record2;
            record2 = score;
        } else if(record3 < score) {
            record3 = score;
        }

        //Preferenceの保存
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("score1", record1);
        editor.putInt("score2", record2);
        editor.putInt("score3", record3);

        editor.commit();
    }

    public void onStartMenu(View view){
        Intent intent = new Intent(this,Start.class);
        startActivity(intent);
        finish();
    }

    public void onRank(View view){
        Intent intent = new Intent(this,Rank.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
        bgm.start3();
    }

    @Override
    protected void onPause(){
        super.onPause();
        bgm.stop3();
    }
}