package emanager.com.cityeye;

/**
 * Created by ADMIN on 21/11/2016.
 */

public class User {

    private String id, nickname, cel, email, pass, document;

    public User(){

    }

    public User(String id, String nickname, String cel, String email, String pass, String document) {
        this.id = id;
        this.nickname = nickname;
        this.cel = cel;
        this.email = email;
        this.pass = pass;
        this.document = document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
