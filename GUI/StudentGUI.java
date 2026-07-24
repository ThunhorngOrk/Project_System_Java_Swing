package Library_management_system.GUI;

import Library_management_system.Manager.StudentManager;
import Library_management_system.Model.User;

import javax.swing.*;
import java.awt.*;

public class StudentGUI extends JFrame {

    private Dashboard dashboard;
    private StudentManager studentManager;

    private JTextField txtId;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtPassword;

    public StudentGUI(Dashboard dashboard, StudentManager studentManager) {

        this.dashboard = dashboard;
        this.studentManager = studentManager;

        setTitle("Manage Students / Users");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // ================= FORM =================
        JPanel formPanel = new JPanel(new GridLayout(4,2,10,10));

        formPanel.add(new JLabel("User ID (for update/delete):"));
        txtId = new JTextField();
        formPanel.add(txtId);

        formPanel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Password:"));
        txtPassword = new JTextField();
        formPanel.add(txtPassword);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // ================= BUTTONS =================
        JPanel buttonPanel = new JPanel(new GridLayout(4,1,0,10));

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);

        // ================= ADD =================
        addBtn.addActionListener(e -> {

            try {

                String username = txtUsername.getText().trim();
                String email = txtEmail.getText().trim();
                String password = txtPassword.getText().trim();

                if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please fill all fields!"
                    );
                    return;
                }

                // id is ignored on insert (auto-increment); role defaults to "user"
                boolean added = studentManager.addStudent(
                        new User(0, username, email, password, "user")
                );

                if (added) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Student/User Added Successfully!"
                    );
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to add user (username/email may already exist)."
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Input!"
                );
            }
        });

        // ================= UPDATE =================
        updateBtn.addActionListener(e -> {

            try {

                int id = Integer.parseInt(txtId.getText().trim());

                String username = txtUsername.getText().trim();
                String email = txtEmail.getText().trim();
                String password = txtPassword.getText().trim();

                boolean updated = studentManager.updateStudent(
                        id,
                        username,
                        email,
                        password
                );

                if (updated) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Student Updated!"
                    );
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "No user found with that ID."
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Update Failed! Make sure User ID is a valid number."
                );
            }
        });

        // ================= DELETE =================
        deleteBtn.addActionListener(e -> {

            try {

                int id = Integer.parseInt(txtId.getText().trim());

                int confirm =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Delete this student?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION
                        );

                if(confirm == JOptionPane.YES_OPTION) {

                    studentManager.deleteStudent(id);

                    JOptionPane.showMessageDialog(
                            this,
                            "Student Deleted!"
                    );

                    clearFields();
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid ID!"
                );
            }
        });

        // ================= BACK =================
        backBtn.addActionListener(e -> {

            dispose();

            if(dashboard != null) {
                dashboard.showMenu();
            }
        });

        setVisible(true);
    }

    // ================= CLEAR =================
    private void clearFields() {

        txtId.setText("");
        txtUsername.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }
}