package com.wondershare.gallery.photo;

import android.content.Context;
import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FolderProvider {
    private static FolderProvider INSTANCE;
    private File cacheDir;
    private File ucopCacheFile;

    public static FolderProvider getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FolderProvider(context);
        }
        return INSTANCE;
    }

    private FolderProvider(Context context) {
        String str = "text";
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                this.cacheDir = context.getExternalCacheDir();
            } else {
                this.cacheDir = context.getCacheDir();
            }
            this.ucopCacheFile = new File(this.cacheDir, "sample.dat");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getUcopCacheFile() {
        return this.ucopCacheFile;
    }
}
