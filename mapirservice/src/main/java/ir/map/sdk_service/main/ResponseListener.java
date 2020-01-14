package ir.map.sdk_service.main;

import ir.map.sdk_service.model.base.MapirError;

public interface ResponseListener<T> {
    void onSuccess(T response);

    void onError(MapirError error);
}
