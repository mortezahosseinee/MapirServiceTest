package ir.mapservice.models.requests;

import java.util.List;

import ir.mapservice.models.other.DistanceMatrixOutputType;
import ir.mapservice.models.other.DistanceMatrixPointRequest;

public class DistanceMatrixRequest {

    private List<DistanceMatrixPointRequest> origins;
    private List<DistanceMatrixPointRequest> destinations;
    private boolean sorted = false;
    private String filter = null;

    public DistanceMatrixRequest(List<DistanceMatrixPointRequest> origins, List<DistanceMatrixPointRequest> destinations, boolean sorted) {
        if (origins.size() == 0)
            throw new RuntimeException("origins for distanceMatrix api must have at least on point.");
        else if (destinations.size() == 0)
            throw new RuntimeException("destinations for distanceMatrix api must have at least on point.");

        this.origins = origins;
        this.destinations = destinations;
        this.sorted = sorted;
    }

    public DistanceMatrixRequest(List<DistanceMatrixPointRequest> origins, List<DistanceMatrixPointRequest> destinations, boolean sorted, DistanceMatrixOutputType outputType) {
        if (origins.size() == 0)
            throw new RuntimeException("origins for distanceMatrix api must have at least on point.");
        else if (destinations.size() == 0)
            throw new RuntimeException("destinations for distanceMatrix api must have at least on point.");

        this.origins = origins;
        this.destinations = destinations;
        this.sorted = sorted;
        this.filter = outputType.toString();
    }

    public String getOrigins() {
        StringBuilder tempOrigins = new StringBuilder();

        for (int i = 0; i < origins.size(); i++)
            tempOrigins
                    .append(origins.get(i).getId())
                    .append(",")
                    .append(origins.get(i).getLatitude())
                    .append(",")
                    .append(origins.get(i).getLongitude())
                    .append((i != origins.size() - 1) ? "|" : "");

        return tempOrigins.toString();
    }

    public String getDestinations() {
        StringBuilder tempDestinations = new StringBuilder();

        for (int i = 0; i < destinations.size(); i++)
            tempDestinations
                    .append(destinations.get(i).getId())
                    .append(",")
                    .append(destinations.get(i).getLatitude())
                    .append(",")
                    .append(destinations.get(i).getLongitude())
                    .append((i != destinations.size() - 1) ? "|" : "");

        return tempDestinations.toString();
    }

    public boolean isSorted() {
        return sorted;
    }

    public String getFilter() {
        return filter;
    }

    public boolean hasFilter() {
        return filter != null;
    }
}
