package com.yatuhasiumai.newbomproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private final int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
    BattleView battleView;
    private Bitmap crossKey;
    private Bitmap bomButton;

    public BgmPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //プレイヤーの初期化
        this.bgm = new BgmPlayer(this);

        //BattleViewのInstance生成
        battleView = new BattleView(this);

        //BattleViewへActivityのInstanceを受け渡し
        battleView.activity = this;

        //十字キー表示の処理
        Resources res = getResources();
        ImageView crossKeyImageView = new ImageView(this);
        Bitmap tmpCrossKeyImage = BitmapFactory.decodeResource(res, R.drawable.cross_key);
        crossKey = Bitmap.createScaledBitmap(tmpCrossKeyImage, 200, 200, false);
        crossKeyImageView.setAlpha(0.7f);
        crossKeyImageView.setImageBitmap(crossKey);

        //ボムボタン表示の処理
        ImageView bomButtonImageView = new ImageView(this);
        Bitmap tmpBomImageView = BitmapFactory.decodeResource(res, R.drawable.bom);
        bomButton = Bitmap.createScaledBitmap(tmpBomImageView, 200, 200, false);
        bomButtonImageView.setAlpha(0.7f);
        bomButtonImageView.setImageBitmap(bomButton);


        //FrameLayoutのInstance生成
        FrameLayout frameLayout = new FrameLayout(this);
        //十字キー座標
        FrameLayout.LayoutParams crossKeyLayoutParams = new FrameLayout.LayoutParams(300, 300);
        crossKeyLayoutParams.setMargins(60, 700, 0, 0);

        //ボムボタン座標
        FrameLayout.LayoutParams bomButtonLayoutParams = new FrameLayout.LayoutParams(300, 300);
        bomButtonLayoutParams.setMargins(1600, 700, 0, 0);

        frameLayout.addView(battleView, new LinearLayout.LayoutParams(WC, WC));
        frameLayout.addView(crossKeyImageView, crossKeyLayoutParams);
        frameLayout.addView(bomButtonImageView, bomButtonLayoutParams);

        setContentView(frameLayout);
    }

    // bgm再生
    @Override
    protected void onResume() {
        super.onResume();
        bgm.battleBgmStart();
    }

    // bgm停止
    @Override
    protected void onPause() {
        super.onPause();
        bgm.battleBgmStop();
    }

}
