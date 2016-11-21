package emanager.com.cityeye;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class camera extends Fragment {

    ImageView imagen,fotoFormulario;
    Button foto, cancelar, enviar;
    View myView;
    Bitmap bm;
    LinearLayout formulario;
    TextView camara;
    EditText descripcion;
    Spinner tipoDenuncia, ubicacionDenuncia;
    ArrayAdapter<CharSequence> myAdapter;
    ArrayAdapter<CharSequence> myAdapter2;
    ShareButton sharebutton;
    private String tipoden, ubicacion, descrip;
    private SwitchCompat switchCompat;
    private boolean publica = false;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private String user = "users", celphone = "cel", doc = "document", mail = "email", fbcomplaints = "complaints";
    private String fbdesc = "descripcion", fbim = "imageURL", fbloc = "location", fblat = "latitude", fblon = "longitude", fblugar = "lugar";
    private String fbuser = "user", fbnick = "nickname", fbuid = "uid";


    public camera() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView=inflater.inflate(R.layout.fragment_camera, container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(fbcomplaints);
        formulario=(LinearLayout) myView.findViewById(R.id.llFormulario);
        fotoFormulario=(ImageView) myView.findViewById(R.id.ivFoto_Formulario);
        foto=(Button) myView.findViewById(R.id.btnFoto);
        cancelar=(Button) myView.findViewById(R.id.btncancelar);
        enviar = (Button) myView.findViewById(R.id.btnenviar);
        camara=(TextView) myView.findViewById(R.id.tvCamara);
        descripcion=(EditText) myView.findViewById(R.id.etDescripcion);
        tipoDenuncia=(Spinner) myView.findViewById(R.id.spTipoDenuncia);
        ubicacionDenuncia=(Spinner) myView.findViewById(R.id.spUbicacionDenuncia);
        switchCompat = (SwitchCompat) myView.findViewById(R.id.sdenunciaPublica);
        sharebutton = (ShareButton) myView.findViewById(R.id.share_btn);

        myAdapter = ArrayAdapter.createFromResource(formulario.getContext(), R.array.tipo_denuncia_array, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoDenuncia.setAdapter(myAdapter);

        myAdapter2 = ArrayAdapter.createFromResource(formulario.getContext(), R.array.ubicacion_denuncias, android.R.layout.simple_spinner_item);
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ubicacionDenuncia.setAdapter(myAdapter2);

        formulario.setVisibility(View.INVISIBLE);


        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,01);

            }
        });
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchCompat.isChecked()){
                    publica = true;
                } else
                    publica = false;
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formulario.setVisibility(View.INVISIBLE);
                camara.setVisibility(View.VISIBLE);
                foto.setVisibility(View.VISIBLE);
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descrip = descripcion.getText().toString();
                if (descrip.equals("")){
                    Toast.makeText(getActivity(), "Introducir descripción", Toast.LENGTH_SHORT).show();
                } else {
                    String key = myRef.child(user).push().getKey();
                    myRef.child(tipoden).child(key).child(fbdesc).setValue(descrip);
                    myRef.child(tipoden).child(key).child(fbim).setValue("imagen");
                    myRef.child(tipoden).child(key).child(fbloc).child(fblat).setValue("6");
                    myRef.child(tipoden).child(key).child(fbloc).child(fblon).setValue("-75");
                    myRef.child(tipoden).child(key).child(fbloc).child(fblugar).setValue(ubicacion);
                    myRef.child(tipoden).child(key).child(user).child(fbnick).setValue("nick");
                    myRef.child(tipoden).child(key).child(user).child(fbuid).setValue("uid");
                }
            }
        });
        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postpic(bm);
            }
        });

        return myView;
    }



   /* public void createFormularioDialog(){

        AlertDialog.Builder builder=  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v= inflater.inflate(R.layout.formulario,null);

        //aqui tienes que inicializar las variables de la vista del alertdialog
        //ImageView foto_descripcion=(ImageView) myView.findViewById(R.id.ivFoto_Formulario);
        ImageView foto_descripcion=(ImageView) v.findViewById(R.id.ivFoto_Formulario);
        foto_descripcion.setImageBitmap(bm);

        builder.setView(v);


        builder.create().show();


    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bm=(Bitmap) data.getExtras().get("data");
        descripcion.setText("");
        formulario.setVisibility(View.VISIBLE);
        tipoDenuncia.setAdapter(myAdapter);
        ubicacionDenuncia.setAdapter(myAdapter2);
        fotoFormulario.setImageBitmap(bm);
        foto.setVisibility(View.INVISIBLE);
        camara.setVisibility(View.INVISIBLE);

        tipoDenuncia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
                tipoden = tipoDenuncia.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(),"Por favor seleccione un tipo de denuncia",LENGTH_SHORT).show();

            }
        });

        ubicacionDenuncia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                ubicacion = ubicacionDenuncia.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(),"Por favor seleccione una ubicación",LENGTH_SHORT).show();
            }
        });



    }
    public void postpic(final Bitmap foto){
        //myView.setDrawingCacheEnabled(true);
        //myView.destroyDrawingCache();
        AlertDialog.Builder shareDialog = new AlertDialog.Builder(getActivity());
        shareDialog.setTitle(getActivity().getResources().getString(R.string.share_comp));
        shareDialog.setMessage(getActivity().getResources().getString(R.string.share_msg));
        shareDialog.setPositiveButton(getActivity().getResources().getString(R.string.acepta), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharePhoto photo = new SharePhoto.Builder().setBitmap(foto).build();
                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                sharebutton.setShareContent(content);
                sharebutton.performClick();
            }
        });
        shareDialog.setNegativeButton(getActivity().getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        shareDialog.show();

    }

    public static camera newInstance(int i) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", i);
        camera mycamera = new camera();
        mycamera.setArguments(args);
        return mycamera;
    }
}
