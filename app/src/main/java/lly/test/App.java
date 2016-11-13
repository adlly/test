package lly.test;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by addy on 2016/11/12.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
