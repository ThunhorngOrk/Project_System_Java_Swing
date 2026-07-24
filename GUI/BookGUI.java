package Library_management_system.GUI;

import Library_management_system.Manager.BookManager;
import Library_management_system.Model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class BookGUI {

    private BookManager manager;
    private DefaultTableModel model;

    private JFrame frame;
    private Dashboard dashboard;

    private JComboBox<String> categoryCombo;
    private Map<Integer, String> categoryMap; // id -> name, backs the combo box

    public BookGUI(Dashboard dashboard, BookManager manager) {

        this.dashboard = dashboard;
        this.manager = manager;

        frame = new JFrame("Manage Books");
        frame.setSize(1000, 650);
        frame.setLayout(new GridLayout(1, 2, 10, 10));
        frame.setLocationRelativeTo(null);

        // ================= LEFT PANEL =================
        JPanel left = new JPanel(null);
        left.setBorder(BorderFactory.createTitledBorder("Book Information"));

        JLabel idLbl = new JLabel("Book ID (auto):");
        idLbl.setBounds(20, 30, 150, 25);
        left.add(idLbl);

        JTextField idField = new JTextField();
        idField.setBounds(20, 60, 300, 30);
        idField.setEditable(false); // id is auto-increment; only set when a row is selected
        left.add(idField);

        JLabel titleLbl = new JLabel("Title:");
        titleLbl.setBounds(20, 110, 100, 25);
        left.add(titleLbl);

        JTextField titleField = new JTextField();
        titleField.setBounds(20, 140, 300, 30);
        left.add(titleField);

        JLabel authorLbl = new JLabel("Author:");
        authorLbl.setBounds(20, 190, 100, 25);
        left.add(authorLbl);

        JTextField authorField = new JTextField();
        authorField.setBounds(20, 220, 300, 30);
        left.add(authorField);

        JLabel categoryLbl = new JLabel("Category:");
        categoryLbl.setBounds(20, 270, 100, 25);
        left.add(categoryLbl);

        categoryCombo = new JComboBox<>();
        categoryCombo.setBounds(20, 300, 300, 30);
        left.add(categoryCombo);

        JLabel copiesLbl = new JLabel("Total Copies:");
        copiesLbl.setBounds(20, 340, 150, 25);
        left.add(copiesLbl);

        JTextField copiesField = new JTextField();
        copiesField.setBounds(20, 370, 300, 30);
        left.add(copiesField);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(20, 420, 300, 40);
        left.add(addBtn);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(20, 470, 300, 40);
        left.add(updateBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(20, 520, 300, 40);
        left.add(deleteBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 570, 300, 40);
        left.add(backBtn);

        // ================= RIGHT PANEL =================
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Book List"));

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "Title", "Author", "Category", "Total", "Available"
        });

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        right.add(scrollPane, BorderLayout.CENTER);

        loadCategories();

        // ================= ADD =================
        addBtn.addActionListener(e -> {

            try {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                int categoryId = getSelectedCategoryId();
                int totalCopies = Integer.parseInt(copiesField.getText().trim());

                if (title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                    return;
                }

                if (categoryId == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a category!");
                    return;
                }

                Book book = new Book(title, author, categoryId, totalCopies);

                if (manager.addBook(book)) {
                    refreshTable();
                    clearFields(idField, titleField, authorField, copiesField);
                    JOptionPane.showMessageDialog(frame, "Book Added Successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add book.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Total Copies must be a number!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input!");
            }
        });

        // ================= UPDATE =================
        updateBtn.addActionListener(e -> {

            try {
                if (idField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Select a book from the table first!");
                    return;
                }

                int id = Integer.parseInt(idField.getText());
                int categoryId = getSelectedCategoryId();
                int totalCopies = Integer.parseInt(copiesField.getText().trim());

                if (categoryId == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a category!");
                    return;
                }

                if (manager.updateBook(id, titleField.getText().trim(),
                        authorField.getText().trim(), categoryId, totalCopies)) {

                    refreshTable();
                    JOptionPane.showMessageDialog(frame, "Book Updated!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update book.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Total Copies must be a number!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input!");
            }
        });

        // ================= DELETE =================
        deleteBtn.addActionListener(e -> {

            try {
                if (idField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Select a book from the table first!");
                    return;
                }

                int id = Integer.parseInt(idField.getText());

                if (manager.isBookBorrowed(id)) {
                    JOptionPane.showMessageDialog(frame, "Cannot delete.\nBook is currently borrowed.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        frame, "Delete this book?", "Confirm", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    manager.deleteBook(id);
                    refreshTable();
                    clearFields(idField, titleField, authorField, copiesField);
                    JOptionPane.showMessageDialog(frame, "Book Deleted!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ID!");
            }
        });

        // ================= BACK =================
        backBtn.addActionListener(e -> {
            frame.dispose();
            dashboard.setVisible(true);
        });

        // ================= TABLE CLICK =================
        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {
                idField.setText(model.getValueAt(row, 0).toString());
                titleField.setText(model.getValueAt(row, 1).toString());
                authorField.setText(model.getValueAt(row, 2).toString());
                categoryCombo.setSelectedItem(model.getValueAt(row, 3).toString());
                copiesField.setText(model.getValueAt(row, 4).toString());
            }
        });

        frame.add(left);
        frame.add(right);

        refreshTable();

        frame.setVisible(true);
    }

    private void loadCategories() {
        categoryMap = manager.getCategories();
        categoryCombo.removeAllItems();
        for (String name : categoryMap.values()) {
            categoryCombo.addItem(name);
        }
    }

    private int getSelectedCategoryId() {
        String selectedName = (String) categoryCombo.getSelectedItem();
        if (selectedName == null) return -1;

        for (Map.Entry<Integer, String> entry : categoryMap.entrySet()) {
            if (entry.getValue().equals(selectedName)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    private void clearFields(JTextField... fields) {
        for (JTextField f : fields) {
            f.setText("");
        }
    }

    private void refreshTable() {

        model.setRowCount(0);

        for (Book b : manager.getAllBooks()) {
            model.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getCategoryName(),
                    b.getTotalCopies(),
                    b.getAvailableCopies()
            });
        }
    }
}