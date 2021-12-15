package com.example.isumbong;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.drjacky.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class admin_account extends AppCompatActivity {

    Uri img_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

        String user = getIntent().getStringExtra("user");
        setinfo(user);
        changePic();
        signOut();

    }

    private void setinfo(String user){
        database db = new database(this);

        TextView name = findViewById(R.id.textView_acc_name);
        EditText username = findViewById(R.id.editText_acc_user);
        EditText id = findViewById(R.id.editText_acc_id);
        EditText sector = findViewById(R.id.editText_acc_sector);
        EditText email = findViewById(R.id.editText_acc_email);
        ImageView profile = findViewById(R.id.imageView);


        username.setEnabled(false);
        id.setEnabled(false);
        sector.setEnabled(false);
        email.setEnabled(false);

        name.setText(db.getName(user));
        username.setText(user);
        id.setText(db.getIdNo(user));
        sector.setText(db.getPnpSector(admin_login.admin_code));
        email.setText(db.getEmail(user));
        if(img_profile == null){
            try {
                profile.setImageURI(Uri.parse(db.getProfile(user)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void changePic(){
        ImageButton edit = findViewById(R.id.imageButton_EDIT);
        ImageView profile = findViewById(R.id.imageView);
        database db = new database(this);
        String user = getIntent().getStringExtra("user");

        ActivityResultLauncher<Intent> launcher_v =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        img_profile = result.getData().getData();
                        profile.setImageURI(img_profile);
                        String profile_uri = img_profile.toString();
                        db.updateImg(profile_uri,user);
                        // Use the uri to load the image
                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(admin_account.this)
                        .crop()
                        .cropSquare()
                        .maxResultSize(512, 512, true)
                        .createIntentFromDialog(new Function1() {
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                launcher_v.launch(it);
                            }
                        });
            }
        });
    }

    private void signOut(){
        ImageButton out = findViewById(R.id.imageButton_logout);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(admin_account.this)
                        .setTitle("Sign Out")
                        .setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(admin_account.this, admin_login.class);
                                intent.putExtras(getIntent());
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

    }
}