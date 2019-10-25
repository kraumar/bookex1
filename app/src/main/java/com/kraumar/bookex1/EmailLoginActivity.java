package com.kraumar.bookex1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class EmailLoginActivity extends AppCompatActivity {

    static final String TAG = "bookex";

    EditText eMailAdress;
    EditText pass;
    Button button;
    TextView register;
    FirebaseAuth xFirebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        xFirebaseAuth = FirebaseAuth.getInstance();
        eMailAdress   = findViewById(R.id.emailLogin);
        pass          = findViewById(R.id.passwordLogin);
        button        = findViewById(R.id.loginBut);
        register      = findViewById(R.id.textLogin);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailString = eMailAdress.getText().toString();
                String passString = pass.getText().toString();


                if(emailString.isEmpty()){

                    eMailAdress.setError("Please insert an Email");
                    eMailAdress.requestFocus();

                }

                else if(passString.isEmpty()){

                    pass.setError("Please insert the Password");
                    pass.requestFocus();

                }
                else if(!(passString.isEmpty() && emailString.isEmpty())){

                    xFirebaseAuth.signInWithEmailAndPassword(emailString,passString).addOnCompleteListener(EmailLoginActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){

                                try {

                                    throw task.getException();

                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthInvalidUserException invalidEmail) {

                                    Log.d(TAG, "invalid_email");
                                    eMailAdress.setError("Invalid Email");
                                    eMailAdress.requestFocus();

                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException wrongPassword) {

                                    Log.d(TAG, "wrong_password");
                                    pass.setError("Wrong Password");
                                    pass.requestFocus();

                                }
                                catch (Exception e) {

                                    Log.d(TAG, e.getMessage());


                                }


                            }
                            else{

                                Intent intent = new Intent(EmailLoginActivity.this,MainActivity.class);
                                overridePendingTransition(0,0);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(intent);

                            }

                        }
                    });

                }
                else{

                    Toast.makeText(EmailLoginActivity.this,"Error",Toast.LENGTH_SHORT);

                }





            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EmailLoginActivity.this,EmailRegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);

            }
        });

    }



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(EmailLoginActivity.this,MainLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);

    }
}
