package lly.test.tmp;

import com.android.volley.RequestQueue;

import lly.test.App;

/**
 * Created by hz on 16/11/18.
 */

public class Tmp1 {

    /*
    * 1. 成员变量
    * 2. 成员方法
    * */

    public Tmp1() {
    }


    public static void fun1(){

        Tmp a11 = Tmp.getInstance(App.getApp());
        RequestQueue requestQueue = Tmp.getInstance(App.getApp()).getRequestQueue();
    }

    public void fun2(){


    }
}
