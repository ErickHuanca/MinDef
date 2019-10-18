package com.example.ministeriodefensa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProductDeliveryActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    DatabaseReference dbProd;
    DatabaseReference dbProd2;


    private TextView pTvMostrarProducto;
    private TextView pTvMostrarCantidad;
    private TextView pTvMostrarMedida;
    private Spinner pSpTipoProd;
    private EditText pEtCantidad;
//    private TextView pQuantit;

    private TextView pTvMuestraAdiProd;
    private TextView pTvMuestraAdiCant;

    String datoMuestraProd;
    String datoMuestraQuan;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_delivery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        String optProduct [] = {"Seleccionar", "Azucar", "Harina de trigo", "Arroz", "Fideos", "Picota de mango", "Pala con mango", "Kit de cocina", "Kit de limpieza", "Kit de higiene", "Otros"};
        String optSection [] = {"Ninguno", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "C1"};
        int optCapacity [] = {0 ,14750, 12300, 12300,8938, 506, 360, 2520, 2223, 2223, 12};
        String optUnitMeas [] = {"Ninguno","Quintales", "Unidades", "Cajas"};

        pTvMostrarProducto = (TextView)findViewById(R.id.tvMostrarProducto);
        pTvMostrarCantidad = (TextView)findViewById(R.id.tvMostrarCantidad);
        pTvMostrarMedida = (TextView)findViewById(R.id.tvmostrarMedida);
        pSpTipoProd = (Spinner) findViewById(R.id.spProduct);
        pEtCantidad = (EditText) findViewById(R.id.etQuantity);

        pTvMuestraAdiProd = (TextView) findViewById(R.id.tvMuestraProd);
        pTvMuestraAdiCant = (TextView) findViewById(R.id.tvMuestraQuan);

        datoMuestraProd = "";
        datoMuestraQuan = "";

        mostrarProdSeleccion(optProduct, optSection, optCapacity, optUnitMeas);


    }

    public void btnEntregar (View vista){

        String optProduct [] = {"Seleccionar", "Azucar", "Harina de trigo", "Arroz", "Fideos", "Picota de mango", "Pala con mango", "Kit de cocina", "Kit de limpieza", "Kit de higiene", "Otros"};
        String optSection [] = {"Ninguno", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "C1"};
        int optCapacity [] = {0 ,14750, 12300, 12300,8938, 506, 360, 2520, 2223, 2223, 10000};
        String optUnitMeas [] = {"Ninguno","Quintales", "Unidades", "Cajas"};

        String datoMosProducto = pTvMostrarProducto.getText().toString();
        String datoMostrarCantidad = pTvMostrarCantidad.getText().toString();
        String datoMostrarUnidad = pTvMostrarCantidad.getText().toString();
        final String datoSpProducto = pSpTipoProd.getSelectedItem().toString();
        String datoCantidadEntreda = pEtCantidad.getText().toString();

        if (datoMosProducto != "Seleccionar" && datoCantidadEntreda != null && !datoCantidadEntreda.equals("")){

            final int nCantidad = Integer.parseInt(datoCantidadEntreda);

            if (nCantidad>0){
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                String idUser = mAuth.getCurrentUser().getUid();

                deliveryHistory(datoSpProducto, nCantidad, currentDate, currentTime, idUser);

                dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(datoSpProducto);
                dbProd.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dbProd2 = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(datoSpProducto);
                        int val = Integer.parseInt(dataSnapshot.getValue().toString());
                        val = val - nCantidad;
                        dbProd2.setValue(val);
                        limpiarCampos();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }

                });

                datoMuestraProd = datoMuestraProd + datoSpProducto +"\n";
                datoMuestraQuan = datoMuestraQuan + nCantidad +"\n";
                pTvMuestraAdiProd.setText(datoMuestraProd);
                pTvMuestraAdiCant.setText(""+datoMuestraQuan);
//            actualizacionCantidad(producto, nCantidad);

                Toast toastProduct = Toast.makeText(ProductDeliveryActivity.this,"Entrega satisfactorio, puede agregar otro.",Toast.LENGTH_LONG);
                toastProduct.setGravity(Gravity.CENTER, 0,0 );
                toastProduct.show();

                limpiarCampos();
            }
            else {
                Toast toastProduct = Toast.makeText(ProductDeliveryActivity.this,"La cantidad de entrega no puede ser 0.",Toast.LENGTH_LONG);
                toastProduct.setGravity(Gravity.CENTER, 0,0 );
                toastProduct.show();
            }

        }
        else {

            Toast toastProduct = Toast.makeText(ProductDeliveryActivity.this,"Complete los campos correctamente.",Toast.LENGTH_LONG);
            toastProduct.setGravity(Gravity.CENTER, 0,0 );
            toastProduct.show();
        }

        limpiarCampos();


    }

    public void deliveryHistory(String datoSpProducto, int nCantidad, String currentDate, String currentTime, String idUser) {
        Map<String,Object> map = new HashMap<>();
        map.put("iduser",idUser);
        map.put("product",datoSpProducto);
        map.put("quantity",nCantidad);
//            map.put("section",seccion);
        map.put("date", currentDate);
        map.put("time", currentTime);
        mDatabase.child("DeliveryProductHistory").push().setValue(map);
    }

    public void mostrarProdSeleccion(final String[] optProduct, final String[] optSection, final int[] optCapacity, final String[] optUnitMeas) {
        ArrayAdapter<String> viewSpProduct = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, optProduct);
        pSpTipoProd.setAdapter(viewSpProduct);

        pSpTipoProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        pTvMostrarProducto.setText("Ninguno");
                        pTvMostrarMedida.setText(optUnitMeas[0]);
                        pTvMostrarCantidad.setText("0");

                        break;

                    case 1:
                        pTvMostrarProducto.setText(""+optProduct[1]);
                        pTvMostrarMedida.setText(optUnitMeas[1]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[1]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 2:
                        pTvMostrarProducto.setText(""+optProduct[2]);
                        pTvMostrarMedida.setText(optUnitMeas[1]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[2]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 3:
                        pTvMostrarProducto.setText(""+optProduct[3]);
                        pTvMostrarMedida.setText(optUnitMeas[1]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[3]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 4:
                        pTvMostrarProducto.setText(""+optProduct[4]);
                        pTvMostrarMedida.setText(optUnitMeas[1]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[4]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 5:
                        pTvMostrarProducto.setText(""+optProduct[5]);
                        pTvMostrarMedida.setText(optUnitMeas[2]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[5]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 6:
                        pTvMostrarProducto.setText(""+optProduct[6]);
                        pTvMostrarMedida.setText(optUnitMeas[2]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[7]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 7:
                        pTvMostrarProducto.setText(""+optProduct[7]);
                        pTvMostrarMedida.setText(optUnitMeas[3]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[7]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 8:
                        pTvMostrarProducto.setText(""+optProduct[8]);
                        pTvMostrarMedida.setText(optUnitMeas[3]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[8]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 9:
                        pTvMostrarProducto.setText(""+optProduct[9]);
                        pTvMostrarMedida.setText(optUnitMeas[3]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[9]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 10:
                        pTvMostrarProducto.setText(""+optProduct[10]);
                        pTvMostrarMedida.setText(optUnitMeas[2]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[10]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pTvMostrarCantidad.setText("" + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
//                        este codigo sirve para hacer una consulta a la base de datos
//                        dbProd = FirebaseDatabase.getInstance().getReference("Products");
//                        Query qProd10 = dbProd.orderByChild("product").equalTo(optProduct[10]);
//                        qProd10.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()){
//                                    int val = 0;
//                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                                        val = val + Integer.parseInt(snapshot.child("quantity").getValue().toString());
//                                    }
//                                    pTvMostrarCantidad.setText(""+val);
//                                }
//                                else {
//                                    limpiarCampos();
//                                    Toast toastProduct = Toast.makeText(ProductDeliveryActivity.this,"No hay disponible este producto.",Toast.LENGTH_LONG);
//                                    toastProduct.setGravity(Gravity.CENTER, 0,0 );
//                                    toastProduct.show();
//                                }
//                            }
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                            }
//                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void finalizar (View vista){
        startActivity(new Intent(ProductDeliveryActivity.this, HomeActivity.class));

        finish();
    }

    public void limpiarCampos(){
        pTvMostrarProducto.setText("Ninguno");
        pTvMostrarCantidad.setText("0");
        pTvMostrarMedida.setText("Ninguno");
        pSpTipoProd.setSelection(0);
        pEtCantidad.setText("");
    }
}

