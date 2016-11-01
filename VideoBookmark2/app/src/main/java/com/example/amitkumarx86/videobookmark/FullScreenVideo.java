package com.example.amitkumarx86.videobookmark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by amitkumarx86 on 18/6/16.
 */
public class FullScreenVideo extends Activity {


    //DbHandler dbHandler;
    private static String path = "";
    VideoView videoView;
    public int seekTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_fullscreen);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            path = extras.getString("path");
            seekTime = new Integer(extras.getInt("seekTime"));
        }
        playVideo(path, seekTime);
    }

    public void playVideo(String path, int seekTime){

        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath(path);

        MediaController mediaController = new MediaController(this);

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
        videoView.seekTo(seekTime);
        //mediaController.hide();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, PlayVideo.class);
        i.putExtra("path",path);
        i.putExtra("seekTime",videoView.getCurrentPosition());
        startActivity(i);
        super.onBackPressed();
    }
}
