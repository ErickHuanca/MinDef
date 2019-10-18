package com.example.ministeriodefensa;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HistoryDeliveryActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference dbProd;

    TextView pTvHistoryReceipt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_delivery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        pTvHistoryReceipt = (TextView) findViewById(R.id.tvHistoryDelivery);

        historialDelivery();

    }

    public void historialDelivery() {


        String userId = mAuth.getCurrentUser().getUid();

        dbProd = FirebaseDatabase.getInstance().getReference("DeliveryProductHistory");
        Query qProd = dbProd.orderByChild("iduser").equalTo(userId);
        qProd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String valor = "";

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        valor = valor + snapshot.child("product").getValue().toString()+" ";
                        valor = valor + snapshot.child("quantity").getValue().toString()+"  ";
                        valor = valor + snapshot.child("date").getValue().toString()+" ";
                        valor = valor + snapshot.child("time").getValue().toString()+"\n";

                    }
                    pTvHistoryReceipt.setText(valor);
                } else {
//                    limpiarCampos();
                    Toast toastProduct = Toast.makeText(HistoryDeliveryActivity.this, "No hay historial de entrega", Toast.LENGTH_LONG);
                    toastProduct.setGravity(Gravity.CENTER, 0, 0);
                    toastProduct.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
