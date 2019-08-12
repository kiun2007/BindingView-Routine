package kiun.com.bindingdemo.bean;

import kiun.com.bindingdemo.warp.CheckBoxGroup;

public class CheckBoxItem implements CheckBoxGroup.CheckItem {

    private String name;
    private String id;

    public CheckBoxItem(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public Object getValue() {
        return id;
    }
}
