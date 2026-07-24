package Library_management_system.GUI;

import Library_management_system.Manager.UserManager;

import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {

    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirm;
    private JCheckBox chkIsAdmin;

    public RegisterForm() {

        setTitle("User Registration");
        setSize(350, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel("Confirm Password:"));
        txtConfirm = new JPasswordField();
        add(txtConfirm);

        add(new JLabel("Register as Admin:"));
        chkIsAdmin = new JCheckBox();
        add(chkIsAdmin);

        JButton btnRegister = new JButton("Register");
        JButton btnBack = new JButton("Back");

        add(btnRegister);
        add(btnBack);

        btnRegister.addActionListener(e -> {

            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirm = new String(txtConfirm.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            if (!email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
                return;
            }

            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            String role = chkIsAdmin.isSelected() ? "admin" : "user";

            UserManager manager = new UserManager();

            if (manager.register(username, email, password, role)) {

                JOptionPane.showMessageDialog(this,
                        "Account Created Successfully as " + role.toUpperCase() + "!");

                dispose();
                new LoginForm().setVisible(true);

            } else {

                JOptionPane.showMessageDialog(this,
                        "Username or email already exists!");
            }
        });

        btnBack.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        setVisible(true);
    }
}