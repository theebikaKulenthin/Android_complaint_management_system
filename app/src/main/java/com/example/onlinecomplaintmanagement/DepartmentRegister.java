package com.example.onlinecomplaintmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecomplaintmanagement.model.DepartmentUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class DepartmentRegister extends AppCompatActivity {

    EditText et_email, et_password, et_confirmPassword, et_username, et_phoneNumber;
    Button btn_Register;
    TextView tv_loginBtn;

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        btn_Register = findViewById(R.id.btn_register);
        tv_loginBtn = findViewById(R.id.tv_loginButton);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        tv_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DepartmentRegister.this, DepartmentLogin.class));
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });

    }

    private void PerformAuth() {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String confirmPassword = et_confirmPassword.getText().toString();
        String username = et_username.getText().toString();
        String phoneNumber = et_phoneNumber.getText().toString();

        if (email.isEmpty()) {
            et_email.setError("Please Enter Email");
        } else if (!pat.matcher(email).matches()) {
            et_email.setError("Please Enter a valid Email");
        } else if (password.isEmpty()) {
            et_password.setError("Please input Password");
        } else if (password.length() < 6) {
            et_password.setError("Password too short");
        } else if (!confirmPassword.equals(password)) {
            et_confirmPassword.setError("Password doesn't matches");
        } else {
            progressDialog.setMessage("Creating your Account....");
            progressDialog.setTitle("Creating");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();

                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String userId = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference().child(RegisterActivity.DEPARTMENT).child(userId);
//                        HashMap<String, String> hashMap = new HashMap<>();
//                        hashMap.put("id", userId);
//                        hashMap.put("username", username);
//                        hashMap.put("email", email);
//                        hashMap.put("password", password);
//                        hashMap.put("address", address);
//                        hashMap.put("phoneNumber", phoneNumber);

                        DepartmentUser model = new DepartmentUser();
                        model.setPhoneNumber(phoneNumber);
                        model.setEmail(email);
                        model.setPassword(password);
                        model.setId(userId);
                        model.setUserName(username);

                        reference.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    sendUserToMainActivity();
                                }
                            }
                        });


                        Toast.makeText(DepartmentRegister.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(DepartmentRegister.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToMainActivity() {
        Intent intent = new Intent(DepartmentRegister.this, DepartmentDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}