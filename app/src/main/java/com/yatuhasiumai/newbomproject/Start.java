package com.yatuhasiumai.newbomproject;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;

public class Start extends ActionBarActivity {
    private BgmPlayer bgm;
    private SoundPool soundPool;

    private int start_se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        this.bgm = new BgmPlayer(this);


        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        start_se = soundPool.load(this, R.raw.gamestart_moe, 1);
    }
    public void onStart(View view){
        soundPool.play(start_se, 1F, 1F, 0, 0, 1F);
        Intent intent = new Intent(this,MainActivity.class);
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

        bgm.start();
    }

    @Override
    protected void onPause(){
        super.onPause();

        bgm.stop();
    }
}
