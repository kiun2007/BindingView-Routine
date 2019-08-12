package kiun.com.bindingdemo.warp;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bindingdemo.R;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class CheckBoxGroup extends GridLayout implements CompoundButton.OnCheckedChangeListener {

    public interface CheckItem{
        String getTitle();
        Object getValue();
    }

    List<CheckItem> checkItemList;
    List<CheckBox> allCheckBox = new LinkedList<>();
    InverseBindingListener selectListener;
    Object selectedValue;
    SetCaller<Object> setCaller;
    boolean groupIsList = false;
    AttributeSet attributeSet;
    int defStyleAttr = -1;

    public CheckBoxGroup(Context context) {
        super(context);
    }

    public CheckBoxGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBoxGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof CheckBox){
                CheckBox checkBox = (CheckBox) view;
                allCheckBox.add(checkBox);
                checkBox.setOnCheckedChangeListener(this);
            }
        }
    }

    private void setCheckItemList(List<CheckItem> checkItemList){
        this.checkItemList = checkItemList;
        if (groupIsList) {
            if(getChildCount() > 0 && getChildAt(0) instanceof SimpleCheckBox){
                SimpleCheckBox sample = (SimpleCheckBox) getChildAt(0);
                attributeSet = sample.getAttrs();
                defStyleAttr = sample.getDefStyleAttr();
            }
            groupIsList = false;
        }
        removeAllViews();
        allCheckBox.clear();

        for (int i = 0; i < checkItemList.size(); i ++){
            CheckItem item = checkItemList.get(i);
            CheckBox newCheckBox = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                newCheckBox = new CheckBox(getContext(), null,0, defStyleAttr);
            }else{
                newCheckBox = new CheckBox(getContext(), null,defStyleAttr);
            }

            newCheckBox.setText(item.getTitle());
            addView(newCheckBox);
            allCheckBox.add(newCheckBox);
            newCheckBox.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!compoundButton.isPressed()){
            return;
        }
        if (b){
            for (CheckBox checkBox : allCheckBox){
                if (compoundButton.equals(checkBox)){
                    selectedValue = checkBox.getTag();
                    if (selectListener != null){
                        selectListener.onChange();
                    }
                    if (setCaller != null){
                        setCaller.call(selectedValue);
                    }
                    continue;
                }
                checkBox.setChecked(false);
            }
        }else{
            compoundButton.setChecked(true);
        }
    }

    private void setSelectedValue(Object value){
        for (CheckBox checkBox : allCheckBox){
            if (value == null){
                checkBox.setChecked(checkBox.getTag() == null);
            }else{
                if (value != null && value.equals(checkBox.getTag())){
                    checkBox.setChecked(true);
                }else{
                    checkBox.setChecked(false);
                }
            }
        }
        selectedValue = value;
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static Object getSelectedValue(CheckBoxGroup view){
        return view.selectedValue;
    }

    @BindingAdapter("onChanged")
    public static void setOnChanged(CheckBoxGroup view, SetCaller<Object> setCaller){
        view.setCaller = setCaller;
    }

    @BindingAdapter("checkItems")
    public static void setCheckItems(CheckBoxGroup view, List<CheckItem> checkItems){
        view.setCheckItemList(checkItems);
    }

    @BindingAdapter("groupIsList")
    public static void setIsList(CheckBoxGroup view, boolean isList){
        view.groupIsList = isList;
    }

    @BindingAdapter("checkText")
    public static void setCheckText(CheckBox checkText, String text){
        checkText.setText(text);
    }

    @BindingAdapter("selectedValue")
    public static void setSelectedValue(CheckBoxGroup view, Object selectedValue){
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setSelectedValue(selectedValue);
            }
        },50);
    }

    @BindingAdapter(value = {"selectedValueAttrChanged"}, requireAll = false)
    public static void setSelectedValueChanged(CheckBoxGroup view, InverseBindingListener timeListener) {
        view.selectListener = timeListener;
    }

    @BindingAdapter("itemValue")
    public static void setItemValue(CheckBox checkBox, Object itemValue){
        checkBox.setTag(itemValue);
    }
}
