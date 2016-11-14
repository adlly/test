package lly.test.Net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import lly.test.App;

/**
 * Created by addy on 2016/11/13.
 */

public class Net {

    private static RequestQueue requestQueue;
    private static ImageLoader loader;
    private static Gson gson;
    /** 请使用 ApplicaitonContext */
    private static Context ctx;
    private static Net net;


    private Net(Context ctx) {
        Net.ctx = ctx;
        this.requestQueue = getRequestQueue();

        loader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

//        gson = new Gson();
    }

    public static synchronized Net getInstance(Context ctx){

        if(net == null) {
            net = new Net(ctx);
        }
        return net;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx);
        }
        return requestQueue;
    }

    public Gson getGson() {

        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return loader;
    }

    public static <T> void addRequest(String tag, GsonRequest<T> req){
        if(net == null) {
            net = getInstance(App.getContext());
        }
        req.setTag(tag);
        requestQueue.add(req);
    }

    public static void cancel(Object tag){
        requestQueue.cancelAll(tag);
    }

}
