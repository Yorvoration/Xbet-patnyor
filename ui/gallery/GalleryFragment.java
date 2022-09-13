package com.UzCodeMD.xpak.ui.gallery;

import static android.app.Activity.RESULT_OK;
import static android.util.Log.d;
import static com.airbnb.lottie.L.TAG;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.UzCodeMD.xpak.BoshOyna;
import com.UzCodeMD.xpak.DataLogin;
import com.UzCodeMD.xpak.R;
import com.UzCodeMD.xpak.Xabar;
import com.UzCodeMD.xpak.databinding.FragmentGalleryBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class GalleryFragment extends Fragment {
    private FirebaseFirestore db;
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
    public final String SONGIVAQT = "SONGIVAQT";
    public final String PHOTO = "PHOTO";
    public final String DARAJA = "DARAJA";
    private Uri filePath;
    private TextView textView;
    private ImageView imgageusergallarey;
    private final int PICK_IMAGE_REQUEST = 71;

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                final TextView niknamegal = binding.niknamegal;
                final ImageView imgageusergallarey = binding.imgageusergallarey;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imgageusergallarey.setImageBitmap(bitmap);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                if(filePath != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    //txturluserphoto.setText(UUID.randomUUID().toString());
                    StorageReference ref = storageReference.child(niknamegal.getText().toString()+"/" + niknamegal.getText().toString());
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

                                            db.collection("photourl").document(niknamegal.getText().toString()).set(user1)
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
                                    Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    d("TAG", e.toString());
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        DataLogin Mydb = new DataLogin(getContext());
        db = FirebaseFirestore.getInstance();
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textGallery;
        final TextView useridgallary = binding.useridgallary;
        final TextView txturlgallarey = binding.txturlgallarey;
        final ImageView imgageusergallarey = binding.imgageusergallarey;
        final ConstraintLayout swipereflesh1 = binding.swipereflesh1;
        final Button btntahrirgal = binding.btntahrirgal;
        final Button btnsaqlashgal = binding.btnsaqlashgal;
        final EditText ediismgal = binding.ediismgal;
        final EditText edifamilyagal = binding.edifamilyagal;
        final EditText edipochtagal = binding.edipochtagal;
        final EditText editelgal = binding.editelgal;
        final EditText edimanzilgal = binding.edimanzilgal;
        final DatePicker edidatapickergal = binding.edidatapickergal;
        final TextView texttimegal = binding.texttimegal;
        final TextView niknamegal = binding.niknamegal;
        final View view = binding.view11;
        final ImageButton imgbtntelegram = binding.imgbtntelegram;
        final ImageButton imgbtninstagram = binding.imgbtninstagram;
        final ImageButton imgbtntiktok = binding.imgbtntiktok;


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnsaqlashgal.setVisibility(View.INVISIBLE);
                btntahrirgal.setVisibility(View.VISIBLE);
                imgbtntelegram.setVisibility(View.VISIBLE);
                imgbtninstagram.setVisibility(View.VISIBLE);
                imgbtntiktok.setVisibility(View.VISIBLE);
                ediismgal.setEnabled(false);
                edifamilyagal.setEnabled(false);
                edipochtagal.setEnabled(false);
                editelgal.setEnabled(false);
                edimanzilgal.setEnabled(false);
                texttimegal.setEnabled(false);
            }
        });
        imgbtntelegram.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Uri uri = Uri.parse("https://t.me/joinchat/OgBhwFFFcESnDAsIJ4sLmQ");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.telegram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://t.me/joinchat/OgBhwFFFcESnDAsIJ4sLmQ")));
                }
            }
        });
        imgbtninstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/dasturchi01/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/dasturchi01/")));
                }
            }
        });
        imgbtntiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.tiktok.com/@bir_tiyin_hammasi?");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.tiktok.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.tiktok.com/@bir_tiyin_hammasi?")));
                }
            }
        });
        btntahrirgal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("malumotlar") .setMessage("hisob malumotlarini o`zgartirmoqchimisiz")
                        .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                btnsaqlashgal.setVisibility(View.VISIBLE);
                                btntahrirgal.setVisibility(View.INVISIBLE);
                                imgbtntelegram.setVisibility(View.INVISIBLE);
                                imgbtninstagram.setVisibility(View.INVISIBLE);
                                imgbtntiktok.setVisibility(View.INVISIBLE);

                                ediismgal.setEnabled(true);
                                edifamilyagal.setEnabled(true);
                                edipochtagal.setEnabled(true);
                                editelgal.setEnabled(true);
                                edimanzilgal.setEnabled(true);
                                texttimegal.setEnabled(true);
                            } })
                        .setNegativeButton("Yo'q", null)
                        .show();

            }
        });
        btnsaqlashgal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttimegal.setText(edidatapickergal.getDayOfMonth()+"/"+ (edidatapickergal.getMonth() + 1)+"/"+edidatapickergal.getYear());
                if (ediismgal.getText().toString().isEmpty()){
                    ediismgal.setError("bo`sh");
                }else {
                    if (ediismgal.getText().toString().isEmpty()){
                        ediismgal.setError("bo`sh");
                    }else {
                        if (edifamilyagal.getText().toString().isEmpty()){
                            edifamilyagal.setError("bo`sh");
                        }else {
                            if (edipochtagal.getText().toString().isEmpty()){
                                edipochtagal.setError("bo`sh");
                            }else {
                                if (editelgal.getText().toString().isEmpty()){
                                    editelgal.setError("bo`sh");
                                }else {
                                    if (edimanzilgal.getText().toString().isEmpty()){
                                        edimanzilgal.setError("bo`sh");
                                    }else {
                                        btnsaqlashgal.setVisibility(View.INVISIBLE);
                                        btntahrirgal.setVisibility(View.VISIBLE);
                                        imgbtntelegram.setVisibility(View.VISIBLE);
                                        imgbtninstagram.setVisibility(View.VISIBLE);
                                        imgbtntiktok.setVisibility(View.VISIBLE);

                                        ediismgal.setEnabled(false);
                                        edifamilyagal.setEnabled(false);
                                        edipochtagal.setEnabled(false);
                                        editelgal.setEnabled(false);
                                        edimanzilgal.setEnabled(false);
                                        texttimegal.setEnabled(false);

                                        documentReference = db.document("user/"+niknamegal.getText().toString());
                                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()){
                                                    textView.setText(documentSnapshot.getString(NAME));
                                                    String nameo = documentSnapshot.getString(NAME);
                                                    String net_kash = documentSnapshot.getString(NET_KASH);
                                                    String niknameo = documentSnapshot.getString(NAME);
                                                    String parolo = documentSnapshot.getString(PAROL);
                                                    String telo = documentSnapshot.getString(TEL);
                                                    String bugunpulo = documentSnapshot.getString(BUGUNPUL);
                                                    String kechpulo = documentSnapshot.getString(KECHAPUL);
                                                    String haftapulo = documentSnapshot.getString(HAFTAPUL);
                                                    String oypulo = documentSnapshot.getString(OYPUL);
                                                    String jamipulo = documentSnapshot.getString(JAMIPUL);
                                                    String yangiliklaro = documentSnapshot.getString(YANGILIKLAR);
                                                    String murojatmaydono = documentSnapshot.getString(MUROJATMAYDON);
                                                    String tarixotkazmao = documentSnapshot.getString(TARIXOTKAZMA);
                                                    String aloqao = documentSnapshot.getString(ALOQA);
                                                    String promocodo = documentSnapshot.getString(PROMOCOD);
                                                    String userido = documentSnapshot.getString(USERID);
                                                    String yosho = documentSnapshot.getString(YOSH);
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.getDefault());
                                                            String currentDateandTime = sdf.format(new Date());
                                                            String name5 = ediismgal.getText().toString();
                                                            String name = nameo;
                                                            String nikname = niknameo;
                                                            String parol5 = parolo;
                                                            String tel5 = editelgal.getText().toString();
                                                            String davlat = edimanzilgal.getText().toString();
                                                            String ism = ediismgal.getText().toString();
                                                            String familya = edifamilyagal.getText().toString();
                                                            String pochta = edipochtagal.getText().toString();
                                                            String yosh = texttimegal.getText().toString();
                                                            String bugunpul = bugunpulo;
                                                            String kechpul = kechpulo;
                                                            String haftapul = haftapulo;
                                                            String oypul = oypulo;
                                                            String jamipul = jamipulo;
                                                            String yangiliklar = yangiliklaro;
                                                            String murojatmaydon = murojatmaydono;
                                                            String tarixotkazma = tarixotkazmao;
                                                            String aloqa = aloqao;
                                                            String promocod = promocodo;
                                                            String userid = userido;
                                                            //String kirishvaqt = currentDateandTime;
                                                            String vaqt = currentDateandTime;

                                                            Map<String, Object> user1 = new HashMap<>();
                                                            user1.put(NIKNAME, nikname);                 //1
                                                            user1.put(NAME, name);                 //1
                                                            user1.put(PAROL, parol5);               //2
                                                            user1.put(TEL, tel5);                   //3
                                                            user1.put(DAVLAT, davlat);              //4
                                                            user1.put(ISM, ism);                    //5
                                                            user1.put(FAMILYA, familya);            //6
                                                            user1.put(POCHTA, pochta);              //7
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
                                                            user1.put(YOSH, yosh);              //20
                                                            Map<String, Object> usertel = new HashMap<>();
                                                            usertel.put(NAME, name);
                                                            usertel.put(TEL, tel5);
                                                            Boolean result = Mydb.kiritish(name,parol5,tel5,davlat,ism,familya,
                                                                    pochta,net_kash,bugunpul,kechpul,haftapul,oypul,jamipul,
                                                                    yangiliklar,murojatmaydon,tarixotkazma,aloqa,promocod,userid);

                                                            if (result == true){
                                                            }
                                                            db.collection("user").document(name)
                                                                    .update(user1);
                                                            db.collection("telefonlar").
                                                                    document(telo).
                                                                    delete().
                                                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                db.collection("telefonlar").document(tel5).set(usertel)
                                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void unused) {
                                                                                            }
                                                                                        });
                                                                            } else {

                                                                            }
                                                                        }
                                                                    });
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
                            }
                        }
                    }
                }
            }
        });
        imgageusergallarey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        if (Objects.equals(txturlgallarey.getText().toString(),"TextView")){
        }else {
            String url=txturlgallarey.getText().toString();
            Glide.with(getActivity()).load(url).into(imgageusergallarey);
        }
        Cursor res = Mydb.oqish();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer1.append(res.getString(1));
                stringBuffer2.append(res.getString(19));
                stringBuffer3.append(res.getString(3));
            }
            /*textView.setText(stringBuffer1.toString());*/
            niknamegal.setText(stringBuffer1.toString());
            useridgallary.setText("id "+stringBuffer2.toString());
        }
       /* swipereflesh1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String url=txturlgallarey.getText().toString();
                Toast.makeText(getContext(), textView.getText().toString()+"va"+txturlgallarey.getText().toString(), Toast.LENGTH_SHORT).show();
                Glide.with(getActivity()).load(url).into(imgageusergallarey);
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
                            textView.setText(nomi);
                            Boolean result = Mydb.kiritish(nomi,paroli,teli,davlati,ismi,familyasi,
                                    pochta,net_kash,bugunpul,kechpul,haftapul,oypul,jamipul,
                                    yangiliklar,murojatmaydon,tarixotkazma,aloqa,promocod,userid);

                            if (result == true){
                                Toast.makeText(getActivity(), "yangilandi", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Toast.makeText(getActivity(), "exxx", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                           // Toast.makeText(getActivity(),"xatolik shekili", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                swipereflesh1.setRefreshing(false);
            }
        });*/
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                documentReference = db.document("user/"+niknamegal.getText().toString());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String ismi = documentSnapshot.getString(ISM);
                            String nomi = documentSnapshot.getString(NIKNAME);
                            String familyasi = documentSnapshot.getString(FAMILYA);
                            String pochta = documentSnapshot.getString(POCHTA);
                            String tel = documentSnapshot.getString(TEL);
                            String davlat = documentSnapshot.getString(DAVLAT);
                            String time = documentSnapshot.getString(YOSH);
                            textView.setText(ismi);
                            niknamegal.setText(nomi);
                            ediismgal.setText(ismi);
                            edifamilyagal.setText(familyasi);
                            edipochtagal.setText(pochta);
                            editelgal.setText(tel);
                            edimanzilgal.setText(davlat);
                            texttimegal.setText(time);

                            ediismgal.setEnabled(false);
                            edifamilyagal.setEnabled(false);
                            edipochtagal.setEnabled(false);
                            editelgal.setEnabled(false);
                            edimanzilgal.setEnabled(false);
                            texttimegal.setEnabled(false);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

                documentReference = db.document("photourl/"+niknamegal.getText().toString());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String urell = documentSnapshot.getString(PHOTO);
                            txturlgallarey.setText(urell);
                            if (Objects.equals(txturlgallarey.getText().toString(),"TextView")){
                            }else {
                                String url=txturlgallarey.getText().toString();
                                Glide.with(getActivity()).load(url).into(imgageusergallarey);
                            }
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

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

}