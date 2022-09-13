package com.UzCodeMD.xpak;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class Asosiy extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private Button btnsendsms;
    private EditText editelsms,ediyozuvsms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asosiy);

        btnsendsms = findViewById(R.id.btnsendsms);
        editelsms = findViewById(R.id.editelsms);
        ediyozuvsms = findViewById(R.id.ediyozuvsms);
        setTitle("SMS");

        btnsendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smssend();
            }
        });

    }
    private void smssend(){
        if (Objects.equals(editelsms.getText().toString(),"")){
            editelsms.setError("bo`sh");
        }
        else {
            if (Objects.equals(ediyozuvsms.getText().toString(),"")){
                editelsms.setError("bo`sh");
            }else {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(editelsms.getText().toString(),
                        null, ediyozuvsms.getText().toString(), null, null);

                if (ContextCompat.checkSelfPermission(Asosiy.this,Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Asosiy.this,
                            Manifest.permission.SEND_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions(Asosiy.this,
                                new String[]{Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String tell = editelsms.getText().toString();
        String yozuv = ediyozuvsms.getText().toString();
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(tell, null, yozuv, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}
