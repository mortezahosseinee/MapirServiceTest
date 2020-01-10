package ir.mapservice.models.requests;

import androidx.annotation.NonNull;

import ir.mapservice.models.other.RouteOverView;
import ir.mapservice.models.other.RoutePlan;
import ir.mapservice.models.other.RouteType;

import static ir.mapservice.models.other.RouteType.DRIVING;

public class RouteRequest {

    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;

    private String routeType;
    private String routePlan = "";
    private String routeOverView = RouteOverView.NONE.toString();

    private boolean alternatives = false;
    private boolean steps = false;

    public RouteRequest(
            Double startLatitude,
            Double startLongitude,
            Double endLatitude,
            Double endLongitude,
            @NonNull RouteType routeType) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.routeType = routeType.toString();
    }

    public RouteRequest(
            Double startLatitude,
            Double startLongitude,
            Double endLatitude,
            Double endLongitude,
            @NonNull RouteType routeType,
            @NonNull RoutePlan routePlan) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;

        this.routeType = routeType.toString();

        if (routeType == DRIVING)
            this.routePlan = routePlan.toString();
        else
            throw new RuntimeException("can't have RoutePlan with RouteType equals to BICYCLE or WALKING");
    }

    public RouteRequest(
            Double startLatitude,
            Double startLongitude,
            Double endLatitude,
            Double endLongitude,
            @NonNull RouteType routeType,
            boolean alternative,
            boolean steps
    ) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.routeType = routeType.toString();
        this.alternatives = alternative;
        this.steps = steps;
    }

    public RouteRequest(
            Double startLatitude,
            Double startLongitude,
            Double endLatitude,
            Double endLongitude,
            @NonNull RouteType routeType,
            @NonNull RoutePlan routePlan,
            boolean alternative,
            boolean steps
    ) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.alternatives = alternative;
        this.steps = steps;

        this.routeType = routeType.toString();

        if (routeType == DRIVING)
            this.routePlan = routePlan.toString();
        else
            throw new RuntimeException("can't have RoutePlan with RouteType equals to BICYCLE or WALKING");
    }

    public RouteRequest(
            Double startLatitude,
            Double startLongitude,
            Double endLatitude,
            Double endLongitude,
            @NonNull RouteType routeType,
            @NonNull RouteOverView routeOverView
    ) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.routeType = routeType.toString();
        this.routeOverView = routeOverView.toString();
    }

    public RouteRequest(
            Double startLatitude,
            Double startLongitude,
            Double endLatitude,
            Double endLongitude,
            @NonNull RouteType routeType,
            @NonNull RoutePlan routePlan,
            @NonNull RouteOverView routeOverView
    ) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;

        this.routeType = routeType.toString();
        this.routeOverView = routeOverView.toString();

        if (routeType == DRIVING)
            this.routePlan = routePlan.toString();
        else
            throw new RuntimeException("can't have RoutePlan with RouteType equals to BICYCLE or WALKING");
    }

    public RouteRequest(
            Double startLatitude,
            Double startLongitude,
            Double endLatitude,
            Double endLongitude,
            @NonNull RouteType routeType,
            boolean alternative,
            boolean steps,
            @NonNull RouteOverView routeOverView
    ) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.routeType = routeType.toString();
        this.alternatives = alternative;
        this.steps = steps;
        this.routeOverView = routeOverView.toString();
    }

    public RouteRequest(
            Double startLatitude,
            Double startLongitude,
            Double endLatitude,
            Double endLongitude,
            @NonNull RouteType routeType,
            @NonNull RoutePlan routePlan,
            boolean alternative,
            boolean steps,
            @NonNull RouteOverView routeOverView
    ) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.alternatives = alternative;
        this.steps = steps;

        this.routeType = routeType.toString();
        this.routeOverView = routeOverView.toString();

        if (routeType == DRIVING)
            this.routePlan = routePlan.toString();
        else
            throw new RuntimeException("can't have RoutePlan with RouteType equals to BICYCLE or WALKING");
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
}
