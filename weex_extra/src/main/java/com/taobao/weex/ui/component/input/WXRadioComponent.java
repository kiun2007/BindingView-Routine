/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.taobao.weex.ui.component.input;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.StateListUtils;
import com.taobao.weex.utils.WXResourceUtils;

import java.util.HashMap;
import java.util.Map;

@Component(lazyload = false)
public class WXRadioComponent extends WXComponent<CompoundButton> implements CompoundButton.OnCheckedChangeListener {

    String mValue;
    String color = null;
    String selectedColor = null;
    boolean isCheckBox = false;

    public WXRadioComponent(WXSDKInstance instance, WXVContainer parent, boolean isLazy, BasicComponentData basicComponentData) {
        super(instance, parent, isLazy, basicComponentData);
    }

    @Override
    protected CompoundButton initComponentHostView(@NonNull Context context) {

        CompoundButton button = null;
        if ("check".equals(getAttrs().get(Constants.Name.TYPE))){
            button = new CheckBox(context);
            isCheckBox = true;
        }else if ("radio".equals(getAttrs().get(Constants.Name.TYPE))){
            button = new RadioButton(context);
        }

        if (button != null){
            initStyle(button);
        }
        return button;
    }

    private void initStyle(CompoundButton button){

        String color = (String) getStyles().get(Constants.Name.COLOR);
        if (color != null){
            this.color = color;
        }
        setColor(color, button);

        selectedColor = (String) getStyles().get(Constants.Name.SELECTED_COLOR);
    }

    private void setColor(String color, CompoundButton button){

        if (TextUtils.isEmpty(color)){
            return;
        }

        button.setTextColor(WXResourceUtils.getColor(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setButtonTintList(StateListUtils.createColorStateList(WXResourceUtils.getColor(color)));
        }
    }

    @WXComponentProp(name = "text")
    public void setText(String text){
        getHostView().setText(text);
    }

    @Override
    public void refreshData(WXComponent component) {
        super.refreshData(component);
    }

    @Override
    public void updateExtra(Object extra) {
        super.updateExtra(extra);
    }

    @Override
    public void addEvent(String type) {

        if (Constants.Event.CHANGE.equals(type)){
            getHostView().setOnCheckedChangeListener(this);
        }
        super.addEvent(type);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (mValue != null && buttonView.isPressed()){
            Map<String, Object> params = new HashMap<>(2);
            params.put("value", mValue);
            params.put("timeStamp", System.currentTimeMillis());
            if (isCheckBox && !isChecked){
                params.put("remove", true);
            }
            fireEvent(Constants.Event.CHANGE, params);
        }
    }

    @WXComponentProp(name = "value")
    public void setValue(String value){
        mValue = value;
    }

    @WXComponentProp(name = "checked")
    public void setChecked(boolean checked){
        getHostView().setChecked(checked);
        setColor(checked ? selectedColor : color, getHostView());
    }
}