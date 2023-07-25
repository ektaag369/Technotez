package com.example.technotez.UploadFragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.technotez.FileInfoModel;
import com.example.technotez.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class PdfFragment extends Fragment {
    ImageView browseimg,pdfimg,cancelimg;
    EditText pdftitle,pdfsemester,pdfsubj;
    Button uploadbtn;
    Uri filepath;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseFirestore firestore;
    String UUID="";

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pdf, container, false);

        browseimg=view.findViewById(R.id.browseimg);
        pdfimg=view.findViewById(R.id.pdfimg);
        pdfsemester=view.findViewById(R.id.pdfsem);
        pdfsubj=view.findViewById(R.id.pdfsubj);
        cancelimg=view.findViewById(R.id.cancelimg);
        pdftitle=view.findViewById(R.id.pdftitle);
        uploadbtn=view.findViewById(R.id.upload);

        mAuth=FirebaseAuth.getInstance();

        pdfimg.setVisibility(View.INVISIBLE);
        cancelimg.setVisibility(View.INVISIBLE);

        cancelimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfimg.setVisibility(View.INVISIBLE);
                cancelimg.setVisibility(View.INVISIBLE);
                browseimg.setVisibility(View.VISIBLE);
            }
        });
        browseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent i=new Intent();
                                i.setType("application/pdf");
                                i.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(i,"Select pdf files"),101);

                            }@Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {}
                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {}
                        }).check();
            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processUpload(filepath);
            }
        });

        return view;
    }
    private void processUpload(Uri filepath) {


        ProgressDialog pd=new ProgressDialog(getContext());
        pd.setTitle("Uploading PDF....!!");
        pd.show();

        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();
        firestore=FirebaseFirestore.getInstance();
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

        StorageReference reference=storageReference.child("uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                HashMap<String,Object> pdfdetails=new HashMap<>();

                                pdfdetails.put("Title",pdftitle.getText().toString());
                                pdfdetails.put("Subject",pdfsubj.getText().toString());
                                pdfdetails.put("Semester",pdfsemester.getText().toString());
                                pdfdetails.put("PdfUrl",uri.toString());
                                pdfdetails.put("UUID",UUID);
                                firestore.collection("PdfDetails").add(pdfdetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        pd.dismiss();
                                        Toast.makeText(getContext(), "PDF uploaded", Toast.LENGTH_SHORT).show();

                                        pdfimg.setVisibility(View.INVISIBLE);
                                        cancelimg.setVisibility(View.INVISIBLE);
                                        browseimg.setVisibility(View.VISIBLE);
                                        pdftitle.setText("");
                                        pdfsemester.setText("");
                                        pdfsubj.setText("");
                                    }
                                });
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded:"+(int)percent+"%");
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            pdfimg.setVisibility(View.VISIBLE);
            cancelimg.setVisibility(View.VISIBLE);
            browseimg.setVisibility(View.INVISIBLE);
        }
    }
}