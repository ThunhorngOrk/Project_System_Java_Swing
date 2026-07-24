package Library_management_system.GUI;

import Library_management_system.Manager.BookManager;
import Library_management_system.Model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SearchBookGUI {

    private BookManager manager;
    private DefaultTableModel model;

    private JFrame frame;
    private Dashboard dashboard;

    public SearchBookGUI(Dashboard dashboard, BookManager manager) {
        this.dashboard = dashboard;
        this.manager = manager;

        frame = new JFrame("Search Books");
        frame.setSize(750, 450);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);

        // ===== TOP PANEL (Search Bar) =====
        JPanel top = new JPanel();

        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");

        top.add(new JLabel("Search (ID, Title, or Author): "));
        top.add(searchField);
        top.add(searchBtn);

        // ===== CENTER TABLE =====
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Title", "Author", "Category", "Available"});

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        // ===== BOTTOM PANEL =====
        JPanel bottom = new JPanel();

        JButton backBtn = new JButton(" Back ");
        bottom.add(backBtn);

        // ===== SEARCH ACTION =====
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().toLowerCase().trim();
            model.setRowCount(0);

            for (Book b : manager.getAllBooks()) {

                boolean matchID = String.valueOf(b.getId()).contains(keyword);
                boolean matchTitle = b.getTitle().toLowerCase().contains(keyword);
                boolean matchAuthor = b.getAuthor().toLowerCase().contains(keyword);

                if (matchID || matchTitle || matchAuthor) {
                    model.addRow(new Object[]{
                            b.getId(),
                            b.getTitle(),
                            b.getAuthor(),
                            b.getCategoryName(),
                            b.getAvailableCopies()
                    });
                }
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(frame, "No book found!");
            }
        });

        // BACK ACTION
        backBtn.addActionListener(e -> {
            frame.dispose();
            dashboard.setVisible(true);
        });

        // ===== ADD TO FRAME =====
        frame.add(top, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}