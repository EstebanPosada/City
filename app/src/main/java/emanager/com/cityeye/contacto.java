package emanager.com.cityeye;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class contacto extends Fragment {

    RecyclerView rv;
    LinearLayoutManager layoutManager;
    Button btn;
    View myView;

    public contacto() {
        // Required empty public constructor
    }

    public static contacto newInstance(int i) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", i);
        contacto mycontacto = new contacto();
        mycontacto.setArguments(args);
        return mycontacto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_contacto, container, false);

        btn = (Button) myView.findViewById(R.id.idedit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Editar", Toast.LENGTH_SHORT).show();
            }
        });

        /*rv = (RecyclerView) myView.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        AdapterProbe adatador = new AdapterProbe();
        rv.setAdapter(adatador);*/

        return myView;
    }

}
