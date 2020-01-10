package ir.mapservice.models.responses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.InputStream;

import ir.mapservice.models.base.BaseModel;
import ir.mapservice.models.base.MapirResponse;

public class StaticMapResponse extends MapirResponse {

    private Bitmap bitmap;

    private StaticMapResponse(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public static BaseModel createStaticMapResponse(Bitmap bitmap) {
        return new StaticMapResponse(bitmap);
    }

    public Bitmap getBitmapStaticMap() {
        return bitmap;
    }
}

