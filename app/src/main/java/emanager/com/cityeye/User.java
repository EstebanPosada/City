package emanager.com.cityeye;

/**
 * Created by ADMIN on 21/11/2016.
 */

public class User {

    private String id, name, phone, email, pass;

    public User(){

    }

    public User(String id, String name, String phone, String email, String pass) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
