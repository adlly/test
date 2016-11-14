package lly.test.Net;

import com.android.volley.VolleyError;

/**
 * Created by addy on 2016/11/13.
 */

public interface Success<T>{
    void onSuccess(T response);
    void onError(VolleyError error);
}
