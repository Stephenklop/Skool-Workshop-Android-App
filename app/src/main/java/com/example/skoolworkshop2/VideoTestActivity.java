package com.example.skoolworkshop2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import vimeoextractor.OnVimeoExtractionListener;
import vimeoextractor.VimeoExtractor;
import vimeoextractor.VimeoVideo;

public class VideoTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_test);

        initializePlayer();
    }

    private void initializePlayer() {

        VimeoExtractor.getInstance().fetchVideoWithURL("https://vimeo.com/video/218614795", null, new OnVimeoExtractionListener() {
            @Override
            public void onSuccess(VimeoVideo video) {
                String hdStream = video.getStreams().get("720p");
                System.out.println("VIMEO VIDEO STREAM" + hdStream);
                if (hdStream != null) {
                    playVideo(hdStream);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void playVideo(final String stream) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final VideoView videoView = findViewById(R.id.vv);
                final MediaController mediacontroller = new MediaController(VideoTestActivity.this);
                mediacontroller.setAnchorView(videoView);
                videoView.setMediaController(mediacontroller);

                videoView.setBackgroundColor(Color.TRANSPARENT);
                Uri video = Uri.parse(stream);
                videoView.setVideoURI(video);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoView.requestFocus();
                        videoView.start();
                    }
                });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        System.out.println("Video Finish");
                        finish();
                    }
                });
            }
        });

    }
}