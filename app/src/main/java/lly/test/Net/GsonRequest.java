package lly.test.Net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by addy on 2016/11/13.
 */

public abstract class GsonRequest<T> extends Request<T> implements Success {

    private Map<String, String> headers;
    private Class<T> clazz;
    private Response.Listener listener;
    private Gson gson;

    public GsonRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }
//    public GsonRequest(int methord, String url, Map<String, String> headers, Class<T> clazz, Response.Listener<T> listener){
    public GsonRequest(int methord, String url, Map<String, String> headers, Response.Listener<T> listener){
        this(methord, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        this.headers = headers;
//        this.clazz = clazz;
        findTypeArguments(getClass());
        this.listener = listener;
        gson = Net.getGson();
    }


    private void findTypeArguments(Type t) {
        if (t instanceof ParameterizedType) {
            Type[] typeArgs = ((ParameterizedType) t).getActualTypeArguments();
            //noinspection unchecked
            Log.e("YY", typeArgs.toString());
            clazz = (Class<T>) typeArgs[0];
        } else {
            Class c = (Class) t;
            findTypeArguments(c.getGenericSuperclass());
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
