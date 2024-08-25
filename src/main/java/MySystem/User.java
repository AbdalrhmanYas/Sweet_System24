package MySystem;

public class User {
    private String username;
    private String password;
    private String role;
    private String city;
    private String email; 
    private String phoneNumber; 
    
    // حاليا مش مستخدممم بس لاحقا بجوز استخدمهم اذا لحقت تكمل
    protected String businessName;

    // Constructor
    public User(String username, String password, String role, String city) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.city = city;
        this.email = "";
        this.phoneNumber = "";
    }
    //No arguments Constructor
    public User() {
        this.username = "";
        this.password = "";
        this.role = "";
        this.city = "";
        this.email = "";
        this.phoneNumber = "";
    }
    
    
    // هاظ الكونستركتر راح يفيدني لما اعمل الفيتشر الثالثة في البارت الثاني 
    


 // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getCity() {
        return city;
    }



    
    
    
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    
    
    
    // this is related to user account management feature file which i had programmed in loginlogupSteps to update the information os the reguler user
    public void updateDetails(String newPassword, String newCity, String newEmail, String newPhoneNumber) 
    {
        if (newPassword != null && !newPassword.isEmpty()) this.password = newPassword;
        if (newCity != null && !newCity.isEmpty()) this.city = newCity;
        if (newEmail != null && !newEmail.isEmpty()) this.email = newEmail;
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) this.phoneNumber = newPhoneNumber;
    }
    
    

}