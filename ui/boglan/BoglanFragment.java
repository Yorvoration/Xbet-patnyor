package com.UzCodeMD.xpak.ui.boglan;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.UzCodeMD.xpak.DataLogin;
import com.UzCodeMD.xpak.DataSozlama;
import com.UzCodeMD.xpak.databinding.FragmentBoglanBinding;
import com.UzCodeMD.xpak.databinding.FragmentHomeBinding;
import com.UzCodeMD.xpak.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class BoglanFragment extends Fragment {
    private BoglanViewModel boglanViewModel;
    private FragmentBoglanBinding binding;

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        boglanViewModel = new ViewModelProvider(this).get(BoglanViewModel.class);
        binding = FragmentBoglanBinding.inflate(inflater, container, false);
        View root1 = binding.getRoot();
        final TextView textView = binding.textHome;
        final ImageView img_insta_bog = binding.imginstabog;
        final ImageView img_teleg_bog = binding.imgtelegbog;
        final ImageView img_tikt_bog = binding.imgtiktbog;

        img_insta_bog.setOnClickListener(new View.OnClickListener() {
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
        img_teleg_bog.setOnClickListener(new View.OnClickListener() {
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
        img_tikt_bog.setOnClickListener(new View.OnClickListener() {
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

        boglanViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root1;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}