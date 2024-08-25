// هاظ الكلاس اللي بدو يحتوي صفات مشتركة ل الاونر والسبلاير بحيث الاونر والسبلاير الثنين بدهم يعملو وراثة من هاظ الكلاس وهاظ الكلاس اصلا وارث كلشي في اليوزر 
package MySystem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InventoryManager extends User {
    protected List<Product> inventory;
    private static final Logger logger = Logger.getLogger(InventoryManager.class.getName());

    public InventoryManager(String username, String password, String role, String city) {
        super(username, password, role, city);
        this.inventory = new ArrayList<>();
    }
    //No arguments constructor
    public InventoryManager() {
        super();
        this.inventory = new ArrayList<>();
    }

    public boolean addProduct(Product product) {
        logger.info("InventoryManager adding product: " + product.getName());
        if (product != null && !isProductInInventory(product.getName())) {
            inventory.add(product);
            logger.info("Product added to inventory. New inventory size: " + inventory.size());
            return true;
        }
        logger.info("Product not added. Already in inventory or null product.");
        return false;
    }

    public boolean updateProduct(String productName, double newPrice, int newQuantity) {
        for (Product product : inventory) {
            if (product.getName().equals(productName)) {
                product.setPrice(newPrice);
                product.setQuantity(newQuantity);
                return true;
            }
        }
        return false;
    }

    public boolean removeProduct(String productName) {
        return inventory.removeIf(product -> product.getName().equals(productName));
    }

    public boolean isProductInInventory(String productName) {
        for (Product product : inventory) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }
    
    
    public Product getProductByName(String name) {
        return inventory.stream()
            .filter(p -> p.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    public List<Product> getInventory() {
        return new ArrayList<>(inventory);
    }
}
