package Library_management_system.Manager;

import Library_management_system.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserManager {

    // LOGIN
    public String login(String username, String password) {

        String sql = "SELECT role FROM users WHERE username=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role"); // "admin" or "user"
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // REGISTER — role is now chosen at registration time
    public boolean register(String username, String email, String password, String role) {

        String checkSql = "SELECT id FROM users WHERE username=? OR email=?";
        String insertSql = "INSERT INTO users(username, email, password, role) VALUES(?,?,?,?)";

        try (Connection con = DBConnection.getConnection()) {

            try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {
                checkPs.setString(1, username);
                checkPs.setString(2, email);

                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        return false; // username or email already taken
                    }
                }
            }

            try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {
                insertPs.setString(1, username);
                insertPs.setString(2, email);
                insertPs.setString(3, password);
                insertPs.setString(4, role);

                return insertPs.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}