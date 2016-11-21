package emanager.com.cityeye;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {

    RecyclerView rv;
    LinearLayoutManager layoutManager;
    View myView;

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

        rv = (RecyclerView) myView.findViewById(R.id.recycler2);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        AdapterProbe adatador = new AdapterProbe();
        rv.setAdapter(adatador);

        return myView;

    }

}
