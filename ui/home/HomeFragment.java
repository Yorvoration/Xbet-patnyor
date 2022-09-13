package com.UzCodeMD.xpak.ui.home;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.UiModeManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.UzCodeMD.xpak.BoshOyna;
import com.UzCodeMD.xpak.DataLogin;
import com.UzCodeMD.xpak.DataSozlama;
import com.UzCodeMD.xpak.LocaleHelper;
import com.UzCodeMD.xpak.Login;
import com.UzCodeMD.xpak.R;
import com.UzCodeMD.xpak.databinding.FragmentHomeBinding;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
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
    public final String ID = "ID";
    public final String UMUMIYXABAR = "UMUMIYXABAR";
    Context context;
    Resources resources;

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
         DataLogin Mydb = new DataLogin(getContext());
         DataSozlama MySoz = new DataSozlama(getContext());
        db = FirebaseFirestore.getInstance();
        final TextView textView = binding.textHome;
        final SwipeRefreshLayout swipereflesh = binding.swipereflesh;
        final TextView txtbp1 = binding.txtbp1;
        final TextView txtkp1 = binding.txtkp1;
        final TextView txthp1 = binding.txthp1;
        final TextView txtop1 = binding.txtop1;
        final TextView txtjp1 = binding.txtjp1;
        final TextView txtbp1txt = binding.txtbp1txt;
        final TextView txtkp1txt = binding.txtkp1txt;
        final TextView txthp1txt = binding.txthp1txt;
        final TextView txtop1txt = binding.txtop1txt;
        final TextView txtjp1txt = binding.txtjp1txt;
        final TextView txttilhome = binding.txttilhome;
        final TextView txtxabarid = binding.txtxabarid;
        final TextView txtyangilikid = binding.txtyangilikid;
        txtxabarid.setMovementMethod(new ScrollingMovementMethod());
        txtyangilikid.setMovementMethod(new ScrollingMovementMethod());
        final LottieAnimationView animqon1 = binding.animqon1;
        final LottieAnimationView animqon2 = binding.animqon2;
        final TextView txtpromokodbosh = binding.txtpromokodbosh;
        txtxabarid.setTextIsSelectable(true);
        txtyangilikid.setTextIsSelectable(true);

        Cursor res = Mydb.oqish();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer1.append(res.getString(1));
                stringBuffer2.append(res.getString(2));
                stringBuffer3.append(res.getString(3));
            }
            textView.setText(stringBuffer1.toString());
        }
        Cursor res1 = MySoz.oqish();
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        if (res1 != null && res1.getCount() > 0) {
            while (res1.moveToNext()) {
                buffer.append(res1.getString(1));
                buffer1.append(res1.getString(2));
                buffer2.append(res1.getString(3));
            }
            txttilhome.setText(buffer2.toString());
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animqon1.setVisibility(View.INVISIBLE);
                animqon2.setVisibility(View.INVISIBLE);
            }
        },5500);

        swipereflesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                documentReference = db.document("NETCASH/netcash");
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot1) {
                        if (documentSnapshot1.exists()){
                            String yangiliklar1 = documentSnapshot1.getString(YANGILIKLAR);
                            String xabarxamma1 = documentSnapshot1.getString(UMUMIYXABAR);
                            txtxabarid.setText(xabarxamma1);
                            txtyangilikid.setText(yangiliklar1);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
                documentReference = db.document("user/"+textView.getText().toString());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String nomi = documentSnapshot.getString(NAME);
                            String paroli = documentSnapshot.getString(PAROL);
                            String teli = documentSnapshot.getString(TEL);
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
                            Boolean result = Mydb.kiritish(nomi,paroli,teli,davlati,ismi,familyasi,
                                    pochta,net_kash,bugunpul,kechpul,haftapul,oypul,jamipul,
                                    yangiliklar,murojatmaydon,tarixotkazma,aloqa,promocod,userid);

                            if (result == true){
                               // Toast.makeText(getActivity(), "SQLga yozildi", Toast.LENGTH_SHORT).show();
                            }
                            else {
                               // Toast.makeText(getActivity(), "exxx", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                           // Toast.makeText(getActivity(),"xatolik shekili", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                swipereflesh.setRefreshing(false);
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //textView.setText(s);
                documentReference = db.document("user/"+textView.getText().toString());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String nomi = documentSnapshot.getString(NAME);
                            String paroli = documentSnapshot.getString(PAROL);
                            String teli = documentSnapshot.getString(TEL);
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
                            String id = documentSnapshot.getString(ID);

                            txtbp1.setText("NetCash: "+bugunpul);
                            txtkp1.setText("NetCash: "+kechpul);
                            txthp1.setText("NetCash: "+haftapul);
                            txtop1.setText("NetCash: "+oypul);
                            txtjp1.setText("NetCash: "+jamipul);
                            txtpromokodbosh.setText(promocod);
                            //textView.setText("promocod: "+promocod);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
                documentReference = db.document("NETCASH/netcash");
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String yangiliklar = documentSnapshot.getString(YANGILIKLAR);
                            String xabarxamma = documentSnapshot.getString(UMUMIYXABAR);
                            String a = txtxabarid.getText().toString();
                            String b = txtyangilikid.getText().toString();
                            txtxabarid.setText(a+": "+xabarxamma);
                            txtyangilikid.setText(b+": "+yangiliklar);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }

        });
        return root;
    }

    private Fragment getBaseContext() {
        return null;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}