package com.UzCodeMD.xpak;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private LottieAnimationView btnroyot,btnkir1;
    private EditText edilogin1,ediparol1;
    private TextView txtparol1,txtkalitlog,txttunlog,txttillog,txtparun,txtniknamelogin;
    private DataLogin MyDb;
    private DataSozlama DBSOZ;
    private DataAdminPanel MyAdminData;
    private FirebaseFirestore db;
    String userID="ok";
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
    public final String PUL = "PUL";
    public final String KARTA = "KARTA";
    public final String VAQT = "VAQT";
    public final String KIRISHVAQT = "KIRISHVAQT";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        MyDb = new DataLogin(this);
        DBSOZ = new DataSozlama(this);
        MyAdminData = new DataAdminPanel(this);
        db = FirebaseFirestore.getInstance();

        btnroyot = findViewById(R.id.btnroyot2);
        btnkir1 = findViewById(R.id.btnkir1);
        edilogin1 = findViewById(R.id.edilogin1);
        ediparol1 = findViewById(R.id.ediparol1);
        txtparol1 = findViewById(R.id.txtparol1);
        txtkalitlog = findViewById(R.id.txtkalitlog);
        txttunlog = findViewById(R.id.txttunlog);
        txttillog = findViewById(R.id.txttillog);
        txtparun = findViewById(R.id.txtparun);
        txtniknamelogin = findViewById(R.id.txtniknamelogin);
        txtniknamelogin.setText("Dilshodbek");
        oqi1();
        oqishsh();
        txtparun.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Login.this,AdminPanel.class);
                startActivity(intent);
                return false;
            }
        });
        txtparun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Murojat.class);
                startActivity(intent);
            }
        });
        ediparol1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                oqi1();
                return false;
            }
        });
        edilogin1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                edilogin1.setText("");
                edilogin1.setHint("foydalanuvchi nomi");
                return false;
            }
        });
        btnkir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ediparol1.getText().toString().isEmpty()){
                    ediparol1.setError("xato");
                    edilogin1.setError("xato");
                }
                else {
                    oqi1();
                    if (ediparol1.getText().toString().length()<4) {
                        ediparol1.setError("parol yetarli emas");
                    }else {
                        if (Objects.equals(ediparol1.getText().toString(), txtparol1.getText().toString())) {
                            yoz();
                            oqi1();
                            admindatawrite();
                            Intent intent = new Intent(Login.this,Parol.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    edilogin1.setError("xato");
                                    ediparol1.setError("xato");
                                }
                            },2000);
                        }
                    }

                }
            }
        });
        btnroyot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,HisobHosil.class);
                startActivity(intent);
            }
        });
    }
    public void oqi1(){
        if (edilogin1.getText().toString().isEmpty()){
            //Toast.makeText(Login.this, "bosh", Toast.LENGTH_SHORT).show();
        }
        else {
            documentReference = db.document("user/"+edilogin1.getText().toString());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String nomi = documentSnapshot.getString(NAME);
                        String paroli = documentSnapshot.getString(PAROL);
                        String teli = documentSnapshot.getString(TEL);
                        String nikname = documentSnapshot.getString(NIKNAME);
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
                        String kir = edilogin1.getText().toString();
                        Boolean result = MyDb.kiritish(kir,paroli,teli,davlati,ismi,familyasi,
                                pochta,net_kash,bugunpul,kechpul,haftapul,oypul,jamipul,
                                yangiliklar,murojatmaydon,tarixotkazma,aloqa,promocod,userid);
                        if (result == true){
                           // Toast.makeText(Login.this, "SQLga yozildi", Toast.LENGTH_SHORT).show();
                        }
                        txtparol1.setText(paroli);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }
    //bazadan ma`lumotlarni o`qish uchun funksiya
    private void oqishsh() {
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
            txtkalitlog.setText(stringBuffer1.toString());
            txttunlog.setText(stringBuffer2.toString());
            txtkalitlog.setText(stringBuffer3.toString());
        }
    }
    private void yoz(){
        String Kalit = "kalit";
        String tun = "0";
        String ozbek = "englsh";
        Boolean result = DBSOZ.kiritish(Kalit,tun,ozbek);

    }
    private void admindatawrite(){
        String songifaollik ="0";
        String kirishvqati = "0";
        String login = txtniknamelogin.getText().toString();
        Boolean result = MyAdminData.writeAdmin(login,songifaollik,kirishvqati);
        if (result == true){
            Toast.makeText(Login.this, "SQLga yozildi", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(Login.this, "exxx", Toast.LENGTH_SHORT).show();
        }
    }
}
