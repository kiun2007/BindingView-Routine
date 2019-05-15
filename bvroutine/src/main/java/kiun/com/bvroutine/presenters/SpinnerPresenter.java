package kiun.com.bvroutine.presenters;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.List;
import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.data.SpinnerValue;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.callers.GetThrowCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.SpinnerView;

public class SpinnerPresenter extends ViewsPresenter<Spinner> implements AdapterView.OnItemSelectedListener {

    private GetThrowCaller<Object,SpinnerValue> callers[] = null;
    private ArrayAdapter<String> arrayAdapters[] = null;
    private List allLists[];
    SpinnerView spinnerView;
    RequestBindingPresenter presenter;

    public SpinnerPresenter(int layout, SpinnerView spinnerView, Spinner ...args){
        super(args);
        callers = new GetThrowCaller[args.length];
        arrayAdapters = new ArrayAdapter[args.length];
        this.spinnerView = spinnerView;
        presenter = spinnerView.getRequestPresenter();
        allLists = new List[args.length];

        for (int i = 0; i < args.length; i++) {
            arrayAdapters[i] = new ArrayAdapter<String>(spinnerView.getContext(), layout);
            arrayAdapters[i].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            allViews.get(i).setAdapter(arrayAdapters[i]);
            allViews.get(i).setOnItemSelectedListener(this);
        }
    }

    public<IN extends Object> SpinnerPresenter setGetter(int index, GetThrowCaller<IN,SpinnerValue> caller){
        callers[index] = (GetThrowCaller<Object, SpinnerValue>) caller;
        return this;
    }

    public void start(){
        if (callers == null || callers.length == 0){
            return;
        }
        presenter.addRequest(()->callers[0].call(null), v -> onDataComplete(0, v));
    }

    private void onDataComplete(int index, SpinnerValue value){
        if (index < 0 || index >= arrayAdapters.length){
            return;
        }

        allLists[index] = value.getItems();
        arrayAdapters[index].setNotifyOnChange(false);
        arrayAdapters[index].clear();
        for (Object item : value.getItems()){
            GetCaller<Object,String> caller;
            if ((caller = value.getCaller()) != null){
                arrayAdapters[index].add(caller.call(item));
            }else{
                arrayAdapters[index].add(item.toString());
            }
        }
        arrayAdapters[index].notifyDataSetChanged();
        allViews.get(index).setSelection(value.getPosition());
    }

    public<T,I> T getValue(int index, GetCaller<I,T> caller){
        if (index < 0 || index >= allViews.size()){
            return null;
        }
        int position = allViews.get(index).getSelectedItemPosition();
        return caller.call((I)allLists[index].get(position));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int index = allViews.indexOf(parent);
        int nextIndex = index + 1;

        if (nextIndex < allViews.size() && callers[nextIndex] != null){
            presenter.addRequest(()->callers[nextIndex].call(allLists[index].get(position)), v -> onDataComplete(nextIndex, v), this::onError);
        }
    }

    private void onError(Exception ex){

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
