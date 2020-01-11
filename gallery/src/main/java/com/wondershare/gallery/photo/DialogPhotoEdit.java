package com.wondershare.gallery.photo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.wondershare.gallery.R;

public abstract class DialogPhotoEdit extends AppCompatDialog {
    protected ListView mListView;

    protected DialogPhotoEdit(Context context) {
        super(context);
        init();
    }

    public DialogPhotoEdit(Context context, int i) {
        super(context, i);
        init();
    }

    public DialogPhotoEdit(Context context, boolean z, OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        init();
    }

    public abstract void update(int i);

    private void init() {
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
        requestWindowFeature(1);
        setContentView((int) R.layout.dialog_listview);
        this.mListView = (ListView) findViewById(R.id.dialog_listview);
        this.mListView.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                lambda$init$0$DialogPhotoEdit(adapterView, view, i, j);
            }
        });
        this.mListView.setAdapter(new SizeAdapter());
        findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                lambda$init$1$DialogPhotoEdit(view);
            }
        });
    }

    public  void lambda$init$0$DialogPhotoEdit(AdapterView adapterView, View view, int i, long j) {
        update(i);
        dismiss();
    }

    public  void lambda$init$1$DialogPhotoEdit(View view) {
        dismiss();
    }

    private class SizeAdapter extends BaseAdapter {
        SizeAdapter() {
        }

        public int getCount() {
            return 3;
        }

        public Integer getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_photo_edit, viewGroup, false);
            }
            TextView textView = (TextView) view.findViewById(R.id.textview1);
            TextView textView2 = (TextView) view.findViewById(R.id.textview2);
            if (i == 0) {
                textView.setText(R.string.photo_edit_dialog_item_ratio1);
                textView2.setText(R.string.photo_edit_dialog_item_sub1);
            } else if (i == 1) {
                textView.setText(R.string.photo_edit_dialog_item_ratio2);
                textView2.setText(R.string.photo_edit_dialog_item_sub2);
            } else {
                textView.setText(R.string.photo_edit_dialog_item_ratio3);
                textView2.setText(R.string.photo_edit_dialog_item_sub3);
            }
            return view;
        }
    }
}
