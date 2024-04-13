package main;

// import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import static util.Constant.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {
    // attributes
    private GameFrame gameFrame;
    private String currentUser;
    // constructor
    public Login() {
        // set up the frame
        setSize(FRAM_WIDTH, FRAM_HEIGHT);
        setTitle(FRAM_TITLE);
        setLocation(FRAM_X, FRAM_Y);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up the panel
        // JPanel jp = new JPanel();
        setLayout(null);
        setBounds(0, 0, FRAM_WIDTH, FRAM_HEIGHT);
        // setBorder(new EmptyBorder(5, 5, 5, 5));

        // set up the background
        JLabel backgroundLabel = new JLabel();
        ImageIcon backgroundIcon = new ImageIcon("./img/loginbackground.png");
        backgroundLabel.setIcon(backgroundIcon);
        backgroundLabel.setBounds(0, 0, FRAM_WIDTH, FRAM_HEIGHT);
        add(backgroundLabel, -1);

        // set up the layout
        JLabel userName = new JLabel("User Name: ");
        userName.setBounds(150, 140, 70, 30);
        add(userName, 0);

        JLabel password = new JLabel("Password: ");
        password.setBounds(150, 180, 70, 30);
        add(password, 0);

        JTextField userNameField = new JTextField();
        userNameField.setBounds(225, 140, 180, 30);
        add(userNameField, 0);
        JTextField passwordField = new JTextField();
        passwordField.setBounds(225, 180, 180, 30);
        add(passwordField, 0);

        JButton loginButton = new JButton("Log In");
        JButton registerButton = new JButton("Register");

        add(loginButton, 0);
        add(registerButton, 0);
        loginButton.setBounds(220, 240, 90, 30);
        registerButton.setBounds(320, 240, 90, 30);
        // add button listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            // method to register a user
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String password = passwordField.getText();
                // check if the username and password are empty
                if (userName.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both username and password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // JDBC drive name and database URL
                final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
                Connection conn = null;
                PreparedStatement pstmt = null;
                // register the user
                try {
                    try {
                        Class.forName(JDBC_DRIVER);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    // connect to database
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/applyjobs", "root", "mysql");
                    String query = "INSERT INTO user (email_address, password, scores) VALUES (?, ?, ?)";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, userName);
                    pstmt.setString(2, password);
                    pstmt.setString(3, "0");
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "User registered successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    pstmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to register user", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // add login button listeners
        loginButton.addActionListener(e -> {
            // get the username and password
            String inputUsername = userNameField.getText();
            String inputPassword = passwordField.getText();
            currentUser = inputUsername;
            // check if the username and password are empty
            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both username and password", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // JDBC drive name and database URL
            try {
                // connect to database and check if the username and password match
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/applyjobs", "root", "mysql");
                String query = "SELECT * FROM user WHERE email_address=? AND password=?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, inputUsername);
                pstmt.setString(2, inputPassword);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // username and password match
                    JOptionPane.showMessageDialog(null, "Login successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    gameFrame = new GameFrame(currentUser);
                } else {
                    // username and password do not match
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                // close the connection
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to connect to database", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });

        // add(jp);

        setVisible(true);

    }
    public String getCurrentUser() {
        return currentUser;
    }

}
