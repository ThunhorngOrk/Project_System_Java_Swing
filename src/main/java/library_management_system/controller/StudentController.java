package library_management_system.controller;

import library_management_system.entity.User;
import library_management_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<User>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public ResponseEntity<User> addStudent(@RequestBody User student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody User student) {
        User updated = studentService.updateStudent(id, student);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
    }
}
