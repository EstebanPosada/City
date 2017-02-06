package emanager.com.cityeye;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 02/12/2016.
 */

public class AdapterDen extends RecyclerView.Adapter<AdapterDen.ViewHolder> {

    private List<Denuncias> ls = new ArrayList<>();

    public AdapterDen(List<Denuncias> lista) {
        this.ls = lista;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView desc;
        public TextView stimage;
        public TextView lati;
        public TextView longi;
        public TextView luga;
        public TextView user;
        public TextView uid;

        public ViewHolder(View itemView) {
            super(itemView);
            desc = (TextView) itemView.findViewById(R.id.descden);
            stimage = (TextView) itemView.findViewById(R.id.imagenden);
            user = (TextView) itemView.findViewById(R.id.tit_nick);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_denuncias, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.desc.setText(ls.get(position).getDescripcion());
        holder.stimage.setText(ls.get(position).getImagen());
        holder.user.setText(ls.get(position).getNick());

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }
}
