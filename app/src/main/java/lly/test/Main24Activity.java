package lly.test;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import lly.test.Net.Net;

import static com.android.volley.Request.Method.GET;

public class Main24Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private ImageView iv;
    private String main24 = "Main24";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main24);

        initView();
    }

    private void initView() {
       findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.nnew).setOnClickListener(this);
        findViewById(R.id.btn_req_img).setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
//                点击的是btn

//                获得requestQueue
//                RequestQueue requestQueue = App.getRequestQueue();

                requestQueue = Net.getInstance(getApplicationContext()).getRequestQueue();

                String url = "http://httpbin.org/get";
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText(response);
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "请求错误" + error.getLocalizedMessage()
                        , Toast.LENGTH_SHORT).show();
                    }
                };
                StringRequest sq = new StringRequest(
                        GET, url, listener, errorListener);

                requestQueue.add(sq);
                break;
            case R.id.nnew:
//                Intent intent = new Intent();
//                intent.setClass(this,Main25Activity.class);
                Intent intent = new Intent(this, Main25Activity.class);
                startActivity(intent);

                break;
            case R.id.btn_req_img:
                requestQueue = Net.getInstance(getApplicationContext()).getRequestQueue();

                url = "http://pic33.nipic.com/20130916/3420027_192919547000_2.jpg";

                Bitmap.Config decodeConfig = Bitmap.Config.ARGB_8888;

                Response.Listener<Bitmap> listener_img = new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        iv.setImageBitmap(response);
                    }
                };
                Response.ErrorListener errorListener_img = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iv.setImageResource(R.mipmap.ic_launcher);
                        error.printStackTrace();
                    }
                };
                ImageRequest iq = new ImageRequest(url, listener_img, 0, 0, ImageView.ScaleType.FIT_CENTER, decodeConfig, errorListener_img);

                iq.setTag(main24);
                requestQueue.add(iq);
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(requestQueue != null) {
            requestQueue.cancelAll(main24);
        }
    }
}
