import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserGUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox isNewUserCheckBox;
    public boolean newUser = false;
    private ArrayList<User> Users = new ArrayList<>();
    public UserGUI() {
        frame = new JFrame("User Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createEmptyBorder(0, 100, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        isNewUserCheckBox = new JCheckBox("New User");
        isNewUserCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("   Login  ");
        loginButton.setPreferredSize(new Dimension(130, 40));

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel1.add(isNewUserCheckBox);
        panel1.add(new JLabel()); // Empty label for spacing
        panel1.add(loginButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(panel1, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        isNewUserCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isNewUserCheckBox.isSelected())
                    loginButton.setText("Register");
                else
                    loginButton.setText("   Login  ");
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isNewUserCheckBox.isSelected()) {
                    registerUser();
                } else {
                    performLogin();
                };
            }
        });

        try {
            File myObj = new File("userLogin.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] logins = data.split(" ");
                User user = new User(logins[0], logins[1]);
                Users.add(user);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading from file.\n");
        }
    }

    private void performLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String enteredPassword = new String(password);
        boolean login = false;
        for(User user : Users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(enteredPassword)) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                login = true;
                break;
            }
        }
        if(login) {
            frame.dispose();
            CustomerGUI customerGUI = new CustomerGUI(Main.productList, username, newUser);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.");
        }
        passwordField.setText("");
    }

    private void registerUser() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String enteredPassword = new String(password);

        boolean exists = false;
        for(User user : Users) {
            if(user.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(frame, "Username already exists!");
                exists = true;
                break;
            }
        }

        if(!exists) {
            User.addUser(username, enteredPassword);
            User user = new User(username, enteredPassword);
            Users.add(user);
            JOptionPane.showMessageDialog(frame, "Successfully created the user!");
            newUser = true;
            performLogin();
        }
    }
}
