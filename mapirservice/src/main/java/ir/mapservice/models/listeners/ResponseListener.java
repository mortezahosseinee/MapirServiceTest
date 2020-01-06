package ir.mapservice.models.listeners;

import ir.mapservice.models.base.MapirError;
import ir.mapservice.models.base.MapirResponse;

public interface ResponseListener {
    void onSuccess(MapirResponse response);

    void onError(MapirError error);
}
