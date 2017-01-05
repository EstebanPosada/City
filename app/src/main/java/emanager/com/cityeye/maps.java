package emanager.com.cityeye;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class maps extends Fragment implements OnMapReadyCallback{

    private GoogleMap mapa;
    private View Myview;
    private MapView mapView;
    //private static List<Denuncias> myphoto = new ArrayList<>();



    public maps() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Myview = inflater.inflate(R.layout.fragment_maps, container, false);
        mapView = (MapView) Myview.findViewById(R.id.myMapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e){
            e.printStackTrace();
        }
        mapView.getMapAsync(this);
        //agregarFAB();




        return Myview;
    }

    public static maps newInstance(int i) {

        Bundle args = new Bundle();
        args.putInt("argsInstance", i);
        maps mymap = new maps();
        mymap.setArguments(args);
        return mymap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.2686734,-75.6664331), 8));
        for (int i = 0; i<home.myphoto.size(); i++){
            mapa.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.valueOf(home.myphoto.get(i).getLatitud()),Double.valueOf(home.myphoto.get(i).getLongitud())))
                    .title(home.myphoto.get(i).getDescripcion()).snippet(home.myphoto.get(i).getLugar()));
        }

    }

    public void createMultipleListDialog(){

       /* AlertDialog.Builder builder=  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v= inflater.inflate(R.layout.tipo_denuncias,null);

        //aqui tienes que inicializar las variables de la vista del alertdialog
        //ImageView foto_descripcion=(ImageView) myView.findViewById(R.id.ivFoto_Formulario);
        //ImageView foto_descripcion=(ImageView) v.findViewById(R.id.ivFoto_Formulario);
        //foto_descripcion.setImageBitmap(bm);

        builder.setView(v);


        builder.create().show();*/


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final ArrayList itemsSeleccionados = new ArrayList();

        builder.setTitle(getActivity().getResources().getStringArray(R.array.tipo_denuncia_array)[0])
                .setMultiChoiceItems(getActivity().getResources().getStringArray(R.array.tipo_denuncia_array), null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // Guardar indice seleccionado
                            itemsSeleccionados.add(which);
                            Toast.makeText(
                                    getActivity(),
                                    "Checks seleccionados:(" + itemsSeleccionados.size() + ")",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else if (itemsSeleccionados.contains(which)) {
                            // Remover indice sin selección
                            itemsSeleccionados.remove(Integer.valueOf(which));
                        }
                    }
                }).setPositiveButton(getActivity().getResources().getString(R.string.send), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Holita", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();


    }

    /*public void agregarFAB (){
       FloatingActionButton miFAB= (FloatingActionButton) Myview.findViewById(R.id.fabMiFAB);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),getResources().getString(R.string.olvidaste_contraseña),Toast.LENGTH_SHORT).show();
                createMultipleListDialog();
            }
        });
    }*/
}
