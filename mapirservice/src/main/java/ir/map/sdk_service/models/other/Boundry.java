package ir.map.sdk_service.models.other;

import java.util.List;

import ir.map.sdk_service.models.base.MapirResponse;

public class Boundry extends MapirResponse {

    private String type;
    private List<List<Coordinate>> coordinates;

    public Boundry(String type, List<List<Coordinate>> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public List<List<Coordinate>> getCoordinates() {
        return coordinates;
    }
}

