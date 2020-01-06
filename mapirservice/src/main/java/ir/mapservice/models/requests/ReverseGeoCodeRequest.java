package ir.mapservice.models.requests;

public class ReverseGeoCodeRequest {

    private double latitude;
    private double longitude;

    public ReverseGeoCodeRequest(double latitude, double longitude) {
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
