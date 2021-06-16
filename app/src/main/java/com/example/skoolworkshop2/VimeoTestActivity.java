package com.example.skoolworkshop2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import vimeoextractor.OnVimeoExtractionListener;
import vimeoextractor.VimeoExtractor;
import vimeoextractor.VimeoVideo;

public class VimeoTestActivity extends AppCompatActivity {

    private VideoView vv;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vimeo_test);
        System.out.println("opened videoplayer");

        vv = findViewById(R.id.activity_vimeo_vv);

        initializePlayer();
    }

    private void initializePlayer(){
        VimeoExtractor.getInstance().fetchVideoWithURL("https://vimeo.com/video/218614795", null, new OnVimeoExtractionListener() {
            @Override
            public void onSuccess(VimeoVideo video) {
                System.out.println("loaded video");
                String hdStream = video.getStreams().get("720p");
                playVideo(hdStream);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void playVideo(String stream){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MediaController mediaController = new MediaController(VimeoTestActivity.this);
                mediaController.setAnchorView(vv);
                vv.setMediaController(mediaController);
                System.out.println("video loaded in view");

                vv.setVideoURI(Uri.parse(stream));
                System.out.println("videouri created");

                vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        System.out.println("completed");
                        vv.requestFocus();
                        vv.start();
                    }
                });
            }
        });

    }
}