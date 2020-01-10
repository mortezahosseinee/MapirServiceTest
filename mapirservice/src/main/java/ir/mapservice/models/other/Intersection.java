package ir.mapservice.models.other;

import java.util.List;

public class Intersection {
    private Integer out;
    private List<Boolean> entry = null;
    private List<Integer> bearings = null;
    private List<Double> location = null;
    private Integer in;

    public Integer getOut() {
        return out;
    }

    public List<Boolean> getEntry() {
        return entry;
    }

    public List<Integer> getBearings() {
        return bearings;
    }

    public List<Double> getLocation() {
        return location;
    }

    public Integer getIn() {
        return in;
    }
}
