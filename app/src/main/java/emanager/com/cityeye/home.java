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
public class home extends Fragment implements View.OnClickListener {

    RecyclerView rv;
    LinearLayoutManager layoutManager;
    View myView;

    public home() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_home, container, false);

        rv = (RecyclerView) myView.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        AdapterProbe adatador = new AdapterProbe();
        rv.setAdapter(adatador);

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
