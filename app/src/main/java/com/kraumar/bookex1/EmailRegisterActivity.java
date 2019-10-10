package com.kraumar.bookex1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class EmailRegisterActivity extends AppCompatActivity {


    static final String TAG = "bookex";

    //variables
    EditText password_1;
    EditText password_2;
    EditText eMail;
    Button loginBut;
    FirebaseAuth xfirebaseAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);


        xfirebaseAuth = FirebaseAuth.getInstance();
        eMail         = findViewById(R.id.emailRegister);
        password_1    = findViewById(R.id.password1);
        password_2    = findViewById(R.id.password2);
        loginBut      = findViewById(R.id.registerBut);

        loginBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String email_text = eMail.getText().toString();
                String pwd1 = password_1.getText().toString();
                String pwd2 = password_2.getText().toString();

                if(email_text.isEmpty()){

                    eMail.setError("Prosze podac email");
                    eMail.requestFocus();
                }
                else if (pwd1.isEmpty()){

                    password_1.setError("Prosze podac haslo");
                    password_1.requestFocus();
                }
                else if (pwd2.isEmpty()){

                    password_2.setError("Prosze powtorzyc haslo");
                    password_2.requestFocus();
                }
                else if (!pwd1.equals(pwd2)){

                    password_1.setError("Hasla sie nie zgadza");
                    password_1.requestFocus();

                    password_2.setError("Hasla sie nie zgadza");
                    password_2.requestFocus();


                }
                else if (!(email_text.isEmpty()) && !(pwd1.isEmpty()) && !(pwd2.isEmpty()) && pwd1.equals(pwd2)){

                    xfirebaseAuth.createUserWithEmailAndPassword(email_text,pwd2).addOnCompleteListener(EmailRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){

                                try {

                                    throw task.getException();

                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthWeakPasswordException weakPassword) {

                                    Log.d(TAG, "onComplete: weak_password");
                                    password_1.setError("Weak Password");
                                    password_1.requestFocus();


                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException malformedEmail) {

                                    Log.d(TAG, "onComplete: malformed_email");
                                    eMail.setError("Malformed Email");
                                    eMail.requestFocus();

                                }
                                catch (FirebaseAuthUserCollisionException existEmail){

                                    Log.d(TAG, "onComplete: exist_email");
                                    eMail.setError("Email already Exists");
                                    eMail.requestFocus();


                                }
                                catch (Exception e) {

                                    Log.d(TAG, "onComplete: " + e.getMessage());

                                }

                            }
                            else{

                                Intent intent = new Intent(EmailRegisterActivity.this,MainActivity.class);
                                overridePendingTransition(0,0);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(intent);

                            }


                        }
                    });

                }

                else {

                    Toast.makeText(EmailRegisterActivity.this,"Error",Toast.LENGTH_SHORT).show();

                }




            }
        });




    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(EmailRegisterActivity.this,EmailLoginActivity.class);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);

    }



}
