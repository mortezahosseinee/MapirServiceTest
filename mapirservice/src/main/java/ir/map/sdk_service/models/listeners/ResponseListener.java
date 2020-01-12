package ir.map.sdk_service.models.listeners;

import ir.map.sdk_service.models.base.MapirError;
import ir.map.sdk_service.models.base.MapirResponse;

public interface ResponseListener {
    void onSuccess(MapirResponse response);

    void onError(MapirError error);
}
