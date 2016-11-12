package lly.test;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main23Activity extends AppCompatActivity {

    private static final int BUF = 4096 * 2;
    private ImageView iv;
    private DwnAsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main23);

        initView();
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.image);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new DwnAsyncTask();
                task.execute("http://img.qqhsh.com/uploads/allimg/160604/2-16060410430S43.gif");
//                task.execute("http://image.tianjimedia.com/uploadImages/2015/105/36/6L02TLJDMI0Q.jpg");
            }
        });
    }

    private class DwnAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Main23Activity.this);
            pd.setMax(100);
            pd.setProgress(0);
            pd.setTitle("正在下载中...");
//            pd.setIndeterminate(true);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setIndeterminate(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            执行完成后的动作 主线程
            pd.dismiss();
            iv.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
            pd.setProgress(values[0]);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
//            return null;
//            任务正在后台执行 子线程

            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000); //设置读取超时
                httpURLConnection.setRequestMethod("GET"); //设置请求方法，注意大写
                httpURLConnection.setConnectTimeout(10000); // 连接超时

                //5.发送请求，与服务器建立连接
                httpURLConnection.connect();
                //如果响应码为200，说明请求成功
                if(httpURLConnection.getResponseCode() == 200 ){

                    int contentLengthLong = httpURLConnection.getContentLength();

                    InputStream inputStream = httpURLConnection.getInputStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    return bitmap;
                    File file = new File(getFilesDir(), "xxx.png");
                    if(file.exists()) {//&& file.length() == contentLengthLong){
                        httpURLConnection.disconnect();
                        return BitmapFactory.decodeFile(file.getAbsolutePath());
                    }
                    OutputStream os = new FileOutputStream(file);
//                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//                    br.read();
                    byte[] bytes = new byte[BUF];
                    int read, sum = 0;
                    while((read = inputStream.read(bytes)) > 0){
                        sum += read;
                        publishProgress(new Integer[]{ (int)(sum * 100 / contentLengthLong)});
                        Log.v("Prog", String.valueOf(sum * 100 / contentLengthLong) + ":" +
                                contentLengthLong + "#" + sum + "@" + read);
                        os.write(bytes, 0, read);
                    }

                    inputStream.close();
                    os.flush();
                    os.close();
                    return BitmapFactory.decodeFile(file.getAbsolutePath());
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }
    }
}
