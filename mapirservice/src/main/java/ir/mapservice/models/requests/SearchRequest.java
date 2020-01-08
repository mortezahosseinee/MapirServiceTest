package ir.mapservice.models.requests;

import android.util.Pair;

import androidx.annotation.Nullable;

import ir.mapservice.models.other.FilterOptions;
import ir.mapservice.models.other.SelectOptions;

public class SearchRequest {

    private String text;
    private String selects = null;
    private String filter = null;

    private Double latitude = 0.0;
    private Double longitude = 0.0;

    public SearchRequest(String text) {
        this.text = text;
    }

    public SearchRequest(String text, @Nullable SelectOptions[] selects, @Nullable Pair<FilterOptions, Object> filter) {
        this.text = text;

        if (filter != null)
            if (!(filter.second instanceof String) && !(filter.second instanceof Double))
                throw new RuntimeException("value of filter must be String or Double, but it is " + filter.second.getClass().toString());
            else
                this.filter = filter.first.toString() + " eq " + filter.second;

        if (selects != null && selects.length != 0) {
            StringBuilder tempSelects = new StringBuilder();
            for (int i = 0; i < selects.length - 1; i++) {
                tempSelects.append(selects[i].toString());
                tempSelects.append(",");
            }
            this.selects = tempSelects.toString() + selects[selects.length - 1];
        }
    }

    public SearchRequest(String text, @Nullable SelectOptions[] selects, @Nullable Pair<FilterOptions, Object> filter, Double latitude, Double longitude) {
        this.text = text;

        if (filter != null)
            if (!(filter.second instanceof String) && !(filter.second instanceof Double))
                throw new RuntimeException("value of filter must be String or Double, but it is " + filter.second.getClass().toString());
            else
                this.filter = filter.first.toString() + " eq " + filter.second;

        if (selects != null || filter != null) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        if (selects != null && selects.length != 0) {
            StringBuilder tempSelects = new StringBuilder();
            for (int i = 0; i < selects.length - 1; i++) {
                tempSelects.append(selects[i].toString());
                tempSelects.append(",");
            }
            this.selects = tempSelects.toString() + selects[selects.length - 1];
        }
    }

    public String getText() {
        return text;
    }

    public String getSelects() {
        return selects;
    }

    public String getFilter() {
        return filter;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public boolean hasLatLng() {
        return this.latitude != 0.0 && this.longitude != 0.0;
    }
}
