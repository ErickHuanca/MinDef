package com.example.ministeriodefensa;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
//    private LoginViewModel loginViewModel;

    private EditText pName;
    private EditText pEmail;
    private EditText pPass;
    private Button pRegister;

    private String dName;
    private String dEmail;
    private String dPass;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        pName = (EditText) findViewById(R.id.name);
        pEmail = (EditText) findViewById(R.id.email);
        pPass = (EditText) findViewById(R.id.password);
        pRegister = (Button) findViewById(R.id.register);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        pRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dName = pName.getText().toString();
                dEmail = pEmail.getText().toString();
                dPass = pPass.getText().toString();

                if (!dName.isEmpty() && !dEmail.isEmpty() && !dPass.isEmpty()){
                    if (dPass.length()>=6){
                        registerUser();
                    }else {
//                        Toast.makeText(this,"La contraseñia debe ser mayor a 6 caracteres",Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void registerUser (){

        mAuth.createUserWithEmailAndPassword(dEmail,dPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String,Object> map = new HashMap<>();
                    map.put("name",dName);
                    map.put("email",dEmail);
//                    map.put("pass",dPass);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();
                            }else {
//                              Toast.makeText(this,"No se puedo crear los datos correctamente",Toast.LENGTH_LONG).show();

                            }

                        }
                    });


                }else {
//                    Toast.makeText(this,"No se puedo registrar este usuario",Toast.LENGTH_LONG).show();

                }
            }
        });

    }





//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
