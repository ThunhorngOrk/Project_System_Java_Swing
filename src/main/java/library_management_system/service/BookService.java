package library_management_system.service;

import library_management_system.entity.Book;
import library_management_system.entity.Category;
import library_management_system.repository.BookRepository;
import library_management_system.repository.BorrowRepository;
import library_management_system.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(int id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Book not found"));
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setCategoryId(updatedBook.getCategoryId());
        book.setTotalCopies(updatedBook.getTotalCopies());
        return bookRepository.save(book);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public boolean isBookBorrowed(int id) {
        return borrowRepository.countActiveBorrowsByBookId(id) > 0;
    }

    public Map<Integer, String> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .collect(Collectors.toMap(
                        Category::getId,
                        Category::getCategoryName,
                        (a, b) -> b,
                        LinkedHashMap::new
                ));
    }

    public int getTotalAvailableCopies() {
        return bookRepository.sumAvailableCopies();
    }
}
