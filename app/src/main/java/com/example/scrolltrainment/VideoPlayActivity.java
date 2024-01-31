package com.example.scrolltrainment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
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
            Button downloadButton=findViewById(R.id.downloadButton);
            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadVideourl(url);
                }

                private void downloadVideourl(String url) {

                        // Create a DownloadManager.Request with the video URL

                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                        // Set title and description for the download notification
                        request.setTitle("Video Download");
                        request.setDescription("Downloading video...");

                        // Set the destination directory for the downloaded video

                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "video.mp4");

                        // Allow the MediaScanner to scan the downloaded file
                        request.allowScanningByMediaScanner();

                        // Get the DownloadManager service
                        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                        // Enqueue the download and get the download ID
                        long downloadId = downloadManager.enqueue(request);

                        // Show a toast message indicating that the download has started

                    }


            });
        }
        else {
            finish();
        }
    }
}