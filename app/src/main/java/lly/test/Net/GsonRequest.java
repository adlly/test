package lly.test.Net;

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

import lly.test.App;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

/**
 * Created by addy on 2016/11/13.
 */

public abstract class GsonRequest<T> extends Request<T> implements Success<T>{

    private Map<String, String> headers;
    private Map<String, String> params;
    private Class<T> clazz;
//    private Success<T> listener;
    private Gson gson;

    private GsonRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }
//    public GsonRequest(int methord, String url, Map<String, String> headers, Class<T> clazz, Response.Listener<T> listener){
    public GsonRequest(int methord, String url, Map<String, String> headers, Map<String, String> params){
        this(methord, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        this.headers = headers;
        this.params = params;
//        this.clazz = clazz;
        findTypeArguments(getClass());
//        this.listener = listener;
        gson = Net.getInstance(App.getContext()).getGson();
    }


    /**
     * @param url get the json from {url}
     *
     */
    public GsonRequest(String url){
        this(GET, url, null, null);
    }

    public GsonRequest(String url, Map<String, String> params){
        this(POST, url, null, params);
    }

    private void findTypeArguments(Type t) {
        if (t instanceof ParameterizedType) {
            Type[] typeArgs = ((ParameterizedType) t).getActualTypeArguments();
            //noinspection unchecked
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
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

//            Log.e(getTag().toString(), json);
            T result = gson.fromJson(json, getMyType());
            return Response.success(
                    result,
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
//        listener.onSuccess(response);
        onSuccess(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
//        listener.onError(error);
        onError(error);
    }

    public Type getMyType()
    {
        // How do I return the type of T? (your question)
        return clazz;
    }
}
