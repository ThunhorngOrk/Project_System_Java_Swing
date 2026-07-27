package library_management_system.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "borrow")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_id", nullable = false)
    private int bookId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "borrow_date", nullable = false)
    private Date borrowDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(nullable = false)
    private String status;

    public Borrow() {}

    public Borrow(int bookId, int userId, Date borrowDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.status = "borrowed";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
