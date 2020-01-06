package ir.shiveh.mapirservicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import ir.mapservice.MapirService;
import ir.mapservice.models.base.MapirError;
import ir.mapservice.models.base.MapirResponse;
import ir.mapservice.models.listeners.ResponseListener;
import ir.mapservice.models.responses.FastReverseGeoCodeResponse;
import ir.mapservice.models.responses.ReverseGeoCodeResponse;
import ir.mapservice.models.responses.SearchResponse;

public class MainActivity extends AppCompatActivity implements ResponseListener {

    private MapirService mapirService = new MapirService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mapirService.reverseGeoCode(35.1213654, 51.236548);
//        mapirService.fastReverseGeoCode(35.1213654, 51.236548);
        mapirService.search("کوچه مسجد پلاک 6");
    }

    @Override
    public void onSuccess(MapirResponse response) {
        if (response instanceof ReverseGeoCodeResponse)
            Toast.makeText(this, ((ReverseGeoCodeResponse) response).getAddress(), Toast.LENGTH_LONG).show();
        else if (response instanceof FastReverseGeoCodeResponse)
            Toast.makeText(this, ((FastReverseGeoCodeResponse) response).getAddress(), Toast.LENGTH_LONG).show();
        else if (response instanceof SearchResponse)
            Toast.makeText(this, ((SearchResponse) response).getSearchItems().get(0).getAddress(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(MapirError error) {

    }
}