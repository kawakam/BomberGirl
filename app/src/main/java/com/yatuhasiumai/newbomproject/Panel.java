package com.yatuhasiumai.newbomproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;


public class Panel {
    private final int WC = LinearLayout.LayoutParams.WRAP_CONTENT;

    private int panelWidth;
    private int panelHeight;
    public Bitmap playerPanel;
    public Bitmap enemyPanel;
    public Bitmap blockPanel;
    public Bitmap bomPanel;
    public Bitmap blastPanel;
    public Bitmap walkablePanel;

    public Panel(Resources res) {
        //ディスプレイサイズの取得
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        panelWidth = dm.widthPixels / 15;
        panelHeight = dm.heightPixels / 11 - 4;

        //後でリファクタ
        Bitmap tmpBlockPanel = BitmapFactory.decodeResource(res, R.drawable.map_iwa);
        blockPanel = Bitmap.createScaledBitmap(tmpBlockPanel, panelWidth, panelHeight, false);

        Bitmap tmpWalkablePanel = BitmapFactory.decodeResource(res, R.drawable.map_masu);
        walkablePanel = Bitmap.createScaledBitmap(tmpWalkablePanel, panelWidth, panelHeight, false);

        Bitmap tmpEnemyPanel = BitmapFactory.decodeResource(res, R.drawable.ene_top);
        enemyPanel = Bitmap.createScaledBitmap(tmpEnemyPanel, panelWidth, panelHeight, false);

        Bitmap tmpBomPanel = BitmapFactory.decodeResource(res, R.drawable.my_bom);
        bomPanel = Bitmap.createScaledBitmap(tmpBomPanel, panelWidth, panelHeight, false);

        Bitmap tmpBlastPanel = BitmapFactory.decodeResource(res, R.drawable.blast);
        blastPanel = Bitmap.createScaledBitmap(tmpBlastPanel, panelWidth, panelHeight, false);
    }
}
