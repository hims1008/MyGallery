package com.wondershare.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.wondershare.gallery.photo.PhotoGridFragment;
import com.wondershare.gallery.photo.PhotoListFragment;
import com.yalantis.ucrop.UCrop;

public class PhotoActivity extends AppCompatActivity {
    private static final String FRAGMENT_TAG_GRID = "photo_lgrid_fragment";
    private static final String FRAGMENT_TAG_LIST = "photo_list_fragment";
    public static final String OUTPUTPATH = "output_path";
    TextView mAlbumName;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_photo);
        this.mAlbumName = (TextView) findViewById(R.id.album_name);
        findViewById(R.id.back_btn).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.album_btn).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                replacePhotoListFragment();

            }
        });
        if (bundle == null) {
            addPhotoGridFragment();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 69) {
            Uri output = UCrop.getOutput(intent);
            Log.d("Outputpath=", "" + output.getPath());
            Intent intent1 = new Intent();
            intent1.putExtra(OUTPUTPATH,output.getPath());
            setResult(-1,intent1);
            finish();

        } else if (i2 == 96) {
            UCrop.getError(intent);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
    }

    public void addPhotoGridFragment() {
        PhotoGridFragment newInstance = PhotoGridFragment.newInstance();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.photogallery_grid_appear, R.anim.photogallery_list_disappear);
        beginTransaction.add(R.id.container, newInstance, FRAGMENT_TAG_GRID);
        beginTransaction.commit();
    }

    public void replacePhotoListFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        String str = FRAGMENT_TAG_LIST;
        if (supportFragmentManager.findFragmentByTag(str) != null) {
            onBackPressed();
            return;
        }
        PhotoListFragment newInstance = PhotoListFragment.newInstance();
        newInstance.setListener(new PhotoListFragment.OnFragmentListener() {
            public void onSelectBucketID(final String str, final String str2) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public void run() {
                        ((PhotoGridFragment) getSupportFragmentManager().findFragmentByTag(PhotoActivity.FRAGMENT_TAG_GRID)).setBucketID(str);
                        if (str2 == null) {
                            mAlbumName.setText(getString(R.string.photo_all));
                        } else {
                            mAlbumName.setText(str2);
                        }
                    }
                }, 0);
                onBackPressed();
            }
        });
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.photogallery_list_appear, R.anim.photogallery_grid_disappear);
        beginTransaction.add(R.id.container, newInstance, str);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }
}
