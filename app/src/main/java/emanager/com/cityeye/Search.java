package emanager.com.cityeye;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {

    RecyclerView rv;
    LinearLayoutManager layoutManager;
    View myView;
    ImageButton btnsearch;
    EditText eds;
    private List<Denuncias> mysearch = new ArrayList<>();

    public Search() {
        // Required empty public constructor
    }

    public static Search newInstance(int i) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", i);
        Search mysearch = new Search();
        mysearch.setArguments(args);
        return mysearch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_search, container, false);

        eds = (EditText) myView.findViewById(R.id.edsearch);
        btnsearch = (ImageButton) myView.findViewById(R.id.lupa);

        mysearch = new ArrayList<>();
        final AdapterDen adp = new AdapterDen(mysearch);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busqueda = eds.getText().toString().trim();
                if (busqueda.isEmpty()){
                    Toast.makeText(getActivity(), "Ingrese nombre a buscar", Toast.LENGTH_SHORT).show();
                } else {
                    mysearch.clear();
                    for (int j = 0; j < home.myphoto.size(); j++) {
                        String d = home.myphoto.get(j).getDescripcion().trim(), l = home.myphoto.get(j).getLugar().trim(), n = home.myphoto.get(j).getNick().trim();
                        if (busqueda.equals(d) || busqueda.equals(l) || busqueda.equals(n))
                            mysearch.add(home.myphoto.get(j));
                    }
                    adp.notifyDataSetChanged();
                }

            }
        });

        rv = (RecyclerView) myView.findViewById(R.id.recycler2);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);



        //AdapterProbe adatador = new AdapterProbe();
        rv.setAdapter(adp);

        return myView;

    }

}
