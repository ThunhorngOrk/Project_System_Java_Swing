package Library_management_system.Manager;

import Library_management_system.Database.DBConnection;
import Library_management_system.Model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BookManager {

    public boolean addBook(Book book) {

        String sql = "INSERT INTO book(title, author, category_id, total_copies, available_copies) " +
                "VALUES(?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getCategoryId());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, book.getAvailableCopies());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(int id, String title, String author,
                              int categoryId, int totalCopies) {

        String sql = "UPDATE book SET title=?, author=?, category_id=?, total_copies=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, categoryId);
            ps.setInt(4, totalCopies);
            ps.setInt(5, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteBook(int id) {

        String sql = "DELETE FROM book WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isBookBorrowed(int id) {

        String sql = "SELECT COUNT(*) FROM borrow WHERE book_id=? AND status='borrowed'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Joins categories so the table can show the category name, not just its id
    public ArrayList<Book> getAllBooks() {

        ArrayList<Book> list = new ArrayList<>();

        String sql = "SELECT b.id, b.title, b.author, b.category_id, " +
                "c.category_name, b.total_copies, b.available_copies " +
                "FROM book b LEFT JOIN categories c ON b.category_id = c.id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // For populating the category dropdown: id -> name
    public Map<Integer, String> getCategories() {

        Map<Integer, String> categories = new LinkedHashMap<>();
        String sql = "SELECT id, category_name FROM categories ORDER BY category_name";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.put(rs.getInt("id"), rs.getString("category_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    // Sum of all available_copies across every book — used by ReportGUI
    public int getTotalAvailableCopies() {

        String sql = "SELECT SUM(available_copies) AS total FROM book";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}