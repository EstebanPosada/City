package emanager.com.cityeye;

/**
 * Created by ADMIN on 02/12/2016.
 */

public class Denuncias {

    private String descripcion;
    private String imagen;
    private String latitud;
    private String longitud;
    private String lugar;
    private String nick;
    private String uid;

    public Denuncias(String descripcion, String imagen, String latitud, String longitud, String lugar, String nick, String uid) {
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.lugar = lugar;
        this.nick = nick;
        this.uid = uid;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getLugar() {
        return lugar;
    }

    public String getNick() {
        return nick;
    }

    public String getUid() {
        return uid;
    }
}
