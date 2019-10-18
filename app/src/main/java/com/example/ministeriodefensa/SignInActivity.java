package com.example.ministeriodefensa;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText pEmail;
    private EditText pPass;
    private Button pRegister;
    private TextView pNewRegister;

    private String dEmail;
    private String dPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        pEmail = (EditText) findViewById(R.id.email);
        pPass = (EditText) findViewById(R.id.password);
        pRegister = (Button) findViewById(R.id.register);
        pNewRegister = (TextView) findViewById(R.id.newRegister);

        pRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dEmail = pEmail.getText().toString();
                dPass = pPass.getText().toString();

                if (!dEmail.isEmpty() && !dPass.isEmpty()){
                    if (dPass.length()>=6){
                        signInUser();
                    }else {
                        Toast.makeText(SignInActivity.this,"La contraseñia debe ser mayor a 6 caracteres",Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        pNewRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this, "Registre nuevo usuario.", Toast.LENGTH_SHORT).show();

                Intent pVista = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(pVista);
            }
        });



    }


    public void signInUser (){

        mAuth.signInWithEmailAndPassword(dEmail, dPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
                    finish();
                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));

                } else {
                    // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                    Toast toastProduct = Toast.makeText(SignInActivity.this,"Autenticacion Fallida!!!.",Toast.LENGTH_LONG);
                    toastProduct.setGravity(Gravity.CENTER, 0,0 );
                    toastProduct.show();
//                            updateUI(null);
                }
            }
        });
    }

}
