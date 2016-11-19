package lly.test.bean;

import java.util.Date;

/**
 * Created by addy on 2016/11/19.
 */

public class NewsItem {
    /*
    *  "ctime": "2016-11-18 00:00",
      "title": "今年的购物狂欢季，iPhone 7 能卖多少部？",
      "description": "爱思助手",
      "picUrl": "http://d.image.i4.cn/i4web/image/news/2016-11-18/1479447714738.jpg",
      "url": "http://www.i4.cn/news_detail_11527.html"
    * */
    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getCtime() {
        return ctime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getUrl() {
        return url;
    }

    public NewsItem(String ctime, String title, String description, String picUrl, String url) {
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }
}
