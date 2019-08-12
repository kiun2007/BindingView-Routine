package com.taobao.weex.ui.component.input;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.WXStyle;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.view.spinner.WXSpinner;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXUtils;

import org.apache.taobao.weex.R;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

@Component(lazyload = false)
public class WXSelectComponent extends WXComponent<LinearLayout> implements AdapterView.OnItemSelectedListener,WXSpinner.DropDownListener {

    private JSONArray selectItems = null;
    SelectAdapter selectAdapter = null;
    ImageView imgArrow = null;
    WXSpinner spinner;
    Animation downAnimation = null;
    Animation upAnimation = null;

    public WXSelectComponent(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    @Override
    protected LinearLayout initComponentHostView(@NonNull Context context) {

        LinearLayout contentLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.component_select, null);

        imgArrow = contentLayout.findViewById(R.id.imgArrow);
        spinner = contentLayout.findViewById(R.id.selectMain);
        spinner.setAdapter(selectAdapter = new SelectAdapter(context));
        spinner.setOnItemSelectedListener(this);
        spinner.setOnDropDownListener(this);
        spinner.setDropDownVerticalOffset(20);
        spinner.setDropDownReference(contentLayout);

        downAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_down);
        upAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_up);
        return contentLayout;
    }

    @WXComponentProp(name = "items")
    public void setItems(JSONArray items){
        selectItems = items;
        selectAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void destroy() {
        spinner.setOnDropDownListener(null);
        spinner.setOnItemSelectedListener(null);
        super.destroy();
    }

    @Override
    public void onDropDownShow() {
        if (imgArrow != null){
            imgArrow.setRotation(270);
            imgArrow.startAnimation(upAnimation);
        }
    }

    @Override
    public void onDropDownHide() {
        if (imgArrow != null){
            imgArrow.setRotation(90);
            imgArrow.startAnimation(downAnimation);
        }
    }

    private class SelectAdapter implements SpinnerAdapter {

        private Context mContext;

        DataSetObservable mDataSetObservable = new DataSetObservable();

        public SelectAdapter(Context context){
            mContext = context;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return createView(position, convertView, parent, true);
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            mDataSetObservable.registerObserver(observer);
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            mDataSetObservable.unregisterObserver(observer);
        }

        public void notifyDataSetChanged() {
            mDataSetObservable.notifyChanged();
        }

        @Override
        public int getCount() {
            if(selectItems == null){
                return 0;
            }
            return selectItems.size();
        }

        @Override
        public Object getItem(int position) {
            if (selectItems != null){
                return selectItems.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        private View createView(int position, View convertView, ViewGroup parent, boolean isDropDown){

            View contentView;

            if (convertView == null){
                contentView = LayoutInflater.from(mContext).inflate( isDropDown ? R.layout.component_select_item : R.layout.component_select_content, null);
            }else{
                contentView = convertView;
            }

            TextView text = contentView == null ? null : (TextView) contentView.findViewById(R.id.textContent);

            JSONObject item = (JSONObject) getItem(position);
            if (text != null && item != null){
                text.setText(item.getString("name"));

                if (getStyles().containsKey(Constants.Name.FONT_SIZE)){
                    text.setTextSize(COMPLEX_UNIT_PX, WXStyle.getFontSize(getStyles(),getViewPortWidth()));
                }

                if (spinner.getSelectedItemPosition() == position){
                    if (getStyles().containsKey(Constants.Name.SELECTED_COLOR)){
                        text.setTextColor(WXResourceUtils.getColor(WXStyle.getSelectedColor(getStyles())));
                    }
                }else{
                    if (getStyles().containsKey(Constants.Name.COLOR)){
                        text.setTextColor(WXResourceUtils.getColor(WXStyle.getTextColor(getStyles())));
                    }
                }
            }
            return contentView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return createView(position, convertView, parent, false);
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
