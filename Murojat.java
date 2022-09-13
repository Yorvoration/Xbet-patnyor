package com.UzCodeMD.xpak;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Murojat extends AppCompatActivity {

    private DataLogin MyDb;
    private DataSozlama DBSOZ;
    private FirebaseFirestore db;
    private DocumentReference documentReference;

    private Button btnjonatmur;
    private EditText editelmur,edixatmur;
    private TextView txttelmur,txttelmur2,txtmalmur;

    public final String NAME = "NAME";
    public final String PAROL = "PAROL";
    public final String TEL = "TEL";
    public final String DAVLAT = "DAVLAT";
    public final String ISM = "ISM";
    public final String FAMILYA = "FAMILYA";
    public final String POCHTA = "POCHTA";
    public final String NET_KASH = "NET_KASH";
    public final String BUGUNPUL = "BUGUNPUL";
    public final String KECHAPUL = "KECHAPUL";
    public final String HAFTAPUL = "HAFTAPUL";
    public final String OYPUL = "OYPUL";
    public final String JAMIPUL = "JAMIPUL";
    public final String YANGILIKLAR = "YANGILIKLAR";
    public final String MUROJATMAYDON = "MUROJATMAYDON";
    public final String TARIXOTKAZMA = "TARIXOTKAZMA";
    public final String ALOQA = "ALOQA";
    public final String PROMOCOD = "PROMOCOD";
    public final String USERID = "USERID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.murojat);

        MyDb = new DataLogin(this);
        DBSOZ = new DataSozlama(this);
        db = FirebaseFirestore.getInstance();

        btnjonatmur = findViewById(R.id.btnjonatmur);
        editelmur = findViewById(R.id.editelmur);
        edixatmur = findViewById(R.id.edixatmur);
        txttelmur = findViewById(R.id.txttelmur);
        txttelmur2 = findViewById(R.id.txttelmur2);
        txtmalmur = findViewById(R.id.txtmalmur);

        btnjonatmur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oqi1();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (editelmur.getText().toString().isEmpty()){
                            editelmur.setError("bo`sh");
                        }else {
                            if (Objects.equals(txttelmur.getText().toString(),"TextView")){
                                editelmur.setError("bunday foydalanuvchi yo`q");
                                txttelmur.setText("");
                                txttelmur2.setText(".");
                            }else {
                                String textt = txttelmur.getText().toString();
                                String textt1 = txttelmur2.getText().toString();
                                if (Objects.equals(textt,textt1)){
                                    txttelmur.setText("");
                                    txttelmur2.setText(".");
                                    if (edixatmur.getText().toString().isEmpty()){
                                        edixatmur.setError("bo`sh");
                                    }else{
                                        txtmalmur.setVisibility(View.VISIBLE);
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.getDefault());
                                        String currentDateandTime = sdf.format(new Date());
                                        String name = "";
                                        String parol = "";
                                        String tel5 = "";
                                        Toast.makeText(Murojat.this, currentDateandTime, Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    txttelmur.setText("");
                                    txttelmur2.setText(".");
                                    editelmur.setError("bunday foydalanuvchi yo`q");
                                }
                            }
                        }
                    }
                },1500);
            }
        });
    }
    public void oqi1(){
        if (editelmur.getText().toString().isEmpty()){
        }
        else {
            documentReference = db.document("telefonlar/"+editelmur.getText().toString());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String nomi      =     documentSnapshot.getString(NAME);
                        String paroli   =      documentSnapshot.getString(PAROL);
                        String teli     =      documentSnapshot.getString(TEL);
                        String davlati    =    documentSnapshot.getString(DAVLAT);
                        String ismi      =     documentSnapshot.getString(ISM);
                        String familyasi   =   documentSnapshot.getString(FAMILYA);
                        String pochta      =   documentSnapshot.getString(POCHTA);
                        String net_kash    =   documentSnapshot.getString(NET_KASH);
                        String bugunpul    =   documentSnapshot.getString(BUGUNPUL);
                        String kechpul     =   documentSnapshot.getString(KECHAPUL);
                        String haftapul    =   documentSnapshot.getString(HAFTAPUL);
                        String oypul      =    documentSnapshot.getString(OYPUL);
                        String jamipul      =  documentSnapshot.getString(JAMIPUL);
                        String yangiliklar  =  documentSnapshot.getString(JAMIPUL);
                        String murojatmaydon = documentSnapshot.getString(JAMIPUL);
                        String tarixotkazma  = documentSnapshot.getString(JAMIPUL);
                        String aloqa     =     documentSnapshot.getString(JAMIPUL);
                        String promocod    =   documentSnapshot.getString(PROMOCOD);
                        String userid     =    documentSnapshot.getString(USERID);

                        txttelmur.setText(nomi);
                        txttelmur2.setText(nomi);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }
}
