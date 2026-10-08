package TCP;

import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 20170711L;

    int id;
    String code;
    String name;
    String dayOfBirth;
    String userName;

    public Customer(int id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
