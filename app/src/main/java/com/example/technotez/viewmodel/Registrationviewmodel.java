package com.example.technotez.viewmodel;

import android.app.Application;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.technotez.repository.AuthenticationRepository;
import com.google.firebase.auth.FirebaseUser;

public class Registrationviewmodel extends AndroidViewModel {

    private AuthenticationRepository repository;
    private MutableLiveData<Boolean> loginSuccessLiveData=new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData=new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> userData;

    public MutableLiveData<FirebaseUser> getUserData() {
        return userData;
    }

    public MutableLiveData<Boolean> getLoginSuccessLiveData() {
        return loginSuccessLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public Registrationviewmodel(@NonNull Application application) {
        super(application);
        repository=new AuthenticationRepository(application);
        userData=repository.getFirebaseUserMutableLiveData();
        loginSuccessLiveData=repository.getLoginSuccessLiveData();
        errorLiveData=repository.getErrorLiveData();
    }

    public void register(String email, String password, String name, ProgressDialog pd){
        repository.register(email,password,name,pd);
    }
    public void login(String email, String password, ProgressDialog pd){
        repository.login(email,password,pd);
    }

}
