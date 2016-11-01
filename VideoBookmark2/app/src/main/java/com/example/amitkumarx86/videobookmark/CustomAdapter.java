package com.example.amitkumarx86.videobookmark;

import android.app.Activity;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] video;

    static class ViewHolder {
        public TextView videoTitle;
        public ImageView videoThumbnail;
        public int position;
        public String path;
    }

    public CustomAdapter(Activity context, String[] video) {
        super(context, R.layout.custom_row, video);
        this.context = context;
        this.video = video;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater videoInflator = LayoutInflater.from(getContext());
        View customView = videoInflator.inflate(R.layout.custom_row, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.position = position;
            viewHolder.path = video[position];
            viewHolder.videoTitle = (TextView) customView.findViewById(R.id.videoTitle);
            viewHolder.videoThumbnail = (ImageView) customView.findViewById(R.id.videoThumbnail);
            //rowView.setTag(viewHolder);
        //}
        customView.setTag(viewHolder);


        final String videoItem = video[position];
        int index=videoItem.lastIndexOf('/');
        String lastString=(videoItem.substring(index +1));
        index = lastString.indexOf(".mp4");
        lastString=(lastString.substring(0,index));
        viewHolder.videoTitle.setText(lastString);

        final MainActivity cache = MainActivity.getInstance(); //Singleton instance handled in ImagesCache class.
        cache.initializeCache();

        new AsyncTask<ViewHolder, Void, Bitmap>() {
            private ViewHolder v;

            @Override
            protected Bitmap doInBackground(ViewHolder... params) {
                v = params[0];
                Bitmap bm = cache.getImageFromWarehouse(videoItem);
                Bitmap thumb = null;
                if(bm == null)
                {
                    thumb = ThumbnailUtils.createVideoThumbnail(videoItem, MediaStore.Images.Thumbnails.MICRO_KIND);
                    bm = thumb;
                }
                return bm;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                if (v.position == position) {
                    // If this item hasn't been recycled already, hide the
                    // progress and set and show the image
                    v.videoThumbnail.setImageBitmap(result);
                    cache.addImageToWarehouse(v.path,result); // adding image to cache
                }
            }
        }.execute(viewHolder);

        return customView;
    }


}
