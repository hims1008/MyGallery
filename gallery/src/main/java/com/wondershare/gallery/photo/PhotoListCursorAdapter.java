package com.wondershare.gallery.photo;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wondershare.gallery.R;

import java.io.File;
import java.util.Locale;

public class PhotoListCursorAdapter extends CursorAdapter {
    private Context mContext;

    public PhotoListCursorAdapter(Context context, Cursor cursor, boolean z) {
        super(context, cursor, z);
        init(context);
    }

    public PhotoListCursorAdapter(Context context, Cursor cursor, int i) {
        super(context, cursor, i);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_photolist, viewGroup, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = view.findViewById(R.id.listview_item_iv_async);
        TextView textView = view.findViewById(R.id.listview_item_tv_title);
        TextView textView2 = view.findViewById(R.id.listview_item_tv_subtitle);
        String string = cursor.getString(cursor.getColumnIndex("bucket_id"));
        String string2 = cursor.getString(cursor.getColumnIndex("bucket_display_name"));
        int columnIndex = cursor.getColumnIndex("_data");
        if (columnIndex >= 0) {
            String string3 = cursor.getString(columnIndex);
            if (string3 != null) {
                /*GlideApp*/
                Glide.with(this.mContext).load(new File(string3)).into(imageView);
            }
        }
        textView.setText(string2);
        textView2.setText(String.format(Locale.US, "%d Photos", getBucketCount(string)));
    }

    private int getBucketCount(String str) {
        Cursor query = this.mContext.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "bucket_id=?", new String[]{str}, null);
        if (query == null) {
            return 0;
        }
        int count = query.getCount();
        query.close();
        return count;
    }
}
