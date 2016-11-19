package lly.test.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lly.test.R;
import lly.test.bean.PostItem;

/**
 * Created by addy on 2016/11/19.
 */
public class PostAdapter extends BaseAdapter {
    List<PostItem> posts;

    public PostAdapter(List<PostItem> posts) {
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
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
        ViewXX xx;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
            xx = new ViewXX();
            xx.title = (TextView) convertView.findViewById(R.id.title);
            xx.body = (TextView) convertView.findViewById(R.id.body);
            xx.userId = (TextView) convertView.findViewById(R.id.userId);
            xx.id = (TextView) convertView.findViewById(R.id.id);

            convertView.setTag(xx);
        }else{
            xx = (ViewXX) convertView.getTag();
        }

        PostItem postItem = posts.get(position);
        xx.title.setText(postItem.getTitle());
        xx.body.setText(postItem.getBody());
        xx.userId.setText(String.valueOf(postItem.getUserId()));
        xx.id.setText(String.valueOf(postItem.getId()));

        return convertView;
    }


    public class ViewXX{
        public TextView title;
        public TextView body;
        public TextView userId;
        public TextView id;

    }
}
