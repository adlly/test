package lly.test.test_net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import lly.test.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Main3Activity extends AppCompatActivity {

    private ImageView iv;
    final private int IMAGE_VIEW = 0x11;
    private String ss;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IMAGE_VIEW:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    iv.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }

    private void initView() {



        iv = (ImageView) findViewById(R.id.iv);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        ss = "http://img1.imgtn.bdimg.com/it/u=949232872,2013536703&fm=11&gp=0.jpg";
                        try {
                            URL url = new URL(ss);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            InputStream inputStream = urlConnection.getInputStream();

                            urlConnection.connect();
                            if(urlConnection.getResponseCode() == 200){

                                File file = new File(getCacheDir(), getFileName(ss));
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                byte[] bytes = new byte[1024];
                                int len = 0;
                                while((len = inputStream.read(bytes)) != -1){
                                    fileOutputStream.write(bytes, 0, len);
                                }
                                fileOutputStream.close();
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getName());
                                Message msg = handler.obtainMessage();
                                msg.what = IMAGE_VIEW;
                                msg.obj = bitmap;
                                handler.sendMessage(msg);
                            }



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

    public String getFileName(String path) {
        int i = path.lastIndexOf("/");
        return path.substring(i);
    }
}
