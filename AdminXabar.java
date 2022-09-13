package com.UzCodeMD.xpak;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AdminXabar extends AppCompatActivity {

    private ListView listView;
    private TextView txtxabnomi,txtxabklent,txtxadmin11,txtoqildi,txtkorildi,txtptichkadmin;
    private ImageButton imgbtn1;
    private EditText ediadminyoz1;


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
    public final String VAQT = "VAQT";
    public final String SONGIVAQT = "SONGIVAQT";
    public final String PHOTO = "PHOTO";
    public final String DARAJA = "DARAJA";
    public final String KIRISHVAQT = "KIRISHVAQT";
    public final String XABARBIZ = "XABARBIZ";
    public final String XABARSIZ = "XABARSIZ";
    public final String KORILDI = "KORILDI";
    public final String OQILDI = "OQILDI";
    private long backpasset;

    DataLogin MyDb;
    DataSozlama MySoz;
    private FirebaseFirestore db;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad  = "fr"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.adminxabar);

        db = FirebaseFirestore.getInstance();
        MyDb = new DataLogin(this);
        MySoz = new DataSozlama(this);

        listView = findViewById(R.id.xabaradminlist);
        txtxabnomi = findViewById(R.id.txtxabnomi);
        txtxabklent = findViewById(R.id.txtxabklent);
        txtxadmin11 = findViewById(R.id.txtxadmin11);
        imgbtn1 = findViewById(R.id.imgbtn1);
        ediadminyoz1 = findViewById(R.id.ediadminyoz1);
        txtkorildi = findViewById(R.id.txtkorildi);
        txtoqildi = findViewById(R.id.txtoqildi);
        txtptichkadmin = findViewById(R.id.txtptichkadmin);
        txtxabklent.setMovementMethod(new ScrollingMovementMethod());
        txtxadmin11.setMovementMethod(new ScrollingMovementMethod());
        cheskfirebase();
        admindataupdate();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (String) (listView.getItemAtPosition(position));
                txtxabnomi.setText(selectedFromList);
                listView.setVisibility(View.INVISIBLE);
                txtxabklent.setVisibility(View.VISIBLE);
                txtxadmin11.setVisibility(View.VISIBLE);
                txtptichkadmin.setVisibility(View.VISIBLE);
                admindataupdate();
                cheskfirebase();
                documentReference = db.document("xabar/"+txtxabnomi.getText().toString());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String xabaradmin = documentSnapshot.getString(KORILDI);
                            String xabarklent = documentSnapshot.getString(OQILDI);
                            txtkorildi.setText(xabaradmin);
                            txtoqildi.setText(xabarklent);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (Objects.equals(txtkorildi.getText().toString(),"1")){
                                        if (Objects.equals(txtoqildi.getText().toString(),"1")){
                                            new AlertDialog.Builder(AdminXabar.this)
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .setTitle("Ilovadan chiqish") .setMessage("Ilovadan chiqishni xohlaysizmi?")
                                                    .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                                                        @Override public void onClick(DialogInterface dialog, int which) {
                                                            String xabar = "xabar";
                                                            db.collection(xabar).
                                                                    document(txtxabnomi.getText().toString()).
                                                                    delete().
                                                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                cheskfirebase();
                                                                                admindataupdate();
                                                                                txtxadmin11.setVisibility(View.INVISIBLE);
                                                                                txtxabklent.setVisibility(View.INVISIBLE);
                                                                                txtptichkadmin.setVisibility(View.INVISIBLE);
                                                                                listView.setVisibility(View.VISIBLE);
                                                                            } else {
                                                                                Toast.makeText(AdminXabar.this, "xatolik", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                        } })
                                                    .setNegativeButton("Yo'q", null)
                                                    .show();
                                        }else {

                                        }
                                    } else {
                                        if (Objects.equals("0",xabaradmin)){
                                            Toast.makeText(getApplicationContext(), "foydalanuvchiga javob bering!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            },1000);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String korildi = "0";
                String javob = ediadminyoz1.getText().toString();
                Map<String, Object> user1 = new HashMap<>();
                user1.put(KORILDI, korildi);
                user1.put(XABARBIZ,javob);
                Toast.makeText(AdminXabar.this, "chiqish uchun 2 marta bosing!", Toast.LENGTH_SHORT).show();
                db.collection("xabar")
                        .document(txtxabnomi.getText().toString())
                        .update(user1);
                admindataupdate();
                ediadminyoz1.setText(null);
            }
        });

    }
    public void cheskfirebase() {
        db.collection("xabar").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayAdapter<String> list = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getId());
                                listView.setAdapter(list);
                                }

                        } else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
    private void admindataupdate(){
        documentReference = db.document("xabar/"+txtxabnomi.getText().toString());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String xabaradmin = documentSnapshot.getString(XABARBIZ);
                    String xabarklent = documentSnapshot.getString(XABARSIZ);
                    String oqil = documentSnapshot.getString(OQILDI);
                    txtxabklent.setText(xabarklent);
                    txtxadmin11.setText(xabaradmin);
                    if (Objects.equals(oqil,"1")){
                        txtptichkadmin.setText("✓✓");
                    } else {
                        txtptichkadmin.setText("✓");
                    }

                    if (Objects.equals(xabaradmin,"kuting")){
                        String korildi11 = "0";
                        Map<String, Object> user1 = new HashMap<>();
                        user1.put(KORILDI, korildi11);
                        db.collection("xabar")
                                .document(txtxabnomi.getText().toString())
                                .update(user1);
                    }else {
                        String korildi11 = "1";
                        Map<String, Object> user1 = new HashMap<>();
                        user1.put(KORILDI, korildi11);
                        db.collection("xabar")
                                .document(txtxabnomi.getText().toString())
                                .update(user1);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    @Override public void onBackPressed() {
        if (backpasset + 200 >System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else {
            txtxadmin11.setVisibility(View.INVISIBLE);
            txtxabklent.setVisibility(View.INVISIBLE);
            txtptichkadmin.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            Toast.makeText(AdminXabar.this, "chiqish uchun 2 marta bosing!", Toast.LENGTH_SHORT).show();
        }
        backpasset = System.currentTimeMillis();
    }
}
