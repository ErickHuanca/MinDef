package com.example.ministeriodefensa;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProductReceiptActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    DatabaseReference dbProd;
    DatabaseReference dbProd2;


    private Spinner pSpTipoProd;
    private EditText pEtQuantity;
    private TextView pTvSectionView;

    private TextView pTvProductoLibre;
    private TextView pTvProductoCapacidad;
    private TextView pProdUnidMed;



    private String datoMuestraProd = "";
    private String datoMuestraQuan = "";
    private String datoMuestraSecc = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_receipt);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        pSpTipoProd = (Spinner) findViewById(R.id.spProduct);
        pEtQuantity = (EditText) findViewById(R.id.etQuantity);
        pTvSectionView = (TextView) findViewById(R.id.tvSectionView);

        String optProduct [] = {"Seleccionar", "Azucar", "Harina de trigo", "Arroz", "Fideos", "Picota de mango", "Pala con mango", "Kit de cocina", "Kit de limpieza", "Kit de higiene", "Otros"};
        String optSection [] = {"Ninguno", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "C1"};
        int optCapacity [] = {0 ,14750, 12300, 12300,8938, 506, 360, 2520, 2223, 2223, 10000};
        String optUnitMeas [] = {"Ninguno","Quintales", "Unidades", "Cajas"};

        muestraPrdSec(optProduct, optSection, optCapacity, optUnitMeas);

        pTvProductoLibre = (TextView) findViewById(R.id.tvProductoLibre);
        pTvProductoCapacidad = (TextView) findViewById(R.id.tvProductoCappacidad);
        pProdUnidMed = (TextView) findViewById(R.id.tvUnitMeas);


    }

    public void muestraPrdSec(final String[] optProduct, final String[] optSection, final int[] optCapacity, final String[] optUnitMeas) {
        ArrayAdapter<String> viewSpProduct = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, optProduct);
        pSpTipoProd.setAdapter(viewSpProduct);

        pSpTipoProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        pTvProductoCapacidad.setText(""+optCapacity[0]);
                        pTvSectionView.setText(optSection[0]);
                        pProdUnidMed.setText(optUnitMeas[0]);
                        break;

                    case 1:
                        pTvProductoCapacidad.setText(""+optCapacity[1]);
                        pTvSectionView.setText(optSection[1]);
                        pProdUnidMed.setText(optUnitMeas[1]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[1]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[1] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                        break;

                    case 2:
                        pTvProductoCapacidad.setText(""+optCapacity[2]);
                        pTvSectionView.setText(""+optSection[2]);
                        pProdUnidMed.setText(optUnitMeas[1]);

                        dbProd2 = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[2]);
                        dbProd2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[2] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 3:
                        pTvProductoCapacidad.setText(""+optCapacity[3]);
                        pTvSectionView.setText(""+optSection[3]);
                        pProdUnidMed.setText(optUnitMeas[1]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[3]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[3] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 4:
                        pTvProductoCapacidad.setText(""+optCapacity[4]);
                        pTvSectionView.setText(""+optSection[4]);
                        pProdUnidMed.setText(optUnitMeas[1]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[4]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[4] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 5:
                        pTvProductoCapacidad.setText(""+optCapacity[5]);
                        pTvSectionView.setText(""+optSection[5]);
                        pProdUnidMed.setText(optUnitMeas[2]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[5]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[5] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 6:
                        pTvProductoCapacidad.setText(""+optCapacity[6]);
                        pTvSectionView.setText(""+optSection[6]);
                        pProdUnidMed.setText(optUnitMeas[2]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[6]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[6] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 7:
                        pTvProductoCapacidad.setText(""+optCapacity[7]);
                        pTvSectionView.setText(""+optSection[7]);
                        pProdUnidMed.setText(optUnitMeas[3]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[7]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[7] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 8:
                        pTvProductoCapacidad.setText(""+optCapacity[8]);
                        pTvSectionView.setText(""+optSection[8]);
                        pProdUnidMed.setText(optUnitMeas[3]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[8]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[8] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 9:
                        pTvProductoCapacidad.setText(""+optCapacity[9]);
                        pTvSectionView.setText(""+optSection[9]);
                        pProdUnidMed.setText(optUnitMeas[3]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[9]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[9] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;

                    case 10:
                        pTvProductoCapacidad.setText(""+optCapacity[10]);
                        pTvSectionView.setText(""+optSection[10]);
                        pProdUnidMed.setText(optUnitMeas[2]);

                        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(optProduct[10]);
                        dbProd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int val = 0;
                                val = optCapacity[10] - Integer.parseInt(dataSnapshot.getValue().toString());
                                pTvProductoLibre.setText(""+val);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void adicionar (View vista){

        String optProduct [] = {"Seleccionar", "Azucar", "Harina de trigo", "Arroz", "Fideos", "Picota de mango", "Pala con mango", "Kit de cocina", "Kit de limpieza", "Kit de higiene", "Otros"};
        String optSection [] = {"Ninguno", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "C1"};
        int optCapacity [] = {0 ,14750, 12300, 12300,8938, 506, 360, 2520, 2223, 2223, 10000};
        String optUnitMeas [] = {"Ninguno","Quintales", "Unidades", "Cajas"};

        final String producto = pSpTipoProd.getSelectedItem().toString();
        String cantidad = pEtQuantity.getText().toString();
        String seccion = pTvSectionView.getText().toString();

        TextView pMuestraProd = (TextView) findViewById(R.id.tvMuestraProd);
        TextView pMuestraQuan = (TextView) findViewById(R.id.tvMuestraQuan);
        TextView pMuestraSecc = (TextView) findViewById(R.id.tvMuestraSecc);


        if (producto != "Seleccionar" && cantidad != null && !cantidad.equals("") && seccion != "Ninguno"){

            final int nCantidad = Integer.parseInt(cantidad);



            String idProd = mDatabase.push().getKey();
            String idUser = mAuth.getCurrentUser().getUid();

            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


            cargarDatosFirebase(producto, seccion, nCantidad, idProd, idUser, currentDate, currentTime);

            datoMuestraProd = datoMuestraProd + producto +"\n";
            datoMuestraQuan = datoMuestraQuan + cantidad +"\n";
            datoMuestraSecc = datoMuestraSecc + seccion +"\n";
            pMuestraProd.setText(datoMuestraProd);
            pMuestraQuan.setText(datoMuestraQuan);
            pMuestraSecc.setText(datoMuestraSecc);


            actualizacionCantidad(producto, nCantidad);

            limpiarDatos();
        }
        else {

            Toast toastProduct = Toast.makeText(ProductReceiptActivity.this,"Complete los campos correctamente.",Toast.LENGTH_LONG);
            toastProduct.setGravity(Gravity.CENTER, 0,0 );
            toastProduct.show();
        }


    }

    public void actualizacionCantidad(final String producto, final int nCantidad) {
        dbProd = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(producto);
        dbProd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbProd2 = FirebaseDatabase.getInstance().getReference("CantidadProductos").child(producto);
                int val = Integer.parseInt(dataSnapshot.getValue().toString());
                val = val + nCantidad;
                dbProd2.setValue(val);
                limpiarDatos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }

    public void cargarDatosFirebase(String producto, String seccion, int nCantidad, String idProd, String idUser, String currentDate, String currentTime) {

        Map<String, Object> map = receiptHistory(producto, seccion, nCantidad, idProd, idUser, currentDate, currentTime);

//      mDatabase.child("Products").push().setValue(map);//funciona con la llave unica push()
        mDatabase.child("Products").child(idProd).setValue(map);
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task2) {
//                    if (task2.isSuccessful()){
//
//                        Toast toastProduct;
//                        toastProduct = Toast.makeText(ProductReceiptActivity.this,"Producto Adicionado.",Toast.LENGTH_LONG);
//                        toastProduct.setGravity(Gravity.CENTER, 0,-200 );
//                        toastProduct.show();
//
//                    }else {
//                        Toast.makeText(ProductReceiptActivity.this,"No se puedo crear los datos correctamente",Toast.LENGTH_LONG).show();
//
//                    }
//
//
//
//                }
//            });



    }

    public Map<String, Object> receiptHistory(String producto, String seccion, int nCantidad, String idProd, String idUser, String currentDate, String currentTime) {
        Map<String,Object> map = new HashMap<>();
        map.put("iduser",idUser);
        map.put("product",producto);
        map.put("quantity",nCantidad);
        map.put("section",seccion);
        map.put("date", currentDate);
        map.put("time", currentTime);
        mDatabase.child("ReceivedProductHistory").child(idProd).setValue(map);
        return map;
    }

    public void limpiarDatos(){
        pSpTipoProd.setSelection(0);
        pEtQuantity.setText("");
        pTvSectionView.setText("Ninguno");
        pTvProductoLibre.setText("0");
        pTvProductoCapacidad.setText("0");
    }

    public void finalizar (View vista){

        finish();

    }


}
