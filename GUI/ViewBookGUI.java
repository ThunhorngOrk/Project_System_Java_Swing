package Library_management_system.GUI;

import Library_management_system.Manager.BookManager;
import Library_management_system.Model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ViewBookGUI {

    private BookManager manager;
    private DefaultTableModel model;

    private JFrame frame;
    private Dashboard dashboard;

    private DefaultListModel<String> categoryListModel;
    private JList<String> categoryList;
    private JLabel headerLabel;

    // category -> list of books
    private Map<String, List<Book>> booksByCategory;

    public ViewBookGUI(Dashboard dashboard, BookManager manager) {
        this.dashboard = dashboard;
        this.manager = manager;

        frame = new JFrame("View Books");
        frame.setSize(800, 480);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);

        // ===== LEFT: CATEGORY LIST =====
        categoryListModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryListModel);
        categoryList.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane categoryScroll = new JScrollPane(categoryList);
        categoryScroll.setPreferredSize(new Dimension(180, 0));
        categoryScroll.setBorder(BorderFactory.createTitledBorder("Categories"));

        // ===== RIGHT: TABLE =====
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Book ID", "Title", "Author", "Available"});

        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        JScrollPane tableScroll = new JScrollPane(table);

        // ===== TOP HEADER =====
        headerLabel = new JLabel("Select a category", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // ===== BOTTOM BUTTONS =====
        JButton refreshBtn = new JButton(" Refresh ");
        JButton backBtn = new JButton(" Back ");

        JPanel bottom = new JPanel();
        bottom.add(refreshBtn);
        bottom.add(backBtn);

        // ===== ACTIONS =====
        backBtn.addActionListener(e -> {
            frame.dispose();
            dashboard.setVisible(true);
        });

        refreshBtn.addActionListener(e -> {
            loadData();
            categoryList.clearSelection();
            model.setRowCount(0);
            headerLabel.setText("Select a category");
        });

        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = categoryList.getSelectedValue();
                if (selected != null) {
                    showBooksForCategory(selected);
                }
            }
        });

        // ===== LAYOUT =====
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(categoryScroll, BorderLayout.WEST);
        centerPanel.add(tableScroll, BorderLayout.CENTER);

        frame.add(headerLabel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        loadData();
        frame.setVisible(true);
    }

    // Load all books and group them by category
    private void loadData() {
        booksByCategory = new TreeMap<>(); // TreeMap keeps categories sorted alphabetically

        List<Book> allBooks = manager.getAllBooks();
        for (Book b : allBooks) {
            String category = b.getCategoryName() == null ? "Uncategorized" : b.getCategoryName();
            booksByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(b);
        }

        categoryListModel.clear();
        for (String category : booksByCategory.keySet()) {
            int count = booksByCategory.get(category).size();
            categoryListModel.addElement(category + " (" + count + ")");
        }
    }

    // Show books belonging to the clicked category
    private void showBooksForCategory(String selectedLabel) {
        // strip the " (count)" suffix to get the real category name
        String category = selectedLabel.replaceAll(" \\(\\d+\\)$", "");

        model.setRowCount(0);
        List<Book> books = booksByCategory.get(category);

        if (books != null) {
            for (Book b : books) {
                model.addRow(new Object[]{
                        b.getId(),
                        b.getTitle(),
                        b.getAuthor(),
                        b.getAvailableCopies()
                });
            }
        }

        headerLabel.setText(category + " — " + (books == null ? 0 : books.size()) + " book(s)");
    }
}