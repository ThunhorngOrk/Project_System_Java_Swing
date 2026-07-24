package Library_management_system.Model;

import java.sql.Date;

public class Borrow {

    private int id;
    private int bookId;
    private int userId;
    private Date borrowDate;
    private Date returnDate;
    private String status; // "borrowed" or "returned"

    // Used when loading a full record from the DB
    public Borrow(int id, int bookId, int userId, Date borrowDate, Date returnDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Used when creating a new borrow record (no id/returnDate yet)
    public Borrow(int bookId, int userId, Date borrowDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.status = "borrowed";
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}