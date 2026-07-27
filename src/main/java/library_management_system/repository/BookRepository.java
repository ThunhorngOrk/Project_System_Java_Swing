package library_management_system.repository;

import library_management_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByTitle(String title);

    List<Book> findByCategoryId(int categoryId);

    @Query("SELECT COALESCE(SUM(b.availableCopies), 0) FROM Book b")
    int sumAvailableCopies();
}
