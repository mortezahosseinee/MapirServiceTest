package ir.mapservice;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import ir.mapservice.models.base.BaseModel;
import ir.mapservice.models.base.MapirError;
import ir.mapservice.models.base.MapirResponse;
import ir.mapservice.models.listeners.ResponseListener;
import ir.mapservice.models.other.Geom;
import ir.mapservice.models.requests.ReverseGeoCodeRequest;
import ir.mapservice.models.requests.SearchRequest;

import static ir.mapservice.models.responses.FastReverseGeoCodeResponse.createReverseGeoCodeResponse;
import static ir.mapservice.models.responses.ReverseGeoCodeResponse.createFastReverseGeoCodeResponse;
import static ir.mapservice.models.responses.SearchResponse.createSearchResponse;

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

    public void search(String text) {
        new SearchTask().execute(new SearchRequest(text));
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

            int statusCode = request.getResponseCode();
            if (statusCode == 200)
                return createReverseGeoCodeResponse(request.getResponseBody());
            else if (statusCode == 401)
                return new MapirError("get valid apiKey from https://corp.map.ir", 401);

            return null;
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

            int statusCode = request.getResponseCode();
            if (statusCode == 200)
                return createFastReverseGeoCodeResponse(request.getResponseBody());
            else if (statusCode == 401)
                return new MapirError("get valid apiKey from https://corp.map.ir", 401);

            return null;
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
            HttpGetRequest request = new HttpGetRequest(
                    "search/v2" +
                            "?text=" + searchRequest[0].getText()
            );

            int statusCode = request.getResponseCode();
            if (statusCode == 200)
                return createSearchResponse(request.getResponseBody());
            else if (statusCode == 401)
                return new MapirError("get valid apiKey from https://corp.map.ir", 401);

            return null;
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
        } catch (Exception e){
        }

        return null;
    }

    private void handleResponse(BaseModel response) {
        if (response instanceof MapirResponse)
            listener.onSuccess((MapirResponse) response);
        else if (response instanceof MapirError)
            listener.onError((MapirError) response);
    }
    //endregion Utils
}
