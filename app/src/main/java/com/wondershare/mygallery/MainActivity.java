package com.wondershare.mygallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wondershare.gallery.PhotoActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView mViewImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
    }

    private void initView() {
        mViewImg = findViewById(R.id.img_view);
    }

    public void mycreation(View view) {
        startActivityForResult(new Intent(this, PhotoActivity.class), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1) {
            mViewImg.invalidate();
            String path = data.getStringExtra(PhotoActivity.OUTPUTPATH);
            mViewImg.setImageURI(Uri.fromFile(new File(path)));
        }
    }
}
