package com.UzCodeMD.xpak;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AdminPanel extends AppCompatActivity {

    private Button btnadminkir;
    private EditText ediadminparol,ediadminlogin;
    private TextView txtadminname,txtadminparol;

    private DataLogin MyDb;
    private DataSozlama DBSOZ;
    private DataAdminPanel MyAdminData;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
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
    public final String NIKNAME = "NIKNAME";
    public final String YOSH = "YOSH";
    public final String PUL = "PUL";
    public final String KARTA = "KARTA";
    public final String VAQT = "VAQT";
    public final String KIRISHVAQT = "KIRISHVAQT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpanel);

        db = FirebaseFirestore.getInstance();
        MyAdminData = new DataAdminPanel(this);
        btnadminkir = findViewById(R.id.btnadminkir1);
        ediadminlogin = findViewById(R.id.ediadminlogin);
        ediadminparol = findViewById(R.id.ediadminparol);
        txtadminname = findViewById(R.id.txtadminname1);
        txtadminparol = findViewById(R.id.txtadminparol1);

        oqi1();
        //admindatawrite();

        btnadminkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ediadminlogin.getText().toString().isEmpty()){
                    ediadminlogin.setError("bo`sh");
                    if (ediadminparol.getText().toString().isEmpty()){
                        ediadminparol.setError("bo`sh");
                    }
                }else {
                    if (ediadminparol.getText().toString().isEmpty()){
                        ediadminparol.setError("bo`sh");
                    }else {
                        if (Objects.equals(txtadminname.getText().toString(),ediadminlogin.getText().toString())){
                            if (Objects.equals(txtadminparol.getText().toString(),ediadminparol.getText().toString())){
                                String val = ediadminparol.getText().toString();
                                if (val.length()<7){
                                    ediadminparol.setError("parol yetarli emas");
                                }
                                else {
                                    Intent intent = new Intent(AdminPanel.this,AdminAsosiyPanel.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }else {
                                ediadminparol.setError("parol xato");
                            }
                        }else {
                            ediadminlogin.setError("login xato");
                        }
                    }
                }
            }
        });
    }
    public void oqi1() {
        documentReference = db.collection("admin").document("admin");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String nomi   =documentSnapshot.getString(NAME);
                    String paroli =documentSnapshot.getString(PAROL);

                    txtadminname.setText(nomi);
                    txtadminparol.setText(paroli);
//                    d("MyTAG", documentSnapshot.toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
