package lly.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {

    private Button btn;
    private String url = "http://img5.imgtn.bdimg.com/it/u=1098520611,2884836008&fm=21&gp=0.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        final ImageView iv = (ImageView) findViewById(R.id.iv);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                获取照片并设置为iv的背景
                /** 因为主线程不能访问网络， 所以另起一个子线程蛆访问网络*/
                new Thread(){
                    @Override
                    public void run() {
                        Log.e("XXX", currentThread().getName());
                        Bitmap bitmap = null;
                        try {
                            URL link = new URL(url);
                            URLConnection urlConnection = link.openConnection();
                            InputStream inputStream = urlConnection.getInputStream();
                            bitmap = BitmapFactory.decodeStream(inputStream);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                        if(bitmap == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(v.getContext(), "获取失败", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                        }else{
                            final Bitmap finalBitmap = bitmap;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    iv.setImageBitmap(finalBitmap);
                                }
                            });
                        }


                    }

                }.start();
            }
        });
    }


}
