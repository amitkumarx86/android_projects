package com.example.amitkumarx86.videobookmark;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;
import android.view.View;

import java.util.ArrayList;

public class PlayVideo extends AppCompatActivity {


    DbHandler dbHandler;
    private static String path = "";
    VideoView videoView;
    public static int seekTime = 0;
    private RelativeLayout.LayoutParams paramsNotFullscreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml

        setContentView(R.layout.video_play);
        Bundle extras = getIntent().getExtras();

        // Database Initialization
        dbHandler = new DbHandler(this, null,null,1);

        if (extras != null) {
            path = extras.getString("path");
            seekTime = new Integer(extras.getInt("seekTime"));
        }
        //by default play video
        if (savedInstanceState != null) seekTime = savedInstanceState.getInt("seek_time");
        playVideo(path, seekTime); // video player
        setListView(path);  // video Tag list display

        videoView = (VideoView) findViewById(R.id.videoView);
        EditText addTopicText = (EditText) findViewById(R.id.addTopicText);
        addTopicText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    videoView.pause();
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(this, FullScreenVideo.class);
            i.putExtra("path",path);
            i.putExtra("seekTime",videoView.getCurrentPosition());
            startActivity(i);

        }

    else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();

        }
    }
    /*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);

        if(Configuration.ORIENTATION_LANDSCAPE==newConfig.orientation)
        {
            //Portatrate to landscape...
            Intent i = new Intent(getApplicationContext(), FullScreenVideo.class);
            i.putExtra("path",path);
            i.putExtra("seekTime",seekTime);
            startActivity(i);
        }

        else if(Configuration.ORIENTATION_PORTRAIT==newConfig.orientation)
        {
            //Landscape to portraite....
            //Portatrate to landscape...
            Intent i = new Intent(getApplicationContext(), FullScreenVideo.class);
            i.putExtra("path",path);
            i.putExtra("seekTime",seekTime);
            startActivity(i);
        }

    }*/


    @Override
    protected void onSaveInstanceState (Bundle savedInstanceState){
        savedInstanceState.putInt("seek_time", videoView.getCurrentPosition()); // save it here
    }
    // Add video Tag
    public void addVideoTopic(View view){
        EditText addTopicText = (EditText) findViewById(R.id.addTopicText);
        if(addTopicText.getText().toString() != null) {
            VideoData videoData = new VideoData(path,addTopicText.getText().toString(), Integer.toString(videoView.getCurrentPosition()));

            dbHandler.addVideoData(videoData);

            addTopicText.setText(""); // reset add button

            // hide keyboard after hitting submit
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(addTopicText.getWindowToken(), 0);


            setListView(path);  // video Tag list display
            videoView.requestFocus();
            videoView.start();
        }
    }



    // play video
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

    // set list view
    public void setListView(String path) {
        //Toast.makeText(this, path, Toast.LENGTH_LONG).show();
        ArrayList<String> videoTagList = dbHandler.databaseToString(path.toString());

            String[] videoTags = videoTagList.toArray(new String[videoTagList.size()]);

            // Setting adapter
            ListAdapter videoAdapter = new VideoTagListAdapter(this, videoTags);
            ListView videoList = (ListView) findViewById(R.id.videoTagList);
            videoList.setAdapter(videoAdapter);

        /*
        * Setting Click event on list items
        * */
            videoList.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String videoName = String.valueOf(parent.getItemAtPosition(position)).split(":")[0];
                            String timeStamp = String.valueOf(parent.getItemAtPosition(position)).split(":")[2];
                            //int timeStamp = dbHandler.getVideoTimeStamp(path, videoTopic);
                            playVideo(videoName, new Integer(timeStamp));
                        }
                    }
            );
         /*
        * Setting Long Click event on list items
        * */
        videoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                String videoName = String.valueOf(parent.getItemAtPosition(position)).split(":")[0];
                //Toast.makeText(PlayVideo.this, videoName, Toast.LENGTH_LONG).show();
                String videoTopic = String.valueOf(parent.getItemAtPosition(position)).split(":")[1];
                String timeStamp = String.valueOf(parent.getItemAtPosition(position)).split(":")[2];
                dbHandler.deleteVideoData(videoName, videoTopic, timeStamp);
                Toast.makeText(PlayVideo.this, videoTopic + " deleted..", Toast.LENGTH_SHORT).show();
                setListView(videoName);
                return true;
            }

        });

        /*
        * Double Click to full screen video
        * */

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }

}