package kiun.com.bvroutine.handlers;

import kiun.com.bvroutine.base.BaseHandler;

public class ListHandler<T> extends BaseHandler<T> {

    public final static int LIST_LOADING = 0;
    public final static int LIST_ERROR = 1;
    public final static int LIST_EMPTY = 2;

    private static String normalError = null;
    private static String normalEmpty = null;
    private static String normalLoading = null;

    protected String errorDesc;
    protected String emptyDesc;
    protected String loadingDesc;

    protected String runError;

    private int type = 0;
    private int errorLayout = 0;

    public ListHandler(int handlerBR, int errorLayout) {
        this(handlerBR, errorLayout, null,null);
    }

    public ListHandler(int handlerBR, int errorLayout, String errorDesc, String emptyDesc) {
        this(handlerBR, errorLayout, errorDesc, emptyDesc, null);
    }

    public ListHandler(int handlerBR, int errorLayout, String errorDesc, String emptyDesc, String loadingDesc) {
        super(handlerBR);
        this.errorDesc = errorDesc == null ? normalError : errorDesc;
        this.emptyDesc = emptyDesc == null ? normalEmpty : emptyDesc;
        this.loadingDesc = loadingDesc == null ? normalLoading : loadingDesc;
        this.errorLayout = errorLayout;
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public static void configNormal(String error, String emptyDesc, String loading){
        normalError = error;
        normalEmpty = emptyDesc;
        normalLoading = loading;
    }

    public String getDesc(){
        if (type == LIST_ERROR){
            return errorDesc != null ? errorDesc : runError;
        }else if (type == LIST_EMPTY){
            return emptyDesc;
        }
        return loadingDesc;
    }

    public void setEmptyDesc(String emptyDesc) {
        this.emptyDesc = emptyDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public void setLoadingDesc(String loadingDesc) {
        this.loadingDesc = loadingDesc;
    }

    public String getRunError() {
        return runError;
    }

    public int getErrorLayout() {
        return errorLayout;
    }

    public void error(String error){
        runError = error;
    }

    public boolean isLoading(){
        return type == LIST_LOADING;
    }

    public boolean isError(){
        return type == LIST_ERROR;
    }

    public boolean isEmpty(){
        return type == LIST_EMPTY;
    }
}
