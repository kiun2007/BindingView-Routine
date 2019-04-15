package kiun.com.bindingdemo.services;

import kiun.com.bindingdemo.bean.PblmBean;
import kiun.com.bindingdemo.bean.PblmListReqBean;
import kiun.com.bindingdemo.warp.Constants;
import kiun.com.bindingdemo.warp.NetConstants;
import kiun.com.bindingdemo.warp.NetListBeanWrapper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SupervisionListServices {

    @Headers({NetConstants.URL_NAME + Constants.COLON + NetConstants.URL_NAME_EMERGENCY})
    @POST("dc/insp/pblm/page")
    Call<NetListBeanWrapper<PblmBean>> pblmPageList(@Body PblmListReqBean reqBean);
}
