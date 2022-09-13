package com.UzCodeMD.xpak;

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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class PasswordUpdate extends AppCompatActivity {

    public TextView txtnamepass,txtniknamepass,txtparolpas;
    private EditText edijoriyparpass,ediparolpas,ediqaytaparpas;
    private Button btnsaqlaspas;
    private DataLogin MyDb;
    private DataSozlama DBSOZ;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.passwordupdate);

        MyDb = new DataLogin(this);
        DBSOZ = new DataSozlama(this);
        db = FirebaseFirestore.getInstance();
        txtniknamepass = findViewById(R.id.txtniknamepass);
        txtnamepass = findViewById(R.id.txtnamepass);
        txtparolpas = findViewById(R.id.txtparolpas);
        edijoriyparpass = findViewById(R.id.edijoriyparpass);
        ediparolpas = findViewById(R.id.ediparolpas);
        ediqaytaparpas = findViewById(R.id.ediqaytaparpas);
        btnsaqlaspas = findViewById(R.id.btnsaqlaspas);
        oqishsh();
        Toast.makeText(PasswordUpdate.this, txtniknamepass.getText().toString()+" va "+
                txtnamepass.getText().toString(), Toast.LENGTH_SHORT).show();

        btnsaqlaspas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edijoriyparpass.getText().toString().isEmpty()){
                    edijoriyparpass.setError("bo`sh");
                }else {
                    if (ediparolpas.getText().toString().isEmpty()){
                        ediparolpas.setError("bo`sh");
                    }else {
                        if (ediqaytaparpas.getText().toString().isEmpty()){
                            ediqaytaparpas.setError("bo`sh");
                        }else {
                            if (Objects.equals(txtparolpas.getText().toString(),edijoriyparpass.getText().toString())){
                                if (Objects.equals(ediparolpas.getText().toString(),ediqaytaparpas.getText().toString())){

                                    documentReference = db.document("user/"+txtniknamepass.getText().toString());
                                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()){
                                                String nomi = documentSnapshot.getString(NAME);
                                                String parolo = documentSnapshot.getString(PAROL);
                                                String telo = documentSnapshot.getString(TEL);
                                                String davlato = documentSnapshot.getString(DAVLAT);
                                                String ismio = documentSnapshot.getString(ISM);
                                                String familyasio = documentSnapshot.getString(FAMILYA);
                                                String pochtao = documentSnapshot.getString(POCHTA);
                                                String bugunpulo = documentSnapshot.getString(BUGUNPUL);
                                                String kechpulo = documentSnapshot.getString(KECHAPUL);
                                                String haftapulo = documentSnapshot.getString(HAFTAPUL);
                                                String oypulo= documentSnapshot.getString(OYPUL);
                                                String jamipulo = documentSnapshot.getString(JAMIPUL);
                                                String yangiliklaro = documentSnapshot.getString(JAMIPUL);
                                                String murojatmaydono = documentSnapshot.getString(JAMIPUL);
                                                String tarixotkazmao = documentSnapshot.getString(JAMIPUL);
                                                String aloqao = documentSnapshot.getString(JAMIPUL);
                                                String promocodo = documentSnapshot.getString(PROMOCOD);
                                                String userido = documentSnapshot.getString(USERID);
                                                String niknameo = documentSnapshot.getString(NIKNAME);
                                                String yosho = documentSnapshot.getString(YOSH);

                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.getDefault());
                                                String currentDateandTime = sdf.format(new Date());
                                                String name = nomi;
                                                String nikname = niknameo;
                                                String parol5 = ediparolpas.getText().toString();
                                                String tel5 = telo;
                                                String davlat = davlato;
                                                String ism = ismio;
                                                String familya = familyasio;
                                                String pochta = pochtao;
                                                String yosh = yosho;
                                                String bugunpul = bugunpulo;
                                                String kechpul = kechpulo;
                                                String haftapul = haftapulo;
                                                String oypul = oypulo;
                                                String jamipul = jamipulo;
                                                String yangiliklar = yangiliklaro;
                                                String murojatmaydon = murojatmaydono;
                                                String tarixotkazma = tarixotkazmao;
                                                String aloqa = aloqao;
                                                String promocod = promocodo;
                                                String userid = userido;
                                                //String kirishvaqt = currentDateandTime;
                                                String vaqt = currentDateandTime;

                                                Map<String, Object> user1 = new HashMap<>();
                                                user1.put(NIKNAME, nikname);            //0
                                                user1.put(NAME, name);                  //1
                                                user1.put(PAROL, parol5);               //2
                                                user1.put(TEL, tel5);                   //3
                                                user1.put(DAVLAT, davlat);              //4
                                                user1.put(ISM, ism);                    //5
                                                user1.put(FAMILYA, familya);            //6
                                                user1.put(POCHTA, pochta);              //7
                                                user1.put(BUGUNPUL, bugunpul);          //9
                                                user1.put(KECHAPUL, kechpul);           //10
                                                user1.put(HAFTAPUL, haftapul);          //11
                                                user1.put(OYPUL, oypul);                //12
                                                user1.put(JAMIPUL, jamipul);            //13
                                                user1.put(YANGILIKLAR,yangiliklar );    //14
                                                user1.put(MUROJATMAYDON, murojatmaydon);//15
                                                user1.put(TARIXOTKAZMA, tarixotkazma);  //16
                                                user1.put(ALOQA, aloqa);                //17
                                                user1.put(PROMOCOD, promocod);          //18
                                                user1.put(USERID, userid);              //19
                                                user1.put(YOSH, yosh);                  //20

                                                Boolean result = MyDb.kiritish(nikname,parol5,tel5,davlat,ism,familya,
                                                        pochta,userid,bugunpul,kechpul,haftapul,oypul,jamipul,
                                                        yangiliklar,murojatmaydon,tarixotkazma,aloqa,promocod,userid);
                                                if (result == true){
                                                    db.collection("user").document(nikname).set(user1)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    finish();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                        }
                                                    });
                                                }
                                                else {
                                                }

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                                }
                                else {
                                    ediqaytaparpas.setError("xato kiritdingiz");
                                }
                            }else {
                                edijoriyparpass.setError("kiritilgan parol xato");
                            }
                        }
                    }
                }
            }
        });
    }
    private void oqishsh() {
        Cursor res = MyDb.oqish();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer1.append(res.getString(1));
                stringBuffer2.append(res.getString(2));
                stringBuffer3.append(res.getString(3));
            }
            txtniknamepass.setText(stringBuffer1.toString());

            documentReference = db.document("user/"+txtniknamepass.getText().toString());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String nomi = documentSnapshot.getString(NAME);
                        String paroli = documentSnapshot.getString(PAROL);
                        String teli = documentSnapshot.getString(TEL);
                        String davlati = documentSnapshot.getString(DAVLAT);
                        String ismi = documentSnapshot.getString(ISM);
                        String familyasi = documentSnapshot.getString(FAMILYA);
                        String pochta = documentSnapshot.getString(POCHTA);
                        String net_kash = documentSnapshot.getString(NET_KASH);
                        String bugunpul = documentSnapshot.getString(BUGUNPUL);
                        String kechpul = documentSnapshot.getString(KECHAPUL);
                        String haftapul = documentSnapshot.getString(HAFTAPUL);
                        String oypul = documentSnapshot.getString(OYPUL);
                        String jamipul = documentSnapshot.getString(JAMIPUL);
                        String yangiliklar = documentSnapshot.getString(JAMIPUL);
                        String murojatmaydon = documentSnapshot.getString(JAMIPUL);
                        String tarixotkazma = documentSnapshot.getString(JAMIPUL);
                        String aloqa = documentSnapshot.getString(JAMIPUL);
                        String promocod = documentSnapshot.getString(PROMOCOD);
                        String userid = documentSnapshot.getString(USERID);
                        String kir = documentSnapshot.getString(NIKNAME);

                        txtnamepass.setText(ismi);
                        txtparolpas.setText(paroli);
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
