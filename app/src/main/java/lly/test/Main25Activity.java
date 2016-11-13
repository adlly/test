package lly.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.android.volley.Request.Method.GET;


public class Main25Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main25);
        initView();
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String url = "https://www.baidu.com";
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText(response);
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv.setText("请求发送失败！" + " " + error.getLocalizedMessage());
                    }
                };
                StringRequest stringRequest = new StringRequest(GET, url, listener, errorListener);
                requestQueue.add(stringRequest);
                break;
            default:
                break;
        }

    }
}
