package com.example.technotez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.technotez.MainFragments.ProfileFragment;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Viewpdf extends AppCompatActivity {
    PDFView pdfview;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf);

        pdfview=findViewById(R.id.pdfviewer);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.show();

        String fileurl=getIntent().getStringExtra("fileurl");
        new RetrivePdfStream().execute(fileurl);
    }
    private class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try{

                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            }catch(IOException e){

                return null;
            }
            return inputStream;
        }

        @Override
        // Here load the pdf and dismiss the dialog box
        protected void onPostExecute(InputStream inputStream) {
            pdfview.fromStream(inputStream).load();
            dialog.dismiss();

        }


    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(Viewpdf.this, ProfileFragment.class);
        startActivity(i);
        finish();
        return;

    }

}