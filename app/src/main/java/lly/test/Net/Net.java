package lly.test.Net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by addy on 2016/11/13.
 */

public class Net {

    private RequestQueue requestQueue;
    private static Context ctx;
    private static Net net;

    private Net(Context ctx) {
        this.ctx = ctx;
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
}
