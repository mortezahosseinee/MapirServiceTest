package ir.map.sdk_service.model.request;

import androidx.annotation.NonNull;

import ir.map.sdk_service.model.enums.RouteOverView;
import ir.map.sdk_service.model.enums.RoutePlan;
import ir.map.sdk_service.model.enums.RouteType;

import static ir.map.sdk_service.model.enums.RouteType.DRIVING;

public class RouteRequest {

    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;

    private String routeType;
    private String routePlan = null;
    private String routeOverView;

    private boolean alternatives;
    private boolean steps;

    private RouteRequest(Double startLatitude, Double startLongitude, Double endLatitude, Double endLongitude, String routeType, String routePlan, String routeOverView, boolean alternative, boolean steps) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.alternatives = alternative;
        this.steps = steps;

        this.routeType = routeType;
        this.routeOverView = routeOverView;
    }

    public Double getStartLatitude() {
        return startLatitude;
    }

    public Double getStartLongitude() {
        return startLongitude;
    }

    public Double getEndLatitude() {
        return endLatitude;
    }

    public Double getEndLongitude() {
        return endLongitude;
    }

    public String getRouteType() {
        return routeType;
    }

    public String getRoutePlan() {
        return routePlan;
    }

    public String getRouteOverView() {
        return routeOverView;
    }

    public boolean isAlternatives() {
        return alternatives;
    }

    public boolean needSteps() {
        return steps;
    }

    public boolean hasRoutePlan() {
        return this.routePlan != null;
    }

    public static class Builder {

        private Double startLatitude;
        private Double startLongitude;
        private Double endLatitude;
        private Double endLongitude;
        private String routeType;

        private String routePlan = null;
        private String routeOverView = RouteOverView.NONE.toString();

        private boolean alternatives = false;
        private boolean steps = false;

        public Builder(
                Double startLatitude,
                Double startLongitude,
                Double endLatitude,
                Double endLongitude,
                @NonNull RouteType routeType
        ) {
            this.startLatitude = startLatitude;
            this.startLongitude = startLongitude;
            this.endLatitude = endLatitude;
            this.endLongitude = endLongitude;
            this.routeType = routeType.toString();
        }

        public Builder routePlan(@NonNull RoutePlan routePlan) {
            if (routePlan != null) {
                if (this.routeType.equals(DRIVING.toString()))
                    this.routePlan = routePlan.toString();
                else
                    throw new RuntimeException("can't have RoutePlan with RouteType equals to BICYCLE or WALKING");
            } else
                throw new RuntimeException("routePlan can't be null.");

            return this;
        }

        public Builder routeOverView(@NonNull RouteOverView routeOverView) {
            if (routeOverView != null)
                this.routeOverView = routeOverView.toString();
            else
                throw new RuntimeException("routeOverView can not be null.");

            return this;
        }

        public Builder alternative(boolean value) {
            this.alternatives = value;

            return this;
        }

        public Builder steps(boolean value) {
            this.steps = value;

            return this;
        }

        public RouteRequest build() {
            return new RouteRequest(startLatitude, startLongitude, endLatitude, endLongitude, routeType, routePlan, routeOverView, alternatives, steps);
        }
    }
}
