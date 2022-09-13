package com.UzCodeMD.xpak;

import static android.util.Log.d;
import static com.airbnb.lottie.L.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminAsosiyPanel extends AppCompatActivity {
    private ListView adminlist;
    private TextView txtasosadmin;
    private Button btnadminVeb,btnxabar;

    private DataLogin MyDb;
    private DataAdminPanel MyAdminData;
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
    public final String PUL = "PUL";
    public final String KARTA = "KARTA";
    public final String VAQT = "VAQT";
    public final String KIRISHVAQT = "KIRISHVAQT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.adminpanelasosiy);

        db = FirebaseFirestore.getInstance();
        adminlist = findViewById(R.id.adminlist);
        txtasosadmin = findViewById(R.id.txtasosadmin);
        btnadminVeb = findViewById(R.id.btnadminVeb);
        btnxabar = findViewById(R.id.btnadminxabar);
        MyAdminData = new DataAdminPanel(this);
        db = FirebaseFirestore.getInstance();
        listbaza();

        btnxabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAsosiyPanel.this,AdminXabar.class);
                startActivity(intent);
            }
        });

        btnadminVeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAsosiyPanel.this,AdminWeb.class);
                startActivity(intent);
            }
        });
        adminlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();
                String selectedFromList = (String) (adminlist.getItemAtPosition(position));
                txtasosadmin.setText(selectedFromList);
                admindataupdate();
                Intent intent = new Intent(AdminAsosiyPanel.this,AdminMalumotlar.class);
                startActivity(intent);
            }
        });

    }
    private void listbaza(){
        db.collection("user").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayAdapter<String> list = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getId());
                                adminlist.setAdapter(list);
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
        documentReference = db.document("user/"+txtasosadmin.getText().toString());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String songifaollik = documentSnapshot.getString(VAQT);
                    String kirishvqati = documentSnapshot.getString(KIRISHVAQT);
                    String login = txtasosadmin.getText().toString();
                    String id = "1";
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Boolean result = MyAdminData.updateAdmin(id,login,songifaollik,kirishvqati);
                            if (result==true){
                                Toast.makeText(AdminAsosiyPanel.this, "qoillar", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(AdminAsosiyPanel.this, "eeeee", Toast.LENGTH_SHORT).show();
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
}
