package library_management_system.dto;

public class BorrowRequest {
    private String username;
    private String bookTitle;

    public BorrowRequest() {}
    public BorrowRequest(String username, String bookTitle) {
        this.username = username;
        this.bookTitle = bookTitle;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
}
