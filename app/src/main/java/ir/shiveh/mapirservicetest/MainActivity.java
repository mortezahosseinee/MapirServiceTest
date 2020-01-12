package ir.shiveh.mapirservicetest;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.map.sdk_service.MapirService;
import ir.map.sdk_service.models.base.MapirError;
import ir.map.sdk_service.models.base.MapirResponse;
import ir.map.sdk_service.models.listeners.ResponseListener;
import ir.map.sdk_service.models.requests.EstimatedTimeArrivalRequest;
import ir.map.sdk_service.models.responses.AutoCompleteSearchResponse;
import ir.map.sdk_service.models.responses.DistanceMatrixResponse;
import ir.map.sdk_service.models.responses.EstimatedTimeArrivalResponse;
import ir.map.sdk_service.models.responses.FastReverseGeoCodeResponse;
import ir.map.sdk_service.models.responses.GeofenceResponse;
import ir.map.sdk_service.models.responses.PlaqueReverseGeoCodeResponse;
import ir.map.sdk_service.models.responses.ReverseGeoCodeResponse;
import ir.map.sdk_service.models.responses.RouteResponse;
import ir.map.sdk_service.models.responses.SearchResponse;
import ir.map.sdk_service.models.responses.StaticMapResponse;

public class MainActivity extends AppCompatActivity implements ResponseListener {

    private MapirService mapirService = new MapirService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mapirService.search(
//                new SearchRequest.Builder("خیابان انقلاب")
//                        .select(SelectOptions.POI)
//                        .select(SelectOptions.ROADS)
//                        .filter(FilterOptions.PROVINCE, "کرمان")
//                        .build()
//        );
//
//        mapirService.autoCompleteSearch(
//                new SearchRequest.Builder("بان قلب")
//                        .select(SelectOptions.POI)
//                        .select(SelectOptions.ROADS)
//                        .filter(FilterOptions.PROVINCE, "تهران")
//                        .build()
//        );

//        mapirService.route(
//                new RouteRequest.Builder(35.808208, 51.507911, 35.793179, 51.462016, DRIVING)
//                        .routePlan(RoutePlan.EVEN_ODD)
//                        .alternative(true)
//                        .steps(true)
//                        .routeOverView(RouteOverView.FULL)
//                        .build()
//        );

//        mapirService.geofence(35.72986153843338, 51.40631675720215);
//        mapirService.reverseGeoCode(35.1213654, 51.236548);
//        mapirService.fastReverseGeoCode(35.807665, 51.507960);
//        mapirService.plaqueReverseGeoCode(35.807665, 51.507960);
//        mapirService.staticMap(35.808208, 51.507911, 800, 1200, 18, "خونه مرتضی اینا", PINK);

//        List<DistanceMatrixPointRequest> tempOrigins = new ArrayList<>();
//        tempOrigins.add(new DistanceMatrixPointRequest(1, 35.808208, 51.507911));
//        tempOrigins.add(new DistanceMatrixPointRequest(2, 35.808207, 51.500098));
//        tempOrigins.add(new DistanceMatrixPointRequest(3, 35.804201, 51.461849));
//        tempOrigins.add(new DistanceMatrixPointRequest(4, 35.780042, 51.414385));
//
//        List<DistanceMatrixPointRequest> tempDestinations = new ArrayList<>();
//        tempDestinations.add(new DistanceMatrixPointRequest(5, 35.677769, 51.266842));
//        tempDestinations.add(new DistanceMatrixPointRequest(6, 35.643731, 51.383057));
//        tempDestinations.add(new DistanceMatrixPointRequest(7, 35.648619, 51.479530));
//        tempDestinations.add(new DistanceMatrixPointRequest(8, 35.676652, 51.373959));
//
//        mapirService.distanceMatrix(
//                new DistanceMatrixRequest.Builder(tempOrigins, tempDestinations)
//                        .sorted(false)
//                        .filter(DistanceMatrixOutputType.DISTANCE)
//                        .build()
//        );

        mapirService.estimatedTimeArrival(
                new EstimatedTimeArrivalRequest.Builder(35.808208, 51.507911)
                        .addDestination(35.808207, 51.500098)
                        .addDestination(35.804201, 51.461849)
                        .addDestination(35.780042, 51.414385)
                        .addDestination(35.677769, 51.266842)
                        .addDestination(35.643731, 51.383057)
                        .addDestination(35.648619, 51.479530)
                        .addDestination(35.676652, 51.373959)
                        .build()
        );
    }

    @Override
    public void onSuccess(MapirResponse response) {
        if (response instanceof ReverseGeoCodeResponse)
            Toast.makeText(this, ((ReverseGeoCodeResponse) response).getAddress(), Toast.LENGTH_SHORT).show();
        else if (response instanceof FastReverseGeoCodeResponse)
            Toast.makeText(this, ((FastReverseGeoCodeResponse) response).getAddress(), Toast.LENGTH_SHORT).show();
        else if (response instanceof PlaqueReverseGeoCodeResponse)
            Toast.makeText(this, ((PlaqueReverseGeoCodeResponse) response).getPlaque(), Toast.LENGTH_SHORT).show();
        else if (response instanceof SearchResponse)
            Toast.makeText(this, ((SearchResponse) response).getSearchItems().get(0).getAddress(), Toast.LENGTH_SHORT).show();
        else if (response instanceof AutoCompleteSearchResponse)
            Toast.makeText(this, ((AutoCompleteSearchResponse) response).getSearchItems().get(0).getAddress(), Toast.LENGTH_SHORT).show();
        else if (response instanceof RouteResponse)
            Toast.makeText(this, ((RouteResponse) response).getRoutes().get(0).getDistance().toString(), Toast.LENGTH_SHORT).show();
        else if (response instanceof StaticMapResponse)
            ((ImageView) findViewById(R.id.sample_img)).setImageBitmap(((StaticMapResponse) response).getBitmapStaticMap());
        else if (response instanceof DistanceMatrixResponse)
            Toast.makeText(this, ((DistanceMatrixResponse) response).getOrigins().get(0).getId(), Toast.LENGTH_SHORT).show();
        else if (response instanceof GeofenceResponse)
            Toast.makeText(this, ((GeofenceResponse) response).getGeofenceData().get(0).getBoundry().getType(), Toast.LENGTH_SHORT).show();
        else if (response instanceof EstimatedTimeArrivalResponse)
            Toast.makeText(this, ((EstimatedTimeArrivalResponse) response).getDistance().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(MapirError error) {
        Toast.makeText(this, error.getErrorMessage(), Toast.LENGTH_LONG).show();
    }
}