package com.yatuhasiumai.newbomproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

public class BattleView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public Activity activity; //アクティビティ
    private SurfaceHolder holder; //サーフェイスホルダー
    private Thread thread; //スレッド

    //座標系
    private int playerX;
    private int playerY;
    private int enemyX;
    private int enemyY;
    private int bomX;
    private int bomY;

    //カウンタ系
    private int enemyMoveCount;
    public int bomCount;
    public int scoreCount;

    //フラグ
    private boolean finishFlag;

    //Panel系
    private Panel panel;
    private int panelWidth;
    private int panelHeight;

    private Placement[][] placement;

    public BattleView(Context context) {
        super(context);

        //スコアカウント初期化
        scoreCount = 0;

        //フラグ初期化
        finishFlag = false;

        //プレイヤー初期位置
        playerX = 3;
        playerY = 5;

        //敵初期位置
        enemyX = 11;
        enemyY = 5;

        //ボム初期位置(画面外)
        bomX = 13;
        bomY = 13;

        //ディスプレイサイズの取得
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        panelWidth = dm.widthPixels / 15;
        panelHeight = dm.heightPixels / 11 - 4;

        //サーフェイスホルダーの生成
        holder = getHolder();
        holder.addCallback(this);

        placement = new Placement[16][16];

        //placement配列に中身を入れる
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                placement[i][j] = new Placement(panelWidth * i, panelHeight * j);
            }
        }

        panel = new Panel(getResources());
    }

    //サーフェイスの生成
    public void surfaceCreated(SurfaceHolder holder) {
        //スレッドの開始
        thread = new Thread(this);
        thread.start();
    }

    //サーフェイスの変更
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    }

    //サーフェイスの破棄
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    //ループ処理
    public void run() {
        while (thread != null) {
            long nextTime = System.currentTimeMillis() + 30;
            onTick();

            try {
                Thread.sleep(nextTime - System.currentTimeMillis());
            } catch (Exception e) {
            }
        }
    }

    //定期処理
    private void onTick() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;
        canvas.drawColor(Color.WHITE);

        //スコアカウント増加
        scoreCount += 1;

        //フィールドの描画
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                if ((i == 0) || (i == 14) || (j == 0) || (j == 10)) {
                    canvas.drawBitmap(panel.blockPanel, placement[i][j].x, placement[i][j].y, null);
                } else if (i % 2 == 1) {
                    canvas.drawBitmap(panel.walkablePanel, placement[i][j].x, placement[i][j].y, null);
                } else if (i % 2 == 0 && j % 2 == 1) {
                    canvas.drawBitmap(panel.walkablePanel, placement[i][j].x, placement[i][j].y, null);
                } else {
                    canvas.drawBitmap(panel.blockPanel, placement[i][j].x, placement[i][j].y, null);
                }
            }
        }

        //プレイヤーの描画
        canvas.drawBitmap(playerController().playerPanel1, playerController().x, playerController().y, null);

        //敵の描画
        canvas.drawBitmap(enemyController().enemyPanel, enemyController().x, enemyController().y, null);

        //ボムの描画
        canvas.drawBitmap(bomController().bomPanel, bomController().x, bomController().y, null);

        //ボム
        if(bomCount == 3) {
            //左上
            if((bomX == 1) && (bomY == 1)) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX + 1][bomX + 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY + 1][bomY + 1].y, null);
                judgeBom();
                //右上
            } else if((bomX == 13) && (bomY == 1)) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX - 1][bomX - 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY + 1][bomY + 1].y, null);
                judgeBom();
                //左下
            } else if((bomX == 1) && (bomY == 9)) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX + 1][bomX + 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY - 1][bomY - 1].y, null);
                judgeBom();
                //右下
            } else if((bomX == 13) && (bomY == 9)) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX - 1][bomX - 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY - 1][bomY - 1].y, null);
                judgeBom();
                //左端(ブロックなし)
            } else if((bomX == 1) && (bomY % 2 != 0)) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX + 1][bomX + 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY + 1][bomY + 1].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY - 1][bomY - 1].y, null);
                judgeBom();
                //左端(ブロックあり)
            } else if(bomX == 1) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY + 1][bomY + 1].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY - 1][bomY - 1].y, null);
                judgeBom();
                //右端(ブロックなし)
            } else if((bomX == 13) && (bomY % 2 != 0)) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX - 1][bomX - 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY + 1][bomY + 1].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY - 1][bomY - 1].y, null);
                judgeBom();
                //右端(ブロックあり)
            } else if(bomX == 13) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY + 1][bomY + 1].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY - 1][bomY - 1].y, null);
                judgeBom();
                //上端(ブロックなし)
            } else if((bomY == 1) && (enemyX % 2 != 0)) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX + 1][bomX + 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX - 1][bomX - 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY + 1][bomY + 1].y, null);
                judgeBom();
                //上端(ブロックあり)
            } else if(bomY == 1) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX + 1][bomX + 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX - 1][bomX - 1].x, placement[bomY][bomY].y, null);
                judgeBom();
                //下端(ブロックなし)
            } else if((bomY == 9) && (bomX % 2 != 0)) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX + 1][bomX + 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX - 1][bomX - 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY - 1][bomY - 1].y, null);
                judgeBom();
                //下端(ブロックあり)
            } else if(bomY == 9) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX + 1][bomX + 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX - 1][bomX - 1].x, placement[bomY][bomY].y, null);
                judgeBom();
                //上と下にブロック
            } else if(bomX % 2 == 0) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX + 1][bomX + 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX - 1][bomX - 1].x, placement[bomY][bomY].y, null);
                judgeBom();
                //右と左にブロック
            } else if(bomY % 2 == 0) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY + 1][bomY + 1].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY - 1][bomY - 1].y, null);
                judgeBom();
                //自由
            } else if((bomX % 2 != 0) && (bomY % 2 != 0)) {
                Blast blast = new Blast(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX + 1][bomX + 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX - 1][bomX - 1].x, placement[bomY][bomY].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY + 1][bomY + 1].y, null);
                canvas.drawBitmap(blast.blastPanel, placement[bomX][bomX].x, placement[bomY - 1][bomY - 1].y, null);
                judgeBom();
            }
        }

        //ゲームオーバー処理
        if(finishFlag == true) {
            //何もしない
        } else if((playerX == enemyX) && (playerY == enemyY)) {
            finishFlag = true;
            //遷移
            transitionGameOver();
        //後でリファクタ
        //敵の移動
        } else if(enemyMoveCount == 6) {
            //左上の場合
            if((enemyX == 1) && (enemyY == 1)) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch(n) {
                    case 0: enemyX += 1;
                        break;
                    case 1: enemyY += 1;
                        break;
                }
            //右上の場合
            } else if((enemyX == 13) && (enemyY == 1)) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch(n) {
                    case 0: enemyX -= 1;
                        break;
                    case 1: enemyY += 1;
                        break;
                }
            //左下の場合
            } else if((enemyX == 1) && (enemyY == 9)) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch(n) {
                    case 0: enemyX += 1;
                        break;
                    case 1: enemyY -= 1;
                        break;
                }
            //右下の場合
            } else if((enemyX == 13) && (enemyY == 9)) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch(n) {
                    case 0: enemyX -= 1;
                        break;
                    case 1: enemyY -= 1;
                        break;
                }
            //左端(ブロックなし)
            } else if((enemyX == 1) && (enemyY % 2 != 0)) {
                Random r = new Random();
                int n = r.nextInt(3);
                switch(n) {
                    case 0: enemyY += 1;
                        break;
                    case 1: enemyY -= 1;
                        break;
                    case 2: enemyX += 1;
                }
            //左端(ブロックあり)
            } else if(enemyX == 1) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch(n) {
                    case 0: enemyY += 1;
                        break;
                    case 1: enemyY -= 1;
                        break;
                }
            //右端(ブロックなし)
            } else if((enemyX == 13) && (enemyY % 2 != 0)) {
                Random r = new Random();
                int n = r.nextInt(3);
                switch(n) {
                    case 0: enemyY += 1;
                        break;
                    case 1: enemyY -= 1;
                        break;
                    case 2: enemyX -= 1;
                }
            //右端(ブロックあり)
            } else if(enemyX == 13) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch(n) {
                    case 0: enemyY += 1;
                        break;
                    case 1: enemyY -= 1;
                        break;
                }
            //上端(ブロックなし)
            } else if((enemyY == 1) && (enemyX % 2 != 0)) {
                Random r = new Random();
                int n = r.nextInt(3);
                switch(n) {
                    case 0: enemyX += 1;
                        break;
                    case 1: enemyX -= 1;
                        break;
                    case 2: enemyY += 1;
                        break;
                }
            //上端(ブロックあり)
            } else if(enemyY == 1) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch(n) {
                    case 0: enemyX += 1;
                        break;
                    case 1: enemyX -= 1;
                        break;
                }
            //下端(ブロックなし)
            } else if((enemyY == 9) && (enemyX % 2 != 0)) {
                Random r = new Random();
                int n = r.nextInt(3);
                switch(n) {
                    case 0: enemyX += 1;
                        break;
                    case 1: enemyX -= 1;
                        break;
                    case 2: enemyY -= 1;
                        break;
                }
            //下端(ブロックあり)
            } else if(enemyY == 9) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch (n) {
                    case 0:
                        enemyX += 1;
                        break;
                    case 1:
                        enemyX -= 1;
                        break;
                }
            //上と下にブロック
            } else if(enemyX % 2 == 0) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch (n) {
                    case 0:
                        enemyX += 1;
                        break;
                    case 1:
                        enemyX -= 1;
                        break;
                }
            //右と左にブロック
            } else if(enemyY % 2 == 0) {
                Random r = new Random();
                int n = r.nextInt(2);
                switch (n) {
                    case 0:
                        enemyY += 1;
                        break;
                    case 1:
                        enemyY -= 1;
                        break;
                }
            //自由
            } else if((1 < enemyX && enemyX < 13) && (1 < enemyY && enemyY < 9)) {
                Random r = new Random();
                int n = r.nextInt(4);
                switch(n) {
                    case 0: enemyX += 1;
                        break;
                    case 1: enemyX -= 1;
                        break;
                    case 2: enemyY += 1;
                        break;
                    case 3: enemyY -= 1;
                        break;
                }
            }

            //移動後のenemyMoveCountの初期化
            enemyMoveCount = 0;

            //BomCountの増加
            bomCount += 1;
        }

        //enemyMoveCountの増加
        enemyMoveCount += 1;

        holder.unlockCanvasAndPost(canvas);
    }

    private Player playerController() {
        Player player = new Player(getResources(), placement[playerX][playerX].x, placement[playerY][playerY].y);
        return player;
    }

    private Enemy enemyController() {
        Enemy enemy = new Enemy(getResources(), placement[enemyX][enemyX].x, placement[enemyY][enemyY].y);
        return enemy;
    }

    private Bom bomController() {
        Bom bom = new Bom(getResources(), placement[bomX][bomX].x, placement[bomY][bomY].y);
        return bom;
    }

    //プライヤー操作
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();

        switch (action&MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // 上ボタン
                if((140 < e.getX() && e.getX() < 240) && (696 < e.getY() && e.getY() < 782)) {
                    if((1 < playerY) && (playerX % 2 != 0)) {
                        playerY -= 1;
                    }
                //右ボタン
                } else if((240 < e.getX() && e.getX() < 370) && (782 < e.getY() && e.getY() < 908)) {
                    if((playerX < 13) && (playerY % 2 != 0)) {
                        playerX += 1;
                    }
                //下ボタン
                } else if((140 < e.getX() && e.getX() < 240) && (908 < e.getY() && e.getY() < 1044)) {
                    if((playerY < 9) && (playerX % 2 != 0)) {
                        playerY += 1;
                    }
                //左ボタン
                } else if((40 < e.getX() && e.getX() < 140) && (782 < e.getY() && e.getY() < 908)) {
                    if((1 < playerX) && (playerY % 2 != 0)) {
                        playerX -= 1;
                    }
                //ボムボタン
                } else if((1600 < e.getX() && e.getX() < 1800) && (782 < e.getY() && e.getY() < 1044)) {
                    if(bomX == 13) {
                        bomCount = 0;
                        bomX = playerX;
                        bomY = playerY;
                    }
                }
            break;
        }
        return true;
    }

    //ゲームオーバー遷移
    public void transitionGameOver() {
        Intent intent = new Intent(activity, GameOver.class);
        activity.startActivity(intent);
        activity.finish();
    }

    //ゲームクリア遷移
    public void transitionGameClear() {
        Intent intent = new Intent(activity, Result.class);
        intent.putExtra("score", scoreCount);
        activity.startActivity(intent);
        activity.finish();
    }

    private void judgeBom() {
        //爆風で自滅
        if ((playerX == bomX) && (playerY == bomY) ||
                (playerX == bomX - 1) && (playerY == bomY) ||
                (playerX + 1 == bomX) && (playerY == bomY) ||
                (playerX == bomX) && (playerY == bomY - 1) ||
                (playerX == bomX) && (playerY == bomY + 1)) {
            //ゲームオーバー遷移
            transitionGameOver();
            finishFlag = true;
        //敵撃破
        } else if ((enemyX == bomX) && (enemyY == bomY) ||
                (enemyX == bomX - 1) && (enemyY == bomY) ||
                (enemyX + 1 == bomX) && (enemyY == bomY) ||
                (enemyX == bomX) && (enemyY == bomY - 1) ||
                (enemyX == bomX) && (enemyY == bomY + 1)) {
            //ゲームクリア遷移
            transitionGameClear();
            finishFlag = true;
        }
        //ボム位置&カウンタ初期化
        bomCount = 0;
        bomX = 13;
        bomY = 13;
    }
}
