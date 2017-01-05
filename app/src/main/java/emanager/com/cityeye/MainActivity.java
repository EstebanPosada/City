package emanager.com.cityeye;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Set;
/*import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;*/

public class MainActivity extends AppCompatActivity {

    private Button login, logfb;
    private TextView regist,clickAqui;
    private EditText mpass, mmail;
    private String mail, pass;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private Firebase mRef;
    private DatabaseReference myDbRef;
    String email, epass;
    private String user = "users";
    /*private FirebaseAuth.AuthStateListener mAuthListener;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mRef = new Firebase("https://e-manager-44914.firebaseio.com/");
        myDbRef = FirebaseDatabase.getInstance().getReference();

        readusers();

        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                String hola = "";
            }
        };
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                String hola = "";
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        loginButton = (LoginButton) findViewById(R.id.loginfb);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
        login = (Button) findViewById(R.id.btnlogin);
        regist = (TextView) findViewById(R.id.tvregister);
        clickAqui=(TextView) findViewById(R.id.tvClickAqui);
        mpass = (EditText) findViewById(R.id.logpass);
        mmail = (EditText) findViewById(R.id.logmail);


        //String at = AccessToken.getCurrentAccessToken().toString();
        /*if (AccessToken.getCurrentAccessToken() == null){
            startActivity(new Intent(getApplicationContext(), Probes.class));
        }*/
        if (mAuth.getCurrentUser() != null){
            //startActivity(new Intent(getApplicationContext(), Probes.class));
        }

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                startActivity(new Intent(getApplicationContext(), Registro.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mmail.getText().toString();
                epass = mpass.getText().toString();
                if (email.equals("") || epass.equals("")){
                    Toast.makeText(MainActivity.this, "Ingrese datos", Toast.LENGTH_SHORT).show();
                } else
                    login(email, epass);

            }
        });

        loginButton.setReadPermissions(Arrays.asList("email","public_profile","user_status","user_birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();

                String userID = loginResult.getAccessToken().getUserId();
                Set<String> perm = loginResult.getAccessToken().getPermissions();
                AccessToken accessToken = loginResult.getAccessToken();

                Set<String> p = AccessToken.getCurrentAccessToken().getPermissions();
                Set<String> d = AccessToken.getCurrentAccessToken().getDeclinedPermissions();



                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                String name = object.optString("name");
                                String mail = object.optString("email");
                                try {
                                    String data = object.getString("name");
                                    String mail2 = object.getString("email");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // Insert your code here
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
                //startActivity(new Intent(getApplicationContext(), Probes.class));
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Inicio de sesión cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Error Login", Toast.LENGTH_SHORT).show();
            }
        });
        /*logfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Probes.class));
            }
        });*/
        clickAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registro.class));
            }
        });
    }

    public void readusers(){
        Firebase userRef = mRef.child(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void login(String correo, final String contrasena){
        mAuth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                
                if (!task.isSuccessful()){
                    if (contrasena.length() < 6){
                        Toast.makeText(MainActivity.this, "Contraseña debe ser de mínimo 6 dígitos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Ingreso incorrecto, verifique su correo y contraseña", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    startActivity(new Intent(getApplicationContext(), Probes.class));
                }
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
