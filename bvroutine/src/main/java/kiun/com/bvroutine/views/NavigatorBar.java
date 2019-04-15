package kiun.com.bvroutine.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.databinding.ActionBarBinding;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.views.viewmodel.ActionBarItem;

/**
 * DeviceService
 * popchain
 *
 * @author kiun_2007 on 2018/3/29.
 * Copyright (c) 2017-2018 The Popchain Core Developers
 */
public class NavigatorBar extends LinearLayout {

    public static final String TAG = "NAVIGATOR_BAR";

    private ImageView leftButton;
    private ImageView rightImageView;
    private TextView titleTextView;
    private TextView leftTitleTextView;
    private TextView rightTextView;
    private LinearLayout rightButton;
    private ActionBarItem barItem = new ActionBarItem();
    ActionBarBinding dataBinding;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public NavigatorBar(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public NavigatorBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public NavigatorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.action_bar, this, true);
        dataBinding.setBarItem(barItem);

        leftButton = findViewById(R.id.backImageView);
        rightImageView = findViewById(R.id.rightImageView);
        titleTextView = findViewById(R.id.titleTextView);
        rightButton = findViewById(R.id.rightButtonLL);
        rightTextView = findViewById(R.id.rightTitleTextView);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NavigatorBar);
        String title = array.getString(R.styleable.NavigatorBar_barTitle);
        if (title != null) {
            titleTextView.setText(title);
        }

        int resId = array.getResourceId(R.styleable.NavigatorBar_barRightImage, -1);

        if (resId != -1) {
            rightImageView.setVisibility(View.VISIBLE);
            rightImageView.setImageDrawable(getResources().getDrawable(resId));
            rightButton.setVisibility(VISIBLE);
        } else {
            rightImageView.setVisibility(View.GONE);
        }

        Drawable drawable = array.getDrawable(R.styleable.NavigatorBar_barLeftImage);
        if (drawable != null) {
            leftButton.setImageDrawable(drawable);
        }

        drawable = array.getDrawable(R.styleable.NavigatorBar_titleImage);
        if (drawable != null) {
            titleTextView.setBackground(drawable);
        }

        String leftTitle = array.getString(R.styleable.NavigatorBar_barLeftTitle);
        if (leftTitle != null) {
            leftTitleTextView = findViewById(R.id.leftTitleTextView);
            leftTitleTextView.setText(leftTitle);
            leftTitleTextView.setVisibility(VISIBLE);
        }

        String rightTitle = array.getString(R.styleable.NavigatorBar_barRightTitle);
        if (rightTitle != null) {
            rightButton.setVisibility(VISIBLE);
            rightTextView.setVisibility(VISIBLE);
            rightTextView.setText(rightTitle);
        }

        int type = array.getInteger(R.styleable.NavigatorBar_barStyle, 0);
        switch (type) {
            case 2:
                ObjectUtil.batchCall(item->item.setTextColor(0xFF000000), titleTextView, rightTextView);
                leftButton.setImageDrawable(getResources().getDrawable(R.drawable.svg_left_arrow_black));
            case 1:
                this.findViewById(R.id.mainLinear).setBackgroundResource(0);
                break;
        }
        setTag(TAG);
    }

    public ActionBarItem getBarItem() {
        return barItem;
    }

    public void setBarItem(ActionBarItem barItem){
        this.barItem = barItem;
        dataBinding.setBarItem(barItem);
    }
}
