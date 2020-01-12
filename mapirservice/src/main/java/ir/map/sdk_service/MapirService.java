package ir.map.sdk_service;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import ir.map.sdk_service.models.base.BaseModel;
import ir.map.sdk_service.models.base.MapirError;
import ir.map.sdk_service.models.base.MapirResponse;
import ir.map.sdk_service.models.enums.StaticMapMarker;
import ir.map.sdk_service.models.listeners.ResponseListener;
import ir.map.sdk_service.models.other.Geom;
import ir.map.sdk_service.models.requests.DistanceMatrixRequest;
import ir.map.sdk_service.models.requests.EstimatedTimeArrivalRequest;
import ir.map.sdk_service.models.requests.GeofenceRequest;
import ir.map.sdk_service.models.requests.ReverseGeoCodeRequest;
import ir.map.sdk_service.models.requests.RouteRequest;
import ir.map.sdk_service.models.requests.SearchRequest;
import ir.map.sdk_service.models.requests.StaticMapRequest;

import static ir.map.sdk_service.models.responses.AutoCompleteSearchResponse.createAutoCompleteSearchResponse;
import static ir.map.sdk_service.models.responses.DistanceMatrixResponse.createDistanceMatrixResponse;
import static ir.map.sdk_service.models.responses.EstimatedTimeArrivalResponse.createEstimatedTimeArrivalResponse;
import static ir.map.sdk_service.models.responses.FastReverseGeoCodeResponse.createFastReverseGeoCodeResponse;
import static ir.map.sdk_service.models.responses.GeofenceResponse.createGeofenceResponse;
import static ir.map.sdk_service.models.responses.PlaqueReverseGeoCodeResponse.createPlaqueReverseGeoCodeResponse;
import static ir.map.sdk_service.models.responses.ReverseGeoCodeResponse.createReverseGeoCodeResponse;
import static ir.map.sdk_service.models.responses.RouteResponse.createRouteResponse;
import static ir.map.sdk_service.models.responses.SearchResponse.createSearchResponse;
import static ir.map.sdk_service.models.responses.StaticMapResponse.createStaticMapResponse;

public class MapirService {

    static String _ApiKey = "";
    private final ResponseListener listener;

    private static final String REVERSE = "reverse";
    private static final String FAST_REVERSE = "fast-reverse";
    private static final String PLAQUE_REVERSE = "reverse/no";
    private static final String SEARCH = "search/v2";
    private static final String AUTOCOMPLETE_SEARCH = "search/v2/autocomplete";
    private static final String ROUTE = "routes/";
    private static final String STATIC_MAP = "static";
    private static final String DISTANCE_MATRIX = "distancematrix";
    private static final String GEOFENCE = "geofence/boundaries";
    private static final String ETA = "eta/driving/";

    public MapirService(ResponseListener listener) {
        this.listener = listener;
    }

    public static void init(String apiKey) {
        _ApiKey = apiKey;
    }

    //region Methods
    public void reverseGeoCode(double latitude, double longitude) {
        new ReverseGeoCodeTask().execute(new ReverseGeoCodeRequest(latitude, longitude));
    }

    public void fastReverseGeoCode(double latitude, double longitude) {
        new FastReverseGeoCodeTask().execute(new ReverseGeoCodeRequest(latitude, longitude));
    }

    public void plaqueReverseGeoCode(double latitude, double longitude) {
        new PlaqueReverseGeoCodeTask().execute(new ReverseGeoCodeRequest(latitude, longitude));
    }

    public void search(SearchRequest requestBody) {
        new SearchTask().execute(requestBody);
    }

    public void autoCompleteSearch(SearchRequest requestBody) {
        new AutoCompleteSearchTask().execute(requestBody);
    }

    public void route(RouteRequest requestBody) {
        new RouteTask().execute(requestBody);
    }

    public void staticMap(Double latitude, Double longitude, int width, int height, int zoom, String label, StaticMapMarker color) {
        new StaticMapTask().execute(new StaticMapRequest(latitude, longitude, width, height, zoom, label, color));
    }

    public void distanceMatrix(DistanceMatrixRequest requestBody) {
        new DistanceMatrixTask().execute(requestBody);
    }

    public void geofence(double latitude, double longitude) {
        new GeofenceTask().execute(new GeofenceRequest(latitude, longitude));
    }

    public void estimatedTimeArrival(EstimatedTimeArrivalRequest requestBody) {
        new EstimatedTimeArrivalTask().execute(requestBody);
    }
    //endregion Methods

    //region Classes
    private class ReverseGeoCodeTask extends AsyncTask<ReverseGeoCodeRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(ReverseGeoCodeRequest... reverseGeoCodeRequests) {
            return createResponse(
                    new HttpGetRequest(
                            REVERSE +
                                    "?lat=" + reverseGeoCodeRequests[0].getLatitude() +
                                    "&lon=" + reverseGeoCodeRequests[0].getLongitude()
                    ),
                    REVERSE);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    private class FastReverseGeoCodeTask extends AsyncTask<ReverseGeoCodeRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(ReverseGeoCodeRequest... reverseGeoCodeRequests) {
            return createResponse(
                    new HttpGetRequest(
                            FAST_REVERSE +
                                    "?lat=" + reverseGeoCodeRequests[0].getLatitude() +
                                    "&lon=" + reverseGeoCodeRequests[0].getLongitude()
                    ),
                    FAST_REVERSE);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    private class PlaqueReverseGeoCodeTask extends AsyncTask<ReverseGeoCodeRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(ReverseGeoCodeRequest... reverseGeoCodeRequests) {
            return createResponse(
                    new HttpGetRequest(
                            PLAQUE_REVERSE +
                                    "?lat=" + reverseGeoCodeRequests[0].getLatitude() +
                                    "&lon=" + reverseGeoCodeRequests[0].getLongitude()
                    ),
                    PLAQUE_REVERSE);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    private class SearchTask extends AsyncTask<SearchRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(SearchRequest... searchRequest) {
            HttpGetRequest request;
            if (searchRequest[0].getSelects() == null && searchRequest[0].getFilter() == null)
                request = new HttpGetRequest(
                        SEARCH +
                                "?text=" + searchRequest[0].getText()
                );
            else if (searchRequest[0].getSelects() == null && searchRequest[0].getFilter() != null) {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$filter=" + searchRequest[0].getFilter() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$filter=" + searchRequest[0].getFilter()
                    );

            } else if (searchRequest[0].getSelects() != null && searchRequest[0].getFilter() == null) {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects()
                    );
            } else {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&$filter=" + searchRequest[0].getFilter() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&$filter=" + searchRequest[0].getFilter()
                    );
            }

            return createResponse(request, SEARCH);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    private class AutoCompleteSearchTask extends AsyncTask<SearchRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(SearchRequest... searchRequest) {
            HttpGetRequest request;
            if (searchRequest[0].getSelects() == null && searchRequest[0].getFilter() == null)
                request = new HttpGetRequest(
                        AUTOCOMPLETE_SEARCH +
                                "?text=" + searchRequest[0].getText()
                );
            else if (searchRequest[0].getSelects() == null && searchRequest[0].getFilter() != null) {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            AUTOCOMPLETE_SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$filter=" + searchRequest[0].getFilter() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            AUTOCOMPLETE_SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$filter=" + searchRequest[0].getFilter()
                    );

            } else if (searchRequest[0].getSelects() != null && searchRequest[0].getFilter() == null) {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            AUTOCOMPLETE_SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            AUTOCOMPLETE_SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects()
                    );
            } else {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            AUTOCOMPLETE_SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&$filter=" + searchRequest[0].getFilter() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            AUTOCOMPLETE_SEARCH +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&$filter=" + searchRequest[0].getFilter()
                    );
            }

            return createResponse(request, AUTOCOMPLETE_SEARCH);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    private class RouteTask extends AsyncTask<RouteRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(RouteRequest... routeRequest) {
            HttpGetRequest request;
            if (routeRequest[0].getRouteType().equals("route"))
                request = new HttpGetRequest(
                        ROUTE +
                                (routeRequest[0].hasRoutePlan() ? routeRequest[0].getRoutePlan() : routeRequest[0].getRouteType()) +
                                "/v1/driving" +
                                "/" + routeRequest[0].getStartLongitude() +
                                "," + routeRequest[0].getStartLatitude() +
                                ";" + routeRequest[0].getEndLongitude() +
                                "," + routeRequest[0].getEndLatitude() +
                                "?alternatives=" + routeRequest[0].isAlternatives() +
                                "&steps=" + routeRequest[0].needSteps() +
                                (routeRequest[0].getRouteOverView().equals("false") ? "" : "&overview=" + routeRequest[0].getRouteOverView())
                );
            else
                request = new HttpGetRequest(
                        ROUTE + routeRequest[0].getRouteType() +
                                "/v1/driving" +
                                "/" + routeRequest[0].getStartLongitude() +
                                "," + routeRequest[0].getStartLatitude() +
                                ";" + routeRequest[0].getEndLongitude() +
                                "," + routeRequest[0].getEndLatitude() +
                                "?alternatives=" + routeRequest[0].isAlternatives() +
                                "&steps=" + routeRequest[0].needSteps() +
                                (routeRequest[0].getRouteOverView().equals("false") ? "" : "&overview=" + routeRequest[0].getRouteOverView())
                );

            return createResponse(request, ROUTE);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    private class StaticMapTask extends AsyncTask<StaticMapRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(StaticMapRequest... staticMapRequest) {
            return createResponse(
                    new HttpGetRequest(
                            STATIC_MAP +
                                    "?width=" + staticMapRequest[0].getWidth() +
                                    "&height=" + staticMapRequest[0].getHeight() +
                                    "&zoom_level=" + staticMapRequest[0].getZoom() + "&markers=" +
                                    "color:" + staticMapRequest[0].getColor() +
                                    "|label:" + staticMapRequest[0].getLabel() +
                                    "|" + staticMapRequest[0].getLongitude() +
                                    "," + staticMapRequest[0].getLatitude()
                    ),
                    STATIC_MAP);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    private class DistanceMatrixTask extends AsyncTask<DistanceMatrixRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(DistanceMatrixRequest... distanceMatrixRequest) {
            return createResponse(
                    new HttpGetRequest(
                            DISTANCE_MATRIX +
                                    "?origins=" + distanceMatrixRequest[0].getOrigins() +
                                    "&destinations=" + distanceMatrixRequest[0].getDestinations() +
                                    "&sorted=" + distanceMatrixRequest[0].isSorted() +
                                    (distanceMatrixRequest[0].hasFilter() ? "&$filter=" + distanceMatrixRequest[0].getFilter() : "")
                    ),
                    DISTANCE_MATRIX);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    private class GeofenceTask extends AsyncTask<GeofenceRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(GeofenceRequest... geofenceRequest) {
            return createResponse(
                    new HttpGetRequest(
                            GEOFENCE +
                                    "?lat=" + geofenceRequest[0].getLatitude() +
                                    "&lon=" + geofenceRequest[0].getLongitude()
                    ),
                    GEOFENCE);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    private class EstimatedTimeArrivalTask extends AsyncTask<EstimatedTimeArrivalRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(EstimatedTimeArrivalRequest... estimatedTimeArrivalRequest) {
            HttpGetRequest request = new HttpGetRequest(
                    ETA + estimatedTimeArrivalRequest[0].getCoordinates()
            );

            return createResponse(request, ETA);
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    //endregion Classes

    //region Utils
    public static Geom createGeom(String geom) {
        try {
            JSONObject tempGeom = new JSONObject(geom);
            JSONArray tempCoordinates = new JSONArray(tempGeom.get("coordinates").toString());

            return new Geom(tempGeom.getString("type"), new Double[]{
                    tempCoordinates.getDouble(0),
                    tempCoordinates.getDouble(1)
            });
        } catch (Exception ignored) {
        }

        return null;
    }

    private void handleResponse(BaseModel response) {
        if (response instanceof MapirResponse)
            listener.onSuccess((MapirResponse) response);
        else if (response instanceof MapirError)
            listener.onError((MapirError) response);
    }

    private BaseModel createResponse(HttpGetRequest request, String api) {
        int statusCode = request.getResponseCode();

        if (statusCode == 200)
            switch (api) {
                case REVERSE:
                    return createReverseGeoCodeResponse(request.getResponseBody());
                case FAST_REVERSE:
                    return createFastReverseGeoCodeResponse(request.getResponseBody());
                case PLAQUE_REVERSE:
                    return createPlaqueReverseGeoCodeResponse(request.getResponseBody());
                case SEARCH:
                    return createSearchResponse(request.getResponseBody());
                case AUTOCOMPLETE_SEARCH:
                    return createAutoCompleteSearchResponse(request.getResponseBody());
                case ROUTE:
                    return createRouteResponse(request.getResponseBody());
                case STATIC_MAP:
                    return createStaticMapResponse(request.getBitmap());
                case DISTANCE_MATRIX:
                    return createDistanceMatrixResponse(request.getResponseBody());
                case GEOFENCE:
                    return createGeofenceResponse(request.getResponseBody());
                case ETA:
                    return createEstimatedTimeArrivalResponse(request.getResponseBody());
            }
        else if (statusCode == 400)
            return new MapirError(api + " api: bad request.", 400);
        else if (statusCode == 401)
            return new MapirError(api + " api: get valid apiKey from https://corp.map.ir", 401);
        else if (statusCode == 402)
            return new MapirError(api + " api: bad request.", 402);
        else if (statusCode == 404)
            return new MapirError(api + " api: not found response.", 400);

        return null;
    }
    //endregion Utils
}
