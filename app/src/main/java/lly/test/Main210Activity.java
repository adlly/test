package lly.test;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import lly.test.tmp.TestRequest;

public class Main210Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private RequestQueue requestQueue;
    private ImageView iv;
    private NetworkImageView net_iv;
    private ImageLoader imageLoader;
    private ImageView iv2;

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
