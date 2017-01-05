package emanager.com.cityeye;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laura on 08/10/2016.
 */

public class Denuncia {


    private String noVisualizaciones;
    private String noLikes;
    private String noComentarios;
    private int idImagen;
    private boolean esFavorita;
    private String titulo;
    private String usuario;
    private String tiempoDeExistencia;
    private int idAvatarUsuario;

    public Denuncia(String noVisualizaciones, String noLikes, String noComentarios,
                    int idImagen, boolean esFavorita, String titulo, String usuario,
                    String tiempoDeExistencia, int idAvatarUsuario) {
        this.noVisualizaciones = noVisualizaciones;
        this.noLikes = noLikes;
        this.noComentarios = noComentarios;
        this.idImagen = idImagen;
        this.esFavorita = esFavorita;
        this.titulo = titulo;
        this.usuario = usuario;
        this.tiempoDeExistencia = tiempoDeExistencia;
        this.idAvatarUsuario = idAvatarUsuario;
    }

    public static final List<Denuncia> FOTOS = new ArrayList<>();

    static {
        FOTOS.add(new Denuncia("4323", "456", "12", R.drawable.ciudad, true,
                "jmejia1221", "Carla Veradio", "2 meses atrás", R.drawable.avatar1));
        FOTOS.add(new Denuncia("2023", "156", "23", R.drawable.camara_ladron, false,
                "juan123", "Julio Perez", "2 meses atrás", R.drawable.avatar2));
        FOTOS.add(new Denuncia("3455", "879", "1", R.drawable.ciudad, true,
                "La chismosa", "Milena Merlano", "2 meses atrás", R.drawable.avatar3));
        FOTOS.add(new Denuncia("1290", "123", "34", R.drawable.camara_ladron, false,
                "Mi vecina", "Julia P.", "2 meses atrás", R.drawable.avatar4));
        FOTOS.add(new Denuncia("2319", "900", "23", R.drawable.foto5, false,
                "El tendero", "Vicentico Renet", "2 meses atrás", R.drawable.avatar5));
        FOTOS.add(new Denuncia("1550", "345", "9", R.drawable.ciudad, false,
                "Vecina nueva", "Carla Veradio", "2 meses atrás", R.drawable.avatar1));
        FOTOS.add(new Denuncia("1323", "401", "20", R.drawable.foto7, true,
                "Fuerza natural", "Julio Perez", "2 meses atrás", R.drawable.avatar2));
    }

    public String getNoVisualizaciones() {
        return noVisualizaciones;
    }

    public String getNoLikes() {
        return noLikes;
    }

    public String getNoComentarios() {
        return noComentarios;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public boolean isEsFavorita() {
        return esFavorita;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTiempoDeExistencia() {
        return tiempoDeExistencia;
    }

    public int getIdAvatarUsuario() {
        return idAvatarUsuario;
    }
}

