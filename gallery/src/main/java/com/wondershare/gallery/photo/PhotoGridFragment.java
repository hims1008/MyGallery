package com.wondershare.gallery.photo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.wondershare.gallery.PhotoActivity;
import com.wondershare.gallery.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop.Options;

import java.io.File;

public class PhotoGridFragment extends Fragment implements OnItemClickListener {
    /* access modifiers changed from: private */
    public FolderProvider mFolderProvider;
    private PhotoGridCursorAdapter mAdapter;
    private String mBucketID;
    private Cursor mCursor;

    public static PhotoGridFragment newInstance() {
        return new PhotoGridFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_photogrid, viewGroup, false);
        this.mFolderProvider = FolderProvider.getInstance(getContext());
        GridView gridView = (GridView) inflate.findViewById(R.id.list);
        this.mAdapter = new PhotoGridCursorAdapter(getContext(), this.mCursor, 0);
        gridView.setAdapter(this.mAdapter);
        gridView.setOnItemClickListener(this);
        gridView.setNumColumns(3);
        gridView.setHorizontalSpacing(2);
        gridView.setVerticalSpacing(2);
        load();
        return inflate;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.mCursor.moveToPosition(i);
        Cursor cursor = this.mCursor;
        String string = cursor.getString(cursor.getColumnIndex("_data"));
        Log.d("onItemClick=", "" + string);
        startCropActivity(Uri.fromFile(new File(string)));
    }

    public void onDestroy() {
        super.onDestroy();
        Cursor cursor = this.mCursor;
        if (cursor != null && !cursor.isClosed()) {
            this.mCursor.close();
        }
    }

    private void load() {
        String[] strArr;
        String str;
        Cursor cursor = this.mCursor;
        String[] strArr2 = {"_id", "_data", "mime_type"};
        String str2 = this.mBucketID;
        String str3 = "image/gif";
        if (str2 != null) {
            str = "bucket_id=? and mime_type!=?";
            strArr = new String[]{str2, str3};
        } else {
            strArr = new String[]{str3};
            str = "mime_type!=?";
        }
        this.mCursor = getContext().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, strArr2, str, strArr, "datetaken DESC");
        this.mAdapter.swapCursor(this.mCursor);
        this.mAdapter.notifyDataSetChanged();
        if (cursor != null) {
            cursor.close();
        }
    }

    public void setBucketID(String str) {
        this.mBucketID = str;
        load();
    }

    private void startCropActivity(final Uri uri) {
        new DialogPhotoEdit(getContext()) {
            /* access modifiers changed from: protected */
            public void update(int i) {
                Options options = new Options();
                options.setToolbarTitle(getString(R.string.crop_title));
                options.setToolbarWidgetColor(-1);
                options.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                options.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                options.setCompressionQuality(100);
                options.setToolbarWidgetColor(ContextCompat.getColor(getContext(), R.color.black_icon));
                UCrop of = UCrop.of(uri, Uri.fromFile(mFolderProvider.getUcopCacheFile()));
                if (i == 0) {
                    of = of.withAspectRatio(1.0f, 1.0f);
                } else if (i == 1) {
                    of = of.withAspectRatio(4.0f, 5.0f);
                }
                of.withOptions(options).start((PhotoActivity) getActivity());
            }
        }.show();
    }
}
