package library_management_system.service;

import library_management_system.entity.Book;
import library_management_system.entity.Borrow;
import library_management_system.entity.User;
import library_management_system.repository.BookRepository;
import library_management_system.repository.BorrowRepository;
import library_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public String borrowBook(String username, String bookTitle) {
        User user = userRepository.findByUsername(username.trim())
                .orElse(null);
        if (user == null) return "User not found!";

        Book book = bookRepository.findByTitle(bookTitle.trim())
                .orElse(null);
        if (book == null) return "Book not found!";

        if (book.getAvailableCopies() <= 0) return "No copies available!";

        Borrow borrow = new Borrow(book.getId(), user.getId(), Date.valueOf(LocalDate.now()));
        borrowRepository.save(borrow);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        return "SUCCESS";
    }

    @Transactional
    public boolean returnBook(String username, String bookTitle) {
        List<Borrow> borrows = borrowRepository.findBorrowByUsernameAndBookTitle(
                username.trim(), bookTitle.trim());

        if (borrows.isEmpty()) return false;

        Borrow borrow = borrows.get(0);
        borrow.setReturnDate(Date.valueOf(LocalDate.now()));
        borrow.setStatus("returned");
        borrowRepository.save(borrow);

        Book book = bookRepository.findById(borrow.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return true;
    }

    public List<Map<String, Object>> getAllActiveBorrows() {
        List<Borrow> borrows = borrowRepository.findByStatus("borrowed");
        List<Map<String, Object>> result = new ArrayList<>();

        for (Borrow b : borrows) {
            Map<String, Object> map = new HashMap<>();
            User user = userRepository.findById(b.getUserId()).orElse(null);
            Book book = bookRepository.findById(b.getBookId()).orElse(null);

            map.put("username", user != null ? user.getUsername() : "Unknown");
            map.put("bookTitle", book != null ? book.getTitle() : "Unknown");
            map.put("borrowDate", b.getBorrowDate());
            map.put("status", b.getStatus());
            result.add(map);
        }
        return result;
    }
}
