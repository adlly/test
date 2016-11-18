package lly.test.tmp;

import com.android.volley.RequestQueue;

import lly.test.App;

/**
 * Created by hz on 16/11/18.
 */

public class Tmp2 {

    public Tmp2() {
        Tmp instance = Tmp.getInstance(App.getApp());
        RequestQueue requestQueue = Tmp.getInstance(App.getApp()).getRequestQueue();
    }
}
