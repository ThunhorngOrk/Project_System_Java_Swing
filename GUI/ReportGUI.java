package Library_management_system.GUI;

import Library_management_system.Manager.BookManager;
import Library_management_system.Manager.BorrowManager;

import javax.swing.*;
import java.awt.*;

public class ReportGUI {

    private JFrame frame;
    private Dashboard dashboard;

    private BookManager bookManager;
    private BorrowManager borrowManager;

    public ReportGUI(Dashboard dashboard,
                     BookManager bookManager,
                     BorrowManager borrowManager) {

        this.dashboard = dashboard;
        this.bookManager = bookManager;
        this.borrowManager = borrowManager;

        frame = new JFrame("View Reports");
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(6, 1, 10, 10));
        frame.setLocationRelativeTo(null);

        // ===== TITLE =====
        JLabel title = new JLabel("📊 Library Reports", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(title);

        // ===== REPORT DATA =====
        int totalBooks = bookManager.getAllBooks().size();
        int totalBorrowed = borrowManager.getAllBorrows().size();
        int totalAvailable = bookManager.getTotalAvailableCopies();

        JLabel bookLabel = new JLabel("Total Book Titles: " + totalBooks);
        JLabel borrowLabel = new JLabel("Currently Borrowed: " + totalBorrowed);
        JLabel availableLabel = new JLabel("Available Copies: " + totalAvailable);

        frame.add(bookLabel);
        frame.add(borrowLabel);
        frame.add(availableLabel);

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton(" Back ");
        frame.add(backBtn);

        backBtn.addActionListener(e -> {
            frame.dispose();
            dashboard.setVisible(true);
        });

        frame.setVisible(true);
    }
}