package ir.mapservice.models.other;

public enum SelectOptions {
    PROVINCE("province"),
    CITY("city"),
    COUNTY("county"),
    DISTRICT("district"),
    REGION("region"),
    NEIGHBOURHOOD("neighbourhood"),
    LAND_USE("landuse"),
    WOOD_WATER("woodwater"),
    ROADS("roads"),
    POI("poi"),
    NEARBY("nearby"),
    NATURAL("natural");

    private String value;

    SelectOptions(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
