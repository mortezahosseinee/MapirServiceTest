package ir.map.sdk_service.models.requests;

public class GeofenceRequest {

    private double latitude;
    private double longitude;

    public GeofenceRequest(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
