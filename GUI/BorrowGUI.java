package Library_management_system.GUI;

import Library_management_system.Manager.BorrowManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BorrowGUI {

    private BorrowManager manager;
    private DefaultTableModel model;

    private JFrame frame;
    private Dashboard dashboard;

    public BorrowGUI(Dashboard dashboard, BorrowManager manager) {
        this.dashboard = dashboard;
        this.manager = manager;

        frame = new JFrame("Borrow Book");
        frame.setSize(800, 500);
        frame.setLayout(new GridLayout(1, 2, 10, 10));
        frame.setLocationRelativeTo(null);

        // ===== LEFT PANEL =====
        JPanel left = new JPanel(null);
        left.setBorder(BorderFactory.createTitledBorder("Borrow Form"));

        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(20, 50, 150, 25);
        left.add(userLbl);

        JTextField userField = new JTextField();
        userField.setBounds(20, 80, 300, 30);
        left.add(userField);

        JLabel bookLbl = new JLabel("Book Title:");
        bookLbl.setBounds(20, 140, 150, 25);
        left.add(bookLbl);

        JTextField bookField = new JTextField();
        bookField.setBounds(20, 170, 300, 30);
        left.add(bookField);

        JButton borrowBtn = new JButton("Borrow Book");
        borrowBtn.setBounds(20, 230, 300, 40);
        left.add(borrowBtn);

        JButton backBtn = new JButton(" Back ");
        backBtn.setBounds(20, 300, 300, 40);
        left.add(backBtn);

        // ===== RIGHT PANEL =====
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Currently Borrowed"));

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Username", "Book Title", "Borrow Date", "Status"});

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        right.add(scroll, BorderLayout.CENTER);

        // ===== BORROW ACTION =====
        borrowBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String bookTitle = bookField.getText().trim();

            if (username.isEmpty() || bookTitle.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in both fields!");
                return;
            }

            String result = manager.borrowBook(username, bookTitle);

            if ("SUCCESS".equals(result)) {
                JOptionPane.showMessageDialog(frame, "Book Borrowed Successfully!");
                userField.setText("");
                bookField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, result);
            }

            refreshTable();
        });

        // ===== BACK =====
        backBtn.addActionListener(e -> {
            frame.dispose();
            dashboard.setVisible(true);
        });

        frame.add(left);
        frame.add(right);

        refreshTable();
        frame.setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);

        for (String[] row : manager.getAllBorrows()) {
            model.addRow(row);
        }
    }
}
