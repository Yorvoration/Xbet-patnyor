package com.UzCodeMD.xpak;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DataLogin MyDb;
    private DataSozlama DBSOZ;
    private TextView txtkalit,txttun,txttil,txtuserN;
    FirebaseFirestore db;
    String userID="ok";
    DocumentReference documentReference;
    public final String NAME = "NAME";
    public final String PAROL = "PAROL";
    public final String TEL = "TEL";
    public final String YANGILANISH = "YANGILANISH";
    String programm_version = "1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        MyDb = new DataLogin(this);
        DBSOZ = new DataSozlama(this);
        db = FirebaseFirestore.getInstance();
        txtkalit = findViewById(R.id.txtkalit);
        txttun = findViewById(R.id.txttunkun);
        txttil = findViewById(R.id.txttil);
        txtuserN = findViewById(R.id.txtusern);
        oqish();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                if ( connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected() ) {
                    if (Objects.equals(txtkalit.getText().toString(), "kalit")) {
                        documentReference = db.document("admin/yangilanish");
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String yangilan = documentSnapshot.getString(YANGILANISH);
                                    if (Objects.equals(yangilan,programm_version)){
                                        Intent intent = new Intent(MainActivity.this,Parol.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Yangilanish").setMessage("dasturni yangilang")
                                                .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Uri uri = Uri.parse("https://t.me/joinchat/OgBhwFFFcESnDAsIJ4sLmQ");
                                                        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                                                        likeIng.setPackage("com.telegram.android");
                                                        try {
                                                            startActivity(likeIng);
                                                        } catch (ActivityNotFoundException e) {
                                                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                                    Uri.parse("https://t.me/joinchat/OgBhwFFFcESnDAsIJ4sLmQ")));
                                                        }
                                                    }
                                                }).setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        }).show();
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                    else{
                        documentReference = db.document("admin/yangilanish");
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String yangilan = documentSnapshot.getString(YANGILANISH);
                                    if (Objects.equals(yangilan,programm_version)){
                                        Intent intent = new Intent(MainActivity.this,Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Yangilanish").setMessage("dasturni yangilang")
                                                .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Uri uri = Uri.parse("https://t.me/joinchat/OgBhwFFFcESnDAsIJ4sLmQ");
                                                        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                                                        likeIng.setPackage("com.telegram.android");
                                                        try {
                                                            startActivity(likeIng);
                                                        } catch (ActivityNotFoundException e) {
                                                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                                    Uri.parse("https://t.me/joinchat/OgBhwFFFcESnDAsIJ4sLmQ")));
                                                        }
                                                    }
                                                }).setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        }).show();
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });

                    }
                }
                else
                {
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("internet").setMessage("internetga ulaning")
                            .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    @SuppressLint("WifiManagerLeak")
                                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                    wifi.setWifiEnabled(true);

                                    if (Objects.equals(txtkalit.getText().toString(), "kalit")) {
                                        Intent intent = new Intent(MainActivity.this,Parol.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Intent intent = new Intent(MainActivity.this,Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }).setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
                    Toast.makeText(MainActivity.this, "internet o`chiq", Toast.LENGTH_SHORT).show();
                }

            }
        },3000);
    }
    //bazadan ma`lumotlarni o`qish uchun funksiya
    private void oqish() {
        Cursor res = DBSOZ.oqish();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer1.append(res.getString(1));
                stringBuffer2.append(res.getString(2));
                stringBuffer3.append(res.getString(3));
            }
            txtkalit.setText(stringBuffer1.toString());
            txttun.setText(stringBuffer2.toString());
            txttil.setText(stringBuffer3.toString());
        }
    }
    public void yangi(){
        db.collection("user").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            txttil.append(" " + document.getString(TEL) + " : ");
                        }
                    }
                });
    }
}