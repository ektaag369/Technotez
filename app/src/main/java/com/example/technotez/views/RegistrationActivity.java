package com.example.technotez.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.technotez.R;
import com.example.technotez.viewmodel.Registrationviewmodel;
import com.example.technotez.model.TeacherDetails;
import com.example.technotez.databinding.ActivityRegistrationBinding;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    EditText email_txt,password_txt,confirmpswd_txt,name_txt;
    Button register;
    TextView signin;
    private Registrationviewmodel registrationviewmodel;
    ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email_txt=findViewById(R.id.email);
        password_txt=findViewById(R.id.password);
        register=findViewById(R.id.register);
        confirmpswd_txt=findViewById(R.id.conpassword);
        name_txt=findViewById(R.id.name);
        signin=findViewById(R.id.signin);

        registrationviewmodel=new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(Registrationviewmodel.class);


        registrationviewmodel.getLoginSuccessLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginSuccess) {
                if(loginSuccess){
                    Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=email_txt.getText().toString();
                String password=password_txt.getText().toString();
                String conpswd=confirmpswd_txt.getText().toString();
                String name=name_txt.getText().toString();

                if(!email.isEmpty() && !password.isEmpty() && !conpswd.isEmpty() && !name.isEmpty()){
                    pd=new ProgressDialog(RegistrationActivity.this);
                    pd.setMessage("Please wait...");
                    pd.show();
                    registrationviewmodel.register(email,password,name,pd);
                }
                else if(password.length()<6){
                    Toast.makeText(RegistrationActivity.this, "Password must have atleast 6 characters", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(conpswd)){
                    Toast.makeText(RegistrationActivity.this, "Please enter correct password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegistrationActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}