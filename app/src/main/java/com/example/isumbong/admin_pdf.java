package com.example.isumbong;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class admin_pdf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        getPDF();

    }
    private void getPDF(){
        String user = getIntent().getStringExtra("user");
        String serial = getIntent().getStringExtra("serial");
        database db = new database(this);

        PDFView pdfView = findViewById(R.id.pdfView);

        String filepath = db.path(user,serial);
        Log.e("PATH", filepath);

        File file = new File(filepath);
        if(file.exists()){
            Log.e("CHECK", "file exists!");
            pdfView.fromUri(Uri.fromFile(new File(filepath))).load();
        }
        else
            Toast.makeText(admin_pdf.this, "File not found", Toast.LENGTH_SHORT).show();


    }
}