package lly.test.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import lly.test.App;
import lly.test.R;
import lly.test.bean.NewsItem;
import lly.test.tmp.TestRequest;

/**
 * Created by addy on 2016/11/19.
 */
public class NewsAdapter extends BaseAdapter {
    List<NewsItem> lists;
    private ImageLoader imageLoader;

    public NewsAdapter(List<NewsItem> lists) {
        this.lists = lists;
        this.imageLoader = TestRequest.getInstance(App.getApp()).getImageLoader();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewNews viewNews;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, null, false);
            viewNews = new ViewNews();
            viewNews.title = (TextView)convertView.findViewById(R.id.title);
            viewNews.body = (TextView)convertView.findViewById(R.id.body);
            viewNews.time = (TextView)convertView.findViewById(R.id.time);
            viewNews.iv = (ImageView)convertView.findViewById(R.id.iv);
            convertView.setTag(viewNews);
        }else{
            viewNews = (ViewNews) convertView.getTag();
        }
        NewsItem newsItem = lists.get(position);
        viewNews.title.setText(newsItem.getTitle());
        viewNews.body.setText(newsItem.getDescription());
        viewNews.time.setText(newsItem.getCtime());
        imageLoader.get(newsItem.getPicUrl(),
                ImageLoader.getImageListener(viewNews.iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher));

        return convertView;
    }

    private class ViewNews {
        private TextView title;
        private TextView body;
        private TextView time;
        private ImageView iv;
    }
}
