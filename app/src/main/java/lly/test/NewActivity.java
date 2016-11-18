package lly.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import lly.test.tmp.Tmp;

public class NewActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv;
    private ImageView iv;
    private NetworkImageView niv;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_new);

        initView();
        initData();


    }

    private void initData() {
//        requestQueue = Net.getInstance(getApplication()).getRequestQueue();
        requestQueue = Tmp.getInstance(App.getApp()).getRequestQueue();
    }

    private void initView() {
        findViewById(R.id.btn_1).setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv_1);
        iv = (ImageView) findViewById(R.id.iv_1);
        niv = (NetworkImageView) findViewById(R.id.niv_1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_1:

                String url = "http://jandan.net/?oxwlxojflwblxbsapi=jandan";
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText(response);
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                };
                requestQueue.add(new StringRequest(url, listener, errorListener));

//                Tmp.getInstance(App.getApp()).addRequest(null, null);
                break;
        }
    }
}
