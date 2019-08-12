package kiun.com.bindingdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import kiun.com.bindingdemo.anim.Bing;

public class AnimActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_anim);
    }

    public void onClick(View view){
        Bing bing = findViewById(R.id.animBing);
        bing.start();
    }
}
