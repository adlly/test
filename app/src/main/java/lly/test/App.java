package lly.test;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by addy on 2016/11/12.
 */

public class App extends Application {

//    private static RequestQueue requestQueue;
    private static Context context;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        app = this;
        context = this;

//        requestQueue = Volley.newRequestQueue(this);
        // Instantiate the cache
//        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
//        Network network = new BasicNetwork(new HurlStack());

//        requestQueue = new RequestQueue(cache, network);
    }

   /* public static RequestQueue getRequestQueue() {
        return requestQueue;
    }*/

    public static Context getContext() {
        return context;
    }

    public static App getApp() {
        return app;
    }
}
