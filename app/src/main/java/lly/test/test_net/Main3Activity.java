package lly.test.test_net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lly.test.R;


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

                                /**
                                 * file.getName() 只是获得文件名字, 不是文件路径 所以报错,
                                 * 应该仔细看看报错信息
                                 * 11-17 00:01:00.017 31222-1554/lly.test E/BitmapFactory: Unable to decode stream: java.io.FileNotFoundException: u=949232872,2013536703&fm=11&gp=0.jpg: open failed: ENOENT (No such file or directory)
                                 * 如果实在找不到问题所在, 首先可以判断是肯定是文件 not found
                                 * 那就把 file.getName() 变量打印出来看一下
                                 * */
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                Log.e("getName", file.getName());
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
