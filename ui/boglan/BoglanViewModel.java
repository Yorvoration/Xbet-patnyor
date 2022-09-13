package com.UzCodeMD.xpak.ui.boglan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BoglanViewModel extends ViewModel {

    private MutableLiveData<String> mText1;

    public BoglanViewModel() {
        mText1 = new MutableLiveData<>();
        mText1.setValue("fragment");
    }

    public LiveData<String> getText() {
        return mText1;
    }
}