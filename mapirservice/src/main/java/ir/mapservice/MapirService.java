package ir.mapservice;

import android.os.AsyncTask;
import android.util.Pair;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import ir.mapservice.models.base.BaseModel;
import ir.mapservice.models.base.MapirError;
import ir.mapservice.models.base.MapirResponse;
import ir.mapservice.models.listeners.ResponseListener;
import ir.mapservice.models.other.DistanceMatrixOutputType;
import ir.mapservice.models.other.DistanceMatrixPointRequest;
import ir.mapservice.models.other.FilterOptions;
import ir.mapservice.models.other.Geom;
import ir.mapservice.models.other.RoutePlan;
import ir.mapservice.models.other.RouteType;
import ir.mapservice.models.other.SelectOptions;
import ir.mapservice.models.other.StaticMapMarker;
import ir.mapservice.models.requests.DistanceMatrixRequest;
import ir.mapservice.models.requests.ReverseGeoCodeRequest;
import ir.mapservice.models.requests.RouteRequest;
import ir.mapservice.models.requests.SearchRequest;
import ir.mapservice.models.requests.StaticMapRequest;

import static ir.mapservice.models.responses.AutoCompleteSearchResponse.createAutoCompleteSearchResponse;
import static ir.mapservice.models.responses.DistanceMatrixResponse.createDistanceMatrixResponse;
import static ir.mapservice.models.responses.FastReverseGeoCodeResponse.createFastReverseGeoCodeResponse;
import static ir.mapservice.models.responses.PlaqueReverseGeoCodeResponse.createPlaqueReverseGeoCodeResponse;
import static ir.mapservice.models.responses.ReverseGeoCodeResponse.createReverseGeoCodeResponse;
import static ir.mapservice.models.responses.RouteResponse.createRouteResponse;
import static ir.mapservice.models.responses.SearchResponse.createSearchResponse;
import static ir.mapservice.models.responses.StaticMapResponse.createStaticMapResponse;

public class MapirService {

    static String _ApiKey = "";
    private final ResponseListener listener;

    public MapirService(ResponseListener listener) {
        this.listener = listener;
    }

    public static void setApiKey(String apiKey) {
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

    public void search(String text) {
        new SearchTask().execute(new SearchRequest(text));
    }

    public void search(String text, @Nullable SelectOptions[] selects, @Nullable Pair<FilterOptions, Object> filter) {
        new SearchTask().execute(new SearchRequest(text, selects, filter));
    }

    public void search(String text, @Nullable SelectOptions[] selects, @Nullable Pair<FilterOptions, Object> filter, Double latitude, Double longitude) {
        new SearchTask().execute(new SearchRequest(text, selects, filter, latitude, longitude));
    }

    public void autoCompleteSearch(String text) {
        new AutoCompleteSearchTask().execute(new SearchRequest(text));
    }

    public void autoCompleteSearch(String text, @Nullable SelectOptions[] selects, @Nullable Pair<FilterOptions, Object> filter) {
        new AutoCompleteSearchTask().execute(new SearchRequest(text, selects, filter));
    }

    public void autoCompleteSearch(String text, @Nullable SelectOptions[] selects, @Nullable Pair<FilterOptions, Object> filter, Double latitude, Double longitude) {
        new AutoCompleteSearchTask().execute(new SearchRequest(text, selects, filter, latitude, longitude));
    }

    public void route(Double startLatitude, Double startLongitude, Double endLatitude, Double endLongitude, RouteType routeType) {
        new RouteTask().execute(new RouteRequest(startLatitude, startLongitude, endLatitude, endLongitude, routeType));
    }

    public void route(Double startLatitude, Double startLongitude, Double endLatitude, Double endLongitude, RouteType routeType, RoutePlan routePlan) {
        new RouteTask().execute(new RouteRequest(startLatitude, startLongitude, endLatitude, endLongitude, routeType, routePlan));
    }

    public void staticMap(Double latitude, Double longitude, int width, int height, int zoom, String label, StaticMapMarker color) {
        new StaticMapTask().execute(new StaticMapRequest(latitude, longitude, width, height, zoom, label, color));
    }

    public void distanceMatrix(List<DistanceMatrixPointRequest> origins, List<DistanceMatrixPointRequest> destinations) {
        new DistanceMatrixTask().execute(new DistanceMatrixRequest(origins, destinations, false));
    }

    public void distanceMatrix(List<DistanceMatrixPointRequest> origins, List<DistanceMatrixPointRequest> destinations, boolean sorted) {
        new DistanceMatrixTask().execute(new DistanceMatrixRequest(origins, destinations, sorted));
    }

    public void distanceMatrix(List<DistanceMatrixPointRequest> origins, List<DistanceMatrixPointRequest> destinations, DistanceMatrixOutputType outputType) {
        new DistanceMatrixTask().execute(new DistanceMatrixRequest(origins, destinations, false, outputType));
    }

    public void distanceMatrix(List<DistanceMatrixPointRequest> origins, List<DistanceMatrixPointRequest> destinations, boolean sorted, DistanceMatrixOutputType outputType) {
        new DistanceMatrixTask().execute(new DistanceMatrixRequest(origins, destinations, sorted, outputType));
    }
    //endregion Methods

    //region Classes
    class ReverseGeoCodeTask extends AsyncTask<ReverseGeoCodeRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(ReverseGeoCodeRequest... reverseGeoCodeRequests) {
            HttpGetRequest request = new HttpGetRequest(
                    "reverse" +
                            "?lat=" + reverseGeoCodeRequests[0].getLatitude() +
                            "&lon=" + reverseGeoCodeRequests[0].getLongitude()
            );


            return createResponse(request, "reverseGeoCode");
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    class FastReverseGeoCodeTask extends AsyncTask<ReverseGeoCodeRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(ReverseGeoCodeRequest... reverseGeoCodeRequests) {
            HttpGetRequest request = new HttpGetRequest(
                    "fast-reverse" +
                            "?lat=" + reverseGeoCodeRequests[0].getLatitude() +
                            "&lon=" + reverseGeoCodeRequests[0].getLongitude()
            );

            return createResponse(request, "fastReverseGeoCode");
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    class PlaqueReverseGeoCodeTask extends AsyncTask<ReverseGeoCodeRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(ReverseGeoCodeRequest... reverseGeoCodeRequests) {
            HttpGetRequest request = new HttpGetRequest(
                    "reverse/no" +
                            "?lat=" + reverseGeoCodeRequests[0].getLatitude() +
                            "&lon=" + reverseGeoCodeRequests[0].getLongitude()
            );

            return createResponse(request, "plaqueReverseGeoCode");
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    class SearchTask extends AsyncTask<SearchRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(SearchRequest... searchRequest) {
            HttpGetRequest request;
            if (searchRequest[0].getSelects() == null && searchRequest[0].getFilter() == null)
                request = new HttpGetRequest(
                        "search/v2" +
                                "?text=" + searchRequest[0].getText()
                );
            else if (searchRequest[0].getSelects() == null && searchRequest[0].getFilter() != null) {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            "search/v2" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$filter=" + searchRequest[0].getFilter() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            "search/v2" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$filter=" + searchRequest[0].getFilter()
                    );

            } else if (searchRequest[0].getSelects() != null && searchRequest[0].getFilter() == null) {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            "search/v2" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            "search/v2" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects()
                    );
            } else {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            "search/v2" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&$filter=" + searchRequest[0].getFilter() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            "search/v2" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&$filter=" + searchRequest[0].getFilter()
                    );
            }

            return createResponse(request, "search");
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    class AutoCompleteSearchTask extends AsyncTask<SearchRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(SearchRequest... searchRequest) {
            HttpGetRequest request;
            if (searchRequest[0].getSelects() == null && searchRequest[0].getFilter() == null)
                request = new HttpGetRequest(
                        "search/v2/autocomplete" +
                                "?text=" + searchRequest[0].getText()
                );
            else if (searchRequest[0].getSelects() == null && searchRequest[0].getFilter() != null) {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            "search/v2/autocomplete" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$filter=" + searchRequest[0].getFilter() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            "search/v2/autocomplete" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$filter=" + searchRequest[0].getFilter()
                    );

            } else if (searchRequest[0].getSelects() != null && searchRequest[0].getFilter() == null) {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            "search/v2/autocomplete" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            "search/v2/autocomplete" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects()
                    );
            } else {
                if (searchRequest[0].hasLatLng())
                    request = new HttpGetRequest(
                            "search/v2/autocomplete" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&$filter=" + searchRequest[0].getFilter() +
                                    "&lat=" + searchRequest[0].getLatitude() +
                                    "&lon=" + searchRequest[0].getLongitude()
                    );
                else
                    request = new HttpGetRequest(
                            "search/v2/autocomplete" +
                                    "?text=" + searchRequest[0].getText() +
                                    "&$select=" + searchRequest[0].getSelects() +
                                    "&$filter=" + searchRequest[0].getFilter()
                    );
            }

            return createResponse(request, "autoCompleteSearch");
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    class RouteTask extends AsyncTask<RouteRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(RouteRequest... routeRequest) {
            HttpGetRequest request;
            if (routeRequest[0].getRouteType().equals("route"))
                request = new HttpGetRequest(
                        "routes/" +
                                (!routeRequest[0].getRoutePlan().equals("") ? routeRequest[0].getRoutePlan() : routeRequest[0].getRouteType()) +
                                "/v1/driving" +
                                "/" + routeRequest[0].getStartLongitude() +
                                "," + routeRequest[0].getStartLatitude() +
                                ";" + routeRequest[0].getEndLongitude() +
                                "," + routeRequest[0].getEndLatitude() +
                                "?alternatives=" + routeRequest[0].isAlternatives() +
                                "&steps=" + routeRequest[0].needSteps() +
                                (routeRequest[0].getRouteOverView().equals("false") ? "" : routeRequest[0].getRouteOverView())
                );
            else
                request = new HttpGetRequest(
                        "routes/" + routeRequest[0].getRouteType() +
                                "/v1/driving" +
                                "/" + routeRequest[0].getStartLongitude() +
                                "," + routeRequest[0].getStartLatitude() +
                                ";" + routeRequest[0].getEndLongitude() +
                                "," + routeRequest[0].getEndLatitude() +
                                "?alternatives=" + routeRequest[0].isAlternatives() +
                                "&steps=" + routeRequest[0].needSteps() +
                                (routeRequest[0].getRouteOverView().equals("false") ? "" : routeRequest[0].getRouteOverView())
                );

            return createResponse(request, "route");
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    class StaticMapTask extends AsyncTask<StaticMapRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(StaticMapRequest... staticMapRequest) {
            HttpGetRequest request = new HttpGetRequest(
                    "static" +
                            "?width=" + staticMapRequest[0].getWidth() +
                            "&height=" + staticMapRequest[0].getHeight() +
                            "&zoom_level=" + staticMapRequest[0].getZoom() + "&markers=" +
                            "color:" + staticMapRequest[0].getColor() +
                            "|label:" + staticMapRequest[0].getLabel() +
                            "|" + staticMapRequest[0].getLongitude() +
                            "," + staticMapRequest[0].getLatitude()
            );

            return createResponse(request, "staticMap");
        }

        @Override
        protected void onPostExecute(BaseModel response) {
            super.onPostExecute(response);

            handleResponse(response);
        }
    }

    class DistanceMatrixTask extends AsyncTask<DistanceMatrixRequest, Void, BaseModel> {
        @Override
        protected BaseModel doInBackground(DistanceMatrixRequest... distanceMatrixRequest) {
            HttpGetRequest request = new HttpGetRequest(
                    "distancematrix" +
                            "?origins=" + distanceMatrixRequest[0].getOrigins() +
                            "&destinations=" + distanceMatrixRequest[0].getDestinations() +
                            "&sorted=" + distanceMatrixRequest[0].isSorted() +
                            (distanceMatrixRequest[0].hasFilter() ? "&$filter=" + distanceMatrixRequest[0].getFilter() : "")
            );

            return createResponse(request, "distanceMatrix");
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
        } catch (Exception e) {
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
                case "reverseGeoCode":
                    return createReverseGeoCodeResponse(request.getResponseBody());
                case "fastReverseGeoCode":
                    return createFastReverseGeoCodeResponse(request.getResponseBody());
                case "plaqueReverseGeoCode":
                    return createPlaqueReverseGeoCodeResponse(request.getResponseBody());
                case "search":
                    return createSearchResponse(request.getResponseBody());
                case "autoCompleteSearch":
                    return createAutoCompleteSearchResponse(request.getResponseBody());
                case "route":
                    return createRouteResponse(request.getResponseBody());
                case "staticMap":
                    return createStaticMapResponse(request.getBitmap());
                case "distanceMatrix":
                    return createDistanceMatrixResponse(request.getResponseBody());
            }
        else if (statusCode == 400)
            return new MapirError(api + " api: bad request.", 400);
        else if (statusCode == 401)
            return new MapirError(api + " api: get valid apiKey from https://corp.map.ir", 401);
        else if (statusCode == 402)
            return new MapirError(api + " api: bad request.", 402);
        else if (statusCode == 404)
            return new MapirError(api + " api: bad request.", 400);

        return null;
    }
    //endregion Utils
}
