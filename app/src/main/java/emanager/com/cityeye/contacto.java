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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class contacto extends Fragment {

    RecyclerView rv;
    LinearLayoutManager layoutManager;
    Button btn;
    View myView;
    private List<Denuncias> mio = new ArrayList<>();

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

        mio = new ArrayList<>();
        final AdapterDen adp = new AdapterDen(mio);

        String thisUser = "";
        for (int j = 0; j < home.myphoto.size(); j++) {
            String d = home.myphoto.get(j).getDescripcion().trim(), l = home.myphoto.get(j).getLugar().trim(), n = home.myphoto.get(j).getNick().trim();
            if (thisUser.equals(d) || thisUser.equals(l) || thisUser.equals(n))
                mio.add(home.myphoto.get(j));
        }

        rv = (RecyclerView) myView.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        //AdapterProbe adatador = new AdapterProbe();
        rv.setAdapter(adp);

        return myView;
    }

}
