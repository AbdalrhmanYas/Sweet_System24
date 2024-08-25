package MySystem;

import java.time.LocalDate;

public class Feedback {
    private int id;
    private String content;
    private String author;
    private boolean resolved;
    private LocalDate date;

    public Feedback(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.resolved = false;
        this.date = LocalDate.now(); // Set the current date when feedback is created
    }

    // Getters and setters
    public int getId() {
        return id;
    }



    public String getAuthor() {
        return author;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }


}