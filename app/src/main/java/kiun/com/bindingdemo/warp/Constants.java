package kiun.com.bindingdemo.warp;

/**
 * Created
 *
 * @author lcarry
 * @version V1.0
 * @Title: ${enclosing_method}
 * @Description: ${todo}(这里用一句话描述这个方法的作用)
 * @date 2017/4/25
 */
public final class Constants {

    public static final String TIMER_CHANGE_ID = "TIMER_CHANGE_ID";
    public static final String DIALOG_PARAM = "DIALOG_PARAM";

    public static final String STAR = "***";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String SINGLE_QUOTES = "'";
    public static final String BLANK = "";
    public static final String BLANK_JSON = "{}";
    public static final String UNDERLINE = "_";
    public static final String VERTICALLINE = "|";
    public static final String LINE = "-";
    public static final String POINT = ".";
    public static final String SPACE = " ";
    public static final String SLASH = "/";
    public static final String BACK_SLASH = "\\";
    public static final String SIGN_EQUAL = "=";

    public static final String FILE_PATH = "files/";
    // 照片文件的位置
    public static final String IAMGE_SUFFIX_JPG = ".jpg";
    // 照相机保存文件的位置
    public static final String CAMERA_PATH = "cameras/";
    // 保存头像文件的位置
    public static final String HEAD_PATH = "head/";

    public static final int CONSULT_ACTIVITY_REQUEST_CODE = 11;
    public static final int RESULT_FROM_CAMERA = 12;
    public static final int RESULT_FROM_GALLERY = 13;

    //请求标识
    public static final int REQUEST_TAG = 0;
    public static final int REQUEST_TO_LOGIN = 1;
    public static final int RESULT_FROM_LOGIN = 2;

    public static final String BUNDLE_DATA = "bundleData";
    public static final String BUNDLE_FLAG = "bundleFlag";
    public static final String BUNDLE_NAME = "bundleName";

    public static final String BUNDLE_DATA_APPEND = "bundleDataAppend";
    public static final String BUNDLE_DATA_APPEND_ADD = "bundleDataAppendAdd";

    public static final String BROADCAST_FORCE_OFFLINE = "com.ministryofwater.approvalapplyapplication.FORCE_OFFLINE";

    public static final String BROADCAST_OFFLINE_FLAG = "OFFLINE_BROADCAST";


    public static final String ROW = "allrows";
//    public static final String ROW = "rows";
    public static final String LIST = "list";
    public static final String ROLEINFOLIST = "roleInfoList";
    public static final String DATA = "data";
    public static final String MSG = "msg";
    public static final String SUCCESSSTR = "success";
    public static final String CODE = "code";
    public static final String SUCCESS = "0";

    public static final String DATA_CAPITAL = "DATA";


    //列表相关
    public static String LIST_ONE_ITEM_LIMIT = "10";
    public static String LIST_ITEM_LIMIT = "20";
//    public static String LIST_PAGE_START = "0";//改从0开始
public static String LIST_PAGE_START = "1";//改从1开始
    public static int LIST_ITEM_LIMIT_INT = 20;

    public static String LIST_ITEM_LIMIT_HOME = "5";
    public static int LIST_ITEM_LIMIT_HOME_INT = 5;




    //下面的值都要和后台统一
    // 默认type值
    //这个值表示全部类型
    public static final String FUNC_TYPE_DEFAULT = "0";
    // 汛情摘要
    public static final String FUNC_TYPE_ESSAY = "1";
    // 实时监测
    public static final String FUNC_TYPE_RAIN_WATER = "2";
    // 天气预报
    public static final String FUNC_TYPE_WEATHER = "3";
    // 雷达图
    public static final String FUNC_TYPE_RADAR = "4";
    // 气象云图
    public static final String FUNC_TYPE_CLOUD = "5";
    // 台风路径
    public static final String FUNC_TYPE_TYPHONE = "6";

    // 热点
    public static final String FUNC_TYPE_ESSAY_NEW = "7";
    // 水情
    public static final String FUNC_TYPE_WATER_NEW = "8";
    // 水情预警
    public static final String FUNC_TYPE_WATER_ALARM_NEW = "9";
    // 雨情
    public static final String FUNC_TYPE_RAIN_NEW = "10";
    // 气象云图
    public static final String FUNC_TYPE_CLOUD_NEW = "11";
    // 台风
    public static final String FUNC_TYPE_TYPHOON_NEW = "12";
    // 分析文档
    public static final String FUNC_TYPE_DOC_NEW = "13";
    // 短信彩信
    public static final String FUNC_TYPE_MESSAGE_NEW = "14";
    // 我在现场
    public static final String FUNC_TYPE_LIVE_NEW = "15";


    //这个值表示测站类型
    public static String STATION_LIST_TYPE = "STATION_LIST_TYPE";

    public static final String STATION_TYPE_DEFAULT = "0";
    // 降水
    public static final String STATION_TYPE_RAIN = "1";
    // 江河
    public static final String STATION_TYPE_RIVER = "2";
    // 水库
    public static final String STATION_TYPE_RESERVOIR = "3";
    // 堰闸
    public static final String STATION_TYPE_WEIR_SLUICE = "4";
    // 墒情
    public static final String STATION_TYPE_SOIL_MOISTURE = "5";


    public static final String RAIN_TYPE_DEFAULT = "0";
    // 日雨量
    public static final String RAIN_TYPE_DAY = "1";
    // 逐时雨量
    public static final String RAIN_TYPE_HOUR = "2";
    // 旬雨量
    public static final String RAIN_TYPE_PERIOD = "3";
    // 降雨距平
    public static final String RAIN_TYPE_ANOMALY = "4";
    // 降雨预报
    public static final String RAIN_TYPE_FORECAST = "5";


    public static final String FUNC_TYPE_MATTER = "16";


    public static final String RVLK_TYPE_RADIUS = "5000"; // 3公里 5公里 10公里
    public static final String RVLK_TYPE_LEVEL = "4";//3 区县级 4 乡级
    public static final float MAP_SCALE = 13f;//12f 14f
    public static final float MAP_SCALE_BIG = 12f;//12f 14f




    public static final int STAT_TYPE_RSVR=1;//水库站
    public static final int STAT_TYPE_SLUICE=2;//水闸
    public static final int STAT_TYPE_HYDROPOWER=3;//水电站
    public static final int STAT_TYPE_PUMP=4;//泵站
    public static final int STAT_TYPE_DVERSION_WATER=5;//引调水工程
    public static final int STAT_TYPE_WATER_SUPPLY=6;//供水工程
    public static final int STAT_TYPE_RUBBER=7;//橡胶坝
    public static final int STAT_TYPE_DIKE=8;//提防


    public static final String STAT_TYPE_CODE_RSVR="P201";//水库站
    public static final String STAT_TYPE_CODE_SLUICE="P203";//水闸
    public static final String STAT_TYPE_CODE_HYDROPOWER="P202";//水电站
    public static final String STAT_TYPE_CODE_PUMP="P205";//泵站
    public static final String STAT_TYPE_CODE_DVERSION_WATER="P206";//引调水工程
    public static final String STAT_TYPE_CODE_WATER_SUPPLY="P208";//供水工程
    public static final String STAT_TYPE_CODE_RUBBER="P204";//橡胶坝
    public static final String STAT_TYPE_CODE_DIKE="P207";//提防


    public static final String IN_TYPE="IN_TYPE";
    public static final String INTENT_DATA_CODE="Intent_Data_Code";
    public static final String IN_TYPE_LAT="IN_TYPE_LAT";
    public static final String IN_TYPE_LNG="IN_TYPE_LNG";
    public static final String IN_TYPE_CODE="IN_TYPE_CODE";
    public static final String IN_TYPE_NAME="IN_TYPE_NAME";
    public static final String IN_TYPE_TYPE="IN_TYPE_TYPE";


    public static final String LIST_RGNCD="rgncd";
    public static final String LIST_NAME="name";
    public static final String LIST_TYPE="type";

    public static final int LAYER_STAT_TYPE_REA=0;//河断
    public static final int LAYER_STAT_TYPE_LKS=1;//湖片
    public static final int LAYER_STAT_TYPE_RSVR=2;//水库
    public static final int LAYER_STAT_TYPE_FD=3;//农饮
    public static final int LAYER_STAT_TYPE_172=4;//172
    public static final int LAYER_STAT_TYPE_WASHOUT=5;//水毁
    public static final int LAYER_STAT_TYPE_PEOPLE=6;//人员
    public static final int LAYER_STAT_TYPE_DUCHA=7;//督查
    public static final int LAYER_STAT_TYPE_COUNTY=8; //行政村
    public static final int LAYER_STAT_TYPE_WATER_SOURCE=9; //水源地


    public static final String CATAT_TYPE_RSVR="RSVR";
    public static final String CATAT_TYPE_CWS="CWS";
    public static final String CATAT_TYPE_WTDST="WTDST";


    public static final String RV_RVSR_TOKEN = "11119143aea1dddd4691bab8a922e9752a79";
    public static final String TAKE_MEDIA_FILE_PATH="sldc_Image";

    //用来标记是从什么界面跳转到扫描界面的(1、我的页面)
    public static final String SCAN_TAG = "SCAN_TAG";
    public static final int SCAN_FROM_MINE = 1;

}
