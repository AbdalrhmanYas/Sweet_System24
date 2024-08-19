package MySystem;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String store;

    private int salesQuantity;
    private double revenue;

    public Product(int id, String name, double price, int quantity, String store) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.store = store;
    }

    // Getters and setters
    public int getId() 
    {
    	return id; 
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getStore() { return store; }

    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }



    public void setSalesQuantity(int salesQuantity) { this.salesQuantity = salesQuantity; }
    public int getSalesQuantity() { return salesQuantity; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
    public double getRevenue() { return revenue; }



    private double cost; // Cost price of the product
    private int purchaseCount; // Number of times the recipe has been purchased

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public int getPurchaseCount() { return purchaseCount; }
    public void incrementPurchaseCount() { this.purchaseCount++; }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return name.equals(product.name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}