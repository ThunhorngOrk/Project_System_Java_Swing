package Library_management_system.Model;

public class Book {

    private int id;
    private String title;
    private String author;
    private int categoryId;
    private String categoryName; // for display only, joined from categories table
    private int totalCopies;
    private int availableCopies;

    // Used when loading from DB (has id + categoryName for the table view)
    public Book(int id, String title, String author, int categoryId,
                String categoryName, int totalCopies, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    // Used when creating a new book (no id yet, no availableCopies yet)
    public Book(String title, String author, int categoryId, int totalCopies) {
        this.title = title;
        this.author = author;
        this.categoryId = categoryId;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}