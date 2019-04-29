package kiun.com.bindingdemo.warp;

/**
 * @author lcarry
 * @version V1.0
 * 2017/7/13
 */
public class NetConstants {

    //联调环境地址：http://192.168.0.15:8080/  xpjc/spjc/app/service/
    //测试环境地址：http://10.1.102.136:8101/  spjc/spjc/app/service/
    //预生产环境地址：
    //正式环境地址：http://sv.goldenwater.com.cn/

    //{
    //调试环境
    public final static String API_APP_BASE_URL_TEST = "http://10.1.3.215/";//内网ip

    //生产环境 http://10.1.102.136:8101/
    public final static String API_APP_BASE_URL_SECOND = "http://sv.goldenwater.com.cn/";//外网 主URL

//    public final static String API_APP_BASE_URL_SECOND = "http://10.1.102.136:8101/";//外网 主URL

//    public final static String API_APP_BASE_URL_SECOND = "http://192.168.1.162:7001/";//外网 主URL
    public final static String API_APP_FILE_URL = API_APP_BASE_URL_SECOND;
    public final static String API_H5_BASE_URL = API_APP_BASE_URL_SECOND;

    //通用环境
    public final static String API_APP_BASE_URL = "http://hzz-monitor.goldenwater.com.cn/";//河湖URL 以前的版本河湖的接口
    public final static String API_H5_BASE_URL_SECOND = API_APP_BASE_URL;
    public final static String API_APP_SERVICE_APPEND_TEST = API_APP_BASE_URL;//"http://hzz-monitor-test.goldenwater.com.cn/";
//}

    public final static String API_APP_SERVICE = "";

    public final static String API_APP_IMAGE_APPEND = "hzz_web/";

    public final static String API_BASE_URL_TEST = API_APP_BASE_URL_TEST + API_APP_SERVICE;

    public final static String API_BASE_URL = API_APP_BASE_URL + API_APP_SERVICE;//正式


    public final static String API_BASE_URL_SEC = API_APP_BASE_URL_SECOND;

    public final static String APP_VERSION_V2 = "v2";

    public final static String APP_CLIENT = "001";

    public final static String APP_KEY = "46c7eeacd71b4a209168a21f0a4b0034";

    public final static String REQUEST_NET_SUCCESS = "0";

    //    public final static String REQUEST_HEADER_TOKEN = "token";
//
    public final static String REQUEST_HEADER_SOURCE_CODE = "0X010101";
    //
//    public final static String REQUEST_AUTH_CODE_FAIL = "1002";
//
    public final static String REQUEST_TOKEN_FAIL = "1003";


    public final static String URL_NAME = "url_name";
    public final static String URL_NAME_DEFAULT = "default";
    public final static String URL_NAME_EMERGENCY = "emergency";
}
