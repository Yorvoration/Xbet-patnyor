package com.UzCodeMD.xpak;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hanks.passcodeview.PasscodeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Parol extends AppCompatActivity {
    DataLogin MyDb;
    private PasscodeView passcodeView;
    private TextView txtparol6,txtparolname,txtparolvaqt;
    FirebaseFirestore db;
    DataSozlama MySoz;
    DocumentReference documentReference;
    public final String VAQT = "VAQT";
    public final String SONGIVAQT = "SONGIVAQT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.parol);

        MySoz = new DataSozlama(this);
        MyDb = new DataLogin(this);
        db = FirebaseFirestore.getInstance();
        passcodeView = findViewById(R.id.passcodeview);
        txtparol6 = findViewById(R.id.txtparol6);
        txtparolname = findViewById(R.id.txtparolname);
        txtparolvaqt = findViewById(R.id.txtparolvaqt);
        oqishsh();

        passcodeView.setPasscodeLength(4)
                .setLocalPasscode(txtparol6.getText().toString())
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(Parol.this, "Password is wrong!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(String number) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        String vaqt = currentDateandTime;
                        String songivaqt = txtparolvaqt.getText().toString();

                        Map<String, Object> user1 = new HashMap<>();
                        user1.put(VAQT, vaqt);                 //1
                        db.collection("user")
                                .document(txtparolname.getText().toString())
                                .update(user1);

                        Intent intent_passcode = new Intent(Parol.this, BoshOyna.class);
                        startActivity(intent_passcode);
                        finish();
                    }
                });
    }
    //bazadan ma`lumotlarni o`qish uchun funksiya
    private void oqishsh() {
        Cursor res = MyDb.oqish();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer1.append(res.getString(1));
                stringBuffer2.append(res.getString(2));
            }
            txtparolname.setText(stringBuffer1);
            txtparol6.setText(stringBuffer2);
        }

    }
    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}
