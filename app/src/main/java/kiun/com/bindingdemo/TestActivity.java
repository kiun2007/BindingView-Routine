package kiun.com.bindingdemo;

import android.view.View;
import android.widget.Button;

import kiun.com.bindingdemo.databinding.ActivityTestBinding;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.VueViewActivity;

public class TestActivity extends BVBaseActivity<ActivityTestBinding> {
    @Override
    public int getViewId() {
        return R.layout.activity_test;
    }

    private String data = "{\"adNm\":\"德露苑社区居委会\",\"createTime\":\"2019-06-05 16:04:11\",\"cwsStatus\":\"0\",\"engId\":\"ca46c8e6db2a4d1aa94767f2fd96bcd6\",\"isCenwtSupply\":\"0\",\"isFluexc\":\"0\",\"isMtarea\":\"0\",\"isPoverty\":\"0\",\"updateTime\":\"2019-06-05 16:04:11\",\"userStatus\":\"0\",\"villId\":\"31d9a50f11224b9e8aacc2b278c752d4\",\"villageCode\":\"110109001001\",\"visitDate\":\"2019-06-05\",\"wasPoverty\":\"0\",\"watersupplyPer\":0.0,\"recPersId\":\"afa0851be8624383971636ea729d6a86\"}";
    private String pull = "{\"engId\":\"ca46c8e6db2a4d1aa94767f2fd96bcd6\",\"guid\":\"8330E74158EB1759E0530DC3010A9154\",\"objId\":\"cdfd832dc10f465090aa52db4965e68a\",\"plnaId\":\"002041001001\",\"state\":\"1\",\"villageCode\":\"110109001001\",\"villageName\":\"德露苑社区居委会\"}";

    @Override
    public void initView() {

//        http://10.0.2.2:8020/SupervisonSumit/integration/vill/maintenance.html?debug=1
//        mViewBinding.hybirdContainer.set
    }

    public void onClick(View view){

        Button button = (Button) view;
//        VueViewActivity.context(this).url("http://www.baidu.com").start();
        if(button.getText().equals("HTML5_0")){
            VueViewActivity.context(this).url("http://192.168.0.107:8081/dist/index.js").extra("good", 1.0).start();
        }else if (button.getText().equals("HTML5_1")){
            VueViewActivity.context(this).url("http://192.168.0.107:8081/dist/pages/vill/maintenance.js").extra("data", data).extra("pull", pull).start();
        }else if (button.getText().equals("HTML5_2")){
            VueViewActivity.context(this).url("http://192.168.0.109:8020/SupervisonSumit/integration/vill/duty-manage.html").start();
//            VueViewActivity.context(this).url("http://192.168.0.109:8020/SupervisonSumit/integration/rsvr/test.html").start();
        }
    }
}
