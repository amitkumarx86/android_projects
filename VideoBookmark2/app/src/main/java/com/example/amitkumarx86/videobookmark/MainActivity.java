package com.example.amitkumarx86.videobookmark;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {


    private LruCache<String, Bitmap> MainActivity;
    private static MainActivity cache;

    public static MainActivity getInstance()
    {
        if(cache == null)
        {
            cache = new MainActivity();
        }

        return cache;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setListView();


    }

    public void setListView(){

        // create object of GetVideos class
        GetVideos getVideos = new GetVideos();
        List<String> fileList1 = new ArrayList<String>();
        List<String> fileList2 = new ArrayList<String>();

        // get video from external sd card
        String secStore = System.getenv("SECONDARY_STORAGE");
        File root_sd_ext = new File(secStore);
        fileList1 = getVideos.GetVideosMethod(root_sd_ext);

        // get video from internal storage
        String intStore = System.getenv("EXTERNAL_STORAGE");
        File root_sd = new File(intStore);
        fileList2 = getVideos.GetVideosMethod(root_sd);

        Set<String> s = new HashSet<String>();
        s.addAll(fileList1);
        s.addAll(fileList2);
        List<String> fileList = new ArrayList<>(s);


        String [] videoNames = fileList.toArray(new String[fileList.size()]);

        // Setting adapter
        ListAdapter videoAdapter = new CustomAdapter(this,videoNames);
        ListView videoList = (ListView) findViewById(R.id.amitlistview);
        videoList.setScrollingCacheEnabled(false);

        videoList.setAdapter(videoAdapter);

        /*
        * Setting Click event on list items
        * */
        videoList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String video = String.valueOf(parent.getItemAtPosition(position));
                        Intent i = new Intent(getApplicationContext(), PlayVideo.class);
                        i.putExtra("path",video);
                        finish(); // end current activity
                        startActivity(i);

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
                String videoName = String.valueOf(parent.getItemAtPosition(position));
                File file = new File(videoName);
                boolean deleted = file.delete();
                if(deleted) setListView();
                else Toast.makeText(MainActivity.this,"Video cannot be deleted..", Toast.LENGTH_SHORT).show();

                return true;
            }

        });


    }

    // cache implementation
    public void initializeCache()
    {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() /1024);

        final int cacheSize = maxMemory / 8;

        System.out.println("cache size = "+cacheSize);

        MainActivity = new LruCache<String, Bitmap>(cacheSize)
        {
            protected int sizeOf(String key, Bitmap value)
            {
                // The cache size will be measured in kilobytes rather than number of items.

                int bitmapByteCount = value.getRowBytes() * value.getHeight();

                return bitmapByteCount / 1024;
            }
        };
    }
    public void addImageToWarehouse(String key, Bitmap value)
    {
        if(MainActivity != null && MainActivity.get(key) == null)
        {
            MainActivity.put(key, value);
        }
    }

    public Bitmap getImageFromWarehouse(String key)
    {
        if(key != null)
        {
            return MainActivity.get(key);
        }
        else
        {
            return null;
        }
    }


}