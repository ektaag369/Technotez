package com.example.technotez.repository;

import android.app.Application;
import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class AuthenticationRepository {
    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> loginSuccessLiveData=new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData=new MutableLiveData<>();
    private FirebaseAuth mauth;
    FirebaseFirestore firestore;

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }
    public MutableLiveData<Boolean> getLoginSuccessLiveData() {
        return loginSuccessLiveData;
    }
    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
    public AuthenticationRepository(Application application){
        this.application=application;
        firebaseUserMutableLiveData=new MutableLiveData<>();
        mauth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        if(mauth.getCurrentUser()!=null){
            firebaseUserMutableLiveData.postValue(mauth.getCurrentUser());
        }

    }
    public void register(String email, String password, String name, ProgressDialog pd){
        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    addDetails(email,password,name);
                }
                else{
                    pd.dismiss();
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void addDetails(String email, String password, String name) {
        HashMap<String,Object> registerdetails=new HashMap<>();
        String uniqueId = UUID.randomUUID().toString();

        registerdetails.put("Email",email);
        registerdetails.put("Password",password);
        registerdetails.put("UserName",name);
        registerdetails.put("UserID",uniqueId);
        firestore.collection("TeacherDetails").add(registerdetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(application, "Registration Done", Toast.LENGTH_SHORT).show();
                loginSuccessLiveData.setValue(true);
                firebaseUserMutableLiveData.postValue(mauth.getCurrentUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, "Registration failed: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(String email, String password, ProgressDialog pd){
        mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null){
                        pd.dismiss();
                        loginSuccessLiveData.setValue(true);
                        Toast.makeText(application, "Login Done", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        pd.dismiss();
                        errorLiveData.setValue("Failed to get user information");
                        Toast.makeText(application, "Login Failed"+errorLiveData, Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    pd.dismiss();
                    errorLiveData.setValue("Authentication failed:" + task.getException().getMessage());
                    Toast.makeText(application, "Login Failed"+errorLiveData, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
