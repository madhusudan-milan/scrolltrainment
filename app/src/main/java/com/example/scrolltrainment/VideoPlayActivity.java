package com.example.scrolltrainment;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        String url=getIntent().getStringExtra("url");

        if (url != null) {
            VideoView videoView = findViewById(R.id.videoview);

            // Use MediaController to enable play/pause controls
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            // Set video URI
            videoView.setVideoURI(Uri.parse(url));

            // Start video asynchronously
            videoView.setOnPreparedListener(MediaPlayer::start);

            // Finish activity when video completes
            videoView.setOnCompletionListener(MediaPlayer::release);
        }
        else {
            finish();
        }
    }
}