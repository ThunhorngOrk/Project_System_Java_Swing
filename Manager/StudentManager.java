package Library_management_system.Manager;

import Library_management_system.Database.DBConnection;
import Library_management_system.Model.User;

import java.sql.*;
import java.util.ArrayList;

public class StudentManager {

    public boolean addStudent(User u) {

        String sql =
                "INSERT INTO users(username, email, password, role) VALUES(?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudent(int id, String username, String email, String password) {

        String sql =
                "UPDATE users SET username=?, email=?, password=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteStudent(int id) {

        String sql = "DELETE FROM users WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllStudents() {

        ArrayList<User> list = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE role='user'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                list.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("role")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}