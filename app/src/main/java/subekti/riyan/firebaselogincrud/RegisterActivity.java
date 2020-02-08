package subekti.riyan.firebaselogincrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import subekti.riyan.firebaselogincrud.model.Users;

public class RegisterActivity extends AppCompatActivity {
    private EditText etNama, etEmail, etPassword,etAlamat, etRepassword;
    private RadioButton radioMale, radioFemale;
    private Button btnSignup, btnBack;
    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        etNama = findViewById(R.id.et_nama);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etRepassword = findViewById(R.id.et_repassword);
        etAlamat = findViewById(R.id.et_alamat);
        radioMale = findViewById(R.id.radio_male);
        radioFemale = findViewById(R.id.radio_female);
        btnSignup = findViewById(R.id.btn_signup);
        btnBack = findViewById(R.id.btn_back);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String repassword = etRepassword.getText().toString();

                if (email.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Silahkan isi Email anda", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Silahkan isi Password anda", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(repassword)) {
                    Toast.makeText(RegisterActivity.this, "Password yang anda masukan tidak sama", Toast.LENGTH_SHORT).show();
                } else {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    String email = etEmail.getText().toString();
                                    String password = etPassword.getText().toString();
                                    String nama = etNama.getText().toString();
                                    String alamat = etAlamat.getText().toString();
                                    String gender = "";

                                    if (radioMale.isChecked()) {
                                        gender = "Male";
                                    } else if (radioFemale.isChecked()) {
                                        gender = "Female";
                                    }

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        loading = ProgressDialog.show(RegisterActivity.this, null, "Pleas Wait",
                                                true, false);
                                        submitUser(new Users(email.toLowerCase(), nama, password.toLowerCase(), alamat, gender));
                                        Toast.makeText(RegisterActivity.this, "Authentication success.",
                                                Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }

    private void submitUser(Users users){
        database.child("firebase-login-crud").child("users").child("pembeli").child("profil").child(mAuth.getCurrentUser().getUid())
                .setValue(users)
                .addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();
                        etEmail.setText("");
                        etPassword.setText("");
                        etNama.setText("");
                        etAlamat.setText("");
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
        }
    }
}
