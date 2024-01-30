package com.example.scrolltrainment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL ,false);
        recyclerView.setLayoutManager(layoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

       PexelsApiClient.getPopularVideos(new PexelsApiClient.VideoResponseListener(){
           @Override
           public void onVideoResponse(ArrayList<Video> videoArrayList) {
               runOnUiThread(() -> {
                   VideoAdapter adapter = new VideoAdapter(MainActivity.this, videoArrayList);
                   recyclerView.setAdapter(adapter);

                   adapter.setOnItemclickListner(video -> {
                       BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                       View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.video_details, null);
                       bottomSheetDialog.setContentView(view);
                       bottomSheetDialog.show();

                       ImageView imageView = view.findViewById(R.id.videoThumbnail);
                       TextView videoID = view.findViewById(R.id.videoId);
                       TextView videoQuality = view.findViewById(R.id.videoQuality);
                       TextView videoFileType = view.findViewById(R.id.videofileType);
                       TextView videoDuration = view.findViewById(R.id.duration);
                       TextView videoResolution = view.findViewById(R.id.videoResolution);
                       TextView videoUploadedBy = view.findViewById(R.id.videoUploadedBy);
                       MaterialButton viewUser = view.findViewById(R.id.viewUserButton);
                       MaterialButton playVideo = view.findViewById(R.id.playButton);

                       Glide.with(MainActivity.this).load(video.getLink()).into(imageView);
                       videoID.setText(MessageFormat.format("Video ID: {0}", video.getId()));
                       videoQuality.setText(MessageFormat.format("Video Quality: {0}", video.getQuality()));
                       videoFileType.setText(MessageFormat.format("Video File Type: {0}", video.getFileType()));
                       videoDuration.setText(MessageFormat.format("Video Duration: {0} seconds", video.getDuration()));
                       videoResolution.setText(MessageFormat.format("Video Resolution: {0}x{1}", video.getWidth(), video.getHeight()));
                       videoUploadedBy.setText(MessageFormat.format("Video Uploaded By: {0}", video.getUserName()));

                       viewUser.setOnClickListener(view1 -> {
                           Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getUserUrl()));
                           startActivity(browserIntent);
                       });

                       playVideo.setOnClickListener(view12 -> {
                           Intent intent = new Intent(MainActivity.this, VideoPlayActivity.class);
                           intent.putExtra("url", video.getLink());
                           startActivity(intent);
                       });

                   });
               });


                   }


                   @Override
                   public void onFailure(String errorMessage) {
                       Log.e("APIError", errorMessage);
                   }
               });

           }
       }