package Library_management_system.Manager;

import Library_management_system.Database.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class BorrowManager {

    // BORROW USING USERNAME + TITLE
    public String borrowBook(String username, String bookTitle) {

        String findUser = "SELECT id FROM users WHERE username=?";
        String findBook = "SELECT id, available_copies FROM book WHERE title=?";
        String insertBorrow = "INSERT INTO borrow (user_id, book_id, borrow_date, status) VALUES (?, ?, CURDATE(), 'borrowed')";
        String decrementCopies = "UPDATE book SET available_copies = available_copies - 1 WHERE id=?";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {
                int userId;
                try (PreparedStatement ps1 = conn.prepareStatement(findUser)) {
                    ps1.setString(1, username.trim());
                    try (ResultSet rs1 = ps1.executeQuery()) {
                        if (!rs1.next()) {
                            conn.rollback();
                            return "User not found!";
                        }
                        userId = rs1.getInt("id");
                    }
                }

                int bookId;
                int available;
                try (PreparedStatement ps2 = conn.prepareStatement(findBook)) {
                    ps2.setString(1, bookTitle.trim());
                    try (ResultSet rs2 = ps2.executeQuery()) {
                        if (!rs2.next()) {
                            conn.rollback();
                            return "Book not found!";
                        }
                        bookId = rs2.getInt("id");
                        available = rs2.getInt("available_copies");
                    }
                }

                if (available <= 0) {
                    conn.rollback();
                    return "No copies available!";
                }

                try (PreparedStatement ps3 = conn.prepareStatement(insertBorrow)) {
                    ps3.setInt(1, userId);
                    ps3.setInt(2, bookId);
                    ps3.executeUpdate();
                }

                try (PreparedStatement ps4 = conn.prepareStatement(decrementCopies)) {
                    ps4.setInt(1, bookId);
                    ps4.executeUpdate();
                }

                conn.commit();
                return "SUCCESS";

            } catch (Exception e) {
                conn.rollback();
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
    }

    // RETURN USING USERNAME + TITLE
    public boolean returnBook(String username, String bookTitle) {

        String findBorrow = "SELECT br.id, br.book_id FROM borrow br " +
                "JOIN users u ON br.user_id = u.id " +
                "JOIN book bk ON br.book_id = bk.id " +
                "WHERE u.username=? AND bk.title=? AND br.status='borrowed' " +
                "ORDER BY br.borrow_date LIMIT 1";

        String updateBorrow = "UPDATE borrow SET return_date=CURDATE(), status='returned' WHERE id=?";
        String incrementCopies = "UPDATE book SET available_copies = available_copies + 1 WHERE id=?";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {
                int borrowId;
                int bookId;

                try (PreparedStatement ps = conn.prepareStatement(findBorrow)) {
                    ps.setString(1, username.trim());
                    ps.setString(2, bookTitle.trim());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }
                        borrowId = rs.getInt("id");
                        bookId = rs.getInt("book_id");
                    }
                }

                try (PreparedStatement ps2 = conn.prepareStatement(updateBorrow)) {
                    ps2.setInt(1, borrowId);
                    ps2.executeUpdate();
                }

                try (PreparedStatement ps3 = conn.prepareStatement(incrementCopies)) {
                    ps3.setInt(1, bookId);
                    ps3.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (Exception e) {
                conn.rollback();
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // GET ALL CURRENTLY BORROWED (SHOW USERNAME + TITLE)
    public ArrayList<String[]> getAllBorrows() {

        ArrayList<String[]> list = new ArrayList<>();

        String sql = "SELECT u.username AS user_name, bk.title AS book_title, " +
                "br.borrow_date, br.status " +
                "FROM borrow br " +
                "JOIN users u ON br.user_id = u.id " +
                "JOIN book bk ON br.book_id = bk.id " +
                "WHERE br.status='borrowed'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("user_name"),
                        rs.getString("book_title"),
                        rs.getString("borrow_date"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
