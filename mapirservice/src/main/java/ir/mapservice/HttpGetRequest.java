package ir.mapservice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static ir.mapservice.MapirService._ApiKey;

class HttpGetRequest {

    private HttpURLConnection urlConnection;
    private String BASE_URL = "https://map.ir/";

    HttpGetRequest(String endpoint) {
        try {
            URL url = new URL(BASE_URL + endpoint);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("x-api-key", _ApiKey);
        } catch (Exception exception) {
            Log.i(MapirService.class.getName(), exception.toString());
        }
    }

    int getResponseCode() {
        try {
            return urlConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 402;
    }

    String getResponseBody() {
        return readStream(urlConnection);
    }

    String getErrorBody() {
        return readErrorStream(urlConnection);
    }

    Bitmap getBitmap() {
        try {
            return BitmapFactory.decodeStream(urlConnection.getInputStream());
        } catch (Exception e) {
        }

        return null;
    }

    private static String readStream(HttpURLConnection urlConnection) {
        try {
            InputStream it = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader read = new InputStreamReader(it);
            BufferedReader buff = new BufferedReader(read);
            StringBuilder data = new StringBuilder();
            String chunks;

            while ((chunks = buff.readLine()) != null)
                data.append(chunks);

            return data.toString();
        } catch (Exception e) {
            Log.e(MapirService.class.getName(), e.toString());
        }

        return null;
    }

    private static String readErrorStream(HttpURLConnection urlConnection) {
        try {
            InputStream it = new BufferedInputStream(urlConnection.getErrorStream());
            InputStreamReader read = new InputStreamReader(it);
            BufferedReader buff = new BufferedReader(read);
            StringBuilder data = new StringBuilder();
            String chunks;

            while ((chunks = buff.readLine()) != null)
                data.append(chunks);

            return data.toString();
        } catch (Exception e) {
            Log.e(MapirService.class.getName(), e.toString());
        }

        return null;
    }
}
