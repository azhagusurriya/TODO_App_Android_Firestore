package com.example.todoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import viewmodels.UserViewModel;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = this.getClass().getCanonicalName();
    private TextView tvCreateAccount;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSignIn;
    private ProgressBar progressBar;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        this.userViewModel = UserViewModel.getInstance();

        this.tvCreateAccount = findViewById(R.id.tvCreateAccount);
        this.tvCreateAccount.setOnClickListener(this);

        this.edtEmail = findViewById(R.id.edtEmail);
        this.edtEmail.setText("surriya@gmail.com");
        this.edtPassword = findViewById(R.id.edtPassword);
        this.edtPassword.setText("surriya123");

        this.btnSignIn = findViewById(R.id.btnSignIn);
        this.btnSignIn.setOnClickListener(this);

        this.progressBar = findViewById(R.id.progressBar);

        this.userViewModel.getUserRepository().signInStatus.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String status) {
                if(status.equals("SUCCESS")){
                    progressBar.setVisibility(View.INVISIBLE);
                    goToMain();
                }else if (status.equals("LOADING")){
                    progressBar.setVisibility(View.VISIBLE);
                }else if(status.equals("FAILURE")){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void goToMain(){
        this.finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public void onClick(View view) {
        if(view != null){
            switch (view.getId()){
                case R.id.tvCreateAccount:{
                    Intent signUpIntent = new Intent(this, SignUpActivity.class);
                    startActivity(signUpIntent);
                    break;
                }
                case R.id.btnSignIn:{
                    //verify the user
                    this.validateLogin();
                    break;
                }
                default: break;
            }
        }
    }

    private void validateLogin(){
        String email = this.edtEmail.getText().toString();
        String password = this.edtPassword.getText().toString();

        //ask the view model to verify the credentials
        this.userViewModel.validateUser(email, password);
    }
}