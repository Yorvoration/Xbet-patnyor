package com.UzCodeMD.xpak;

import static android.util.Log.d;
import static com.airbnb.lottie.L.TAG;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.airbnb.lottie.LottieAnimationView;
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
import java.util.Random;

public class HisobHosil extends AppCompatActivity {

    DataLogin MyDb;
    private LottieAnimationView btnroyor2;
    private EditText edilogin2,edismscod2,editel2,ediparol;
    private TextView txtread,txtreadparol,txtreadtel,txtrandom2,
            txtsmskodter, txtbtnhisobyarat, txtrandom1,txtidchesk;

    FirebaseFirestore db;
    String userID;
    DocumentReference documentReference,documentReference1;
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
    public final String KIRISHVAQT = "KIRISHVAQT";
    public final String VAQT = "VAQT";
    public final String YOSH = "YOSH";
    public final String DARAJA = "DARAJA";

    public final String TUN = "TUN";
    public final String TIL = "TIL";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hisobhosil);

        db = FirebaseFirestore.getInstance();
        MyDb = new DataLogin(this);
        btnroyor2 = findViewById(R.id.btnroyot2);
        edilogin2 = findViewById(R.id.edilogin2);
        editel2 = findViewById(R.id.editel2);
        edismscod2 = findViewById(R.id.edismscod2);
        ediparol = findViewById(R.id.ediparol2);
        txtread = findViewById(R.id.txtread1);
        txtrandom2 = findViewById(R.id.txtrandom2);
        txtreadparol = findViewById(R.id.txtread2parol);
        txtreadtel = findViewById(R.id.txtread2tel);
        txtsmskodter = findViewById(R.id.txtsmskodter);
        txtrandom1 = findViewById(R.id.txtrandom1);
        txtbtnhisobyarat = findViewById(R.id.txtbtnhisobyarat);
        txtidchesk = findViewById(R.id.txtidchesk);
        generatsiya();
        edismscod2.setEnabled(false);
        edilogin2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                txtread.setText("");
                txtreadparol.setText("");
                txtreadtel.setText("");
                return false;
            }
        });
        editel2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                oqi();
                return false;
            }
        });
        btnroyor2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent =new Intent(HisobHosil.this,Asosiy.class);
                startActivity(intent);
                return false;
            }
        });
        idgeneratsiya();
        cheskfirebaseId();
        tekshir();
        btnroyor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtread.setText("");
                txtreadparol.setText("");
                txtreadtel.setText("");
                oqi();

                if (edilogin2.getText().toString().isEmpty()){
                    edilogin2.setError("bo`sh");
                }
                else {
                    if (ediparol.getText().toString().isEmpty()){
                        ediparol.setError("bo`sh");
                    }
                    else {
                        if (editel2.getText().toString().isEmpty()){
                            editel2.setError("bo`sh");
                        }
                        else {

                            if (Objects.equals(txtread.getText().toString(),"")){
                                if (Objects.equals(txtreadparol.getText().toString(),"")) {
                                    if (Objects.equals(txtreadtel.getText().toString(), "")) {
                                        oqi();

                                        String val = ediparol.getText().toString();
                                        if (val.length()<4){
                                            ediparol.setError("parol yetarli emas");
                                        }
                                        else {

                                            Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                oqi();
                                                if (Objects.equals(txtread.getText().toString(),"")){
                                            edilogin2.setEnabled(false);
                                            ediparol.setEnabled(false);
                                            editel2.setEnabled(false);
                                            edismscod2.setEnabled(true);
                                            edilogin2.setVisibility(View.INVISIBLE);
                                            ediparol.setVisibility(View.INVISIBLE);
                                            editel2.setVisibility(View.INVISIBLE);
                                            edismscod2.setVisibility(View.VISIBLE);
                                            txtsmskodter.setVisibility(View.VISIBLE);
                                            txtbtnhisobyarat.setText("        kirish");
                                            Handler handler1 = new Handler();
                                            handler1.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    natifikation();
                                                }
                                            },7389);

                                        }else {
                                            editel2.setError("bu raqam mavjut");
                                            edilogin2.setText(null);
                                            ediparol.setText(null);
                                            txtread.setText("");
                                            txtreadparol.setText("");
                                            txtread.setText("");
                                            oqi();
                                        }
                                            }
                                        },900);
                                    }
                                        }
                                    }
                                }else {
                                edilogin2.setError("bunday foydalanuvchi mavjud");
                            }
                            }
                        if (Objects.equals(txtidchesk.getText().toString(),"xato")){
                            Toast.makeText(HisobHosil.this, "qayta uruning", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            if (Objects.equals(txtidchesk.getText().toString(),"yoq")){

                                if (Objects.equals(edismscod2.getText().toString(),txtrandom2.getText().toString())){

                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.getDefault());
                                    String currentDateandTime = sdf.format(new Date());
                                    String name5 = edilogin2.getText().toString();
                                    String parol5 = ediparol.getText().toString();
                                    String tel5 = editel2.getText().toString();
                                    String davlat = "0";
                                    String ism = "-";
                                    String familya = "-";
                                    String pochta = "-";
                                    String net_kash = "10";
                                    String bugunpul = "-";
                                    String kechpul = "-";
                                    String haftapul = "-";
                                    String oypul = "-";
                                    String jamipul = "-";
                                    String yangiliklar = "-";
                                    String murojatmaydon = "-";
                                    String tarixotkazma = "-";
                                    String aloqa = "-";
                                    String promocod = "-";
                                    String yosh = "-";
                                    String daraja = "1";
                                    String userid = txtrandom1.getText().toString();
                                    String kirishvaqt = currentDateandTime;
                                    String vaqt = currentDateandTime;

                                    Map<String, Object> user1 = new HashMap<>();
                                    user1.put(KIRISHVAQT, kirishvaqt);                 //1
                                    user1.put(NAME, name5);                 //1
                                    user1.put(PAROL, parol5);               //2
                                    user1.put(TEL, tel5);                   //3
                                    user1.put(DAVLAT, davlat);              //4
                                    user1.put(ISM, ism);                    //5
                                    user1.put(FAMILYA, familya);            //6
                                    user1.put(POCHTA, pochta);              //7
                                    user1.put(NET_KASH,net_kash);           //8
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
                                    user1.put(DARAJA, daraja);              //21
                                    user1.put(KIRISHVAQT,kirishvaqt);
                                    user1.put(VAQT,vaqt);
                                    Map<String, Object> user3 = new HashMap<>();
                                    user3.put(NAME,name5);
                                    user3.put(TEL,tel5);
                                    user3.put(NET_KASH,net_kash);
                                    user3.put(KIRISHVAQT,kirishvaqt);

                                    userID = name5;
                                    Boolean result = MyDb.kiritish(name5,parol5,tel5,davlat,ism,familya,
                                            pochta,net_kash,bugunpul,kechpul,haftapul,oypul,jamipul,
                                            yangiliklar,murojatmaydon,tarixotkazma,aloqa,promocod,userid);

                                    if (result == true){
                                        //Toast.makeText(HisobHosil.this, "SQLga yozildi", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        // Toast.makeText(HisobHosil.this, "exxx", Toast.LENGTH_SHORT).show();
                                    }
                                    db.collection("id").document(txtrandom1.getText().toString()).set(user3)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                    db.collection("telefonlar").document(tel5).set(user3)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            });
                                    db.collection("user").document(name5).set(user1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                                    Intent intent = new Intent(HisobHosil.this, ShaxsMal.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    public void oqi(){
        if (Objects.equals(editel2.getText().toString(),"")){
        }else {
            documentReference1 = db.document("telefonlar/"+editel2.getText().toString());
            documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String nomi2 = documentSnapshot.getString(NAME);
                        String tel2 = documentSnapshot.getString(TEL);
                        txtread.setText(nomi2);
                        txtreadtel.setText(tel2);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        if (Objects.equals(edilogin2.getText().toString(),"")){

        }else {
            documentReference = db.document("user/"+edilogin2.getText().toString());

            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String nomi2 = documentSnapshot.getString(NAME);
                        edilogin2.setTextColor(R.color.yashil);
                        String parol2 = documentSnapshot.getString(PAROL);
                        String tel2 = documentSnapshot.getString(TEL);
                        txtread.setText(nomi2);
                        txtreadparol.setText(parol2);
                        txtreadtel.setText(tel2);
                    }
                    else {
                        edilogin2.setTextColor(R.color.qizil);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }

    }
    public void generatsiya(){
        int min = 100000;
        int max = 999999;
        int diff = max - min;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        i += min;
        String son = String.valueOf(i);
        txtrandom2.setText(son);
    }
    public void idgeneratsiya(){
        int min = 10000;
        int max = 99999;
        int diff = max - min;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        i += min;
        String son = String.valueOf(i);
        txtrandom1.setText(son);
    }
    @Override public void onBackPressed() {
        generatsiya();
        finish();
    }
    public void cheskfirebaseId() {
        db.collection("id").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list1 = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list1.add(document.getId());
                                boolean contains = list1.contains(txtrandom1.getText().toString());
                                if (contains == true) {
                                    txtidchesk.setText("xato");
                                } else {
                                    txtidchesk.setText("yoq");
                                    if (contains == false) {
                                        txtidchesk.setText("yoq");
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
    private void tekshir() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Objects.equals(txtidchesk.getText().toString(), "yoq")) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtidchesk.setText("yoq");
                        }
                    },700);
                } else {
                    if (Objects.equals(txtidchesk.getText().toString(), "xato")) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                idgeneratsiya();
                                cheskfirebaseId();
                                tekshir();
                            }
                        },700);
                    }
                }
            }
        },700);

    }
    private void natifikation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                    .setContentTitle("sms cod")
                    .setContentText(txtrandom2.getText().toString())
                    .setSmallIcon(R.drawable.net_kash)
                    .setAutoCancel(true);
            NotificationManagerCompat managercompat = NotificationManagerCompat.from(this);

            PendingIntent contentintent = PendingIntent.getActivity(this, 0,
                    new Intent(this, HisobHosil.class), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentintent);

            managercompat.notify(999,builder.build());
        }
    }
}
