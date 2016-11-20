package lly.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lly.test.adapter.NewsAdapter;
import lly.test.bean.NewsItem;
import lly.test.tmp.TestRequest;

public class NewsActivity extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueue;
    private String url = "http://apis.baidu.com/txapi/apple/apple?num=50&page=1";
    private List<NewsItem> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initView();
        initData();

    }

    private void initData() {

        lists = new ArrayList<>();
        requestQueue = TestRequest.getInstance(getApplication()).getRequestQueue();

        String[]args = new String[8];

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int code = response.optInt("code");
                String msg = response.optString("msg");
                if(code == 200 && msg.equals("success")){
                    JSONArray newslist = response.optJSONArray("newslist");
                    int length = newslist.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject news = newslist.optJSONObject(i);
//                        private String ctime;
//                        private String title;
//                        private String description;
//                        private String picUrl;
//                        private String url;
                        NewsItem newsItem = new NewsItem(news.optString("ctime"),
                                news.optString("title"),
                                news.optString("description"),
                                news.optString("picUrl"),
                                news.optString("url")
                        );

                        lists.add(newsItem);

                    }
                    listView.setAdapter(new NewsAdapter(lists));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(NewsActivity.this, WebViewActivity.class);
                            intent.putExtra("url", lists.get(position).getUrl());
                            startActivity(intent);
                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(), "返回数据异常!", Toast.LENGTH_SHORT).show();
                    Log.e("XX", response.toString());
                    return;
                }

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "请求失败!", Toast.LENGTH_SHORT).show();
            }
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("apikey", "8083205faedcb24040b1871f12a7b3a0");
                return header;
            }
        };
        jsonObjectRequest.setTag(url);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestQueue.cancelAll(url);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);

    }
}
