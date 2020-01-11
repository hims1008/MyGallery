package com.wondershare.gallery.photo;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wondershare.gallery.R;

import java.io.File;

public class PhotoGridCursorAdapter extends CursorAdapter {
    private Context mContext;
    private int mImageSize;

    public PhotoGridCursorAdapter(Context context, Cursor cursor, boolean z) {
        super(context, cursor, z);
        init(context);
    }

    public PhotoGridCursorAdapter(Context context, Cursor cursor, int i) {
        super(context, cursor, i);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mImageSize = 48;
    }

    public Cursor swapCursor(Cursor cursor) {
        return super.swapCursor(cursor);
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_photogrid, viewGroup, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView) view.findViewById(R.id.item_photogrid_iv);
        int columnIndex = cursor.getColumnIndex("_data");
        if (columnIndex >= 0) {
            String string = cursor.getString(columnIndex);
            if (string != null) {

                Glide.with(this.mContext).load(new File(string)).into(imageView);
            }
        }
    }
}
