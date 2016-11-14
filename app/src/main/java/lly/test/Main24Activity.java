package lly.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lly.test.Net.GsonRequest;
import lly.test.Net.Net;
import lly.test.Net.Success;
import lly.test.bean.Post;
import lly.test.bean.User;

import static com.android.volley.Request.Method.GET;

public class Main24Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private ImageView iv;
    private String main24 = "Main24";
    private RequestQueue requestQueue;
    private NetworkImageView net_img;
    private ImageLoader imageLoader;

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
        findViewById(R.id.btn_req_imgs).setOnClickListener(this);
        findViewById(R.id.json).setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        net_img = (NetworkImageView) findViewById(R.id.net_img);
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
                break;

            case R.id.btn_req_imgs:

                url = "http://pic33.nipic.com/20130916/3420027_192919547000_2.jpg";

                imageLoader = Net.getInstance(getApplicationContext()).getImageLoader();
                imageLoader.get(url, ImageLoader.getImageListener(
                        iv, R.mipmap.ic_launcher, android.R.drawable.stat_notify_sync_noanim));

                net_img.setImageUrl("http://img05.tooopen.com/images/20140604/sy_62331342149.jpg",
                        imageLoader);

                break;

            case R.id.json:
                requestQueue = Net.getInstance(getApplicationContext()).getRequestQueue();

//                JSONObject jsonRequest = new JSONObject();

                Response.Listener<JSONObject> listener_json = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tv.setText(response.toString());
                    }
                };
                Response.ErrorListener errorListener_json = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv.setText(error.getLocalizedMessage());
                        error.printStackTrace();
                    }
                };
                JsonObjectRequest jr = new JsonObjectRequest(GET, "http://httpbin.org/get",
                        null, listener_json, errorListener_json);
                jr.setTag(main24);

                requestQueue.add(jr);
                jr.cancel();

                GsonRequest<User> gr = new GsonRequest<User>(
                        GET, "https://jsonplaceholder.typicode.com/users/1", null, null) {


                    @Override
                    public void onSuccess(User response) {
                        tv.setText(response.toString());
                    }

                    @Override
                    public void onError(VolleyError error) {
                        tv.setText(error.getNetworkTimeMs() + error.getLocalizedMessage());
                    }
                };

                gr.setTag(main24);

                requestQueue.add(gr);
                gr.cancel();

                /** GET Test*/
                String tag = "tag";
                Net.addRequest(tag, new GsonRequest<User>("https://jsonplaceholder.typicode.com/users/1") {
                    @Override
                    public void onSuccess(User response) {
                        tv.setText(response.toString());
                    }

                    @Override
                    public void onError(VolleyError error) {
                        tv.setText(error.getNetworkTimeMs() + error.getLocalizedMessage());
                    }
                });
                Net.cancel(tag);
                
                /** POST Test */

                /**
                 // POST adds a random id to the object sent
                 $.ajax('http://jsonplaceholder.typicode.com/posts', {
                 method: 'POST',
                 data: {
                 title: 'foo',
                 body: 'bar',
                 userId: 1
                 }
                 }).then(function(data) {
                 console.log(data);
                 });

                 /* will return
                 {
                 id: 101,
                 title: 'foo',
                 body: 'bar',
                 userId: 1
                 }
                 */


                /** Post 请求的参数 */
                Map<String, String> params = new HashMap<>();
                params.put("title", "this is title!");
                params.put("body", "This is body!");
                params.put("userId", "0x11");

                Net.addRequest(tag, new GsonRequest<Post>("http://jsonplaceholder.typicode.com/posts", params) {
                    @Override
                    public void onSuccess(Post response) {

                        tv.setText(response.toString());
                    }

                    @Override
                    public void onError(VolleyError error) {

                        error.printStackTrace();
                    }
                });
                
                



                break;
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
