package MySystem;

public class Feedback {
    private int id;
    private String content;
    private String author;
    private boolean resolved;

    public Feedback(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.resolved = false;
    }

    // Getters and setters
    public int getId()
    { 
    	return id; 
    }
    public String getContent() 
    { 
    	return content;
    }
    public String getAuthor()
    {
    	return author; 
    }
    public boolean isResolved() 
    {
    	return resolved; 
    }
    public void setResolved(boolean resolved) 
    {
    	this.resolved = resolved; 
    }
}