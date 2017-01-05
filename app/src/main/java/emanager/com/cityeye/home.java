package emanager.com.cityeye;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment implements View.OnClickListener {

    RecyclerView rv;
    LinearLayoutManager layoutManager;
    View myView;
    ListView mlist;
    private ArrayList<String> myUsers = new ArrayList<>();
    public static final List<Denuncia> FOTOS = new ArrayList<>();
    public static List<Denuncias> myphoto = new ArrayList<>();

    private DatabaseReference myDatabase, my2;
    private FirebaseDatabase database;
    private StorageReference myStorage;
    private String fbcomplaints = "complaints";
    private String comps = "message", comps1 = "complaints", user = "users";;
    private ListView mlistview;
    private Double[] lat = {}, lon = {};

    private ImageView fotico;

    public home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_home, container, false);

        //myDatabase = FirebaseDatabase.getInstance().getReference();
        //myrootRef = new Firebase("https://e-manager-44914.firebaseio.com");
        database = FirebaseDatabase.getInstance();
        myDatabase = database.getReference(comps1);
        myStorage = FirebaseStorage.getInstance().getReference();
        myphoto = new ArrayList<>();
        final AdapterDen adp = new AdapterDen(myphoto);

        //fotico = (ImageView)  myView.findViewById(R.id.uriimage);


        myDatabase.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

                for (com.google.firebase.database.DataSnapshot compls : dataSnapshot.getChildren()){
                    String Fdesc = (String) compls.child("descripcion").getValue();
                    String Fimg = (String) compls.child("imageURL").getValue();
                    String[] Floc = {"","",""}, Fus = {"",""};
                    int n=0,g=0;
                    //String Flat = compls.child("location").getChildren()

                    //com.google.firebase.database.DataSnapshot loc = (com.google.firebase.database.DataSnapshot) compls.child("location").getChildren();
                    for (com.google.firebase.database.DataSnapshot loc : compls.child("location").getChildren()){
                        Floc[n++] = String.valueOf(loc.getValue());
                        String nf = "sdg";

                    }
                    for (com.google.firebase.database.DataSnapshot user : compls.child("users").getChildren()){
                        Fus[n++-3]= String.valueOf(user.getValue());
                    }
                    //StorageReference filepath = myStorage.child(fbcomplaints)/*.child(Fimg)*/;

                    StorageReference filepath = FirebaseStorage.getInstance().getReference();



                    //Glide.with(getActivity()).load(filepath).into(fotico);
                    String  fff = filepath.getDownloadUrl().toString();

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri myuri = uri;
                            //fotico.setImageURI(uri);
                            Toast.makeText(getActivity(), "Descarga exitosa", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           // Toast.makeText(getActivity(), "Descarga falló", Toast.LENGTH_SHORT).show();
                        }
                    });
                    myphoto.add(new Denuncias(Fdesc, Fimg, Floc[0], Floc[1], Floc[2], Fus[0], Fus[1]));


                }

                adp.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rv = (RecyclerView) myView.findViewById(R.id.recyclerh);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        //adp.notifyDataSetChanged();
        rv.setAdapter(adp);

        //AdapterProbe adatador = new AdapterProbe();
        //rv.setAdapter(adatador);

        return myView;
    }

    public static home newInstance(int i) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", i);
        home myhome = new home();
        myhome.setArguments(args);
        return myhome;
    }

    @Override
    public void onClick(View v) {

    }
}
