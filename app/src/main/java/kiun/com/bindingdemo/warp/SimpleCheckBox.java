package kiun.com.bindingdemo.warp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class SimpleCheckBox extends android.support.v7.widget.AppCompatCheckBox {

    private AttributeSet mAttrs;
    private int mDefStyleAttr = -1;

    public SimpleCheckBox(Context context) {
        this(context, null);
    }

    public SimpleCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.checkboxStyle);
    }

    public SimpleCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        for (int i = 0; i < attrs.getAttributeCount(); i ++){
            String name = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            if (name.equals("style")){
                mDefStyleAttr = Integer.parseInt(value.replace("@",""));
            }
        }
    }

    public AttributeSet getAttrs() {
        return mAttrs;
    }

    public int getDefStyleAttr() {
        return mDefStyleAttr;
    }
}
