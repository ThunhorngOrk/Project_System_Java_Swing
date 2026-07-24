package Library_management_system.GUI;

import Library_management_system.Manager.BookManager;
import Library_management_system.Manager.BorrowManager;
import Library_management_system.Manager.StudentManager;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    // ✅ Shared Managers
    private BorrowManager borrowManager = new BorrowManager();
    private BookManager bookManager = new BookManager();
    private StudentManager studentManager = new StudentManager();

    private String userRole; // Explicitly tracks 'admin' or 'user'

    // Constructor accepts role from LoginForm
    public Dashboard(String role) {
        this.userRole = (role != null) ? role.toLowerCase() : "user";

        setTitle("Library Dashboard - (" + this.userRole.toUpperCase() + ")");
        setSize(400, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Standard auto-calculating grid layout to prevent empty space gaps for normal users
        setLayout(new GridLayout(0, 1, 10, 10));

        // ==========================================
        //  REGULAR USER BUTTONS (Hidden from admins)
        // ==========================================
        if (!"admin".equals(this.userRole)) {

            JButton searchBtn = new JButton("🔍 Search Books");
            add(searchBtn);
            searchBtn.addActionListener(e -> {
                setVisible(false);
                new SearchBookGUI(this, bookManager);
            });

            JButton viewBookBtn = new JButton("📖 View Books");
            add(viewBookBtn);
            viewBookBtn.addActionListener(e -> {
                setVisible(false);
                new ViewBookGUI(this, bookManager);
            });

            JButton borrowBtn = new JButton("📤 Borrow Book");
            add(borrowBtn);
            borrowBtn.addActionListener(e -> {
                setVisible(false);
                new BorrowGUI(this, borrowManager);
            });

            JButton returnBtn = new JButton("📥 Return Book");
            add(returnBtn);
            returnBtn.addActionListener(e -> {
                setVisible(false);
                new ReturnGUI(this, borrowManager);
            });
        }

        // ==========================================
        //  ADMIN ONLY BUTTONS (Hidden from normal users)
        // ==========================================
        if ("admin".equals(this.userRole)) {

            JButton bookBtn = new JButton("📚 Manage Books (Admin Only)");
            add(bookBtn);
            bookBtn.addActionListener(e -> {
                setVisible(false);
                new BookGUI(this, bookManager);
            });

            JButton studentBtn = new JButton("🛠️ Manage Students/Members (Admin Only)");
            add(studentBtn);
            studentBtn.addActionListener(e -> {
                setVisible(false);
                new StudentGUI(this, studentManager);
            });

            // Inside Dashboard.java (under the Admin Only section)
            JButton viewUsersBtn = new JButton("👥 View Registered Users (Admin Only)");
            add(viewUsersBtn);
            viewUsersBtn.addActionListener(e -> {
                setVisible(false);
                new ViewStudentGUI(this, studentManager);
            });

            JButton reportBtn = new JButton("📊 View System Reports (Admin Only)");
            add(reportBtn);
            reportBtn.addActionListener(e -> {
                setVisible(false);
                new ReportGUI(this, bookManager, borrowManager);
            });
        }

        // ==========================================
        //  LOGOUT BUTTON (All Roles)
        // ==========================================
        JButton logoutBtn = new JButton("🚪 Logout");
        logoutBtn.setBackground(Color.RED);
        logoutBtn.setForeground(Color.WHITE);
        add(logoutBtn);

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true); // Return back to clean login pane
        });
    }

    public void showMenu() {
        setVisible(true);
    }
}