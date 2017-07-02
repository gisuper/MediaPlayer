package com.yangxiong.gisuper.mideaplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yangxiong.gisuper.mideaplayer.global.LogUtil;
import com.yangxiong.gisuper.mideaplayer.global.Util;
import com.yangxiong.gisuper.mideaplayer.model.StorageDataManager;
import com.yangxiong.gisuper.mideaplayer.model.VideoBean;

import media.IjkVideoView;
import media.PlayerManager;

public class VideoShowActivity extends AppCompatActivity {
    private static final String TAG = "VideoShowActivity";
    private static final int MSG_REFRESH_CURRENT_POSITION = 1;

    private PlayerManager mPlayerManager;
    private int position;
    private SparseArray<VideoBean> videoList;
    private TextView tvPosition;

    private Handler mHandler = new Handler( ) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REFRESH_CURRENT_POSITION:
                    setCurPosition( );
                    if (mPlayerManager.isPlaying( )) {
                        removeMessages(MSG_REFRESH_CURRENT_POSITION);
                        sendEmptyMessageDelayed(MSG_REFRESH_CURRENT_POSITION, 1000);
                    }
                    break;
            }
        }
    };
    private SeekBar sbProgress;
    private IjkVideoView vvVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show);
        tvPosition = (TextView) findViewById(R.id.tv_position);
        sbProgress = (SeekBar) findViewById(R.id.sb_progress);
        vvVideoView = (IjkVideoView) findViewById(R.id.video_view);
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener( ) {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtil.d(TAG,"progress:"+progress +"is fromUser :"+fromUser);
                if (fromUser) {
                    vvVideoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mPlayerManager.gestureDetector.onTouchEvent(event))
            return true;
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart( );
        position = getIntent( ).getIntExtra("position", -1);

        initPlayer( );
    }

    private void initPlayer() {
        mPlayerManager = new PlayerManager(this);
        mPlayerManager.setFullScreenOnly(true);
        mPlayerManager.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
        mPlayerManager.playInFullScreen(true);
        mPlayerManager.setPlayerStateListener(new PlayerManager.PlayerStateListener( ) {
            @Override
            public void onComplete() {
                LogUtil.d(TAG, "VideoShowActivity~~~");
                position++;
                position = position > (videoList.size( ) - 1) ? 0 : position;
                String uri = StorageDataManager.getInstance(VideoShowActivity.this)
                        .getVideoList( ).get(position).path;
                mPlayerManager.play(uri);

            }

            @Override
            public void onError() {
                LogUtil.d(TAG, "onError~~~");
            }

            @Override
            public void onLoading() {
                LogUtil.d(TAG, "onLoading~~~");
            }

            @Override
            public void onPlay() {
                LogUtil.d(TAG, "onPlay~~~");
                mHandler.removeMessages(MSG_REFRESH_CURRENT_POSITION);
                mHandler.sendEmptyMessage(MSG_REFRESH_CURRENT_POSITION);
            }
        });

        videoList = StorageDataManager.getInstance(VideoShowActivity.this)
                .getVideoList( );
        String uri = videoList.get(position).path;
        mPlayerManager.play(uri);

    }

    private void setCurPosition() {
        String currentPosition = Util.makeTimeString(mPlayerManager.getCurrentPosition( ))
                + "/" + Util.makeTimeString(mPlayerManager.getDuration( ));
        tvPosition.setText(currentPosition);

        sbProgress.setProgress(mPlayerManager.getCurrentPosition( ));
        sbProgress.setMax(mPlayerManager.getDuration( ));
    }


}
