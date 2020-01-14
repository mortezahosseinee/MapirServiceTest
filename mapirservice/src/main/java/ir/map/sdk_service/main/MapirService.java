package ir.map.sdk_service.main;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import ir.map.sdk_service.model.base.BaseModel;
import ir.map.sdk_service.model.base.MapirError;
import ir.map.sdk_service.model.base.MapirResponse;
import ir.map.sdk_service.model.enums.StaticMapMarker;
import ir.map.sdk_service.model.other.Geom;
import ir.map.sdk_service.model.request.DistanceMatrixRequest;
import ir.map.sdk_service.model.request.EstimatedTimeArrivalRequest;
import ir.map.sdk_service.model.request.GeofenceRequest;
import ir.map.sdk_service.model.request.ReverseGeoCodeRequest;
import ir.map.sdk_service.model.request.RouteRequest;
import ir.map.sdk_service.model.request.SearchRequest;
import ir.map.sdk_service.model.request.StaticMapRequest;
import ir.map.sdk_service.model.response.AutoCompleteSearchResponse;
import ir.map.sdk_service.model.response.DistanceMatrixResponse;
import ir.map.sdk_service.model.response.EstimatedTimeArrivalResponse;
import ir.map.sdk_service.model.response.FastReverseGeoCodeResponse;
import ir.map.sdk_service.model.response.GeofenceResponse;
import ir.map.sdk_service.model.response.PlaqueReverseGeoCodeResponse;
import ir.map.sdk_service.model.response.ReverseGeoCodeResponse;
import ir.map.sdk_service.model.response.RouteResponse;
import ir.map.sdk_service.model.response.SearchResponse;
import ir.map.sdk_service.model.response.StaticMapResponse;

import static ir.map.sdk_service.model.response.AutoCompleteSearchResponse.createAutoCompleteSearchResponse;
import static ir.map.sdk_service.model.response.DistanceMatrixResponse.createDistanceMatrixResponse;
import static ir.map.sdk_service.model.response.EstimatedTimeArrivalResponse.createEstimatedTimeArrivalResponse;
import static ir.map.sdk_service.model.response.FastReverseGeoCodeResponse.createFastReverseGeoCodeResponse;
import static ir.map.sdk_service.model.response.GeofenceResponse.createGeofenceResponse;
import static ir.map.sdk_service.model.response.PlaqueReverseGeoCodeResponse.createPlaqueReverseGeoCodeResponse;
import static ir.map.sdk_service.model.response.ReverseGeoCodeResponse.createReverseGeoCodeResponse;
import static ir.map.sdk_service.model.response.RouteResponse.createRouteResponse;
import static ir.map.sdk_service.model.response.SearchResponse.createSearchResponse;
import static ir.map.sdk_service.model.response.StaticMapResponse.createStaticMapResponse;

/**
 * MapirService prepares map.ir services
 *
 * @author Morteza Hosseini
 * @version 4.0.0
 * @since 2020-01-15
 */
public class MapirService {

    static String _ApiKey = "";

    private ResponseListener<ReverseGeoCodeResponse> reverseGeoCodeResponseListener;
    private ResponseListener<FastReverseGeoCodeResponse> fastReverseGeoCodeResponseListener;
    private ResponseListener<PlaqueReverseGeoCodeResponse> plaqueReverseGeoCodeResponseListener;
    private ResponseListener<SearchResponse> searchResponseListener;
    private ResponseListener<AutoCompleteSearchResponse> autoCompleteSearchResponseListener;
    private ResponseListener<RouteResponse> routeResponseListener;
    private ResponseListener<StaticMapResponse> staticMapResponseListener;
    private ResponseListener<DistanceMatrixResponse> distanceMatrixResponseListener;
    private ResponseListener<GeofenceResponse> geofenceResponseListener;
    private ResponseListener<EstimatedTimeArrivalResponse> estimatedTimeArrivalResponseListener;

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

    public static void init(String apiKey) {
        _ApiKey = apiKey;
    }

    //region Methods

    /**
     * This method is used to get reverse geocode response.
     *
     * @param latitude  This latitude in double
     * @param longitude This longitude in double
     * @param listener  This is callback we use to return response
     */
    public void reverseGeoCode(double latitude, double longitude, ResponseListener<ReverseGeoCodeResponse> listener) {

        this.reverseGeoCodeResponseListener = listener;

        new ReverseGeoCodeTask().execute(new ReverseGeoCodeRequest(latitude, longitude));
    }

    /**
     * This method is used to get fast reverse geocode response.
     *
     * @param latitude  This latitude in double
     * @param longitude This longitude in double
     * @param listener  This is callback we use to return response
     */
    public void fastReverseGeoCode(double latitude, double longitude, ResponseListener<FastReverseGeoCodeResponse> listener) {

        this.fastReverseGeoCodeResponseListener = listener;

        new FastReverseGeoCodeTask().execute(new ReverseGeoCodeRequest(latitude, longitude));
    }

    /**
     * This method is used to get plaque reverse geocode response.
     *
     * @param latitude  This latitude in double
     * @param longitude This longitude in double
     * @param listener  This is callback we use to return response
     */
    public void plaqueReverseGeoCode(double latitude, double longitude, ResponseListener<PlaqueReverseGeoCodeResponse> listener) {

        this.plaqueReverseGeoCodeResponseListener = listener;

        new PlaqueReverseGeoCodeTask().execute(new ReverseGeoCodeRequest(latitude, longitude));
    }

    /**
     * This method is used to get search response.
     *
     * @param requestBody This is object of type SearchRequest
     * @param listener    This is callback we use to return response
     */
    public void search(SearchRequest requestBody, ResponseListener<SearchResponse> listener) {

        this.searchResponseListener = listener;

        new SearchTask().execute(requestBody);
    }

    /**
     * This method is used to get autocomplete search response.
     *
     * @param requestBody This is object of type {@link SearchRequest}
     * @param listener    This is callback we use to return response
     */
    public void autoCompleteSearch(SearchRequest requestBody, ResponseListener<AutoCompleteSearchResponse> listener) {

        this.autoCompleteSearchResponseListener = listener;

        new AutoCompleteSearchTask().execute(requestBody);
    }

    /**
     * This method is used to get route response.
     *
     * @param requestBody This is object of type {@link RouteRequest}
     * @param listener    This is callback we use to return response
     */
    public void route(RouteRequest requestBody, ResponseListener<RouteResponse> listener) {

        this.routeResponseListener = listener;

        new RouteTask().execute(requestBody);
    }

    /**
     * This method is used to get static map response.
     *
     * @param latitude  This latitude in double
     * @param longitude This longitude in double
     * @param width     This is width of created static map image
     * @param height    This is height of created static map image
     * @param zoom    This is zoom-level of created static map image
     * @param label    This is label of created static map image positioned on bottom of marker
     * @param color     This is color or type of marker positioned on specific point: {@link StaticMapMarker}
     * @param listener  This is callback we use to return response
     */
    public void staticMap(Double latitude, Double longitude, int width, int height, int zoom, String label, StaticMapMarker color, ResponseListener<StaticMapResponse> listener) {

        this.staticMapResponseListener = listener;

        new StaticMapTask().execute(new StaticMapRequest(latitude, longitude, width, height, zoom, label, color));
    }

    /**
     * This method is used to get distance matrix response.
     *
     * @param requestBody This is object of type {@link DistanceMatrixRequest}
     * @param listener    This is callback we use to return response
     */
    public void distanceMatrix(DistanceMatrixRequest requestBody, ResponseListener<DistanceMatrixResponse> listener) {

        this.distanceMatrixResponseListener = listener;

        new DistanceMatrixTask().execute(requestBody);
    }

    /**
     * This method is used to get geofence response.
     *
     * @param latitude  This latitude in double
     * @param longitude This longitude in double
     * @param listener  This is callback we use to return response
     */
    public void geofence(double latitude, double longitude, ResponseListener<GeofenceResponse> listener) {

        this.geofenceResponseListener = listener;

        new GeofenceTask().execute(new GeofenceRequest(latitude, longitude));
    }

    /**
     * This method is used to get estimated time arrival response.
     *
     * @param requestBody This is object of type {@link EstimatedTimeArrivalRequest}
     * @param listener    This is callback we use to return response
     */
    public void estimatedTimeArrival(EstimatedTimeArrivalRequest requestBody, ResponseListener<EstimatedTimeArrivalResponse> listener) {

        this.estimatedTimeArrivalResponseListener = listener;

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

            if (response instanceof MapirResponse)
                reverseGeoCodeResponseListener.onSuccess((ReverseGeoCodeResponse) response);
            else if (response instanceof MapirError)
                reverseGeoCodeResponseListener.onError((MapirError) response);
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

            if (response instanceof MapirResponse)
                fastReverseGeoCodeResponseListener.onSuccess((FastReverseGeoCodeResponse) response);
            else if (response instanceof MapirError)
                fastReverseGeoCodeResponseListener.onError((MapirError) response);
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

            if (response instanceof MapirResponse)
                plaqueReverseGeoCodeResponseListener.onSuccess((PlaqueReverseGeoCodeResponse) response);
            else if (response instanceof MapirError)
                plaqueReverseGeoCodeResponseListener.onError((MapirError) response);
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

            if (response instanceof MapirResponse)
                searchResponseListener.onSuccess((SearchResponse) response);
            else if (response instanceof MapirError)
                searchResponseListener.onError((MapirError) response);
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

            if (response instanceof MapirResponse)
                autoCompleteSearchResponseListener.onSuccess((AutoCompleteSearchResponse) response);
            else if (response instanceof MapirError)
                autoCompleteSearchResponseListener.onError((MapirError) response);
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

            if (response instanceof MapirResponse)
                routeResponseListener.onSuccess((RouteResponse) response);
            else if (response instanceof MapirError)
                routeResponseListener.onError((MapirError) response);
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

            if (response instanceof MapirResponse)
                staticMapResponseListener.onSuccess((StaticMapResponse) response);
            else if (response instanceof MapirError)
                staticMapResponseListener.onError((MapirError) response);
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

            if (response instanceof MapirResponse)
                distanceMatrixResponseListener.onSuccess((DistanceMatrixResponse) response);
            else if (response instanceof MapirError)
                distanceMatrixResponseListener.onError((MapirError) response);
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

            if (response instanceof MapirResponse)
                geofenceResponseListener.onSuccess((GeofenceResponse) response);
            else if (response instanceof MapirError)
                geofenceResponseListener.onError((MapirError) response);
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

            if (response instanceof MapirResponse)
                estimatedTimeArrivalResponseListener.onSuccess((EstimatedTimeArrivalResponse) response);
            else if (response instanceof MapirError)
                estimatedTimeArrivalResponseListener.onError((MapirError) response);
        }
    }

    //endregion Classes

    //region Utils
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
    //endregion Utils
}
