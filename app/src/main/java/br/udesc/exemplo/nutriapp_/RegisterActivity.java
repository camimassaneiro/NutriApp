package br.udesc.exemplo.nutriapp_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    // Tela de cadastrar novos usuários

    EditText username, email, height, weight, goalWeight, password;
    Button register;
    TextView txtLogin;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        goalWeight = findViewById(R.id.goalWeight);
        password = findViewById(R.id.password);
        register = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Carregando ...");
                pd.show();

                String strUsername = username.getText().toString();
                String strEmail = email.getText().toString();
                String strHeight = height.getText().toString();
                String strWeight = weight.getText().toString();
                String strGoalWeight = goalWeight.getText().toString();
                String strPassword = password.getText().toString();

                if (TextUtils.isEmpty(strUsername) || TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strHeight) ||
                        TextUtils.isEmpty(strWeight) || TextUtils.isEmpty(strGoalWeight) || TextUtils.isEmpty(strPassword)){

                    Toast.makeText(RegisterActivity.this, "Todos os campos devem estar preenchidos", Toast.LENGTH_SHORT).show();
                } else if (strPassword.length() < 6 ) {
                    Toast.makeText(RegisterActivity.this, "Senha deve ter 6 caracteres", Toast.LENGTH_SHORT).show();
                } else {
                    register(strUsername, strEmail, strHeight, strWeight, strGoalWeight, strPassword);
                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void register(String username, String email, String height, String weight, String goalWeight, String password){

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                            HashMap<String, Object> hashMap = new HashMap<>();

                            hashMap.put("id", userId);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("email", email);
                            hashMap.put("height", height);
                            hashMap.put("weight", weight);
                            hashMap.put("goalWeight", goalWeight);
                            hashMap.put("bio", "");
                            hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/nutriapp2-6321e.appspot.com/o/profile.png?alt=media&token=4e1160d8-85da-454d-ac95-5903066b8801");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            Log.d("teste", task.getException()+"");
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "Não foi possível cadastrar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
