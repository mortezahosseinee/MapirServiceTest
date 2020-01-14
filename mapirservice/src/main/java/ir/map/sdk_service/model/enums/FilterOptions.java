package ir.map.sdk_service.model.enums;

public enum FilterOptions {
    PROVINCE("province"),
    CITY("city"),
    COUNTY("county"),
    REGION("region"),
    NEIGHBOURHOOD("neighbourhood"),
    LAND_USE("distance"),
    WOOD_WATER("polygon");

    private String value;

    FilterOptions(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
