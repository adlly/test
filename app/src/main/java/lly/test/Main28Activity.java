package lly.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class Main28Activity extends AppCompatActivity {

    static private ImageView iv;
    private Task_Asy task_asy;
    final static int IMAGE_VIEW = 0x11;
    final static int TOAST_TEXT = 0x10;
    static Main28Activity ma;

    static Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case IMAGE_VIEW:
                    Bitmap bitmap = (Bitmap)msg.obj;
                    iv.setImageBitmap(bitmap);
                    break;
                case TOAST_TEXT:
                    Toast.makeText(ma, "下载失败!", Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main26);
        ma = this;

        initView();
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.imageview);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_asy = new Task_Asy();
                task_asy.execute("http://img1.imgtn.bdimg.com/it/u=949232872,2013536703&fm=11&gp=0.jpg");

            }
        });
    }

    public class Task_Asy extends AsyncTask<String, Integer, Bitmap>{



        @Override
        protected Bitmap doInBackground(String... strings) {


            final String ss = strings[0];


                new Thread(){

                    @Override
                    public void run() {

                        URL url = null;
                        try {
                            url = new URL(ss);
                            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setConnectTimeout(5000);
                            urlConnection.setReadTimeout(5000);
                            urlConnection.connect();


                            if(urlConnection.getResponseCode() == 200){
                                InputStream inputStream = urlConnection.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                Message msg = new Message();
                                msg.what = IMAGE_VIEW;
                                msg.obj = bitmap;
                                handler.sendMessage(msg);
                            }
                            else{
                                Message msg = handler.obtainMessage();
                                msg.what = TOAST_TEXT;
                                handler.sendMessage(msg);

                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();



            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
           //iv.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


    }
}
