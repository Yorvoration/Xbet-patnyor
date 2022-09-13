package com.UzCodeMD.xpak.ui.slideshow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.UzCodeMD.xpak.BoshOyna;
import com.UzCodeMD.xpak.DataLogin;
import com.UzCodeMD.xpak.DataSozlama;
import com.UzCodeMD.xpak.LocaleHelper;
import com.UzCodeMD.xpak.Login;
import com.UzCodeMD.xpak.MainActivity;
import com.UzCodeMD.xpak.PasswordUpdate;
import com.UzCodeMD.xpak.R;
import com.UzCodeMD.xpak.databinding.FragmentSlideshowBinding;
import com.UzCodeMD.xpak.ui.boglan.BoglanViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.internal.ContextUtils;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.Objects;

public class SlideshowFragment extends Fragment {
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    DataLogin Mydb;
    DataSozlama MySoz;
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
    public final String PHOTO = "PHOTO";
    private TextView txttunsoz;
    Resources resources;
    private FragmentSlideshowBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel;
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflater,
                container,
                false);
        View root = binding.getRoot();
        DataLogin Mydb = new DataLogin(getContext());
        DataSozlama MySoz = new DataSozlama(getContext());
        db = FirebaseFirestore.getInstance();
        final TextView textView = binding.textSlideshow;
        final SwipeRefreshLayout swiperefleshsoz = binding.swiperefleshsoz;
        final Button btntilsoz = binding.btntilsoz;
        final Button btnchiqsoz = binding.btnchiqsoz;
        final TextView txttilsozlama = binding.txttilsozlama;
        final TextView txttunsoz = binding.txttunsoz;
        final TextView txttunsw = binding.txttunsw;
        final TextView txtkunsw = binding.txtkunsw;
        final TextView txtsvsoz = binding.txtsvsoz;
        final TextView txtphotourlsliede = binding.txtphotourlsliede;
        final ImageView imgeuserslide = binding.imgeuserslide;
        final Switch aswitch = binding.svitchsoz;
        final ImageView imgbayroqsoz = binding.imgbayroqsoz;
        final ImageView imgkuntun = binding.imgkuntun;
        final Button btnparolsoz = binding.btnparolsoz;

        if (Objects.equals(txtphotourlsliede.getText().toString(),"TextView")){
        }else {
            String url=txtphotourlsliede.getText().toString();
            Glide.with(getActivity()).load(url).into(imgeuserslide);
        }

        Cursor res1 = MySoz.oqish();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        if (res1 != null && res1.getCount() > 0) {
            while (res1.moveToNext()) {
                stringBuffer.append(res1.getString(1));
                stringBuffer1.append(res1.getString(2));
                stringBuffer2.append(res1.getString(3));
            }
            txttilsozlama.setText(stringBuffer2.toString());
            txtsvsoz.setText(stringBuffer1.toString());
        }
        Cursor res = Mydb.oqish();
        StringBuffer Buffer = new StringBuffer();
        StringBuffer Buffer1 = new StringBuffer();
        StringBuffer Buffer2 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                Buffer.append(res.getString(1));
                Buffer1.append(res.getString(2));
                Buffer2.append(res.getString(3));
            }
            textView.setText(Buffer.toString());
        }
        if (Objects.equals(txtsvsoz.getText().toString(),"1")){
            aswitch.setChecked(true);
            aswitch.setText(txttunsw.getText().toString());
            imgkuntun.setImageResource(R.drawable.tunlogo);
        }
        else
        if (Objects.equals(txtsvsoz.getText().toString(),"0")){
            aswitch.setChecked(false);
            aswitch.setText(txtkunsw.getText().toString());
            imgkuntun.setImageResource(R.drawable.kunlogo);
        }
        btnparolsoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PasswordUpdate.class);
                startActivity(intent);
            }
        });

        aswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aswitch.isChecked()){
                    imgkuntun.setImageResource(R.drawable.tunlogo);
                    txtsvsoz.setText("1");
                    String id = "1";
                    String kalit = "kalit";
                    aswitch.setText(txttunsw.getText().toString());
                    String til = btntilsoz.getText().toString();
                    String tun = txtsvsoz.getText().toString();
                    Boolean result = MySoz.ozgartir(id,kalit,tun,til);
                    if (result==true){
                    }
                } else {
                    imgkuntun.setImageResource(R.drawable.kunlogo);
                    txtsvsoz.setText("0");
                    String id = "1";
                    String kalit = "kalit";
                    aswitch.setText(txtkunsw.getText().toString());
                    String til = btntilsoz.getText().toString();
                    String tun = txtsvsoz.getText().toString();
                    Boolean result = MySoz.ozgartir(id,kalit,tun,til);
                    if (result==true){
                    }
                }
            }
        });
        btnchiqsoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = "1";
                String kalit = "chiqildi";
                String tun = txtsvsoz.getText().toString();
                String til = txttilsozlama.getText().toString();
                Boolean result1 = MySoz.ozgartir(i,kalit,tun,til);
                if (result1==true){
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                }
            }
        });
        btntilsoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        if (Objects.equals(txttilsozlama.getText().toString(),"uz")){
            btntilsoz.setText("o`zbek");
            txtkunsw.setText("kun");
            txttunsw.setText("tun");
            btnparolsoz.setText("parolni o`zgartirish");
            imgbayroqsoz.setImageResource(R.drawable.uzbay);
            if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                aswitch.setText(txtkunsw.getText().toString());
            }
            else {
                if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                    aswitch.setText(txttunsw.getText().toString());
                }
            }
        }
        else {
            if (Objects.equals(txttilsozlama.getText().toString(),"en")){
                btntilsoz.setText("englsh");
                txtkunsw.setText("day");
                txttunsw.setText("night");
                btnparolsoz.setText("password update");
                imgbayroqsoz.setImageResource(R.drawable.engbay);
                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                    aswitch.setText(txtkunsw.getText().toString());
                }
                else {
                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                        aswitch.setText(txttunsw.getText().toString());
                    }
                }
            }
            else {
                if (Objects.equals(txttilsozlama.getText().toString(),"ru")){
                    btntilsoz.setText("russian");
                    txtkunsw.setText("день");
                    txttunsw.setText("ночь");
                    btnparolsoz.setText("Измени пароль");
                    imgbayroqsoz.setImageResource(R.drawable.rusbay);
                    if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                        aswitch.setText(txtkunsw.getText().toString());
                    }
                    else {
                        if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                            aswitch.setText(txttunsw.getText().toString());
                        }
                    }
                }
                else {
                    if (Objects.equals(txttilsozlama.getText().toString(),"fr")){
                        btntilsoz.setText("frantsuz");
                        txtkunsw.setText("jour");
                        txttunsw.setText("nuit");
                        btnparolsoz.setText("changer le mot de passe");
                        imgbayroqsoz.setImageResource(R.drawable.farnsiyabay);
                        if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                            aswitch.setText(txtkunsw.getText().toString());
                        }
                        else {
                            if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                aswitch.setText(txttunsw.getText().toString());
                            }
                        }
                    }
                    else {
                        if (Objects.equals(txttilsozlama.getText().toString(),"arab")){
                            btntilsoz.setText("arab");
                            txtkunsw.setText("اليوم");
                            txttunsw.setText("الليل");
                            btnparolsoz.setText("تغيير كلمة المرور");
                            imgbayroqsoz.setImageResource(R.drawable.arabbay);
                            if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                aswitch.setText(txtkunsw.getText().toString());
                            }
                            else {
                                if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                    aswitch.setText(txttunsw.getText().toString());
                                }
                            }
                        }
                        else {
                            if (Objects.equals(txttilsozlama.getText().toString(),"tr")){
                                btntilsoz.setText("turkish");
                                txtkunsw.setText("gün");
                                txttunsw.setText("gece");
                                btnparolsoz.setText("şifre değiştir");
                                imgbayroqsoz.setImageResource(R.drawable.turkbay);
                                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                    aswitch.setText(txtkunsw.getText().toString());
                                }
                                else {
                                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                        aswitch.setText(txttunsw.getText().toString());
                                    }
                                }
                            }
                            else {
                                if (Objects.equals(txttilsozlama.getText().toString(),"de")){
                                    btntilsoz.setText("deutsh");
                                    txtkunsw.setText("Tag");
                                    txttunsw.setText("nächtliche");
                                    btnparolsoz.setText("Passwort ändern");
                                    imgbayroqsoz.setImageResource(R.drawable.deutshbay);
                                    if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                        aswitch.setText(txtkunsw.getText().toString());
                                    }
                                    else {
                                        if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                            aswitch.setText(txttunsw.getText().toString());
                                        }
                                    }
                                }
                                else {
                                    if (Objects.equals(txttilsozlama.getText().toString(),"it")){
                                        btntilsoz.setText("italyan");
                                        txtkunsw.setText("giorno");
                                        txttunsw.setText("notte");
                                        btnparolsoz.setText("cambia la password");
                                        imgbayroqsoz.setImageResource(R.drawable.italyabay);
                                        if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                            aswitch.setText(txtkunsw.getText().toString());
                                        }
                                        else {
                                            if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                                aswitch.setText(txttunsw.getText().toString());
                                            }
                                        }
                                    }
                                    else {
                                        Toast.makeText(getActivity(), txttilsozlama.getText().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        swiperefleshsoz.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                    aswitch.setText(txtkunsw.getText().toString());
                }
                else {
                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                        aswitch.setText(txttunsw.getText().toString());
                    }
                }
                swiperefleshsoz.setRefreshing(false);
            }
        });
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
               // textView.setText(s);
                documentReference = db.document("photourl/"+textView.getText().toString());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String urell = documentSnapshot.getString(PHOTO);
                            txtphotourlsliede.setText(urell);
                            if (Objects.equals(txtphotourlsliede.getText().toString(),"TextView")){
                            }else {
                                String url=txtphotourlsliede.getText().toString();
                                Glide.with(getActivity()).load(url).into(imgeuserslide);
                            }
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
                            //textView.setText("promocod: "+promocod);*/
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.inflate(R.menu.till);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    DataSozlama MySoz1 = new DataSozlama(getContext());
                    final TextView textView = binding.textSlideshow;
                    final SwipeRefreshLayout swiperefleshsoz = binding.swiperefleshsoz;
                    final Button btntilsoz = binding.btntilsoz;
                    final Button btnchiqsoz = binding.btnchiqsoz;
                    final TextView txttilsozlama = binding.txttilsozlama;
                    final TextView txttunsoz = binding.txttunsoz;
                    final TextView txttunsw = binding.txttunsw;
                    final TextView txtkunsw = binding.txtkunsw;
                    final TextView txtsvsoz = binding.txtsvsoz;
                    final Switch aswitch = binding.svitchsoz;
                    final ImageView imgbayroqsoz = binding.imgbayroqsoz;
                    final ImageView imgkuntun = binding.imgkuntun;
                    final Button btnparolsoz = binding.btnparolsoz;

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                                case R.id.ozbek:
                                    getActivity().finish();
                                    Intent intent1 = new Intent(getContext().getApplicationContext(), BoshOyna.class);
                                    startActivity(intent1);
                                txttilsozlama.setText("o`zbek");
                                btntilsoz.setText("o`zbek");
                                btnchiqsoz.setText("chiqish");
                                txtkunsw.setText("kun");
                                txttunsw.setText("tun");
                                btnparolsoz.setText("parolni o`zgartirish");
                                imgbayroqsoz.setImageResource(R.drawable.uzbay);
                                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                    aswitch.setText(txtkunsw.getText().toString());
                                }
                                else {
                                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                        aswitch.setText(txttunsw.getText().toString());
                                    }
                                }
                                String i = "1";
                                String kalit = "kalit";
                                String tun = txtsvsoz.getText().toString();
                                String til = "uz";
                                Boolean result1 = MySoz1.ozgartir(i,kalit,tun,til);
                                if (result1==true){
                                }
                                else {
                                }
                                return true;
                            case R.id.englsh:
                                getActivity().finish();
                                Intent intent2 = new Intent(getContext().getApplicationContext(), BoshOyna.class);
                                startActivity(intent2);
                                txttilsozlama.setText("englsh");
                                btntilsoz.setText("englsh");
                                btnchiqsoz.setText("exit");
                                txtkunsw.setText("day");
                                txttunsw.setText("night");
                                btnparolsoz.setText("password update");
                                imgbayroqsoz.setImageResource(R.drawable.engbay);
                                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                    aswitch.setText(txtkunsw.getText().toString());
                                }
                                else {
                                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                        aswitch.setText(txttunsw.getText().toString());
                                    }
                                }
                                String i1 = "1";
                                String kalit1 = "kalit";
                                String tun1 =  txtsvsoz.getText().toString();
                                String til1 = "en";
                                Boolean result11 = MySoz1.ozgartir(i1,kalit1,tun1,til1);
                                if (result11==true){
                                }
                                else {
                                }
                                return true;
                            case R.id.russian:
                                txttilsozlama.setText("russian");
                                btntilsoz.setText("russian");
                                btnchiqsoz.setText("выход");
                                txtkunsw.setText("день");
                                txttunsw.setText("ночь");
                                btnparolsoz.setText("Измени пароль");
                                imgbayroqsoz.setImageResource(R.drawable.rusbay);
                                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                    aswitch.setText(txtkunsw.getText().toString());
                                }
                                else {
                                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                        aswitch.setText(txttunsw.getText().toString());
                                    }
                                }
                                String i2 = "1";
                                String kalit2 = "kalit";
                                String tun2 =  txtsvsoz.getText().toString();
                                String til2 = "ru";
                                Boolean result2 = MySoz1.ozgartir(i2,kalit2,tun2,til2);
                                if (result2==true){
                                }
                                else {
                                }
                                return true;
                            case R.id.arab:
                                txttilsozlama.setText("arab");
                                btntilsoz.setText("arab");
                                btnchiqsoz.setText("الناتج");
                                txtkunsw.setText("اليوم");
                                txttunsw.setText("الليل");
                                btnparolsoz.setText("تغيير كلمة المرور");
                                imgbayroqsoz.setImageResource(R.drawable.arabbay);
                                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                    aswitch.setText(txtkunsw.getText().toString());
                                }
                                else {
                                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                        aswitch.setText(txttunsw.getText().toString());
                                    }
                                }
                                String i3 = "1";
                                String kalit3 = "kalit";
                                String tun3 =  txtsvsoz.getText().toString();
                                String til3 = "ar";
                                Boolean result3 = MySoz1.ozgartir(i3,kalit3,tun3,til3);
                                if (result3==true){
                                }
                                else {
                                }
                                return true;
                            case R.id.turkish:
                                txttilsozlama.setText("turkish");
                                btntilsoz.setText("turkish");
                                btnchiqsoz.setText("Çıktı");
                                txtkunsw.setText("gün");
                                txttunsw.setText("gece");
                                btnparolsoz.setText("şifre değiştir");
                                imgbayroqsoz.setImageResource(R.drawable.turkbay);
                                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                    aswitch.setText(txtkunsw.getText().toString());
                                }
                                else {
                                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                        aswitch.setText(txttunsw.getText().toString());
                                    }
                                }
                                String i4 = "1";
                                String kalit4 = "kalit";
                                String tun4 =  txtsvsoz.getText().toString();
                                String til4 = "tr";
                                Boolean result4 = MySoz1.ozgartir(i4,kalit4,tun4,til4);
                                if (result4==true){
                                }
                                else {
                                }
                                return true;
                            case R.id.nemis:
                                txttilsozlama.setText("deutsh");
                                btntilsoz.setText("deutsh");
                                btnchiqsoz.setText("Ausgang");
                                txtkunsw.setText("Tag");
                                txttunsw.setText("nächtliche");
                                btnparolsoz.setText("Passwort ändern");
                                imgbayroqsoz.setImageResource(R.drawable.deutshbay);
                                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                    aswitch.setText(txtkunsw.getText().toString());
                                }
                                else {
                                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                        aswitch.setText(txttunsw.getText().toString());
                                    }
                                }
                                String i5 = "1";
                                String kalit5 = "kalit";
                                String tun5 =  txtsvsoz.getText().toString();
                                String til5 = "de";
                                Boolean result5 = MySoz1.ozgartir(i5,kalit5,tun5,til5);
                                if (result5==true){
                                }
                                else {
                                }
                                return true;
                            case R.id.italyan:
                                txttilsozlama.setText("italyan");
                                btntilsoz.setText("italyan");
                                btnchiqsoz.setText("uscita");
                                txtkunsw.setText("giorno");
                                txttunsw.setText("notte");
                                btnparolsoz.setText("cambia la password");
                                imgbayroqsoz.setImageResource(R.drawable.italyabay);
                                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                    aswitch.setText(txtkunsw.getText().toString());
                                }
                                else {
                                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                        aswitch.setText(txttunsw.getText().toString());
                                    }
                                }
                                String i6 = "1";
                                String kalit6 = "kalit";
                                String tun6 =  txtsvsoz.getText().toString();
                                String til6 = "it";
                                Boolean result6 = MySoz1.ozgartir(i6,kalit6,tun6,til6);
                                if (result6==true){
                                }
                                else {
                                }
                                return true;
                            case R.id.frantsuzt:
                                getActivity().finish();
                                Intent intent3 = new Intent(getContext().getApplicationContext(), BoshOyna.class);
                                startActivity(intent3);
                                Context context = LocaleHelper.setLocale(getActivity(), "fr");
                                resources = context.getResources();
                                txttilsozlama.setText("frantsuz");
                                btntilsoz.setText("frantsuz");
                                btnchiqsoz.setText("sortie");
                                txtkunsw.setText("jour");
                                txttunsw.setText("nuit");
                                btnparolsoz.setText("changer le mot de passe");
                                imgbayroqsoz.setImageResource(R.drawable.farnsiyabay);
                                if (Objects.equals(txtsvsoz.getText().toString(),"1")){
                                    aswitch.setText(txtkunsw.getText().toString());
                                }
                                else {
                                    if (Objects.equals(txtsvsoz.getText().toString(),"0")){
                                        aswitch.setText(txttunsw.getText().toString());
                                    }
                                }
                                String i7 = "1";
                                String kalit7 = "kalit";
                                String tun7 =  txtsvsoz.getText().toString();
                                String til7 = "fr";
                                Boolean result7 = MySoz1.ozgartir(i7,kalit7,tun7,til7);
                                if (result7==true){
                                }
                                else {
                                }
                                return true;
                            default:
                                return false;
                        }
                    }
                });
        popupMenu.show();
    }

}