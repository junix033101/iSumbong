package com.example.isumbong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin_signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        EditText user = findViewById(R.id.Text_user);
        EditText pass = findViewById(R.id.Text_password);
        EditText repass = findViewById(R.id.Text_repassword);
        EditText name = findViewById(R.id.Text_name);
        EditText id = findViewById(R.id.Text_ID);
        EditText email = findViewById(R.id.Text_email);

        Button signup = findViewById(R.id.button_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database db = new database(admin_signup.this);
                String User = user.getText().toString();
                String Pass = pass.getText().toString();
                String Repass = repass.getText().toString();
                String Name = name.getText().toString();
                String Id = id.getText().toString();
                String Email = email.getText().toString();

                if(User.matches("")||Pass.matches("")||Name.matches("")||Id.matches("")||Email.matches("")){
                    Toast.makeText(admin_signup.this, "There is an empty field!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Pass.matches(Repass)){
                    Toast.makeText(admin_signup.this,"Password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean check = db.createAdmin(User,Pass,Name,Id,Email);
                if(check){
                    Toast.makeText(admin_signup.this,"Registered", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(admin_signup.this,"Username Taken", Toast.LENGTH_SHORT).show();

            }
        });

    }
}