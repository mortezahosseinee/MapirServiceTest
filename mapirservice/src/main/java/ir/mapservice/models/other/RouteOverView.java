package ir.mapservice.models.other;

public enum RouteOverView {
    FULL("full"),
    SIMPLE("simplified"),
    NONE("false");

    private String value;

    RouteOverView(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
