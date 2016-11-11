package com.yatuhasiumai.newbomproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

public class Player {
    public Resources res;
    public Bitmap playerPanel1;
    private int panelWidth;
    private int panelHeight;
    public int x;
    public int y;

    Player(Resources res, int x, int y) {
        this.res = res;

        //ディスプレイサイズの取得
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        panelWidth = dm.widthPixels / 15;
        panelHeight = dm.heightPixels / 11 - 4;

        this.x = x;
        this.y = y;

        Bitmap tmpPlayerPanel1 = BitmapFactory.decodeResource(res, R.drawable.alice2);
        playerPanel1 = Bitmap.createScaledBitmap(tmpPlayerPanel1, panelWidth, panelHeight, false);
    }
}
