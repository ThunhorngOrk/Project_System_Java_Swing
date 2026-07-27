package library_management_system.service;

import library_management_system.entity.User;
import library_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllStudents() {
        return userRepository.findByRole("user");
    }

    public User addStudent(User student) {
        student.setRole("user");
        return userRepository.save(student);
    }

    public User updateStudent(int id, User updated) {
        User student = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Student not found"));
        student.setUsername(updated.getUsername());
        student.setEmail(updated.getEmail());
        student.setPassword(updated.getPassword());
        return userRepository.save(student);
    }

    public void deleteStudent(int id) {
        userRepository.deleteById(id);
    }
}
