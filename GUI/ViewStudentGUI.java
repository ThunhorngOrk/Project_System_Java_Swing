package Library_management_system.GUI;

import Library_management_system.Model.User;
import Library_management_system.Manager.StudentManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewStudentGUI extends JFrame {

    private Dashboard dashboard;
    private StudentManager studentManager;

    public ViewStudentGUI(Dashboard dashboard, StudentManager studentManager) {
        this.dashboard = dashboard;
        this.studentManager = studentManager;

        setTitle("View Students");
        setSize(500, 400);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Username");
        model.addColumn("Email");

        JTable table = new JTable(model);

        for (User u : studentManager.getAllStudents()) {
            model.addRow(new Object[]{u.getId(), u.getUsername(), u.getEmail()});
        }

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
            dashboard.setVisible(true);
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(backBtn, BorderLayout.SOUTH);

        setVisible(true);
    }
}