package com.example.technotez.ViewProfile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.technotez.FileInfoModel;
import com.example.technotez.PdfAdapter;
import com.example.technotez.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewPdfFragment extends Fragment {
    RecyclerView pdfrv;
    PdfAdapter pdfAdapter;
    FirebaseAuth mAuth;
    String UUID="";
    FirebaseFirestore firestore;
    ArrayList<FileInfoModel> pdflist= new ArrayList<FileInfoModel>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_view_pdf, container, false);
        pdfrv=view.findViewById(R.id.view_pdf_rv);
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        String email=firebaseUser.getEmail();
        firestore= FirebaseFirestore.getInstance();
        firestore.collection("TeacherDetails").whereEqualTo("Email",firebaseUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                UUID=documentSnapshot.getString("UserID");
                            }
                        }else{
                            Toast.makeText(getContext(), "Error getting documents of brand", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        pdfrv.setHasFixedSize(true);
        pdfrv.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        pdfAdapter=new PdfAdapter(getContext(),pdflist);
        pdfrv.setAdapter(pdfAdapter);
        EventChangeListener();

        return view;
    }

    private void EventChangeListener() {
        firestore.collection("PdfDetails").whereEqualTo("UUID",UUID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    FileInfoModel c=d.toObject(FileInfoModel.class);
                    pdflist.add(c);
                }
                pdfAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}