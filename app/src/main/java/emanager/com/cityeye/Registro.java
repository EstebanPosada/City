package emanager.com.cityeye;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
/*import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;*/
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registro extends AppCompatActivity {

    String TAG = "myfirebase";
    private Button btnreg;
    private EditText edNick, edmail, edpass, edcc, edcel;
    private FirebaseDatabase database;


    private User myuser;
    private Firebase myFirebaseRef;
    private FirebaseAuth mAuthp;
    private FirebaseAuth.AuthStateListener mAuthListenerp;

    private DatabaseReference myRef;
    private String user = "users";
    private String probe = "message";
    private String logmail, logpass, logcc, lognick, logphone;
    private static String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        database = FirebaseDatabase.getInstance();
        mAuthp = FirebaseAuth.getInstance();


        mAuthListenerp = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser myFireUs = firebaseAuth.getCurrentUser();
                if (myFireUs != null){
                    Log.d("firebase", "igned in" + myFireUs.getUid());
                } else {
                    Log.d("firebase", "signed out");
                }
            }
        };

        edNick = (EditText) findViewById(R.id.etnick);
        edmail = (EditText) findViewById(R.id.etmail);
        edpass = (EditText) findViewById(R.id.etpass);
        edcc = (EditText) findViewById(R.id.etcc);
        edcel = (EditText) findViewById(R.id.etcel);
        btnreg = (Button) findViewById(R.id.idregistra);


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (!setUpUser()) {
                Toast.makeText(Registro.this, "Faltan datos", Toast.LENGTH_SHORT).show();
            } else {
                mAuthp.createUserWithEmailAndPassword(logmail, logpass).addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Registro.this, "Falló autenticación: " + task.getException(), Toast.LENGTH_SHORT).show();
                        } else {
                            onAuthenticationSucess(task.getResult().getUser());
                        }
                    }
                });
            }
            }
        });

    }
    private void onAuthenticationSucess(FirebaseUser FiUser){
        uid = FiUser.getUid().toString();
        User usernew = new User(FiUser.getUid(), lognick, logphone, logmail, logpass, logcc);
        myRef = database.getReference(user);
        myRef.child(FiUser.getUid()).setValue(usernew);

        startActivity(new Intent(getApplicationContext(), Probes.class));
        finish();
    }
    public boolean setUpUser(){
        logmail = edmail.getText().toString();
        logpass = edpass.getText().toString();
        logcc = edcc.getText().toString();
        lognick = edNick.getText().toString();
        logphone = edcel.getText().toString();
        if (logpass.equals("") || logmail.equals("") || logcc.equals("") || lognick.equals("") || logphone.equals("")) {
            return false;
        } else {
            myuser = new User();
            myuser.setEmail(logmail);
            myuser.setPass(logpass);
            myuser.setDocument(logcc);
            myuser.setNickname(lognick);
            myuser.setCel(logphone);
            return true;
        }
    }

}
