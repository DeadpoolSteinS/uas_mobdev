package edu.upi.cs.yudiwbs.uas_template;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelFragmentSatu extends ViewModel {
    public MutableLiveData<String> data;
    public MutableLiveData<String> jawaban;
    public MutableLiveData<String> statusJawaban;

    public void setData(String data){
        this.data.setValue(data);
    }
    public void setJawaban(String data){
        this.jawaban.setValue(data);
    }
    public void setStatusJawaban(String data){
        this.statusJawaban.setValue(data);
    }

    public LiveData<String> getLiveData() {
        return data;
    }
    public LiveData<String> getLiveJawaban() {
        return jawaban;
    }

    public ViewModelFragmentSatu(){
        this.data = new MutableLiveData<String>();
        this.data.setValue("default");

        this.jawaban = new MutableLiveData<String>();
        this.jawaban.setValue("");

        this.statusJawaban = new MutableLiveData<String>();
        this.statusJawaban.setValue("");
    }
}
