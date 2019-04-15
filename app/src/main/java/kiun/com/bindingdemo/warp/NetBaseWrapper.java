package kiun.com.bindingdemo.warp;

import android.text.TextUtils;

import static kiun.com.bindingdemo.warp.NetConstants.REQUEST_NET_SUCCESS;
import static kiun.com.bindingdemo.warp.NetConstants.REQUEST_TOKEN_FAIL;

/**
 * Created by ${lcarry} on 2017/6/1.
 */

public class NetBaseWrapper {
    // 用来判断本地异常
    private boolean localException = false;
    // 本地异常内容
    private String localMsg;
    //succ
    private String success;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    // 结果状态
    private String code;
    // 结果信息
    private String msg;

    private Object extraObj;

    private static final String DEFAULT_ERROR_MSG = "系统异常";

    public NetBaseWrapper() {

    }

    public NetBaseWrapper(boolean exception, String msg) {
        localException = exception;
        localMsg = msg;
    }

    /**
     * 是否出现本地异常
     *
     * @return
     */
    public boolean isLocalException() {
        return localException;
    }

    public void setLocalException(boolean localException) {
        this.localException = localException;
    }

    public void setLocalExceptionMsg(String msg) {
        localMsg = msg;
    }

    /**
     * 获取本地异常内容
     *
     * @return
     */
    public String getLocalExceptionMsg() {
        return localMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 是否网络请求成功
     *
     * @return
     */
    public boolean isNetSuccess() {
        return !TextUtils.isEmpty(code)
                && code.equals(REQUEST_NET_SUCCESS);
    }

    /**
     * 获取网络请求错误码
     *
     * @return
     */
    public String getNetErrCode() {
        String errCode = REQUEST_NET_SUCCESS;
        if (!isNetSuccess()) {
            return code;
        }
        return errCode;
    }

    /**
     * 获取网络请求错误内容
     *
     * @return
     */
    public String getNetErrMsg() {
        String errMsg = null;
        if (!isNetSuccess()) {
            return msg;
        }
        return errMsg;
    }

    /**
     * 判断是否调用成功，这个调用包括网络错误和本地异常
     *
     * @return
     */
    public boolean isAllSuccess() {
        return isNetSuccess() && !isLocalException();
    }


    /**
     * 返回调用出现异常的时候的错误信息，这个错误信息包括本地异常和网络请求错误2种情况
     *
     * @return
     */
    public String getAllErrMSg() {
        String exceptionMsg = null;

        if (isLocalException()) {
            exceptionMsg = getLocalExceptionMsg();
        } else if (!isNetSuccess()) {
            exceptionMsg = getNetErrMsg();
        }

        if (TextUtils.isEmpty(exceptionMsg)) {
            exceptionMsg = DEFAULT_ERROR_MSG;
        }
        return exceptionMsg;
    }


    public Object getExtraObj() {
        return extraObj;
    }

    public void setExtraObj(Object extraObj) {
        this.extraObj = extraObj;
    }

    /**
     * 判断是否是token认证失败
     *
     * @return
     */
    public boolean isTokenError() {
        if (getNetErrCode() != null && getNetErrCode().equals(REQUEST_TOKEN_FAIL)) {
            return true;
        }
        return false;
    }

//    /**
//     * 判断是否是Authcode认证失败
//     *
//     * @return
//     */
//    public boolean isAuthcodeError() {
//        if (getNetErrCode() != null && getNetErrCode().equals(REQUEST_AUTH_CODE_FAIL)) {
//            return true;
//        }
//        return false;
//    }
}
