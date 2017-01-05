package emanager.com.cityeye;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class camera extends Fragment implements LocationListener {

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private static String provider;
    private static Double latitude = 6.2686734, longitude = -75.6664331;
    protected boolean gps_en, net_en;

    ImageView imagen, fotoFormulario;
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
    private StorageReference myStorage;
    private FirebaseDatabase database;
    private ProgressDialog myprogress;
    private Uri fotouri;
    private StorageTask upload;
    private File imgfile;

    private String user = "users", celphone = "cel", doc = "document", mail = "email", fbcomplaints = "complaints";
    private String fbdesc = "descripcion", fbim = "imageURL", fbloc = "location", fblat = "latitude", fblon = "longitude", fblugar = "lugar";
    private String fbuser = "user", fbnick = "nickname", fbuid = "uid";
    private static String MEDIA_DIRECTORY = "CityEye";
    private String name = "", DirImagen;
    private int TAKE_PICTURE = 123;


    public camera() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_camera, container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(fbcomplaints);
        myStorage = FirebaseStorage.getInstance().getReference();
        myprogress = new ProgressDialog(getActivity());

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getActivity(), "Permisos de GPS desactivados", Toast.LENGTH_SHORT).show();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

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
        //sharebutton = (ShareButton) myView.findViewById(R.id.share_btn);

        myAdapter = ArrayAdapter.createFromResource(formulario.getContext(), R.array.tipo_denuncia_array, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoDenuncia.setAdapter(myAdapter);

        myAdapter2 = ArrayAdapter.createFromResource(formulario.getContext(), R.array.ubicacion_denuncias, android.R.layout.simple_spinner_item);
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ubicacionDenuncia.setAdapter(myAdapter2);

        formulario.setVisibility(View.GONE);


        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
                boolean isDirectoryCreated = file.exists();

                if(!isDirectoryCreated)
                    isDirectoryCreated = file.mkdirs();

                if(isDirectoryCreated) {
                    Long timestamp = System.currentTimeMillis() / 1000;
                    String imageName = timestamp.toString() + ".jpg";

                    name = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                            + File.separator + imageName;

                    File newFile = new File(name);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
                    startActivityForResult(intent, TAKE_PICTURE);
                }

                /*Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                startActivityForResult(intent,2);*/

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
                formulario.setVisibility(View.GONE);
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
                } else if (tipoden.equals(getActivity().getResources().getStringArray(R.array.tipo_denuncia_array)[0])) {
                    Toast.makeText(getActivity(), "Seleccionar tipo de denuncia", Toast.LENGTH_SHORT).show();
                } else if (ubicacion.equals(getActivity().getResources().getStringArray(R.array.ubicacion_denuncias)[0])){
                    Toast.makeText(getActivity(), "Seleccionar Ubicación", Toast.LENGTH_SHORT).show();
                }
                else {

                    myprogress.setMessage("Subiendo información...");
                    myprogress.show();

                    final String key = myRef.child(user).push().getKey();

                    StorageReference filepath = myStorage.child(fbcomplaints).child(descrip);
                    filepath.putFile(fotouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String ruta = taskSnapshot.getDownloadUrl().toString();
                            myRef.child(tipoden).child(key).child(fbdesc).setValue(descrip);
                            myRef.child(tipoden).child(key).child(fbim).setValue(ruta);
                            myRef.child(tipoden).child(key).child(fbloc).child(fblat).setValue(latitude);
                            myRef.child(tipoden).child(key).child(fbloc).child(fblon).setValue(longitude);
                            myRef.child(tipoden).child(key).child(fbloc).child(fblugar).setValue(ubicacion);
                            myRef.child(tipoden).child(key).child(user).child(fbnick).setValue("nick");
                            myRef.child(tipoden).child(key).child(user).child(fbuid).setValue("uid");

                            Toast.makeText(getActivity(), "Foto subida", Toast.LENGTH_SHORT).show();
                            myprogress.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            myprogress.dismiss();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    formulario.setVisibility(View.GONE);
                    camara.setVisibility(View.VISIBLE);
                    foto.setVisibility(View.VISIBLE);
                }
            }
        });
        /*sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postpic(bm);
            }
        });*/

        return myView;
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getActivity(), "GPS activado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "GPS desactivado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(requestCode == TAKE_PICTURE)
                if (resultCode == -1) {
                    MediaScannerConnection.scanFile(getActivity(),
                            new String[]{name}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                    DirImagen = uri.toString();
                                    fotouri = Uri.parse(DirImagen);
                                }
                            });
                    Bitmap bitmap = BitmapFactory.decodeFile(name);
                    fotoFormulario.setImageBitmap(bitmap);
                }

        } catch (Exception e){
            e.printStackTrace();
        }
        descripcion.setText("");
        formulario.setVisibility(View.VISIBLE);
        tipoDenuncia.setAdapter(myAdapter);
        ubicacionDenuncia.setAdapter(myAdapter2);
        foto.setVisibility(View.GONE);
        camara.setVisibility(View.GONE);

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
