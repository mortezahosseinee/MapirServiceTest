package ir.mapservice.models.requests;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.mapservice.models.other.FilterOptions;
import ir.mapservice.models.other.SelectOptions;

public class SearchRequest {

    private String text;
    private String selects = null;
    private String filter;

    private Double latitude = 0.0;
    private Double longitude = 0.0;

    private SearchRequest(@Nullable String text, @Nullable List<SelectOptions> selects, @Nullable String filter, @Nullable Double latitude, @Nullable Double longitude) {
        this.text = text;
        this.filter = filter;

        if (selects != null || filter != null) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        if (selects != null && !selects.isEmpty()) {
            StringBuilder tempSelects = new StringBuilder();

            for (int i = 0; i < selects.size() - 1; i++) {
                tempSelects.append(selects.get(i).toString());
                tempSelects.append(",");
            }

            this.selects = tempSelects.toString() + selects.get(selects.size() - 1);
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

    public static class Builder {

        private String text;

        private List<SelectOptions> selectOptions = new ArrayList<>();

        private String filter = null;

        private Double latitude = 0.0;
        private Double longitude = 0.0;

        public Builder(String text) {
            this.text = text;
        }

        public Builder location(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;

            return this;
        }

        public Builder select(@NonNull SelectOptions selectOption) {
            if (selectOption != null) {
                if (selectOptions.contains(selectOption))
                    throw new RuntimeException("Select option in search api can not be redundant. you set selectOption = " + selectOption.toString() + " many times.");
                else
                    this.selectOptions.add(selectOption);
            } else
                throw new RuntimeException("Select option in search api can not be null.");

            return this;
        }

        public Builder filter(@NonNull FilterOptions filterOption, @NonNull String value) {
            if (filterOption != null) {
                if (filter == null) {
                    if (value != null)
                        this.filter = filterOption.toString() + " eq " + value;
                    else
                        throw new RuntimeException("Filter value in search api can not be null.");
                } else
                    throw new RuntimeException("Filter option in search api must get just one value, check your code; you set many value for filter parameter.");
            } else
                throw new RuntimeException("Filter option in search api can not be null.");

            return this;
        }

        public SearchRequest build() {
            return new SearchRequest(text, selectOptions, filter, latitude, longitude);
        }
    }
}