package ir.mapservice.models.other;

import java.util.List;

public class Maneuver {

    private Integer bearingAfter;
    private List<Double> location = null;
    private Integer bearingBefore;
    private String type;
    private String modifier;

    public Integer getBearingAfter() {
        return bearingAfter;
    }

    public List<Double> getLocation() {
        return location;
    }

    public Integer getBearingBefore() {
        return bearingBefore;
    }

    public String getType() {
        return type;
    }

    public String getModifier() {
        return modifier;
    }
}
