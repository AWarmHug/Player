package com.warm.player;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.warm.playerlib.custom.JustBasePlayController;
import com.warm.playerlib.just.JustVideoPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LIVE_URL = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
    //    public static final String LIVE_URL="http://zv.3gv.ifeng.com/live/zhongwen800k.m3u8";
    private JustVideoPlayer videoView;
    private Button danma, scale;
    private List<Integer> scaleType;
    private int i;

    private DanmakuContext mDanmaContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        videoView = (JustVideoPlayer) findViewById(R.id.videoView);
        danma = (Button) findViewById(R.id.danma);
        scale = (Button) findViewById(R.id.scale);

        scaleType = new ArrayList<>();
        scaleType.add(JustVideoPlayer.SCALE_WRAP_CONTENT);
        scaleType.add(JustVideoPlayer.SCALE_MATCH_PARENT);
        scaleType.add(JustVideoPlayer.SCALE_4_3);
        scaleType.add(JustVideoPlayer.SCALE_16_9);

        initDanma();


        final JustBasePlayController controller = new JustBasePlayController(this);
        controller.setLive(false);
        videoView.setDataSource(LIVE_URL)
//                .setAutoRotation()
                .setDanma(mDanmaContext, new BaseDanmakuParser() {
                    @Override
                    protected IDanmakus parse() {
                        return new Danmakus();
                    }
                })
                .addController(controller);

        danma.setOnClickListener(this);
        scale.setOnClickListener(this);
    }

    /**
     * 初始化弹幕
     */
    private void initDanma() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mDanmaContext = DanmakuContext.create();
        mDanmaContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
//                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair).setDanmakuMargin(40);
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }


    @Override
    public void onBackPressed() {
        if (videoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.release();

    }


    private BaseDanmaku createDanmaku(boolean islive, String txt) {
        BaseDanmaku danmaku = mDanmaContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);

        danmaku.text = txt;
        danmaku.padding = 5;
        danmaku.priority = 0;
        danmaku.isLive = islive;
        danmaku.textSize = 32;
        danmaku.textColor = Color.WHITE;
        return danmaku;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.danma:
                videoView.addDanma(createDanmaku(true, "弹幕~~~~~~~~"));
                break;
            case R.id.scale:
                if (i == 4) i = 0;
                videoView.setScaleType(scaleType.get(i));
                i++;
                break;
        }
    }
}
