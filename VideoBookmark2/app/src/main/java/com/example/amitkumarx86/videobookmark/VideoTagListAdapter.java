package com.example.amitkumarx86.videobookmark;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by amitkumarx86 on 28/5/16.
 */
public class VideoTagListAdapter extends ArrayAdapter<String> {

    //public static int tagSerial = 0 ;

    public VideoTagListAdapter(Context context , String[] video) {
        super(context,R.layout.videotaglayout, video);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater videoInflator = LayoutInflater.from(getContext());
        final View customView = videoInflator.inflate(R.layout.videotaglayout, parent, false);

        final String videoItem = getItem(position);
        final String [] data = videoItem.split(":");
        TextView videoTitle = (TextView) customView.findViewById(R.id.videoTagTopic);
        TextView videoTime = (TextView) customView.findViewById(R.id.videoTiming);
        //int index=videoItem.lastIndexOf('/');
        //String lastString=(videoItem.substring(index +1));
        videoTitle.setText(data[1]);
        videoTime.setText(Integer.toString(new Integer(data[2])/1000) + " sec");

        return customView;
    }


}
