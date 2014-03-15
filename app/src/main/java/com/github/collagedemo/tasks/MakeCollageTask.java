package com.github.collagedemo.tasks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import com.github.collagedemo.App;
import com.github.collagedemo.model.Media;
import com.github.collagedemo.util.Preset;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import java.io.IOException;

/**
* @author Sergey Ryabov
*         Date: 13.03.14
*/
public class MakeCollageTask extends AsyncTask<Media, Void, Bitmap> {
    private static final String TAG = "InstaCollage";

    private static final int IMAGE_SIZE = 640;

    @Inject Picasso picasso;

    public MakeCollageTask() {
        App.get().inject(this);
    }

    @Override
    protected Bitmap doInBackground(Media... medias) {
        try {
            final Preset preset = Preset.valueOf(medias.length);

            final Bitmap dest = Bitmap.createBitmap(IMAGE_SIZE, IMAGE_SIZE, Bitmap.Config.ARGB_8888);
            final Canvas c = new Canvas(dest);

            for (int i = 0; i < medias.length; i++) {
                final Bitmap b = picasso.load(medias[i].getImageUrl()).get();

                Pair<Rect, RectF> rects = preset.getRects(b, c, i);
                c.drawBitmap(b, rects.first, rects.second, null);
            }

            return dest;
        } catch (IOException e) {
            Log.e(TAG, "Error while resolving source bitmaps", e);
            return null;
        }
    }
}
