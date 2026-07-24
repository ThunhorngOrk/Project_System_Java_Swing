package Library_management_system.Main;

import Library_management_system.GUI.LoginForm;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Run on the Swing Event Dispatch Thread (best practice for Swing apps)
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }
}