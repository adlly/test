package lly.test.tmp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by addy on 2016/11/19.
 */

public class Test_ {
    static Test_ test_;
    public static  Application application;
    private static RequestQueue requestQueue;



    private Test_(Application application) {
        this.application = application;
    }

    public static Test_ getInstance(Application app){
        if(test_ == null)
        {
            test_ = new Test_(app);
        }
        return  test_;

    }

    public RequestQueue getRequestQueue(){

        synchronized (this) {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(application);
            }
        }
        return  requestQueue;

    }



}
