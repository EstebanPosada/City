package emanager.com.cityeye;

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
    private String user = "users", celphone = "cel", doc = "document", mail = "email";
    private String probe = "message";
    private String logmail, logpass;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Write a message to the database
        //database = FirebaseDatabase.getInstance();
        //myRef = database.getReference(user);
        myFirebaseRef = new Firebase("https://e-manager-44914.firebaseio.com/users/");
        mAuthp = FirebaseAuth.getInstance();

        FirebaseUser myFireUser = mAuthp.getCurrentUser();
        if (myFireUser != null){
            String uid = mAuthp.getCurrentUser().getUid();

        }

        edNick = (EditText) findViewById(R.id.etnick);
        edmail = (EditText) findViewById(R.id.etmail);
        edpass = (EditText) findViewById(R.id.etpass);
        edcc = (EditText) findViewById(R.id.etcc);
        edcel = (EditText) findViewById(R.id.etcel);
        btnreg = (Button) findViewById(R.id.idregistra);



        /*mAuthListenerp = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Toast.makeText(Registro.this, "Usuario: " + user.getUid(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Registro.this, "No creado", Toast.LENGTH_SHORT).show();
                }
            }
        };*/

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpUser();
                mAuthp.createUserWithEmailAndPassword(logmail,logpass).addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()){
                            Toast.makeText(Registro.this, "Falló autenticación", Toast.LENGTH_SHORT).show();
                        } else {
                            onAuthenticationSucess(task.getResult().getUser());
                        }
                    }
                });

                /*String user = edNick.getText().toString();
                String mail = edmail.getText().toString();
                String pass = edpass.getText().toString();
                //myRef.setValue("Hello, World!");
                if (mail.equals("") && user.equals("") && pass.equals("")){
                    Toast.makeText(Registro.this, "Faltan campos", Toast.LENGTH_SHORT).show();
                } else {
                   *//* String key = myRef.child(user).push().getKey();
                    myRef.child(key).child(celphone).setValue(edcel.getText().toString());
                    myRef.child(key).child(doc).setValue(edcc.getText().toString());
                    myRef.child(key).child(mail).setValue(edmail.getText().toString());
                    Toast.makeText(Registro.this, "Enviado: message - Hello, World!!", Toast.LENGTH_SHORT).show();*//*
                    *//*mAuthp.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (!task.isSuccessful()){
                                Toast.makeText(Registro.this, "Falló registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*//*
                }
*/
            }
        });




        // Read from the database
        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }

    /*@Override
    public void onStart() {
        super.onStart();
        mAuthp.addAuthStateListener(mAuthListenerp);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListenerp != null) {
            mAuthp.removeAuthStateListener(mAuthListenerp);
        }
    }*/
    private void onAuthenticationSucess(FirebaseUser FiUser){
        User usernew = new User(FiUser.getUid(), myuser.getName(), myuser.getPhone(), myuser.getEmail(), myuser.getPass());

        //StartActivity
        myRef.child(user).child(FiUser.getUid()).setValue(usernew);
    }
    public void setUpUser(){
        myuser = new User();
        myuser.setName(edNick.getText().toString());
        myuser.setEmail(edmail.getText().toString());
        myuser.setPhone(edcel.getText().toString());
        myuser.setPass(edpass.getText().toString());
        logmail = myuser.getEmail();
        logpass = myuser.getPass();
    }

}
