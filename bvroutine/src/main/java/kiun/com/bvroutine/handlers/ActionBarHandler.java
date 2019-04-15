package kiun.com.bvroutine.handlers;

import android.app.Activity;
import android.content.Context;

import kiun.com.bvroutine.base.BaseHandler;

public class ActionBarHandler extends BaseHandler {

    /**
     * 返回导航按钮.
     */
    public static final int TAG_BACKBOTTON_ITEM = 100;

    /**
     * 标题.
     */
    public static final int TAG_TITLE_ITEM = 101;

    /**
     * 右边按钮.
     */
    public static final int TAG_RIGHTBUTTON_ITEM = 102;

    /**
     * 右边图片.
     */
    public static final int TAG_RIGHTIMAGE_ITEM = 103;

    @Override
    public void onClick(Context context, int tag, Object data) {
        switch (tag){
            case TAG_BACKBOTTON_ITEM:{
                if (context instanceof Activity){
                    ((Activity) context).finish();
                }
                break;
            }
            case TAG_RIGHTBUTTON_ITEM:{
                break;
            }
            case TAG_TITLE_ITEM:{
                break;
            }
            case TAG_RIGHTIMAGE_ITEM:{
                break;
            }
        }
    }
}
