package com.UzCodeMD.xpak;

import static android.util.Log.d;
import static com.airbnb.lottie.L.TAG;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Xabar extends AppCompatActivity {
    private EditText ediadminyoz;
    private TextView txtsiz,txtbiz,txtnamexabar,txtptichkaklent;

    DataLogin MyDb;
    DataSozlama MySoz;
    private FirebaseFirestore db;
    private DocumentReference documentReference;

    public final String NAME = "NAME";
    public final String ISM = "ISM";
    public final String TEL = "TEL";
    public final String VAQT = "VAQT";
    public final String ID = "ID";
    public final String XABARSiz = "XABARSIZ";
    public final String XABARBiz = "XABARBIZ";
    public final String KORILDI = "KORILDI";
    public final String OQILDI = "OQILDI";
    //private Layout swippe;
    private long backpasset;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xabar);

        db = FirebaseFirestore.getInstance();
        MyDb = new DataLogin(this);
        MySoz = new DataSozlama(this);

        ImageButton imgbtn = findViewById(R.id.imgbtn);
        ediadminyoz = findViewById(R.id.ediadminyoz);
        txtbiz = findViewById(R.id.txtbiz);
        txtsiz = findViewById(R.id.txtsiz);
        txtsiz.setMovementMethod(new ScrollingMovementMethod());
        txtbiz.setMovementMethod(new ScrollingMovementMethod());
        txtnamexabar = findViewById(R.id.txtnamexabar);
        txtptichkaklent = findViewById(R.id.txtptichkaklent);
        SwipeRefreshLayout swippe = findViewById(R.id.swippe);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                firestoreRead();
            }
        },600);
        firestoreRead();
        oqishsh();


        swippe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                documentReference = db.document("user/"+txtnamexabar.getText().toString());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            firestoreRead();
                            natifikation();
                        }else {
                            Toast.makeText(Xabar.this,"xatolik shekili", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                swippe.setRefreshing(false);
            }
        });


        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ediadminyoz.getText().toString().isEmpty()){
                    ediadminyoz.setError("bo`sh");
                    firestoreRead();
                }else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy"+" "+"HH:mm.", Locale.getDefault());
                    String currentDateandTime = sdf.format(new Date());
                    String songivaqt = currentDateandTime;
                    String name1 = "aa";
                    String ism1 = "bb";
                    String tel1 = "dd";
                    String vaqt = "ee";
                    String id1 = "ff";

                    String xabar = txtsiz.getText().toString()+"\n"+"\n"+
                            ediadminyoz.getText().toString()+":   "+songivaqt+"   ";
                    String xabar2 = "kuting";
                    String korildi = "0";
                    String oqildi = "0";

                    Map<String, Object> user1 = new HashMap<>();
                    user1.put(NAME, name1);                 //1
                    user1.put(ISM, ism1);                   //2
                    user1.put(TEL, tel1);                   //3
                    user1.put(VAQT, vaqt);                  //4
                    user1.put(ID, id1);                     //5
                    user1.put(XABARSiz, xabar);             //6
                    user1.put(XABARBiz, xabar2);            //7
                    user1.put(KORILDI, korildi);            //8
                    user1.put(OQILDI, oqildi);              //9

                    db.collection("xabar").document(txtnamexabar.getText().toString()).set(user1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    firestoreRead();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( Exception e) {
                            firestoreRead();
                        }
                    });
                    ediadminyoz.setText("");
                }
            }
        });

    }
    public void firestoreRead() {
            documentReference = db.document("xabar/"+txtnamexabar.getText().toString());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String siz = documentSnapshot.getString(XABARSiz);
                        String biz = documentSnapshot.getString(XABARBiz);
                        String read = documentSnapshot.getString(KORILDI);
                        if (Objects.equals(read,"1")){
                            txtptichkaklent.setText("✓✓");
                        } else {
                            txtptichkaklent.setText("✓");
                        }
                        txtsiz.setText(siz);
                        txtbiz.setText("admin: "+biz);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (Objects.equals(biz,"kuting")){
                                    String korildi = "0";
                                    Map<String, Object> user1 = new HashMap<>();
                                    user1.put(OQILDI, korildi);
                                    db.collection("xabar")
                                            .document(txtnamexabar.getText().toString())
                                            .update(user1);
                                } else{
                                    String korildi = "1";
                                    Map<String, Object> user1 = new HashMap<>();
                                    user1.put(OQILDI, korildi);
                                    db.collection("xabar")
                                            .document(txtnamexabar.getText().toString())
                                            .update(user1);
                                }
                                if (Objects.equals(txtbiz.getText().toString(),"admin: "+"kuting")){
                                    txtbiz.setText("adminimiz sizga 24 soat ichida yordam beradi");
                                }
                                else {
                                    txtbiz.setText("admin: "+biz);
                                }
                            }
                        },500);

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
    }
    private void natifikation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                    .setContentTitle("sms cod")
                    .setContentText(txtbiz.getText().toString())
                    .setSmallIcon(R.drawable.net_kash)
                    .setAutoCancel(true);
            NotificationManagerCompat managercompat = NotificationManagerCompat.from(this);
            PendingIntent contentintent = PendingIntent.getActivity(this, 0,
                    new Intent(this, Xabar.class), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentintent);
            managercompat.notify(999,builder.build());
        }
    }
    private void oqishsh() {
        Cursor res = MyDb.oqish();
        StringBuffer stringBuffer1 = new StringBuffer();

        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer1.append(res.getString(1) );
            }
            txtnamexabar.setText(stringBuffer1.toString());
        }
    }
    /*@Override public void onBackPressed() {
        if (backpasset + 200 >System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else {
            String xabar = "xabar";
                                    db.collection(xabar).
    document(txtnamexabar.getText().toString()).
    delete().
    addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                //admindataupdate();
            } else {

                Toast.makeText(Xabar.this, "xatolik", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(Xabar.this, "chiqish uchun 2 marta bosing!", Toast.LENGTH_SHORT).show();

        }
        backpasset = System.currentTimeMillis();
    }*/


}
