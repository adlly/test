package lly.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lly.test.adapter.PostAdapter;
import lly.test.bean.PostItem;
import lly.test.tmp.Tmp;

public class New2Activity extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueue;
    private String url = "http://jsonplaceholder.typicode.com/posts";

    private List<PostItem> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new2);

        initView();
        initData();
    }

    private void initData() {

        posts = new ArrayList<>();

        requestQueue = Tmp.getInstance(getApplication()).getRequestQueue();
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject post = response.optJSONObject(i);
//                    PostItem postItem = new PostItem(post.optInt("userId"), post.optInt("id"),
//                            post.optString("title"), post.optString("body"));
//                    int userId = post.optInt("userId");
                    Gson gson = Tmp.getInstance(getApplication()).getGson();
                    PostItem postItem = gson.fromJson(post.toString(), PostItem.class);

                    posts.add(postItem);
                }

                listView.setAdapter(new PostAdapter(posts));
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "数据拉取出错, 请重试!", Toast.LENGTH_SHORT)
                        .show();
            }
        };
        requestQueue.add(new JsonArrayRequest(url, listener, errorListener));
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
    }
}
