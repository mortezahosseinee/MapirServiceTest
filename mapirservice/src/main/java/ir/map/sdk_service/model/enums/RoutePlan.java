package ir.map.sdk_service.model.enums;

public enum RoutePlan {
    SIMPLE(""),
    TRAFFIC("tarh"),
    EVEN_ODD("zojofard");

    private String value;

    RoutePlan(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
