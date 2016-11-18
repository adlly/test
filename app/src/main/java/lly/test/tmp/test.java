package lly.test.tmp;

/**
 * Created by hz on 16/11/18.
 */

public class test {

    public static void main(String args[]){

        /**
         * 类名 对象名 = new 构造方法();
         *
         * 类 ==> 类型
         * 对象 ==> 变量
         * */
        Tmp1 tmp1 = new Tmp1();
//        Tmp tmp = new Tmp();

        Tmp1.fun1();
        tmp1.fun2();
        fun1();


        /*
        * 1. 类 4个访问权限
        *   public private protected
        * 2. 变量
        *
        * 3. 方法
        *
        * final
        * */
    }


    private static void fun1(){

       /* final String string = new String("");
        string = string + "";
        String string1 = string;

        Log.e();
        String.valueOf();
        TextUtils.isEmpty()


        final StringBuffer sb = new StringBuffer();
        sb.append("");
        sb.append("");

        final int a = 2;
        a = 5;*/

    }
}
