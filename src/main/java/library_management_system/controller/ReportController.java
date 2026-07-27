package library_management_system.controller;

import library_management_system.entity.Book;
import library_management_system.service.BookService;
import library_management_system.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowService borrowService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalBooks", bookService.getAllBooks().size());
        summary.put("totalAvailableCopies", bookService.getTotalAvailableCopies());
        summary.put("activeBorrows", borrowService.getAllActiveBorrows().size());
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/borrows")
    public ResponseEntity<List<Map<String, Object>>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllActiveBorrows());
    }
}
