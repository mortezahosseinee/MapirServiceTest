package ir.map.sdk_service.models.other;

import ir.map.sdk_service.models.base.MapirResponse;

public class GeofenceData extends MapirResponse {

    private int id;
    private Boundry boundry;
    private String meta;
    private String createdAt;
    private String updatedAt;

    public GeofenceData(int id, Boundry boundry, String meta, String createdAt, String updatedAt) {
        this.id = id;
        this.boundry = boundry;
        this.meta = meta;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public Boundry getBoundry() {
        return boundry;
    }

    public String getMeta() {
        return meta;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}

