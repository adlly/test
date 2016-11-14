package lly.test;

import android.app.Application;
import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by addy on 2016/11/12.
 */

public class App extends Application {

//    private static RequestQueue requestQueue;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

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
}
