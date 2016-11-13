package lly.test;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import static com.android.volley.Request.Method.GET;

public class Main24Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main24);

        initView();
    }

    private void initView() {
       findViewById(R.id.btn).setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
//                点击的是btn

//                获得requestQueue
                RequestQueue requestQueue = App.getRequestQueue();


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
            default:
                break;
        }
    }
}
