package com.UzCodeMD.xpak.ui.otkazma;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OtkazmaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OtkazmaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is otkazma fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}