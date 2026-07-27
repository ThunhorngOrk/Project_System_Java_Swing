package library_management_system.controller;

import library_management_system.entity.Book;
import library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book updated = bookService.updateBook(id, book);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(Map.of("message", "Book deleted successfully"));
    }

    @GetMapping("/{id}/borrowed")
    public ResponseEntity<Map<String, Boolean>> checkBorrowed(@PathVariable int id) {
        return ResponseEntity.ok(Map.of("borrowed", bookService.isBookBorrowed(id)));
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<Integer, String>> getCategories() {
        return ResponseEntity.ok(bookService.getCategories());
    }
}
