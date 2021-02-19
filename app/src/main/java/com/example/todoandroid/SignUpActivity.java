package com.example.todoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import models.User;
import viewmodels.UserViewModel;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getCanonicalName();
    private Button btnCreateAccount;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtName;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.userViewModel = UserViewModel.getInstance();

        this.edtEmail = findViewById(R.id.edtEmail);
        this.edtPassword = findViewById(R.id.edtPassword);
        this.edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        this.edtName = findViewById(R.id.edtName);
        this.btnCreateAccount = findViewById(R.id.btnCreateAccount);
        this.btnCreateAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if( view != null){
            switch (view.getId()){
                case R.id.btnCreateAccount: {
                  if (this.validateData()){
                      //save data to database
                      this.saveUserToDB();

                      //go to main activity
                      this.goToMain();

                  }
                }
                default:
                    break;
            }
        }

    }
    private void saveUserToDB(){
        User newUser = new User();

        newUser.setEmail(this.edtEmail.getText().toString());
        newUser.setName(this.edtName.getText().toString());
        newUser.setPassword(this.edtPassword.getText().toString());

        this.userViewModel.addUser(newUser);
    }

    private void goToMain(){
        this.finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }



    private Boolean validateData(){
        if (this.edtEmail.getText().toString().isEmpty()){
            this.edtEmail.setError("Please enter email");
            return false;
        }

        // add a check for valid email address format

        if (this.edtPassword.getText().toString().isEmpty()){
            this.edtPassword.setError("Password cannot be empty");
            return false;
        }

        if (this.edtConfirmPassword.getText().toString().isEmpty()){
            this.edtConfirmPassword.setError("Please provide confirm password");
            return false;
        }

        if (!edtPassword.getText().toString().equals(this.edtConfirmPassword.getText().toString())){
            this.edtPassword.setError("Both passwords must be same");
            this.edtConfirmPassword.setError("Both passwords must be same");
            return false;
        }

        return true;
    }
}