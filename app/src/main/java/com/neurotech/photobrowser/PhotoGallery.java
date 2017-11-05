package com.neurotech.photobrowser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/11/5.
 */

public class PhotoGallery {
    public static void openPhotoGallery(@NonNull Context context, Bundle bundle) {
        Intent intent = new Intent(context, PhotoGalleryActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
