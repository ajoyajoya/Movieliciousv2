package com.ajoyajoya.movieliciousv2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;

public class PopupVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener {

    private static final String API_KEY = "AIzaSyBTdqc4-XcjiHjR-cCZMX5cSaWKwzp1vZk";

    public static final String EXTRA_VIDEO_ID = "extra_video_id";

    public static final String EXTRA_VIDEO_SOURCE = "extra_video_source";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_video);


        Button btnClose = findViewById(R.id.btn_closeButton);
        btnClose.setOnClickListener(this);

        TextView tvNotfound = findViewById(R.id.tv_notfound);

        String video_id = getIntent().getStringExtra(EXTRA_VIDEO_ID);
        String video_source = getIntent().getStringExtra(EXTRA_VIDEO_SOURCE);

        //System.out.println(video_id+ " & " +video_source);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.trailer_video);

        if (video_id.isEmpty() || !video_source.equals("YouTube")){
            Toast.makeText(this, "Video not Found", Toast.LENGTH_LONG).show();
            youTubePlayerView.setVisibility(View.GONE);
            tvNotfound.setVisibility(View.VISIBLE);
        } else {
            //Toast.makeText(this, video_id+ " & " +video_source, Toast.LENGTH_LONG).show();
            youTubePlayerView.initialize(API_KEY, this);
        }


    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored){

            String video_id = getIntent().getStringExtra(EXTRA_VIDEO_ID);
            youTubePlayer.cueVideo(video_id);
        }

    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, R.string.failed_initialized, Toast.LENGTH_LONG).show();
    }

    private final PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private final PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(ErrorReason errorReason) {

        }
    };

    @Override
    public void onClick(View v) {
        finish();
    }
}
