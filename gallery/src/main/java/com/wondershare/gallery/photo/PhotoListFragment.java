package com.wondershare.gallery.photo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.wondershare.gallery.R;

import java.io.File;
import java.util.Locale;

public class PhotoListFragment extends Fragment implements OnItemClickListener {
    private PhotoListCursorAdapter mAdapter;
    private ViewGroup mAllPhotoBtn;
    private Cursor mCursor;
    private ListView mListView;
    private OnFragmentListener mListener;

    public static PhotoListFragment newInstance() {
        return new PhotoListFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_photolist, viewGroup, false);
        setupView(inflate);
        String[] strArr = {"_id", "bucket_id", "bucket_display_name", "datetaken", "_data", "mime_type"};
        String[] strArr2 = {"image/gif"};
        StringBuilder sb = new StringBuilder();
        sb.append("mime_type!=?");
        sb.append(") GROUP BY 2,(2");
        this.mCursor = getContext().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, strArr, sb.toString(), strArr2, "date_modified DESC");
        this.mAdapter = new PhotoListCursorAdapter(getContext(), this.mCursor, 0);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(this);
        setAllPhotoInfo();
        return inflate;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.mListener != null) {
            this.mCursor.moveToPosition(i);
            Cursor cursor = this.mCursor;
            String string = cursor.getString(cursor.getColumnIndex("bucket_id"));
            Cursor cursor2 = this.mCursor;
            this.mListener.onSelectBucketID(string, cursor2.getString(cursor2.getColumnIndex("bucket_display_name")));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Cursor cursor = this.mCursor;
        if (cursor != null && !cursor.isClosed()) {
            this.mCursor.close();
        }
    }

    @SuppressLint("ResourceType")
    private void setupView(View view) {
        this.mListView = view.findViewById(16908298);
        this.mAllPhotoBtn = view.findViewById(R.id.listview_root);
        view.findViewById(R.id.space_view).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.listview_root).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (mListener != null) {
                    mListener.onSelectBucketID(null, null);
                }
            }
        });
    }

    private void setAllPhotoInfo() {
        String str;
        this.mAllPhotoBtn.setClickable(true);
        ImageView imageView = this.mAllPhotoBtn.findViewById(R.id.listview_item_iv_async);
        TextView textView = this.mAllPhotoBtn.findViewById(R.id.listview_item_tv_title);
        TextView textView2 = this.mAllPhotoBtn.findViewById(R.id.listview_item_tv_subtitle);
        String str2 = "_data";
        String str3 = "mime_type!=?";
        Cursor query = getContext().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "datetaken", str2}, str3, new String[]{"image/gif"}, "date_modified DESC");
        if (query == null || query.getCount() <= 0) {
            str = "0 Photos";
        } else {
            query.moveToFirst();
//            GlideApp
            Glide.with(getContext()).load(new File(query.getString(query.getColumnIndex(str2)))).into(imageView);
            str = String.format(Locale.US, "%d Photos", query.getCount());
            query.close();
        }
        textView.setText(getString(R.string.photo_all));
        textView2.setText(str);
    }

    public void setListener(OnFragmentListener onFragmentListener) {
        this.mListener = onFragmentListener;
    }

    public interface OnFragmentListener {
        void onSelectBucketID(String str, String str2);
    }
}
