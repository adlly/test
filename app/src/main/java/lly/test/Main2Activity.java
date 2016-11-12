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

public class Main2Activity extends AppCompatActivity {


    private static final int NET_WEB_IMAGE = 0x11;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NET_WEB_IMAGE:
                    Bitmap obj = (Bitmap) msg.obj;
                    imageView.setImageBitmap(obj);
                    break;
                default:
                    break;
            }
        }
    };
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.image);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){

                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://img.qqhsh.com/uploads/allimg/160604/2-16060410430S43.gif");
                            URLConnection urlConnection = url.openConnection();
                            InputStream inputStream = urlConnection.getInputStream();
                            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                            /** 打包消息对象*/
                            Message msg = new Message();
                            msg.what = NET_WEB_IMAGE;
                            msg.obj = bitmap;

                            handler.sendMessage(msg);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    imageView.setImageBitmap(bitmap);
//                                }
//                            });



                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
}
