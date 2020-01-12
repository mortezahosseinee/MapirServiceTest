package ir.map.sdk_service.models.responses;

import android.graphics.Bitmap;

import ir.map.sdk_service.models.base.BaseModel;
import ir.map.sdk_service.models.base.MapirResponse;

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

