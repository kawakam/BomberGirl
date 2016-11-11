package com.yatuhasiumai.newbomproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

public class Bom {
    public Bitmap bomPanel;
    private int panelWidth;
    private int panelHeight;
    public int x;
    public int y;

    Bom(Resources res, int x, int y) {
        //ディスプレイサイズの取得
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        panelWidth = dm.widthPixels / 15;
        panelHeight = dm.heightPixels / 11 - 4;

        this.x = x;
        this.y = y;

        Bitmap tmpBomPanel= BitmapFactory.decodeResource(res, R.drawable.my_bom);
        bomPanel = Bitmap.createScaledBitmap(tmpBomPanel, panelWidth, panelHeight, false);
    }
}
