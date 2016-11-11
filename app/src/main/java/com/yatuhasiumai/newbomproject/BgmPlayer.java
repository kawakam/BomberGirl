package com.yatuhasiumai.newbomproject;

import android.content.Context;
import android.media.MediaPlayer;


public class BgmPlayer {
    private MediaPlayer starMediaPlayer;
    private MediaPlayer gameOverMediaPlayer;
    private MediaPlayer battleMediaPlayer;
    private MediaPlayer resultMediaPlayer;
    private MediaPlayer rankMediaPlayer;

    public BgmPlayer(Context context){
        // ファイルの読み込み
        this.starMediaPlayer = MediaPlayer.create(context, R.raw.re_start_bgm);
        // Loop処理
        this.starMediaPlayer.setLooping(true);
        // 音量設定
        this.starMediaPlayer.setVolume(0.6f, 0.6f);

        this.gameOverMediaPlayer = MediaPlayer.create(context, R.raw.re_game_over);
        this.gameOverMediaPlayer.setLooping(true);
        this.gameOverMediaPlayer.setVolume(0.7f, 0.7f);

        this.battleMediaPlayer = MediaPlayer.create(context, R.raw.re_battle);
        this.battleMediaPlayer.setLooping(true);
        this.battleMediaPlayer.setVolume(0.5f, 0.5f);

        this.resultMediaPlayer = MediaPlayer.create(context, R.raw.clear);
        this.resultMediaPlayer.setLooping(true);
        this.resultMediaPlayer.setVolume(0.7f, 0.7f);

        this.rankMediaPlayer = MediaPlayer.create(context, R.raw.rank);
        this.rankMediaPlayer.setLooping(true);
        this.rankMediaPlayer.setVolume(0.7f, 0.7f);
    }

    //
    public void start() {
        if (!starMediaPlayer.isPlaying()){
            starMediaPlayer.seekTo(0);
            starMediaPlayer.start();
        }
    }

    public void stop() {
        if (starMediaPlayer.isPlaying()){
            starMediaPlayer.stop();
            starMediaPlayer.prepareAsync();
        }
    }

    public void start2() {
        if (!gameOverMediaPlayer.isPlaying()){
            gameOverMediaPlayer.seekTo(0);
            gameOverMediaPlayer.start();
        }
    }

    public void stop2() {
        if (gameOverMediaPlayer.isPlaying()){
            gameOverMediaPlayer.stop();
            gameOverMediaPlayer.prepareAsync();
        }
    }

    public void start3(){
        if (!resultMediaPlayer.isPlaying()){
            resultMediaPlayer.seekTo(0);
            resultMediaPlayer.start();
        }
    }


    public void stop3(){
        if (resultMediaPlayer.isPlaying()){
            resultMediaPlayer.stop();
            resultMediaPlayer.prepareAsync();
        }
    }

    public void battleBgmStart() {
        if (!battleMediaPlayer.isPlaying()){
            battleMediaPlayer.seekTo(0);
            battleMediaPlayer.start();
        }
    }

    public void battleBgmStop() {
        if (battleMediaPlayer.isPlaying()){
            battleMediaPlayer.stop();
            battleMediaPlayer.prepareAsync();
        }
    }

    public void rankBgmStart() {
        if (!rankMediaPlayer.isPlaying()){
            rankMediaPlayer.seekTo(0);
            rankMediaPlayer.start();
        }
    }

    public void rankBgmStop() {
        if (rankMediaPlayer.isPlaying()){
            rankMediaPlayer.stop();
            rankMediaPlayer.prepareAsync();
        }
    }
}
