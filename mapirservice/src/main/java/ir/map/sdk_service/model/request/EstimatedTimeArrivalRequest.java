package ir.map.sdk_service.model.request;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ir.map.sdk_service.model.other.Coordinate;

public class EstimatedTimeArrivalRequest {

    private Double originLatitude;
    private Double originLongitude;

    List<Coordinate> destinations;

    private EstimatedTimeArrivalRequest(Double originLatitude, Double originLongitude, List<Coordinate> destinations) {
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;

        if (destinations.isEmpty())
            throw new RuntimeException("Destinations can not be empty. At least add one.");

        this.destinations = destinations;
    }

    public Double getOriginLatitude() {
        return originLatitude;
    }

    public Double getOriginLongitude() {
        return originLongitude;
    }

    public Coordinate getDestination(int index) {
        if (destinations.isEmpty())
            return null;
        else
            return destinations.get(index);
    }

    public String getCoordinates() {
        StringBuilder coordinates = new StringBuilder();

        coordinates.append(originLongitude).append(",").append(originLatitude);

        for (int i = 0; i < destinations.size(); i++)
            coordinates.append(";").append(destinations.get(i).getLongitude()).append(",").append(destinations.get(i).getLatitude());

        return coordinates.toString();
    }

    public static class Builder {

        private Double startLatitude;
        private Double startLongitude;

        List<Coordinate> destinations = new ArrayList<>();

        public Builder(
                Double startLatitude,
                Double startLongitude
        ) {
            this.startLatitude = startLatitude;
            this.startLongitude = startLongitude;
        }

        public Builder addDestination(@NonNull Double latitude, @NonNull Double longitude) {
            if (latitude != null && longitude != null)
                destinations.add(new Coordinate(latitude, longitude));
            else
                throw new RuntimeException("latitude and longitude can't be null.");

            return this;
        }

        public EstimatedTimeArrivalRequest build() {
            return new EstimatedTimeArrivalRequest(startLatitude, startLongitude, destinations);
        }
    }
}
