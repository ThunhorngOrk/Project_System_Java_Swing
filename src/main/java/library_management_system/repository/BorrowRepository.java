package library_management_system.repository;

import library_management_system.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

    List<Borrow> findByStatus(String status);

    Optional<Borrow> findFirstByUserIdAndBookIdAndStatusOrderByBorrowDateAsc(
            int userId, int bookId, String status);

    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.bookId = :bookId AND b.status = 'borrowed'")
    int countActiveBorrowsByBookId(@Param("bookId") int bookId);

    @Query("SELECT b FROM Borrow b, User u, Book bk " +
           "WHERE b.userId = u.id AND b.bookId = bk.id " +
           "AND u.username = :username AND bk.title = :bookTitle AND b.status = 'borrowed' " +
           "ORDER BY b.borrowDate ASC")
    List<Borrow> findBorrowByUsernameAndBookTitle(
            @Param("username") String username, @Param("bookTitle") String bookTitle);
}
