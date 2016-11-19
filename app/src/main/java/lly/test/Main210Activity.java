package lly.test;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import lly.test.tmp.TestRequest;

public class Main210Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private RequestQueue requestQueue;
    private ImageView iv;
    private NetworkImageView net_iv;
    private ImageLoader imageLoader;
    private ImageView iv2;
    private TextView tv2;
    private TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main210);
        initView();
        initData();
    }

    private void initData() {
        requestQueue = TestRequest.getInstance(getApplication()).getRequestQueue();
        imageLoader = TestRequest.getInstance(getApplication()).getImageLoader();


    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        net_iv = (NetworkImageView) findViewById(R.id.net_iv);
        iv2 = (ImageView) findViewById(R.id.iv2);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.btn:
                String path = "http://httpbin.org/ip";

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText("请求结果为: " + response);
                    }
                };
                Response.ErrorListener errorlistener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "请求失败!", Toast.LENGTH_SHORT);
                    }
                };
                requestQueue.add(new StringRequest(path, listener, errorlistener));
                Response.Listener<Bitmap> listener_img = new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        iv.setImageBitmap(response);

                    }
                };
                Response.ErrorListener errorlistener_img = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iv.setImageResource(R.mipmap.ic_launcher);

                    }
                };


                String path2 = "http://httpbin.org/image/webp";
                ImageRequest imageRequest = new ImageRequest(path2, listener_img, 0, 0, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.ARGB_8888, errorlistener_img);
                imageRequest.setTag("Image");
                requestQueue.add(imageRequest);


                String path3 = "http://httpbin.org/image/jpeg";
                imageLoader.get(path3, ImageLoader.getImageListener(iv2, R.mipmap.ic_launcher, R.mipmap.ic_launcher));

                net_iv.setImageUrl(path3, imageLoader);

                String path4 = "http://192.168.1.106:3000/api/1";


                Response.Listener<JSONObject> listener3 = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        tv2.setText(response.opt("message").toString());

                    }
                };
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path4, null, listener3, null);

                requestQueue.add(jsonObjectRequest);

                String path5 = "http://192.168.1.106:3000/api/2";


                Response.Listener<JSONArray> listener5 = new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int length = response.length();


                        for (int i = 0; i < length; i++) {

                            String message = response.optJSONObject(i).optString("message");
                            Log.e("xx", message);
                            tv3.append(message + "\n");

                        }

                      //  tv3.setText(response.toString());
                    }
                };
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(path5, listener5, null);
                requestQueue.add(jsonArrayRequest);


                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestQueue.cancelAll("Image");
    }
}
