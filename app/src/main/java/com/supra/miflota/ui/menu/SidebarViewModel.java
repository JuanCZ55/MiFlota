package com.supra.miflota.ui.menu;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SidebarViewModel  extends ViewModel {
    private final MutableLiveData<String> userEmail = new MutableLiveData<>();

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        userEmail.setValue(email);
    }
}
