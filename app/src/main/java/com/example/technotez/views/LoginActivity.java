package com.example.technotez.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.technotez.R;
import com.example.technotez.viewmodel.Registrationviewmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email_txt,password_txt;
    Button login;
    TextView signup;
    private Registrationviewmodel registrationviewmodel;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_txt=findViewById(R.id.log_email);
        password_txt=findViewById(R.id.log_password);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);

        registrationviewmodel=new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(Registrationviewmodel.class);

        registrationviewmodel.getLoginSuccessLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginSuccess) {
                if(loginSuccess){
                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=email_txt.getText().toString();
                String password=password_txt.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    pd=new ProgressDialog(LoginActivity.this);
                    pd.setMessage("Please wait...");
                    pd.show();
                    registrationviewmodel.login(email,password,pd);
                }
                else if(password.length()<6){
                    Toast.makeText(LoginActivity.this, "Password must have atleast 6 characters", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}