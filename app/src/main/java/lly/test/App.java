package lly.test;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by addy on 2016/11/12.
 */

public class App extends Application {

    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        requestQueue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
