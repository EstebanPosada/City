package emanager.com.cityeye;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Laura on 08/10/2016.
 */

public class AdapterProbe extends RecyclerView.Adapter<AdapterProbe.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        // Campos respectivos de un item
        public ImageView avatar;
        public TextView titulo;
        public ImageView imagen;
        public TextView noVisualizaciones;
        public TextView noLikes;
        public ImageView iconoFavorito;
        public TextView noComentarios;

        public ViewHolder (View v){
            super(v);
            avatar = (ImageView) v.findViewById(R.id.avatar);
            titulo = (TextView) v.findViewById(R.id.titulo_imagen);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            noVisualizaciones = (TextView) v.findViewById(R.id.noVisualizaciones);
            noLikes = (TextView) v.findViewById(R.id.noLikes);
            iconoFavorito = (ImageView) v.findViewById(R.id.iconoFavorito);
            noComentarios = (TextView) v.findViewById(R.id.noComentarios);
        }
    }

    @Override
    public int getItemCount() {
        return Denuncia.FOTOS.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_denuncia, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Denuncia itemActual = Denuncia.FOTOS.get(position);

        Glide.with(holder.avatar.getContext()).load(itemActual.getIdAvatarUsuario())
                .into(holder.avatar);
        holder.titulo.setText(itemActual.getTitulo());

        holder.noVisualizaciones.setText(itemActual.getNoVisualizaciones());

        Glide.with(holder.imagen.getContext()).load(itemActual.getIdImagen())
                .into(holder.imagen);
        holder.noVisualizaciones.setText(itemActual.getNoVisualizaciones());
        holder.noLikes.setText(String.valueOf(itemActual.getNoLikes()));

        int drawableFavorito = itemActual.isEsFavorita() ? R.drawable.favorito:
                R.drawable.no_favorito;
        holder.iconoFavorito.setImageResource(drawableFavorito);
        holder.noComentarios.setText(itemActual.getNoComentarios());

    }
}
