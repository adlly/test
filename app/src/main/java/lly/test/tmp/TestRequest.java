package lly.test.tmp;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by addy on 2016/11/19.
 */

public class TestRequest {
    static TestRequest testRequest;
    public Application app;
    public static RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private TestRequest(Application app) {
        this.app = app;
        requestQueue = Volley.newRequestQueue(app);

        this.imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(4 * 1024 * 1024);

            @Override
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url, bitmap);

            }
        });
    }

    public synchronized static TestRequest getInstance(Application app) {
        if (testRequest == null) {
            testRequest = new TestRequest(app);
        }
        return testRequest;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

//    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,)


}
