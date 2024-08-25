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

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
