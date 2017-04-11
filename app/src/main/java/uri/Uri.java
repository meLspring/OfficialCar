package uri;

/**
 * Created by Lspring on 2017/4/5.
 */

public class Uri {
    //测试接口
    private final static String GET_ENTRY="http://10.101.170.137:8369/usermgr/userlogin/rsapublickey";
    //获取验证码接口
    private final static String POST_CODE="http://10.101.170.137:8369/usermgr/userlogin/sendvcode";
    //登录接口
    private final static String LOGIN="http://10.101.170.137:8369/usermgr/userlogin/login";
    //获取业务数据接口
    private final static String GET_BUSINESS="http://10.101.170.137:8369/usermgr/userlogin/getcarbiz";
    public static String getGetEntry(){
        return GET_ENTRY;
    }
    public static String getPostCode(){
        return POST_CODE;
    }
    public static String getLogin(){
        return LOGIN;
    }
    public static String getGetBusiness(){
        return GET_BUSINESS;
    }
}
