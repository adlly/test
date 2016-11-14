package lly.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import retrofit2.http.Url;

public class Main26Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private final int IMAGE_VIEW = 0x11;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case IMAGE_VIEW:
                    Bitmap bitmap = (Bitmap)msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main26);

        initView();



    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageview);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn:
            new Thread(){

                @Override
                public void run() {
                    try {
                        URL url = new URL("http://img1.imgtn.bdimg.com/it/u=949232872,2013536703&fm=11&gp=0.jpg");
                        URLConnection urlConnection = url.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message msg = new Message();
                        msg.what = IMAGE_VIEW;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
            break;
        }
    }
}
