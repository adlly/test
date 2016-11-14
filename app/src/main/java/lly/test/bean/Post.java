package lly.test.bean;

/**
 * Created by dp on 2016/11/14.
 */

public class Post {
    /** http://jsonplaceholder.typicode.com/posts */
    private int id;
    private String title;
    private String body;
    private String userId;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                '}';
    }
}
