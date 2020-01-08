package ir.shiveh.mapirservicetest;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.mapservice.MapirService;
import ir.mapservice.models.base.MapirError;
import ir.mapservice.models.base.MapirResponse;
import ir.mapservice.models.listeners.ResponseListener;
import ir.mapservice.models.other.SelectOptions;
import ir.mapservice.models.responses.AutoCompleteSearchResponse;
import ir.mapservice.models.responses.FastReverseGeoCodeResponse;
import ir.mapservice.models.responses.ReverseGeoCodeResponse;
import ir.mapservice.models.responses.SearchResponse;

import static ir.mapservice.models.other.FilterOptions.PROVINCE;
import static ir.mapservice.models.other.SelectOptions.DISTRICT;
import static ir.mapservice.models.other.SelectOptions.POI;
import static ir.mapservice.models.other.SelectOptions.ROADS;

public class MainActivity extends AppCompatActivity implements ResponseListener {

    private MapirService mapirService = new MapirService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mapirService.reverseGeoCode(35.1213654, 51.236548);
//        mapirService.fastReverseGeoCode(35.1213654, 51.236548);
//        mapirService.search("خیابان آزادی");
//        mapirService.search("خیابان آزادی",
//                new SelectOptions[]{
//                        ROADS,
//                        POI
//                },
//                new Pair(PROVINCE, true)
//        );
        mapirService.autoCompleteSearch("خی آزاد",
                new SelectOptions[]{
                        ROADS,
                        POI
                },
                new Pair(PROVINCE, "تهران")
        );
    }

    @Override
    public void onSuccess(MapirResponse response) {
        if (response instanceof ReverseGeoCodeResponse)
            Toast.makeText(this, ((ReverseGeoCodeResponse) response).getAddress(), Toast.LENGTH_LONG).show();
        else if (response instanceof FastReverseGeoCodeResponse)
            Toast.makeText(this, ((FastReverseGeoCodeResponse) response).getAddress(), Toast.LENGTH_LONG).show();
        else if (response instanceof SearchResponse)
            Toast.makeText(this, ((SearchResponse) response).getSearchItems().get(0).getAddress(), Toast.LENGTH_LONG).show();
        else if (response instanceof AutoCompleteSearchResponse)
            Toast.makeText(this, ((AutoCompleteSearchResponse) response).getSearchItems().get(0).getAddress(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(MapirError error) {
        Toast.makeText(this, error.getErrorMessage(), Toast.LENGTH_LONG).show();
    }
}