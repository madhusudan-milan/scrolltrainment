package com.example.scrolltrainment;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PexelsApiClient {
    private static final String API_KEY = "d5kQ0ITghsjWqVUtC6UX88QvIzQYzx8CKeMWtJ4vrzUws5E8qe3OEuTF";
    private static final String BASE_URL = "https://api.pexels.com/videos/popular?page=1&per_page=40";

    public interface VideoResponseListener {
        void onVideoResponse(ArrayList<Video> videoUrls);
        void onFailure(String errorMessage);
    }

    public static void getPopularVideos(final VideoResponseListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", API_KEY)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray videosArray = jsonObject.getJSONArray("videos");
                        ArrayList<Video> videoUrls = new ArrayList<>();

                        for (int i = 0; i < videosArray.length(); i++) {
                            JSONObject videoObject = videosArray.getJSONObject(i);
                            String videoUrl = videoObject.getJSONArray("video_files").getJSONObject(0).getString("link");
                            Video video = new Video();
                            video.setId(videoObject.getString("id"));
                            video.setFileType(videoObject.getJSONArray("video_files").getJSONObject(0).getString("file_type"));
                            video.setWidth(videoObject.getInt("width"));
                            video.setHeight(videoObject.getInt("height"));
                            video.setDuration(videoObject.getInt("duration"));
                            video.setQuality(videoObject.getJSONArray("video_files").getJSONObject(0).getString("quality"));
                            video.setUserName(videoObject.getJSONObject("user").getString("name"));
                            video.setUserUrl(videoObject.getJSONObject("user").getString("url"));
                            video.setLink(videoUrl);
                            videoUrls.add(video);
                        }

                        listener.onVideoResponse(videoUrls);
                    } catch (JSONException e) {
                        listener.onFailure("JSON parsing error: " + e.getMessage());
                    }
                } else {
                    listener.onFailure("API request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                listener.onFailure("Request failed: " + e.getMessage());
            }
        });
    }
}