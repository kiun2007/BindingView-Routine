package kiun.com.bindingdemo.services;

import kiun.com.bindingdemo.bean.RsvrRgstrBean;
import kiun.com.bindingdemo.bean.RsvrRgstrReqBean;
import kiun.com.bindingdemo.warp.Constants;
import kiun.com.bindingdemo.warp.NetConstants;
import kiun.com.bindingdemo.warp.NetListBeanWrapper;
import kiun.com.bvroutine.data.QueryBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sky on 2019/3/20.
 */

public interface OffOnlieServices {

    // 获取督查批次列表
    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
    @GET("dc/insp/base/getInspPlan")
    Call<NetListBeanWrapper<RsvrRgstrReqBean>> inspPlanList(@Query("userid") String userid);

    // 获取督查批次列表
    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
    @POST("dc/insp/rsvrRgstr/list/type")
    Call<NetListBeanWrapper<RsvrRgstrBean>> rsvrRgstrList(@Body QueryBean<RsvrRgstrReqBean> rsvrRgstrReqBean);
//
//    // 获取engId
//    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
//    @POST("dc/insp/villRgstr/getEngId")
//    Call<NetBeanWrapper<EngIdBase>> getEngId(@Query("objId") String objId);
//
//    // 获取行政村列表
//    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
//    @POST("dc/att/adXBase/queryTCListLast")
//    Call<NetListBeanWrapper<QueryTCListLastBase>> querTCList(@Body AdXReqBase adXReqBase);
//
//    // 获取饮水工程列表
//    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
//    @POST("dc/att/cwsBase/queryListByObjId")
//    Call<NetListBeanWrapper<QueryListBase>> queryListByObjId(@Body AdXReqBase adXReqBase);
//    // 获取饮水工程列表
//    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
//    @POST("dc/insp/proSourceProtect/queryListByObjId")
//    Call<NetListBeanWrapper<ProSourceProtectBase>> proSourceProtect(@Body AdXReqBase adXReqBase);
//
//    // 行政区域规划
//    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
//    @GET("dc/insp/selarea/getAreaCacheByPersid")
//    Call<NetListBeanWrapper<AreaByPerBean>> getAreaByPersid(@Query("persId") String persId);
//
//    //水库基本数据列表
//    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
//    @POST("dc/insp/selarea/getResByArea")
//    Call<NetListBeanWrapper<AreaByPerBean>> querAreaBase(@Body ResByAreaBase adXReqBase);
//
//    //添加APP离线数据记录上传.
//    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
//    @POST("bis/insp/uplog/insert")
//    Call<NetBeanWrapper<ErrorLogBean>> upLogInerst(@Body ErrorLogBean logBean);
//
//    //添加APP离线同步数据错误日志接口
//    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
//    @POST("bis/insp/appErrlog/insert")
//    Call<NetBeanWrapper<ErrorLogBean>> errLogInerst(@Body ErrorLogBean logBean);
//
//    //增加日志文件.
//    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
//    @Multipart
//    @POST("file/insert")
//    Call<NetBeanWrapper<FileInsertResponseBean>> fileInsert(@Query("bizId") String bizId, @Part MultipartBody.Part file);
}
