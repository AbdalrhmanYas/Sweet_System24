package MySystem;

import java.time.LocalDateTime;

public class Message {
    private String sender;
    private String recipient;
    private String content;
    private LocalDateTime timestamp;

    public Message(String sender, String recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getSender() 
    {
    	return sender; 
    }
    public String getRecipient() 
    { 
    	return recipient; 
    }
    public String getContent() 
    {
    	return content; 
    }
    
    
    
    public LocalDateTime getTimestamp() 
    {
    	return timestamp; 
    }
}