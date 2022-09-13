package com.UzCodeMD.xpak;

import static android.util.Log.d;
import static com.airbnb.lottie.L.TAG;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdminMalumotlar extends AppCompatActivity {
    private EditText ediateladmino,ediismadmino,edidarajaadmino,edifamilyaadmino
            ,ediyoshadmino,edinetcashadmino,edipromoadmino,edibugunpuladmino
            ,edikechapuladmino,edihaftapuladmino,edioypuladmino,edijamipuladmino
            ,ediidadmino;
    private TextView txtdataadmino;
    private Button btntahriradmino,btnsaqlaadmino;
    private DataAdminPanel MyAdminData;

    private FirebaseFirestore db;
    private DocumentReference documentReference;
    public final String NAME = "NAME";
    public final String NIKNAME = "NIKNAME";
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
    public final String YOSH = "YOSH";
    public final String SONGIVAQT = "SONGIVAQT";
    public final String PHOTO = "PHOTO";
    public final String DARAJA = "DARAJA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.adminmalumotlar);

        db = FirebaseFirestore.getInstance();
        MyAdminData = new DataAdminPanel(this);

        ediateladmino = findViewById(R.id.ediateladmino);
        ediismadmino = findViewById(R.id.ediismadmino);
        edidarajaadmino = findViewById(R.id.edidarajaadmino);
        ediyoshadmino = findViewById(R.id.ediyoshadmino);
        edifamilyaadmino = findViewById(R.id.edifamilyaadmino);
        edinetcashadmino = findViewById(R.id.edinetcashadmino);
        edipromoadmino = findViewById(R.id.edipromoadmino);
        edibugunpuladmino = findViewById(R.id.edibugunpuladmino);
        edikechapuladmino = findViewById(R.id.edikechapuladmino);
        edihaftapuladmino = findViewById(R.id.edihaftapuladmino);
        edioypuladmino = findViewById(R.id.edioypuladmino);
        edijamipuladmino = findViewById(R.id.edijamipuladmino);
        ediidadmino = findViewById(R.id.ediidadmino);
        btntahriradmino = findViewById(R.id.btntahriradmino);
        btnsaqlaadmino = findViewById(R.id.btnsaqlaadmino);
        txtdataadmino = findViewById(R.id.dataadmino);
        readname();
        readfirebasedata();
        btnsaqlaadmino.setVisibility(View.INVISIBLE);
        ediateladmino.setEnabled(false);
        ediismadmino.setEnabled(false);
        edidarajaadmino.setEnabled(false);
        edifamilyaadmino.setEnabled(false);
        ediyoshadmino.setEnabled(false);
        edinetcashadmino.setEnabled(false);
        edipromoadmino.setEnabled(false);
        edibugunpuladmino.setEnabled(false);
        edikechapuladmino.setEnabled(false);
        edihaftapuladmino.setEnabled(false);
        edioypuladmino.setEnabled(false);
        edijamipuladmino.setEnabled(false);
        ediidadmino.setEnabled(false);

        btntahriradmino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnsaqlaadmino.setVisibility(View.VISIBLE);
                btntahriradmino.setVisibility(View.INVISIBLE);
                ediateladmino.setEnabled(true);
                ediismadmino.setEnabled(true);
                edidarajaadmino.setEnabled(true);
                edifamilyaadmino.setEnabled(true);
                ediyoshadmino.setEnabled(true);
                edinetcashadmino.setEnabled(true);
                edipromoadmino.setEnabled(true);
                edibugunpuladmino.setEnabled(true);
                edikechapuladmino.setEnabled(true);
                edihaftapuladmino.setEnabled(true);
                edioypuladmino.setEnabled(true);
                edijamipuladmino.setEnabled(true);
                ediidadmino.setEnabled(false);
            }
        });
        btnsaqlaadmino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btntahriradmino.setVisibility(View.VISIBLE);
                btnsaqlaadmino.setVisibility(View.INVISIBLE);
                String tel = ediateladmino.getText().toString();
                String tel1 = ediateladmino.getText().toString();
                String ism = ediismadmino.getText().toString();
                String daraja = edidarajaadmino.getText().toString();
                String familya = edifamilyaadmino.getText().toString();
                String yosh = ediyoshadmino.getText().toString();
                String netcash = edinetcashadmino.getText().toString();
                String promoc = edipromoadmino.getText().toString();
                String bugunpul = edibugunpuladmino.getText().toString();
                String kechapul = edikechapuladmino.getText().toString();
                String hatapul = edihaftapuladmino.getText().toString();
                String oypul = edioypuladmino.getText().toString();
                String jamipul = edijamipuladmino.getText().toString();
                String ud = ediidadmino.getText().toString();
                Map<String, Object> user1 = new HashMap<>();
                user1.put(TEL, tel);
                user1.put(ISM, ism);
                user1.put(DARAJA, daraja);
                user1.put(FAMILYA, familya);
                user1.put(YOSH, yosh);
                user1.put(NET_KASH, netcash);
                user1.put(PROMOCOD, promoc);
                user1.put(BUGUNPUL, bugunpul);
                user1.put(KECHAPUL, kechapul);
                user1.put(HAFTAPUL, hatapul);
                user1.put(OYPUL, oypul);
                user1.put(JAMIPUL, jamipul);
                user1.put(USERID, ud);

                int a = Integer.parseInt(edidarajaadmino.getText().toString());
                if (a<=5){
                    documentReference = db.document("user/"+txtdataadmino.getText().toString());
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String name = documentSnapshot.getString(NAME);
                                String tele = documentSnapshot.getString(TEL);
                                String promoe = documentSnapshot.getString(PROMOCOD);

                                if (Objects.equals(promoe,edipromoadmino)){
                                    Map<String, Object> usertel = new HashMap<>();
                                    usertel.put(NAME, name);
                                    usertel.put(TEL, tel);
                                    db.collection("user")
                                            .document(txtdataadmino.getText().toString())
                                            .update(user1);
                                    ediateladmino.setEnabled(false);
                                    ediismadmino.setEnabled(false);
                                    edidarajaadmino.setEnabled(false);
                                    edifamilyaadmino.setEnabled(false);
                                    ediyoshadmino.setEnabled(false);
                                    edinetcashadmino.setEnabled(false);
                                    edipromoadmino.setEnabled(false);
                                    edibugunpuladmino.setEnabled(false);
                                    edikechapuladmino.setEnabled(false);
                                    edihaftapuladmino.setEnabled(false);
                                    edioypuladmino.setEnabled(false);
                                    edijamipuladmino.setEnabled(false);
                                    ediidadmino.setEnabled(false);

                                    Map<String, Object> useridupdate = new HashMap<>();
                                    useridupdate.put(NAME, ism);
                                    useridupdate.put(NET_KASH, netcash);
                                    useridupdate.put(TEL, tel);

                                    Map<String, Object> useripromo = new HashMap<>();
                                    useripromo.put(NAME, name);
                                    useripromo.put(PROMOCOD, promoc);

                                    db.collection("id")
                                            .document(ediidadmino.getText().toString())
                                            .update(useridupdate);

                                    db.collection("telefonlar").
                                            document(tele).
                                            delete().
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        db.collection("telefonlar").document(tel1).set(usertel)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                    }
                                                                });
                                                    }
                                                }
                                            });
                                    db.collection("promocod").
                                            document(promoe).
                                            delete().
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        db.collection("promocod").document(promoc).set(useripromo)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                    }
                                                                });
                                                    }
                                                }
                                            });
                                }else {
                                    db.collection("promocod").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        List<String> list1 = new ArrayList<>();
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            list1.add(document.getId());
                                                            boolean contains = list1.contains(edipromoadmino.getText().toString());
                                                            if (contains == true) {
                                                                btnsaqlaadmino.setVisibility(View.VISIBLE);
                                                                btntahriradmino.setVisibility(View.INVISIBLE);
                                                                edipromoadmino.setError("bu promocod mavjut");
                                                                Toast.makeText(getApplicationContext(), "bu promocod mavjut", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                if (contains == false){
                                                                    Map<String, Object> usertel = new HashMap<>();
                                                                    usertel.put(NAME, name);
                                                                    usertel.put(TEL, tel);
                                                                    db.collection("user")
                                                                            .document(txtdataadmino.getText().toString())
                                                                            .update(user1);
                                                                    ediateladmino.setEnabled(false);
                                                                    ediismadmino.setEnabled(false);
                                                                    edidarajaadmino.setEnabled(false);
                                                                    edifamilyaadmino.setEnabled(false);
                                                                    ediyoshadmino.setEnabled(false);
                                                                    edinetcashadmino.setEnabled(false);
                                                                    edipromoadmino.setEnabled(false);
                                                                    edibugunpuladmino.setEnabled(false);
                                                                    edikechapuladmino.setEnabled(false);
                                                                    edihaftapuladmino.setEnabled(false);
                                                                    edioypuladmino.setEnabled(false);
                                                                    edijamipuladmino.setEnabled(false);
                                                                    ediidadmino.setEnabled(false);

                                                                    Map<String, Object> useridupdate = new HashMap<>();
                                                                    useridupdate.put(NAME, ism);
                                                                    useridupdate.put(NET_KASH, netcash);
                                                                    useridupdate.put(TEL, tel);

                                                                    Map<String, Object> useripromo = new HashMap<>();
                                                                    useripromo.put(NAME, name);
                                                                    useripromo.put(PROMOCOD, promoc);

                                                                    db.collection("id")
                                                                            .document(ediidadmino.getText().toString())
                                                                            .update(useridupdate);

                                                                    db.collection("telefonlar").
                                                                            document(tele).
                                                                            delete().
                                                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        db.collection("telefonlar").document(tel1).set(usertel)
                                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(Void unused) {
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                }
                                                                            });
                                                                    db.collection("promocod").
                                                                            document(promoe).
                                                                            delete().
                                                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        db.collection("promocod").document(promoc).set(useripromo)
                                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(Void unused) {
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                }
                                                                            });
                                                                }else {
                                                                    finish();
                                                                }

                                                            }
                                                        }
                                                        d(TAG, list1.toString());
                                                        //txtgetalldata.setText(list1.toString());
                                                    } else {
                                                        d(TAG, "Error getting documents: ", task.getException());
                                                        //txtgetalldata.setText(TAG);
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
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }else{
                    edidarajaadmino.setError("daraja xato");
                }

            }
        });

    }
    private void readfirebasedata(){
        documentReference = db.document("user/"+txtdataadmino.getText().toString());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String tel = documentSnapshot.getString(TEL);
                    String ism = documentSnapshot.getString(ISM);
                    String daraja = documentSnapshot.getString(DARAJA);
                    String familya = documentSnapshot.getString(FAMILYA);
                    String yoshi = documentSnapshot.getString(YOSH);
                    String netkash = documentSnapshot.getString(NET_KASH);
                    String promocode = documentSnapshot.getString(PROMOCOD);
                    String bugunP = documentSnapshot.getString(BUGUNPUL);
                    String kechaP = documentSnapshot.getString(KECHAPUL);
                    String haftaP = documentSnapshot.getString(HAFTAPUL);
                    String oyP = documentSnapshot.getString(OYPUL);
                    String jamiP = documentSnapshot.getString(JAMIPUL);
                    String id = documentSnapshot.getString(USERID);
                    ediateladmino.setText(tel);
                    ediismadmino.setText(ism);
                    edidarajaadmino.setText(daraja);
                    edifamilyaadmino.setText(familya);
                    ediyoshadmino.setText(yoshi);
                    edinetcashadmino.setText(netkash);
                    edipromoadmino.setText(promocode);
                    edibugunpuladmino.setText(bugunP);
                    edikechapuladmino.setText(kechaP);
                    edihaftapuladmino.setText(haftaP);
                    edioypuladmino.setText(oyP);
                    edijamipuladmino.setText(jamiP);
                    ediidadmino.setText(id);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
    private void readname(){
        Cursor res = MyAdminData.readAdmin();
        StringBuffer stringBuffer = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append(res.getString(1));
            }
            txtdataadmino.setText(stringBuffer.toString());
        }
    }
}
