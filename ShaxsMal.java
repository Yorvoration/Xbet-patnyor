package com.UzCodeMD.xpak;

import static android.util.Log.d;
import static com.airbnb.lottie.L.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class ShaxsMal extends AppCompatActivity {

    DataLogin MyDb;
    private EditText ediism3, edifamilya3, edipochta3;
    private LottieAnimationView btnsaqlash3;
    private TextView txtname3, txtparol3, txttel3, useridshmal3, txtgetalldata,
            txtrandombor, txtrandomshax, txtcodepromo;
    private AutoCompleteTextView ediautocom3;
    FirebaseFirestore db;
    String userID;
    DocumentReference documentReference;
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
    public final String DARAJA = "DARAJA";
    public final String KIRISHVAQT = "KIRISHVAQT";
    String[] promocodlarhammasi = {"1x_206192","1x_206538","1x_206677","1x_207231","1x_511153",
            "1x_511154","1x_511155","1x_511156","1x_511157","1x_511158",
            "1x_511158","1x_511160","1x_511161","1x_511162","1x_511163",
            "1x_511164","1x_511165","1x_511166","1x_511167","1x_179867",
            "1x_179869","1x_179870","1x_179880","1x_179881","1x_179882",
            "1x_179885","1x_179886","1x_179887","1x_179891","1x_179892",
            "1x_179894","1x_179895","1x_179896","1x_179913","1x_179924",
            "1x_179925","1x_179926","1x_511152","1x_515936","1x_515937",
            "1x_515940","1x_515946","1x_515947","1x_515951","1x_515952",
            "1x_515957","1x_515960","1x_515962","1x_515966","1x_515969",
            "1x_515970","1x_515971","1x_515973","1x_515975","1x_515977",
            "1x_515978","1x_515979","1x_515980","1x_512427","1x_512428",
            "1x_512429","1x_512430","1x_512431","1x_512432","1x_512857",
            "1x_512857","1x_512858","1x_512859","1x_512861","1x_512868",
            "1x_512869","1x_512871","1x_512874","1x_512875","1x_512878",
            "1x_512879","1x_512882","1x_512884","1x_512886"};
    String[] davlat = {"Niderlandlar", " Andorra", "Serbiya", "Germaniya", "Shveytsariya", " Slovakiya",
            "Belgiya", "Mojariston", "Ruminiya", "Irlandiya", "Ukraina", "Moldova", "Daniya", "Portugaliya",
            "Biritanya", "Anglya", "Sloveniya", "Lyuksemburg", "Ispaniya", "Belarus", "Monako", "Rossiya",
            "Norvegiya", "Fransiya", "Chexiya", "Islandiya", "Latviya", "Latviya", "San Marino", " Bosniya va Gertsogovina",
            "Makedoniya", "Bolgariya", "Shvetsiya", "Estoniya", "Albaniya", "Lixtenshteyn", "Malta", "Polsha",
            "Vatikan", "Avstriya", "Litva", "Finlandiya", "Xorvatiya", "Malta ordeni", "Chernogoriya", "Kosovo[2]",
            "Transnistria[3", "Birlashgan Arab Amirliklari", "Iordaniya", "Turkiya", "Turkmaniston", "Iroq",
            "Ozarbayjon", "Tailand", "Bruney", "Livan", "Qirgʻiziston", "Laos", "Bangladesh", "Suriya",
            "Hindiston", "Indoneziya", "Sharqiy Timor", "Qatar", "Tojikiston", "Armaniston", "Isroil",
            "Pokiston", "Afgʻoniston", "Nepal", "Malayziya", "Maldivalar", "Bahrayn", "Filippin", "Oman",
            "Myanma", "Qibris", "Qozogʻiston", "Xitoy", "Kambodja", "Shimoliy Koreya", "Yaman", "Janubiy Koreya",
            "Janubiy Koreya", "Singapur", "Oʻzbekiston", "Gruziya", "Eron", "Yaponiya", "Butan", "Moʻgʻuliston",
            "Vyetnam", "Shri Lanka", "Quvayt", "Saudiya Arabistoni", "Shimoliy Qibris", "Falastin", "Abxaziya",
            "Tayvan", "Janubiy Osetiya", "Nigeriya", "Efiopiya", "Gana", "Jazoir", "Madagaskar", "Eritreya",
            "Mali", " Markaziy Afrika Respublikasi", "Gambiya", "Gvineya-Bisau", "Kongo Respublikasi",
            "Burundi", "Seyshell orollari", "Namibiya", "Botsvana", "Senegal", "Jibuti", "Janubiy Sudan",
            "Tanzaniya", "Misr", "Uganda", "Ruanda", "Kongo DR", "Gvineya", "Gabon", "Malavi", "Togo", "Angola",
            "Zambiya", "Ekvatorial Gvineya", "Ekvatorial Gvineya", "Mozambik", "Lesoto", "Eswatini", "Somaliya",
            "Liberiya", "Komoros", "Keniya", "Chad", "Niger", "Mavritaniya", "Mavrikiy", "Benin", "Kabo Verde",
            "Janubiy Afrika", "Marokash", "San Tome va Prinsipi", "Liviya", "Tunis", "Burkina Faso", "Syerra Leone",
            "Zimbabve", "Sudan", "Fil Suyak Sohili", "Kamerun", "Puntlend", "Azavad", "Somalilend", "Sahroi Kabir Arab Demokratik Respublikasi",
            "Paragvay", "Sent Kitts va Nevis", "Beliz", "Kolumbiya", "Braziliya", "Barbados", "Argentina",
            "AQSh", "Kuba", "Gvatemala", "Gayana", "Venesuela", "Sent Lusiya", "Sent Vinsent va Grenadinlar",
            "Yamayka", "Ekvador", "Peru", "Nikaragua", "Meksika", "Urugvay", "Bagamalar", "Kanada", "Panama",
            "Surinam", "Gaiti", "Trinidad va Tobago", "Dominika", "El Salvador", "Kosta Rika", "Dominika Respublikasi",
            "Chili", "Antigua va Barbuda", "Grenada", "Boliviya", "Samoa", "Yangi Zelandiya", "Avstraliya", "Marshall Orollari",
            "Palau", "Tonga", "Tonga", "Vanuatu", "Papua Yangi Gvineya", "Fiji", "Tuvalu", "Solomon Orollari", "Kiribati",
            "Nauru", "Uzbekiston", "ozbekiston"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shaxsmal);



        MyDb = new DataLogin(this);
        db = FirebaseFirestore.getInstance();

        btnsaqlash3 = findViewById(R.id.btnsaqlash3);
        ediism3 = findViewById(R.id.ediism3);
        edifamilya3 = findViewById(R.id.edifamilya3);
        edipochta3 = findViewById(R.id.edipochta3);
        txtname3 = findViewById(R.id.txtname3);
        txtparol3 = findViewById(R.id.txtparol3);
        txttel3 = findViewById(R.id.txttel3);
        useridshmal3 = findViewById(R.id.useridshmal);
        ediautocom3 = findViewById(R.id.ediautokomp3);
        txtgetalldata = findViewById(R.id.txtgetalldata);
        txtrandombor = findViewById(R.id.txtrandombor);
        txtrandomshax = findViewById(R.id.txtrandomshax);
        txtcodepromo = findViewById(R.id.txtcodepromo);

        oqishsh();
        randompromocode();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, davlat);
        ediautocom3.setAdapter(adapter);

       /* DocumentReference document = db.collection("promocod").document(txtrandomshax.getText().toString());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String promokod = documentSnapshot.getString(PROMOCOD);
                   // txtrandomshax.setText(promokod);
//                    d("MyTAG", documentSnapshot.toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/

        cheskfirebase();
        tekshir();
        if (Objects.equals(txtrandombor.getText().toString(), "xato")) {
            txtrandomshax.setText("adminga murojat qiling");
            randompromocode();
            Toast.makeText(getApplicationContext(), txtrandombor.getText().toString() + ": mavjut", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ShaxsMal.this, txtrandombor.getText().toString(), Toast.LENGTH_SHORT).show();
            btnsaqlash3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ediism3.getText().toString().isEmpty()) {
                        ediism3.setError("bo`sh");
                    } else {
                        if (edifamilya3.getText().toString().isEmpty()) {
                            edifamilya3.setError("bo`sh");
                        } else {
                            if (edipochta3.getText().toString().isEmpty()) {
                                edipochta3.setError("bo`sh");
                            } else {
                                if (ediautocom3.getText().toString().isEmpty()) {
                                    ediautocom3.setError("xato");
                                } else {

                                    String name5 = txtname3.getText().toString();
                                    String parol5 = txtparol3.getText().toString();
                                    String tel5 = txttel3.getText().toString();
                                    String davlatb = ediautocom3.getText().toString();
                                    String ism = ediism3.getText().toString();
                                    String familya = edifamilya3.getText().toString();
                                    String pochta = edipochta3.getText().toString();
                                    String nikname = txtname3.getText().toString();
                                    String net_kash = "0";
                                    String bugunpul = "0";
                                    String kechpul = "0";
                                    String haftapul = "0";
                                    String oypul = "0";
                                    String jamipul = "0";
                                    String daraja = "0";
                                    String yosh = "0";
                                    String promocod = txtcodepromo.getText().toString();
                                    String yangiliklar = "bo`sh";
                                    String murojatmaydon = "bo`sh";
                                    String tarixotkazma = "bo`sh";
                                    String aloqa = "bo`sh";

                                    String userid = useridshmal3.getText().toString();
                                    Map<String, Object> user1 = new HashMap<>();
                                    user1.put(NAME, name5);
                                    user1.put(PAROL, parol5);
                                    user1.put(NIKNAME, nikname);
                                    user1.put(TEL, tel5);
                                    user1.put(DAVLAT, davlatb);
                                    user1.put(ISM, ism);
                                    user1.put(DARAJA, daraja);
                                    user1.put(YOSH,yosh);
                                    user1.put(FAMILYA, familya);
                                    user1.put(POCHTA, pochta);
                                    user1.put(NET_KASH, net_kash);
                                    user1.put(BUGUNPUL, bugunpul);
                                    user1.put(KECHAPUL, kechpul);
                                    user1.put(HAFTAPUL, haftapul);
                                    user1.put(OYPUL, oypul);
                                    user1.put(JAMIPUL, jamipul);
                                    user1.put(PROMOCOD, promocod);
                                    user1.put(USERID, userid);
                                    user1.put(YANGILIKLAR, yangiliklar);
                                    user1.put(MUROJATMAYDON, murojatmaydon);
                                    user1.put(TARIXOTKAZMA, tarixotkazma);
                                    user1.put(ALOQA, aloqa);
                                    Map<String, Object> promo = new HashMap<>();
                                    promo.put(NAME, name5);
                                    promo.put(PROMOCOD, promocod);
                                    userID = txtname3.getText().toString();

                                    if (Objects.equals(txtrandombor.getText().toString(), "xato")) {
                                        Toast.makeText(ShaxsMal.this, "xato", Toast.LENGTH_SHORT).show();

                                        cheskfirebase();
                                    } else {
                                        db.collection("promocod").document(txtrandomshax.getText().toString()).set(promo)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });

                                        db.collection("user").document(userID).set(user1)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Intent intent = new Intent(ShaxsMal.this, BoshOyna.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
       /* db.collection("promocod").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String forpromocode = document.getId();
                                txtrandombor.setText(forpromocode);
                                Toast.makeText(ShaxsMal.this, forpromocode, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/
    }
    //bazadan ma`lumotlarni o`qish uchun funksiya
    private void oqishsh() {
        Cursor res = MyDb.oqish();
        StringBuffer stringBuffer4 = new StringBuffer();
        StringBuffer stringBuffer5 = new StringBuffer();
        StringBuffer stringBuffer7 = new StringBuffer();
        StringBuffer stringBuffer8 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer4.append(res.getString(1));
                stringBuffer5.append(res.getString(2));
                stringBuffer7.append(res.getString(3));
                stringBuffer8.append(res.getString(19));
            }
            txtname3.setText(stringBuffer4.toString());
            txtparol3.setText(stringBuffer5.toString());
            txttel3.setText(stringBuffer7.toString());
            useridshmal3.setText(stringBuffer8.toString());
            Toast.makeText(this, useridshmal3.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void randompromocode() {
        String[] array = promocodlarhammasi;
        String randomStr = array[new Random().nextInt(array.length)];
        String promocod = randomStr;
        txtrandomshax.setText(promocod);
        txtcodepromo.setText(txtrandomshax.getText().toString());

    }

    public void cheskfirebase() {
        db.collection("promocod").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getId());
                                boolean contains = list.contains(txtrandomshax.getText().toString());
                                if (contains == true) {
                                    txtrandombor.setText("xato");
                                } else {
                                    txtrandombor.setText("yoq");
                                    if (contains == false) {
                                        txtrandombor.setText("yoq");
                                    }
                                }
                            }
                            d(TAG, list.toString());
                            txtgetalldata.setText(list.toString());
                        } else {
                            d(TAG, "Error getting documents: ", task.getException());
                            txtgetalldata.setText(TAG);
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
                if (Objects.equals(txtrandombor.getText().toString(), "yoq")) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtrandombor.setText("yoq");
                        }
                    },700);
                } else {
                    if (Objects.equals(txtrandombor.getText().toString(), "xato")) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cheskfirebase();
                                randompromocode();
                                tekshir();
                            }
                        },700);
                    }
                }
            }
        },700);

    }
}

