package no.ntnu.idatt1002.demo.model.objects;

public class User {

    private int userId;

    private String name;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }


}
