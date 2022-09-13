package com.UzCodeMD.xpak.ui.otkazma;

import static android.util.Log.d;
import static com.airbnb.lottie.L.TAG;

import static java.lang.String.valueOf;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.UzCodeMD.xpak.BoshOyna;
import com.UzCodeMD.xpak.DataLogin;
import com.UzCodeMD.xpak.DataSozlama;
import com.UzCodeMD.xpak.Login;
import com.UzCodeMD.xpak.R;
import com.UzCodeMD.xpak.SotibOl;
import com.UzCodeMD.xpak.databinding.FragmentOtkazmaBinding;
import com.UzCodeMD.xpak.databinding.FragmentSlideshowBinding;
import com.UzCodeMD.xpak.ui.slideshow.SlideshowViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class OtkazmaFragment extends Fragment {

    private FragmentOtkazmaBinding binding;
    DataLogin MyDb;
    DataSozlama MySoz;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        OtkazmaViewModel otkazmaViewModel = new ViewModelProvider(this).get(OtkazmaViewModel.class);
        binding = FragmentOtkazmaBinding.inflate(inflater, container, false);
        View rootot = binding.getRoot();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        MyDb = new DataLogin(getContext());
        MySoz = new DataSozlama(getContext());
        db = FirebaseFirestore.getInstance();
        final TextView txtidotkazma = binding.txtidotkazma;
        final TextView txtniknameotkaz = binding.txtniknameotkaz;
        final TextView txtnetkashotkazma = binding.txtnetkashotkazma;
        final TextView txtkirvaqtotkazma = binding.txtkirvaqtotkazma;
        final TextView txttelotkazma = binding.txttelotkazma;
        final TextView txttarixotkazma = binding.texttarixotkazma;
        final TextView txtogohotkazma = binding.txtogohotkazma;
        final TextView txtbazaotkazma = binding.txtbazaotkazma;
        final TextView txtnarxotkazma = binding.txtnarxotkazma;
        final Button btnvalyutaotkaz = binding.btnvalyutaotkaz;
        final Button btnchiqarotkaz = binding.btnchiqarotkaz;
        final Button btntelotkaz = binding.btntelotkaz;
        final Button btnsotibolotkaz = binding.btnsotibolotkaz;
        final EditText edimablagotkaz = binding.edimablagotkaz;
        final EditText edikartaraotkazma = binding.edikartaraotkazma;
        final EditText editelefonkazma = binding.editelefonkazma;
        final SwipeRefreshLayout swipeRefreshLayout = binding.swipereflesh3;
        txtnarxotkazma.setVisibility(View.INVISIBLE);


        edikartaraotkazma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference = db.document("NETCASH/netcash");
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String net_kash = documentSnapshot.getString("som");
                            double setkashsom = Double.parseDouble(net_kash);
                            if (!edimablagotkaz.getText().toString().isEmpty()){

                                if (Objects.equals(btnvalyutaotkaz.getText().toString(),"NetCash-> UZS")){
                                    txtnarxotkazma.setVisibility(View.VISIBLE);
                                    String a = edimablagotkaz.getText().toString();
                                    double aa = Double.parseDouble(a);
                                    aa*=setkashsom;
                                    txtnarxotkazma.setText(valueOf(aa));
                                }else{
                                    if (Objects.equals(btnvalyutaotkaz.getText().toString(),"NetCash-> UZS")){
                                    }else{
                                        if (Objects.equals(btnvalyutaotkaz.getText().toString(),"NetCash-> UZS")){

                                        }else{

                                        }
                                    }
                                }
                            }else{
                                txtnarxotkazma.setText(null);
                            }

                        }
                    }
                });
            }
        });

        btntelotkaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.inflate(R.menu.telefon);
                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.teleid:
                                        editelefonkazma.setVisibility(View.VISIBLE);
                                        edikartaraotkazma.setVisibility(View.INVISIBLE);
                                        if (Objects.equals(btnvalyutaotkaz.getText().toString(),"NetCash-> UZS")){

                                        }else{
                                            if (Objects.equals(btnvalyutaotkaz.getText().toString(),"NetCash-> UZS")){

                                            }else{
                                                if (Objects.equals(btnvalyutaotkaz.getText().toString(),"NetCash-> UZS")){

                                                }else{

                                                }
                                            }
                                        }
                                        return true;
                                    case R.id.kartaid:
                                        editelefonkazma.setVisibility(View.INVISIBLE);
                                        edikartaraotkazma.setVisibility(View.VISIBLE);
                                        return true;
                                }
                                return false;
                            }
                        });
                popupMenu.show();
            }
        });
        btnsotibolotkaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SotibOl.class);
                startActivity(intent);
            }
        });
        btnchiqarotkaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edimablagotkaz.getText().toString().isEmpty()){
                    edimablagotkaz.setError("bo`sh");
                    if (edikartaraotkazma.getText().toString().isEmpty()){
                        edimablagotkaz.setError("bo`sh");
                        edikartaraotkazma.setError("bo`sh");
                    }else {
                    }
                }
                else {
                    if (edikartaraotkazma.getText().toString().isEmpty()){
                        edikartaraotkazma.setError("bo`sh");
                    }
                    else {
                        // mana
                        String val = edikartaraotkazma.getText().toString();
                        if (val.length()<16){
                            edikartaraotkazma.setError("bunday karta mavjut emas");
                        }
                        else{
                            String a = edimablagotkaz.getText().toString();
                            String b = txtnetkashotkazma.getText().toString();
                            double son = Double.parseDouble(a);
                            double pul = Double.parseDouble(b);
                            if (son > pul){
                                edimablagotkaz.setError("mablag` yetarli emas");
                            }else {
                                if (son == pul || son<pul){
                                    double i = pul-son;
                                    String qolganpul = valueOf(i);
                                    String vaqt = txtkirvaqtotkazma.getText().toString();
                                    String name = txtniknameotkaz.getText().toString();
                                    String netcash = txtnetkashotkazma.getText().toString();
                                    String netcash1 = txttarixotkazma.getText().toString();
                                    String netcash2 = edimablagotkaz.getText().toString();
                                    String mablag = edimablagotkaz.getText().toString();
                                    String kirvaqt = txtkirvaqtotkazma.getText().toString();
                                    String tell = txttelotkazma.getText().toString();
                                    String kartahisob = edikartaraotkazma.getText().toString();

                                    Map<String, Object> user1 = new HashMap<>();
                                    user1.put(PUL,mablag);
                                    user1.put(NET_KASH,netcash1+" , "+netcash2);
                                    user1.put(KARTA,kartahisob);
                                    user1.put(VAQT,vaqt);
                                    Map<String, Object> user3 = new HashMap<>();
                                    user3.put(NAME,name);
                                    user3.put(TEL,tell);
                                    user3.put(KARTA,kartahisob);
                                    user3.put(NET_KASH,qolganpul);
                                    user3.put(KIRISHVAQT,kirvaqt);
                                    user3.put(VAQT,vaqt);

                                    db.collection("o`tkazma").document(txtniknameotkaz.getText().toString()).set(user1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
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
                                                        txtniknameotkaz.setText(stringBuffer1.toString());
                                                        txtidotkazma.setText(stringBuffer4.toString());

                                                        documentReference = db.document("id/"+txtidotkazma.getText().toString());
                                                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                if (documentSnapshot.exists()){

                                                                    String net_kash = documentSnapshot.getString(NET_KASH);
                                                                    String kirvaqt = documentSnapshot.getString(KIRISHVAQT);
                                                                    String tel = documentSnapshot.getString(TEL);
                                                                    txtnetkashotkazma.setText(net_kash);
                                                                    txtkirvaqtotkazma.setText(kirvaqt);
                                                                    txttelotkazma.setText(tel);

                                                                    Map<String, Object> updatecash = new HashMap<>();
                                                                    updatecash.put(NET_KASH, net_kash);

                                                                    db.collection("user")
                                                                            .document(txtniknameotkaz.getText().toString())
                                                                            .update(updatecash);
                                                                }
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                            }
                                                        });
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                                    db.collection("id").document(txtidotkazma.getText().toString()).set(user3)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    edikartaraotkazma.setText(null);
                                                    edimablagotkaz.setText(null);
                                                    btnvalyutaotkaz.setVisibility(View.INVISIBLE);
                                                    btnchiqarotkaz.setVisibility(View.INVISIBLE);
                                                    edikartaraotkazma.setVisibility(View.INVISIBLE);
                                                    edimablagotkaz.setVisibility(View.INVISIBLE);
                                                    txtogohotkazma.setVisibility(View.VISIBLE);
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                documentReference = db.document("NETCASH/netcash");
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String net_kash = documentSnapshot.getString("som");
                            double setkashsom = Double.parseDouble(net_kash);

                            if (!edimablagotkaz.getText().toString().isEmpty()){

                                if (Objects.equals(btnvalyutaotkaz.getText().toString(),"NetCash-> UZS")){
                                    String a = edimablagotkaz.getText().toString();
                                    double aa = Double.parseDouble(a);
                                    aa*=setkashsom;
                                    txtnarxotkazma.setText(valueOf(aa));
                                }else{
                                    if (Objects.equals(btnvalyutaotkaz.getText().toString(),"NetCash-> UZS")){
                                    }else{
                                        if (Objects.equals(btnvalyutaotkaz.getText().toString(),"NetCash-> UZS")){

                                        }else{

                                        }
                                    }
                                }
                            }else{
                                txtnarxotkazma.setText(null);
                            }

                        }
                    }
                });
                Cursor res = MyDb.oqish();
                StringBuffer stringBuffer1 = new StringBuffer();
                StringBuffer stringBuffer4 = new StringBuffer();
                if (res != null && res.getCount() > 0) {
                    while (res.moveToNext()) {
                        stringBuffer1.append(res.getString(1));
                        stringBuffer4.append(res.getString(19));
                    }
                    txtniknameotkaz.setText(stringBuffer1.toString());
                    txtidotkazma.setText(stringBuffer4.toString());
                    documentReference = db.document("id/"+txtidotkazma.getText().toString());
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                String net_kash = documentSnapshot.getString(NET_KASH);
                                String kirvaqt = documentSnapshot.getString(KIRISHVAQT);
                                String tel = documentSnapshot.getString(TEL);
                                txtnetkashotkazma.setText(net_kash);
                                txtkirvaqtotkazma.setText(kirvaqt);
                                txttelotkazma.setText(tel);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                    documentReference = db.document("o`tkazma/"+txtniknameotkaz.getText().toString());
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                String net_kash = documentSnapshot.getString(NET_KASH);
                                txttarixotkazma.setText(net_kash);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        otkazmaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

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
                        txtniknameotkaz.setText(stringBuffer1.toString());
                        txtidotkazma.setText(stringBuffer4.toString());

                        documentReference = db.document("id/"+txtidotkazma.getText().toString());
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
                                    String net_kash = documentSnapshot.getString(NET_KASH);
                                    String kirvaqt = documentSnapshot.getString(KIRISHVAQT);
                                    String tel = documentSnapshot.getString(TEL);
                                    txtnetkashotkazma.setText(net_kash);
                                    txtkirvaqtotkazma.setText(kirvaqt);
                                    txttelotkazma.setText(tel);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                        documentReference = db.document("o`tkazma/"+txtniknameotkaz.getText().toString());
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
                                    String net_kash = documentSnapshot.getString(NET_KASH);
                                    txttarixotkazma.setText(net_kash);

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
            }
        });
        return rootot;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
