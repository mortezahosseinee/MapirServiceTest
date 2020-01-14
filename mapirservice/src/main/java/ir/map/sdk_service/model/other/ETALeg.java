package ir.map.sdk_service.model.other;

public class ETALeg {

    private Double distance;
    private Double duration;

    public ETALeg(Double distance, Double duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getDuration() {
        return duration;
    }
}
