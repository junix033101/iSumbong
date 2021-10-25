package com.example.isumbong;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class admin_dl_files extends AppCompatActivity {
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dl_files);

        list();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, admin_homepage.class);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();
    }

    public void list(){
        String user = getIntent().getStringExtra("user");
        database db = new database(this);
        ListView l = findViewById(R.id.listView_dl);
        TextView def = findViewById(R.id.textView_def);
        ArrayList<String> dls = new ArrayList<>();
        dls = db.getPdfDls(user);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,dls);
        l.setAdapter(adapter);
        l.setEmptyView(def);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String serial = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(admin_dl_files.this, admin_pdf.class);
                intent.putExtra("serial", serial);
                intent.putExtras(getIntent());
                startActivity(intent);

            }
        });
    }

    //search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_admin_search_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search File Name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

}