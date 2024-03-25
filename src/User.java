import java.io.FileWriter;
import java.io.IOException;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static void addUser(String username, String password) {
        try {
            FileWriter myWriter = new FileWriter("userLogin.txt", true);
            myWriter.write(username + " " + password + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.\n");
        }

    }

}
