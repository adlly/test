package lly.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    private TextView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        if (intent != null){
            url = intent.getStringExtra("url");
            initView();
        }else{
            Toast.makeText(this, "数据异常", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
        progress = (TextView) findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress >= 99){
                    progress.setVisibility(View.GONE);
                }else{
                    progress.setText(String.valueOf(newProgress));
                }

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        });

        webView.loadUrl(url);
    }
}
