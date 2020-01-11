package ir.mapservice.models.responses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.mapservice.models.base.BaseModel;
import ir.mapservice.models.base.MapirResponse;
import ir.mapservice.models.other.Leg;
import ir.mapservice.models.other.Maneuver;
import ir.mapservice.models.other.RouteItem;
import ir.mapservice.models.other.Step;
import ir.mapservice.models.other.WayPoint;

public class RouteResponse extends MapirResponse {

    private List<RouteItem> routes = null;
    private List<WayPoint> wayPoints = null;

    private RouteResponse(List<RouteItem> routes, List<WayPoint> wayPoints) {
        this.routes = routes;
        this.wayPoints = wayPoints;
    }

    public List<RouteItem> getRoutes() {
        return routes;
    }

    public List<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public static BaseModel createRouteResponse(String data) {
        try {
            JSONObject tempData = new JSONObject(data);

            JSONArray tempRouteItems = new JSONArray(tempData.get("routes").toString());
            List<RouteItem> routeItems = new ArrayList<>();
            for (int i = 0; i < tempRouteItems.length(); i++) {
                JSONObject tempRouteItem = new JSONObject(tempRouteItems.get(i).toString());

                //region legs
                JSONArray tempLegs = new JSONArray(tempRouteItem.get("legs").toString());
                List<Leg> legs = new ArrayList<>();
                for (int j = 0; j < tempLegs.length(); j++) {
                    JSONObject tempLeg = new JSONObject(tempLegs.get(j).toString());

                    //region steps
                    JSONArray tempSteps = new JSONArray(tempLeg.get("steps").toString());
                    List<Step> steps = new ArrayList<>();
                    for (int k = 0; k < tempSteps.length(); k++) {
                        JSONObject tempStep = new JSONObject(tempSteps.get(k).toString());

                        Step item = new Step(
                                tempStep.getString("driving_side"),
                                tempStep.getString("geometry"),
                                tempStep.getString("mode"),
                                tempStep.getInt("duration"),
                                new Maneuver(),
                                tempStep.getInt("weight"),
                                tempStep.getInt("distance"),
                                tempStep.getString("distance")
                        );

                        steps.add(item);
                    }
                    //endregion steps

                    Leg item = new Leg(
                            tempLeg.getDouble("distance"),
                            tempLeg.getDouble("duration"),
                            tempLeg.getString("summary"),
                            tempLeg.getDouble("weight"),
                            steps
                    );

                    legs.add(item);
                }
                //endregion legs

                RouteItem item = new RouteItem(
                        tempRouteItem.getString("geometry"),
                        tempRouteItem.getDouble("distance"),
                        tempRouteItem.getDouble("duration"),
                        tempRouteItem.getString("weight_name"),
                        tempRouteItem.getDouble("weight"),
                        legs
                );

                routeItems.add(item);
            }

            JSONArray tempWayPoints = new JSONArray(tempData.get("waypoints").toString());
            List<WayPoint> wayPoints = new ArrayList<>();
            for (int i = 0; i < tempWayPoints.length(); i++) {
                JSONObject tempWayPoint = new JSONObject(tempWayPoints.get(i).toString());

                JSONArray tempLocation = new JSONArray(tempWayPoint.getString("location"));

                WayPoint item = new WayPoint(
                        tempWayPoint.getString("hint"),
                        tempWayPoint.getDouble("distance"),
                        tempWayPoint.getString("name"),
                        tempLocation.getDouble(1),
                        tempLocation.getDouble(0)
                );

                wayPoints.add(item);
            }

            return new RouteResponse(
                    routeItems,
                    wayPoints
            );
        } catch (Exception e) {
            return null;
        }
    }
}

