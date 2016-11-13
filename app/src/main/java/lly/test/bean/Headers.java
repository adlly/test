package lly.test.bean;

/**
 * Created by addy on 2016/11/13.
 */

public class Headers {

    /*
    Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*//*;q=0.8",
            "Accept-Encoding": "gzip, deflate, sdch",
            "Accept-Language": "zh-CN,zh;q=0.8",
            "Cookie": "_ga=GA1.2.482702865.1479034696; _gat=1",
            "Host": "httpbin.org",
            "Referer": "http://httpbin.org/",
            "Upgrade-Insecure-Requests": "1",
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36"
    *
    * */

    private String Accept;
    private String Cookie;
    private String Host;
    private String Referer;

    @Override
    public String toString() {
        return "Headers{" +
                "Accept='" + Accept + '\'' +
                ", Cookie='" + Cookie + '\'' +
                ", Host='" + Host + '\'' +
                ", Referer='" + Referer + '\'' +
                '}';
    }
}
