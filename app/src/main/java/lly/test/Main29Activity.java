package lly.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import lly.test.tmp.Test_;

public class Main29Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main29);

        initView();
        initData();

    }

    private void initData() {
        requestQueue = Test_.getInstance(getApplication()).getRequestQueue();
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.btn:
                final String path = "https://www.baidu.com";
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                };
                Response.Listener<String> stringListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText(path + response);
                    }
                };
                requestQueue.add(new StringRequest(path, stringListener, errorListener));
                break;
        }
    }
}
