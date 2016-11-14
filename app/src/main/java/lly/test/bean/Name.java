package lly.test.bean;

/**
 * Created by dp on 2016/11/14.
 */

public class Name {

    /**
     * {
     "name": "morpheus",
     "job": "leader",
     "id": "972",
     "createdAt": "2016-11-14T06:16:19.726Z"
     }*/

    private String name;
    private String job;
    private String id;
    private String createdAt;

    @Override
    public String toString() {
        return "Name{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
