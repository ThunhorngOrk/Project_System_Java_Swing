package Library_management_system.GUI;

import Library_management_system.Manager.UserManager;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginForm() {

        setTitle("Library Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        add(btnLogin);
        add(btnRegister);

        // LOGIN
        btnLogin.addActionListener(e -> {

            String username = txtUsername.getText().trim();
            String password =
                    new String(txtPassword.getPassword()).trim();

            UserManager manager = new UserManager();

            String role =
                    manager.login(username, password);

            if (role != null) {

                dispose();
                new Dashboard(role).setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Username or Password!"
                );
            }
        });

        // OPEN REGISTER FORM
        btnRegister.addActionListener(e -> {

            dispose();
            new RegisterForm();
        });
    }
}