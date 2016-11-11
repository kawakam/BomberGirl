package com.yatuhasiumai.newbomproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

public class Blast {
    public Bitmap blastPanel;
    private int panelWidth;
    private int panelHeight;
    public int x;
    public int y;

    Blast(Resources res, int x, int y) {
        //ディスプレイサイズの取得
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        panelWidth = dm.widthPixels / 15;
        panelHeight = dm.heightPixels / 11 - 4;

        this.x = x;
        this.y = y;

        Bitmap tmpBlastPanel= BitmapFactory.decodeResource(res, R.drawable.blast);
        blastPanel = Bitmap.createScaledBitmap(tmpBlastPanel, panelWidth, panelHeight, false);
    }
}
