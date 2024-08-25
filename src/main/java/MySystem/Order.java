package MySystem;

public class Order {
    private String id;
    private String recipient;
    private String phone;
    private String address;
    private String status;
    private String supervisor;

    public Order(String id, String recipient, String phone, String address, String status, String supervisor) {
        this.id = id;
        this.recipient = recipient;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.supervisor = supervisor;

    }

    public String getSupervisor() {
        return supervisor;
    }


    public String getId() {
        return id;
    }


    public String getRecipient() {
        return recipient;
    }


    public String getPhone() {
        return phone;
    }


    public String getAddress() {
        return address;
    }


    public String getStatus() {
        return status;
    }

}
