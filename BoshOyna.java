package com.UzCodeMD.xpak;

import static android.util.Log.d;

import static com.airbnb.lottie.L.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.UzCodeMD.xpak.databinding.BoshOynaBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class BoshOyna extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private View idfull;
    private BoshOynaBinding binding;
    DataLogin MyDb;
    DataSozlama MySoz;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private TextView txtnameuser, txtnet_kashuser, txtnomeruser, txtuseriduser, txttunbosh,
            txttilbosh, txtidcoin, txtfaollikbosh, txturluserphoto, txtnikname, darajabosh;
    private ImageView imgkunlogo, imageuser;
    String telRaqam;
    ProgressDialog progressDialog;
    String til = "uz";
    private Uri filePath;
    //private final int PICK_IMAGE_REQUEST = 22;
    private final int PICK_IMAGE_REQUEST = 71;

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
    public final String KORILDI = "KORILDI";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ){
            setTheme(R.style.tun_theme);
        }
        else {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO ){
                setTheme(R.style.Base_Theme_AppCompat_Light_DarkActionBar);
            }

        }
        String languageToLoad  = til; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        binding = BoshOynaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MyDb = new DataLogin(this);
        MySoz = new DataSozlama(this);
        db = FirebaseFirestore.getInstance();
        setSupportActionBar(binding.appBarBoshOyna.toolbar);

        binding.appBarBoshOyna.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                if ( connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected() ) {
                    Snackbar.make(view, "internet mavjud", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                }
                else {
                    Snackbar.make(view, "bog'lanishni tekshiring", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ConnectivityManager connectivityManager = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        if ( connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected() ) {
                            Intent intent = new Intent(BoshOyna.this,Xabar.class);
                            startActivity(intent);
                        }
                        else{
                            new AlertDialog.Builder(BoshOyna.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("internet").setMessage("internetga ulaning")
                                    .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            @SuppressLint("WifiManagerLeak")
                                            WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                            wifi.setWifiEnabled(true);
                                        }
                                    }).setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).show();
                        }
                    }
                },1000);
            }
        });
        binding.appBarBoshOyna.fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(BoshOyna.this,AdminAsosiyPanel.class);
                startActivity(intent);
                return false;
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bosh_oyna);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bosh_oyna, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageuser.setImageBitmap(bitmap);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                if(filePath != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(BoshOyna.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    //txturluserphoto.setText(UUID.randomUUID().toString());
                    StorageReference ref = storageReference.child(txtnikname.getText().toString()+"/" + txtnikname.getText().toString());
                    ref.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    d("TAG", taskSnapshot.toString());
                                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                                    downloadUrl.addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            Log.v(TAG, "Media is uploaded");
                                            String downloadURL = "https://" + task.getResult().getEncodedAuthority()
                                                    + task.getResult().getEncodedPath()
                                                    + "?alt=media&token="
                                                    + task.getResult().getQueryParameters("token").get(0);
                                            Log.v(TAG, "downloadURL: " + downloadURL);
                                            //save your downloadURL
                                            String photo = downloadURL.toString();
                                            Map<String, Object> user1 = new HashMap<>();
                                            user1.put(PHOTO, photo);                 //1

                                            db.collection("photourl").document(txtnikname.getText().toString()).set(user1)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                        }
                                    });

                                    progressDialog.dismiss();
                                    Toast.makeText(BoshOyna.this, "Uploaded", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    d("TAG", e.toString());
                                    progressDialog.dismiss();
                                    Toast.makeText(BoshOyna.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                    double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");

                                }
                            });
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("WrongViewCast")
    @Override
    public boolean onSupportNavigateUp() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        d("TAG", storageReference.toString());

        txtnameuser =findViewById(R.id.txtnomuser);
        txtnet_kashuser =findViewById(R.id.txtnet_kashuser);
        imgkunlogo = findViewById(R.id.imgkunlogo);
        txtnomeruser = findViewById(R.id.txtnomeruser);
        txtuseriduser = findViewById(R.id.useriduser);
        txttunbosh = findViewById(R.id.txttunbosh);
        txttilbosh = findViewById(R.id.txttilbosh);
        txtidcoin = findViewById(R.id.txtidcoin);
        txtfaollikbosh = findViewById(R.id.txtfaollikbosh);
        imageuser = findViewById(R.id.imguser);
        idfull = findViewById(R.id.idfull);
        txturluserphoto = findViewById(R.id.txturluserphoto);
        txtnikname = findViewById(R.id.txtnikname);
        darajabosh = findViewById(R.id.darajabosh);
        LottieAnimationView animnatification = findViewById(R.id.animnatification);
        oqi1();
        bilish();
        readphoto();

        documentReference = db.document("xabar/"+txtnikname.getText().toString());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String kor = documentSnapshot.getString(KORILDI);
                    if (Objects.equals(kor,"1")){
                        animnatification.setVisibility(View.VISIBLE);
                    }
                    else {
                        animnatification.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        animnatification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoshOyna.this,Xabar.class);
                startActivity(intent);
            }
        });
        if (Objects.equals(txturluserphoto.getText().toString(),"TextView")){

        }else {
            String url=txturluserphoto.getText().toString();
            Glide.with(getApplicationContext()).load(url).into(imageuser);
        }


        imageuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BoshOyna.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("rasm tanlash") .setMessage("hisob rasmini o`zgartirmoqchimisiz")
                        .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                chooseImage();
                            } })
                        .setNegativeButton("Yo'q", null)
                        .show();
            }
        });

        if (Objects.equals(txttunbosh.getText().toString(),"1")){
            imgkunlogo.setImageResource(R.drawable.tunlogo);
        }
        else {
            if (Objects.equals(txttunbosh.getText().toString(),"0")){
                imgkunlogo.setImageResource(R.drawable.kunlogo);
            }
        }

        imgkunlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Objects.equals(txttunbosh.getText().toString(),"1")){
                    imgkunlogo.setImageResource(R.drawable.tunlogo);
                    txttunbosh.setText("0");
                    String id = "1";
                    String kalit = "kalit";
                    String til = txttilbosh.getText().toString();
                    String tun = "0";
                    Boolean result = MySoz.ozgartir(id,kalit,tun,til);
                    if (result==true){
                    }
                    if (Objects.equals(txttunbosh.getText().toString(),"1")){
                        imgkunlogo.setImageResource(R.drawable.tunlogo);
                    }
                    else {
                        if (Objects.equals(txttunbosh.getText().toString(),"0")){
                            imgkunlogo.setImageResource(R.drawable.kunlogo);
                        }
                    }
                }
                else {
                    if (Objects.equals(txttunbosh.getText().toString(),"0")){
                        imgkunlogo.setImageResource(R.drawable.kunlogo);
                        txttunbosh.setText("1");
                        String id = "1";
                        String kalit = "kalit";
                        String til = txttilbosh.getText().toString();
                        String tun = "1";
                        Boolean result = MySoz.ozgartir(id,kalit,tun,til);
                        if (result==true){
                        }
                        if (Objects.equals(txttunbosh.getText().toString(),"1")){
                            imgkunlogo.setImageResource(R.drawable.tunlogo);
                        }
                        else {
                            if (Objects.equals(txttunbosh.getText().toString(),"0")){
                                imgkunlogo.setImageResource(R.drawable.kunlogo);
                            }
                        }
                    }
                }
            }
        });
        oqishsh();
        oqi1();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bosh_oyna);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    //bazadan ma`lumotlarni o`qish uchun funksiya
    private void oqishsh() {
        Cursor res = MyDb.oqish();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        StringBuffer stringBuffer4 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer1.append(res.getString(1));
                stringBuffer2.append(res.getString(8));
                stringBuffer3.append(res.getString(3));
                stringBuffer4.append(res.getString(19));
            }
            telRaqam = stringBuffer3.toString();
            txtnikname.setText(stringBuffer1.toString());
            txtnomeruser.setText(stringBuffer3.toString());
            txtuseriduser.setText("id "+stringBuffer4.toString());
            txtidcoin.setText(stringBuffer4.toString());
        }
    }
    private void bilish() {
        Cursor res = MySoz.oqish();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append(res.getString(1));
                stringBuffer1.append(res.getString(2));
                stringBuffer2.append(res.getString(3));
            }
           txttilbosh.setText(stringBuffer2.toString());
           txttunbosh.setText(stringBuffer1.toString());
        }
    }
    public void oqi1(){
            documentReference = db.document("id/"+txtidcoin.getText().toString());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String net_kash = documentSnapshot.getString(NET_KASH);
                        txtnet_kashuser.setText("NetCash: "+net_kash);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
            documentReference = db.document("user/"+txtnikname.getText().toString());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String ismm = documentSnapshot.getString(ISM);
                        String daraja = documentSnapshot.getString(DARAJA);
                        txtnameuser.setText(ismm);
                        darajabosh.setText(daraja);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
    }
    public void readphoto(){
        documentReference = db.document("photourl/"+txtnikname.getText().toString());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String urell = documentSnapshot.getString(PHOTO);
                    txturluserphoto.setText(urell);
                    if (Objects.equals(txturluserphoto.getText().toString(),"TextView")){

                    }else {
                        String url=txturluserphoto.getText().toString();
                        Glide.with(getApplicationContext()).load(url).into(imageuser);
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
        oqishsh();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Ilovadan chiqish") .setMessage("Ilovadan chiqishni xohlaysizmi?")
                .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        String songivaqt = currentDateandTime;

                        Map<String, Object> user1 = new HashMap<>();
                        user1.put(SONGIVAQT, songivaqt);                 //1
                        db.collection("user")
                                .document(txtnikname.getText().toString())
                                .update(user1);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },700);
                    } })
                .setNegativeButton("Yo'q", null)
                .show();
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MySoz = new DataSozlama(this);
        Cursor res1 = MySoz.oqish();
        StringBuffer buffer2 = new StringBuffer();
        if (res1 != null && res1.getCount() > 0) {
            while (res1.moveToNext()) {
                buffer2.append(res1.getString(3));
            }
            til = buffer2.toString();
        }
    }
    Context mBase;

}