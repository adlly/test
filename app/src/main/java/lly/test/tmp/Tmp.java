package lly.test.tmp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by hz on 16/11/18.
 */

public class Tmp {

    private static Tmp tmp;
    private static Application application;

    /** requestQueue */
    private static RequestQueue requestQueue;

    /**
     * 静态成员变量
     * */

    /**
     *   单例模式
     * 1. 私有构造方法
     * 2. 通过一个静态的方法获得唯一对象
     *
     * */

    private Tmp(Application application) {
        this.application = application;
    }

    /**
     * 1. 静态方法: Class.function()
     * 2. 非静态方法: 对象名.function()
     */
    public static Tmp getInstance(Application application){
        if(tmp == null){
            tmp = new Tmp(application);
        }
        return tmp;
    }

    public RequestQueue getRequestQueue() {
        synchronized (this) {
        if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(application);
            }
        }
        return requestQueue;
    }

    public void addRequest(StringRequest request, String tag){
        tag = tag == null ? "xxx" : tag;
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void cancel(Object tag){
        requestQueue.cancelAll(tag);
    }
}
