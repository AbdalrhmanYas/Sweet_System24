package MySystem;

import java.time.LocalDate;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class sweetSys {
    private final ArrayList<User> users;
    private List<Recipe> recipes;
    private final List<Feedback> feedbacks;
    private final List<Product> products;
    private  ArrayList<Order> orders;

    private final Map<String, List<Message>> userMessages;

    private final Map<String, List<Feedback>> productFeedback;

    private final PaymentSystem paymentSystem;

    private final Map<String, List<String>> userOrderHistory;

    private final Map<String, List<Feedback>> itemFeedback;


    private User currentUser;


    private boolean isLoggedIn;  // FOR LOGIN PROCESS AND I WILL USE IT WHILE TESTING LOGIN PROCESS


    private boolean isSignUpInProgress;    // FOR Signup PROCESS AND I WILL USE IT WHILE TESTING LOGUP PROCESS


    private boolean lastOperationSuccessful;  // FOR TESTING LOGIN & Signup


    private String msg;  // FOR LOGIN & Signup MESSAGES

    private String lastGeneratedReport;
    private StoreOwner storeowner;

    public sweetSys() {
        orders = new ArrayList<>();
        users = new ArrayList<>();
        recipes = new ArrayList<>();
        feedbacks = new ArrayList<>();
        products = new ArrayList<>();

        userMessages = new HashMap<>();
        productFeedback = new HashMap<>();

        itemFeedback = new HashMap<>();

        paymentSystem = new PaymentSystem();
        isLoggedIn = false;
        isSignUpInProgress = false;
        lastOperationSuccessful = false;
        msg = "";
        lastGeneratedReport = "";
        initializeUsersForTesting();

        initializeTestRecipesAndFeedback();


        userOrderHistory = new HashMap<>();


        recipes = new ArrayList<>();
        System.out.println("Initializing recipes:");

        Recipe chocolateCake = new Recipe(1, "Chocolate Cake", "flour, sugar, cocoa powder, eggs, milk", "Mix and bake", "user1", "Cakes");
        chocolateCake.addDietaryRestriction("vegetarian");
        recipes.add(chocolateCake);
        System.out.println("Added recipe: " + chocolateCake.getName());

        Recipe veganBrownies = new Recipe(2, "Vegan Brownies", "flour, sugar, cocoa powder, vegan butter, almond milk", "Mix and bake", "user2", "Brownies");
        veganBrownies.addDietaryRestriction("vegan");
        veganBrownies.addDietaryRestriction("dairy-free");
        recipes.add(veganBrownies);
        System.out.println("Added recipe: " + veganBrownies.getName());

        Recipe glutenFreeCookies = new Recipe(3, "Gluten-Free Cookies", "gluten-free flour, sugar, butter, eggs", "Mix and bake", "user3", "Cookies");
        glutenFreeCookies.addDietaryRestriction("gluten-free");
        recipes.add(glutenFreeCookies);
        System.out.println("Added recipe: " + glutenFreeCookies.getName());

        System.out.println("Total recipes initialized: " + recipes.size());
    }


    private void initializeUsersForTesting() {
        // Admins
        users.add(new Admin("abood", "123456", "Nablus"));
        users.add(new Admin("admin1", "adminpass1", "Nablus"));
        users.add(new Admin("admin2", "adminpass2", "Jenin"));
        users.add(new Admin("admin3", "adminpass3", "Ramallah"));
        users.add(new Admin("admin4", "adminpass4", "Hebron"));
        users.add(new Admin("admin5", "adminpass5", "Bethlehem"));

        // Suppliers
        users.add(new Supplier("supplier1", "supplierpass1", "Nablus"));
        users.add(new Supplier("supplier2", "supplierpass2", "Jenin"));
        users.add(new Supplier("supplier3", "supplierpass3", "Ramallah"));
        users.add(new Supplier("supplier4", "supplierpass4", "Hebron"));
        users.add(new Supplier("supplier5", "supplierpass5", "Bethlehem"));

        // Store Owners
        users.add(new StoreOwner("storeowner_x", "storeowner2", "Nablus"));
        users.add(new StoreOwner("owner1", "ownerpass1", "Nablus"));
        users.add(new StoreOwner("owner2", "ownerpass2", "Jenin"));
        users.add(new StoreOwner("owner3", "ownerpass3", "Ramallah"));
        users.add(new StoreOwner("owner4", "ownerpass4", "Hebron"));
        users.add(new StoreOwner("owner5", "ownerpass5", "Bethlehem"));

        // Regular Users
        users.add(new User("user1", "userpass1", "regular", "Nablus"));
        users.add(new User("user2", "userpass2", "regular", "Jenin"));
        users.add(new User("user3", "userpass3", "regular", "Ramallah"));
        users.add(new User("user4", "userpass4", "regular", "Hebron"));
        users.add(new User("user5", "userpass5", "regular", "Bethlehem"));
    }


    private void initializeTestRecipesAndFeedback() {
        orders.add(new Order("000", "Hamza", "0599888888", "Nablus", "Shipped", "owner"));
        orders.add(new Order("001", "Rami", "0599888888", "Nablus", "Delivered", "supplier"));
        orders.add(new Order("002", "Khaled", "0599888888", "Nablus", "Cancelled", "owner"));
        orders.add(new Order("003", "Ahmed", "0599888888", "Nablus", "In Progress", "supplier"));


        recipes.add(new Recipe(1, "Chocolate Cake", "Flour, Sugar, Cocoa", "Mix and bake", "admin1", "Cakes"));
        recipes.add(new Recipe(2, "Vanilla Cookies", "Flour, Sugar, Vanilla", "Mix and bake", "user1", "Cookies"));
        recipes.add(new Recipe(3, "Strawberry Cheesecake", "Cream cheese, Strawberries, Graham crackers", "Mix, layer, and chill", "owner1", "Cakes"));
        recipes.add(new Recipe(4, "Lemon Tart", "Flour, Butter, Lemon", "Make crust, fill, and bake", "supplier1", "Tarts"));

        feedbacks.add(new Feedback(1, "Great service!", "user1"));
        feedbacks.add(new Feedback(2, "Needs improvement", "user2"));


        addFeedback("Great service! The desserts were delicious.", "user3");
        addFeedback("The delivery was late, but the cake was worth the wait. Please improve delivery times.", "user4");
        addFeedback("I love the new recipe feature! It's so helpful.", "user5");
    }


    //    LOGIN AND Signup FUNCTIONS
    //-----------------------------------------------------------------------------------------------


    public void changePass(String username, String oldPass, String newPass) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(oldPass)) {
            user.setPassword(newPass);
        }
    }

    public void login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                isLoggedIn = true;
                lastOperationSuccessful = true;
                currentUser = user;
                String roleMessage = user instanceof Admin ? "admin" : user.getRole();
                msg = "Welcome to the Sweet Management System, " + roleMessage + " " + username + "!";
                System.out.println("Login successful for " + roleMessage + ": " + username);
                return;
            }
        }
        isLoggedIn = false;
        lastOperationSuccessful = false;
        msg = "Login failed. Please check your username and password.";
        System.out.println("Login failed for user: " + username);
    }


    public void logout() {
        this.isLoggedIn = false;
        currentUser = null;
        msg = "You have been logged out.";
    }


    public boolean isLoggedIn() {
        return isLoggedIn;
    }  //RETURN THE VALUE OF THE FLAG


    public void startSignUp() {    // THIS IS FOR THE FIRST STEP OF TESTING SIGNUP SENARIO
        isSignUpInProgress = true;
    }


    public boolean signUpFlag() {    // TO RETURN THE VALUE OF isSignUpInProgress FLAG
        return isSignUpInProgress;
    }


    public void signUp(String username, String password, String role, String city) {


        if (getUserByUsername(username) != null) {  // check if the username is exist in the system
            lastOperationSuccessful = false;
            msg = "Username already exists";
            return;
        }

        // if the if statement failed i will make new user according to its role

        User newUser;
        switch (role.toLowerCase()) {    // switch to determine the role of the new user
            case "admin":
                newUser = new Admin(username, password, city);
                break;
            case "supplier":
                newUser = new Supplier(username, password, city);
                break;
            case "owner":
            case "store owner":
                newUser = new StoreOwner(username, password, city);
                break;

            // For any other role out of ( admin , suppleir, owner or store owner ) then a generic User object is created.
            default:
                newUser = new User(username, password, role, city);
        }
        users.add(newUser);
        lastOperationSuccessful = true;
        msg = "Welcome! You have successfully signed up as " + role + ".";
        isSignUpInProgress = false;
        currentUser = newUser;
        isLoggedIn = true;
        System.out.println("New user created: " + newUser.getClass().getSimpleName() + " - " + username);
    }


    //  to retrieve a user by username
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {        // call the getter from user class
                return user;
            }
        }
        return null;
    }


    public String getMessage() {
        return msg;
    }


    // this is relate to user account management feature file which i had programmed in loginlogupSteps

    public boolean updateUserDetails(String username, String newPassword, String newCity, String newEmail, String newPhoneNumber) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.updateDetails(newPassword, newCity, newEmail, newPhoneNumber);  // we will call it from user class to update the informations
            msg = "User details updated successfully.";
            lastOperationSuccessful = true;
            return true;
        }
        msg = "User not found.";
        lastOperationSuccessful = false;
        return false;
    }


//------------------------------------------------------------------------------------------------


    // ----------------------------------------------------------------------------------------------------------
    // Method to add a user to the system
    public void addUser(User user) {
        // Ensure the user doesn't already exist
        if (getUserByUsername(user.getUsername()) == null) {
            users.add(user);
        } else {
            throw new IllegalArgumentException("User with this username already exists.");
        }
    }


    public Map<String, String> getUserDetails(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            Map<String, String> details = new HashMap<>();
            details.put("username", user.getUsername());
            details.put("role", user.getRole());
            details.put("city", user.getCity());
            details.put("email", user.getEmail());
            details.put("phoneNumber", user.getPhoneNumber());
            msg = "User details retrieved successfully.";
            lastOperationSuccessful = true;
            return details;
        }
        msg = "User not found.";
        lastOperationSuccessful = false;
        return null;
    }


    // ----------------------------------------------------------------------------------------------------------


    // manage products
    //-----------------------------------------------------------------------------------------------
    public boolean addProduct(String username, Product product) {
        User user = getUserByUsername(username);
        System.out.println("Adding product for user: " + username + ", user object: " + user);
        if (user instanceof InventoryManager) {
            InventoryManager manager = (InventoryManager) user;
            boolean result = manager.addProduct(product);
            System.out.println("Product added result: " + result);
            return result;
        }
        System.out.println("User is not an InventoryManager");
        return false;
    }

    public boolean updateProduct(String username, String productName, double newPrice, int newQuantity) {
        User user = getUserByUsername(username);
        if (user instanceof InventoryManager) {
            return ((InventoryManager) user).updateProduct(productName, newPrice, newQuantity);
        }
        return false;
    }

    public boolean removeProduct(String username, String productName) {
        User user = getUserByUsername(username);
        if (user instanceof InventoryManager) {
            return ((InventoryManager) user).removeProduct(productName);
        }
        return false;
    }

    public boolean isProductInInventory(String productName) {
        for (User user : users) {
            if (user instanceof InventoryManager) {
                InventoryManager manager = (InventoryManager) user;
                if (manager.isProductInInventory(productName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Product getProductByName(String productName) {
        for (User user : users) {
            if (user instanceof InventoryManager) {
                Product product = ((InventoryManager) user).getInventory().stream()
                        .filter(p -> p.getName().equals(productName))
                        .findFirst()
                        .orElse(null);
                if (product != null) {
                    return product;
                }
            }
        }
        return null;
    }





    /*
    public boolean isProductInInventory(String productName) {
        return products.stream().anyMatch(p -> p.getName().equals(productName));
    }

    public Product getProductByName(String productName) {
        return products.stream()
                       .filter(p -> p.getName().equals(productName))
                       .findFirst()
                       .orElse(null);
    }
*/


//////////////////////////////////////////////////////////////////////////////////////////////////
//--------------------------------------------------------------------------------------------------
//purchase products

    public void addFundsToUser(String username, double amount) {
        User user = getUserByUsername(username);
        if (user != null) {
            paymentSystem.addFunds(username, amount);
            msg = "Funds added successfully.";
            lastOperationSuccessful = true;
        } else {
            msg = "User not found.";
            lastOperationSuccessful = false;
        }
    }

    public double getUserBalance(String username) {
        return paymentSystem.getBalance(username);
    }

    public void purchaseProduct(String buyerUsername, String sellerUsername, String productName) {
        User buyer = getUserByUsername(buyerUsername);
        User seller = getUserByUsername(sellerUsername);

        if (buyer == null || seller == null || !(seller instanceof StoreOwner)) {
            lastOperationSuccessful = false;
            msg = "Invalid buyer or seller";
            return;
        }

        StoreOwner storeOwner = (StoreOwner) seller;
        Product product = storeOwner.getProductByName(productName);

        if (product == null || product.getQuantity() <= 0) {
            lastOperationSuccessful = false;
            msg = "Item is out of stock";
            return;
        }

        double price = product.getPrice();
        if (paymentSystem.processPayment(buyerUsername, sellerUsername, price)) {
            product.setQuantity(product.getQuantity() - 1);
            product.setSalesQuantity(product.getSalesQuantity() + 1);
            product.setRevenue(product.getRevenue() + price);

            // Add to user's order history
            userOrderHistory.computeIfAbsent(buyerUsername, k -> new ArrayList<>()).add(productName);

            lastOperationSuccessful = true;
            msg = "Purchase successful";
        } else {
            lastOperationSuccessful = false;
            msg = "Insufficient funds";
        }
    }

    public Optional<Map<String, Object>> getProductDetails(String productName) {
        Product product = getProductByName(productName);
        if (product != null) {
            Map<String, Object> details = new HashMap<>();
            details.put("name", product.getName());
            details.put("price", product.getPrice());
            details.put("quantity", product.getQuantity());
            details.put("store", product.getStore());
            return Optional.of(details);
        }
        return Optional.empty();
    }

    // New method to get store owner's revenue
    public double getStoreOwnerRevenue(String username) {
        User user = getUserByUsername(username);
        if (user instanceof StoreOwner) {
            StoreOwner owner = (StoreOwner) user;
            return owner.getInventory().stream()
                    .mapToDouble(Product::getRevenue)
                    .sum();
        }
        return 0.0;
    }


    public List<String> getUserOrderHistory(String username) {
        return userOrderHistory.getOrDefault(username, new ArrayList<>());
    }


//--------------------------------------------------------------------------------------------------


    //admin functions

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public List<User> getAllOwners() {
        ArrayList<User> owners = new ArrayList<User>();
        for (User u : users) {
            if (u.getRole().equals("owner")) {
                owners.add(u);
            }
        }
        return owners;
    }

    public List<User> getAllSuppliers() {
        ArrayList<User> suppliers = new ArrayList<User>();
        for (User u : users) {
            if (u.getRole().equals("supplier")) {
                suppliers.add(u);
            }
        }
        return suppliers;
    }

    public void updateUserRole(String username, String newRole) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {    // call the getter from user class
                user.setRole(newRole);
                msg = "User role updated successfully.";
                lastOperationSuccessful = true;
                return;
            }
        }
        msg = "User not found.";
        lastOperationSuccessful = false;
    }

    public void deleteUser(String username) {
        boolean removed = users.removeIf(user -> user.getUsername().equals(username));
        if (removed) {
            msg = "User deleted successfully.";
            lastOperationSuccessful = true;
        } else {
            msg = "User not found.";
            lastOperationSuccessful = false;
        }
    }

    // profits and reports and every thing related to admin management
    //---------------------------------------------------------------------------------

    public Map<String, Double> calculateProfits() {
        Map<String, Double> profitBreakdown = new HashMap<>();
        double totalRevenue = 0;
        double totalCosts = 0;
        double totalProfit = 0;

        // Calculate revenue and costs for each store owner
        for (User user : users) {
            if (user instanceof StoreOwner) {
                StoreOwner owner = (StoreOwner) user;
                double storeRevenue = 0;
                double storeCosts = 0;
                for (Product product : owner.getInventory()) {
                    storeRevenue += product.getPrice() * product.getSalesQuantity();
                    storeCosts += product.getCost() * product.getSalesQuantity();
                }
                double storeProfit = storeRevenue - storeCosts;
                profitBreakdown.put(owner.getUsername() + " Revenue", storeRevenue);
                profitBreakdown.put(owner.getUsername() + " Costs", storeCosts);
                profitBreakdown.put(owner.getUsername() + " Profit", storeProfit);
                totalRevenue += storeRevenue;
                totalCosts += storeCosts;
                totalProfit += storeProfit;
            }
        }

        // Add totals to the breakdown
        profitBreakdown.put("Total Revenue", totalRevenue);
        profitBreakdown.put("Total Costs", totalCosts);
        profitBreakdown.put("Total Profit", totalProfit);

        // Calculate profit margin
        double profitMargin = totalRevenue > 0 ? (totalProfit / totalRevenue) * 100 : 0;
        profitBreakdown.put("Profit Margin (%)", profitMargin);

        return profitBreakdown;
    }


    public String generateFinancialReport() {
        StringBuilder report = new StringBuilder();
        Map<String, Double> profitData = calculateProfits();

        report.append("===== Financial Report =====\n");
        report.append(String.format("Date: %s\n\n", LocalDate.now()));

        // Overall Summary
        report.append("1. Overall Financial Summary\n");
        report.append(String.format("   Total Revenue: $%.2f\n", profitData.get("Total Revenue")));
        report.append(String.format("   Total Expenses: $%.2f\n", profitData.get("Total Costs"))); // Changed to "Expenses"
        report.append(String.format("   Total Profit: $%.2f\n", profitData.get("Total Profit")));
        report.append(String.format("   Profit Margin: %.2f%%\n\n", profitData.get("Profit Margin (%)")));

        this.lastGeneratedReport = report.toString();


        // Store-wise Breakdown
        report.append("2. Store-wise Financial Breakdown\n");
        for (User user : users) {
            if (user instanceof StoreOwner) {
                StoreOwner owner = (StoreOwner) user;
                report.append(String.format("   Store: %s\n", owner.getUsername()));
                report.append(String.format("     Revenue: $%.2f\n", profitData.get(owner.getUsername() + " Revenue")));
                report.append(String.format("     Costs: $%.2f\n", profitData.get(owner.getUsername() + " Costs")));
                report.append(String.format("     Profit: $%.2f\n", profitData.get(owner.getUsername() + " Profit")));
                double storeMargin = profitData.get(owner.getUsername() + " Revenue") > 0 ?
                        (profitData.get(owner.getUsername() + " Profit") / profitData.get(owner.getUsername() + " Revenue")) * 100 : 0;
                report.append(String.format("     Profit Margin: %.2f%%\n\n", storeMargin));
            }
        }

        // Top Selling Products
        report.append("3. Top Selling Products\n");
        Map<String, List<Product>> bestSellers = getBestSellingProductss();
        for (Map.Entry<String, List<Product>> entry : bestSellers.entrySet()) {
            report.append(String.format("   Store: %s\n", entry.getKey()));
            List<Product> products = entry.getValue();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                report.append(String.format("     %d. %s - Sold: %d, Revenue: $%.2f\n",
                        i + 1, product.getName(), product.getSalesQuantity(), product.getRevenue()));
            }
            report.append("\n");
        }

        // Sales Trend (placeholder for future implementation)
        report.append("4. Sales Trend\n");
        report.append("   Sales trend analysis is not available in this version.\n\n");

        // Recommendations (placeholder for future implementation)
        report.append("5. Recommendations\n");
        report.append("   Automated recommendations are not available in this version.\n");

        return report.toString();
    }


    private double calculateProductSalesRevenue() {
        return users.stream()
                .filter(user -> user instanceof StoreOwner)
                .map(user -> (StoreOwner) user)
                .flatMap(owner -> owner.getInventory().stream())
                .mapToDouble(Product::getRevenue)
                .sum();
    }

    private double calculateRecipeSalesRevenue() {
        return recipes.stream()
                .filter(recipe -> recipe.getPrice() > 0)
                .mapToDouble(recipe -> recipe.getPrice() * recipe.getPurchaseCount())
                .sum();
    }


    private List<Product> getTopSellingProducts(int limit) {
        return users.stream()
                .filter(user -> user instanceof StoreOwner)
                .map(user -> (StoreOwner) user)
                .flatMap(owner -> owner.getInventory().stream())
                .sorted((p1, p2) -> Integer.compare(p2.getSalesQuantity(), p1.getSalesQuantity()))
                .limit(limit)
                .collect(Collectors.toList());
    }


    public String getLastGeneratedReport() {
        if (lastGeneratedReport.isEmpty()) {
            msg = "No report has been generated yet.";
            lastOperationSuccessful = false;
            return null;
        }
        msg = "Last generated report retrieved successfully.";
        lastOperationSuccessful = true;
        return lastGeneratedReport;
    }


    private double calculateRevenue() {
        double totalRevenue = 0.0;

        // Calculate revenue from product sales
        for (User user : users) {
            if (user instanceof StoreOwner) {
                StoreOwner owner = (StoreOwner) user;
                totalRevenue += owner.getInventory().stream()
                        .mapToDouble(Product::getRevenue)
                        .sum();
            }
        }

        // Calculate revenue from recipe purchases (if applicable)
        totalRevenue += recipes.stream()
                .filter(recipe -> recipe.getPrice() > 0)
                .mapToDouble(recipe -> recipe.getPrice() * recipe.getPurchaseCount())
                .sum();

        // Add any additional revenue streams (e.g., subscription fees, if applicable)
        // totalRevenue += calculateSubscriptionRevenue();

        return totalRevenue;
    }

    private double calculateCOGS() {
        return users.stream()
                .filter(user -> user instanceof StoreOwner)
                .map(user -> (StoreOwner) user)
                .flatMap(owner -> owner.getInventory().stream())
                .mapToDouble(product -> product.getCost() * product.getSalesQuantity())
                .sum();
    }

    private double calculateOperationalExpenses() {
        // This is a simplified calculation. In a real system, you'd have more detailed data.
        double baseOperationalCost = 5000; // Monthly base cost
        double perUserCost = 0.5; // Cost per user
        double perTransactionCost = 0.1; // Cost per transaction

        int totalUsers = users.size();
        int totalTransactions = userOrderHistory.values().stream()
                .mapToInt(List::size)
                .sum();

        return baseOperationalCost + (perUserCost * totalUsers) + (perTransactionCost * totalTransactions);
    }

    private double calculateMarketingExpenses() {
        // This is a placeholder. In a real system, you'd track actual marketing expenses.
        return 1000; // Monthly marketing budget
    }

    private double calculateExpenses() {
        double totalExpenses = 0.0;

        // Calculate cost of goods sold (COGS)
        double cogs = calculateCOGS();
        totalExpenses += cogs;

        // Calculate operational expenses
        double operationalExpenses = calculateOperationalExpenses();
        totalExpenses += operationalExpenses;

        // Calculate marketing expenses (if applicable)
        double marketingExpenses = calculateMarketingExpenses();
        totalExpenses += marketingExpenses;

        // Add any other relevant expenses
        // totalExpenses += calculateOtherExpenses();

        return totalExpenses;
    }

    public Map<String, List<Product>> getBestSellingProductss() {
        Map<String, List<Product>> bestSellers = new HashMap<>();
        // Group products by store
        Map<String, List<Product>> productsByStore = new HashMap<>();
        for (Product product : products) {
            productsByStore.computeIfAbsent(product.getStore(), k -> new ArrayList<>()).add(product);
        }
        // Get top 5 products for each store
        for (Map.Entry<String, List<Product>> entry : productsByStore.entrySet()) {
            List<Product> storeProducts = entry.getValue();
            storeProducts.sort((p1, p2) -> Integer.compare(p2.getQuantity(), p1.getQuantity()));
            bestSellers.put(entry.getKey(), storeProducts.subList(0, Math.min(5, storeProducts.size())));
        }
        msg = "Best selling products report generated successfully.";
        lastOperationSuccessful = true;
        return bestSellers;
    }


    public Map<String, Double> getSalesReport(String ownerUsername) {
        User user = getUserByUsername(ownerUsername);
        if (user instanceof StoreOwner) {
            StoreOwner owner = (StoreOwner) user;
            Map<String, Double> salesReport = new HashMap<>();
            double totalRevenue = 0;
            for (Product product : owner.getInventory()) {
                double productRevenue = product.getPrice() * product.getSalesQuantity();
                salesReport.put(product.getName(), productRevenue);
                totalRevenue += productRevenue;
            }
            salesReport.put("Total Revenue", totalRevenue);
            msg = "Sales report generated successfully.";
            lastOperationSuccessful = true;
            return salesReport;
        }
        msg = "Failed to generate sales report. User is not a store owner.";
        lastOperationSuccessful = false;
        return null;
    }


    //----------------------------------------------------------------------------------
    public String getBestSellingProduct(String ownerUsername) {
        User user = getUserByUsername(ownerUsername);
        if (user instanceof StoreOwner) {
            StoreOwner owner = (StoreOwner) user;
            Optional<Product> bestSeller = owner.getInventory().stream()
                    .max(Comparator.comparingInt(Product::getSalesQuantity));
            if (bestSeller.isPresent()) {
                msg = "Best-selling product identified successfully.";
                lastOperationSuccessful = true;
                return bestSeller.get().getName();
            }
        }
        msg = "Failed to identify best-selling product.";
        lastOperationSuccessful = false;
        return null;
    }


    // for main
    public Map<String, List<Map<String, Object>>> identifyBestSellingProducts(int topN) {
        Map<String, List<Map<String, Object>>> bestSellingProducts = new HashMap<>();

        for (User user : users) {
            if (user instanceof StoreOwner) {
                StoreOwner owner = (StoreOwner) user;
                List<Product> storeProducts = owner.getInventory();

                // Sort products by sales quantity in descending order
                storeProducts.sort((p1, p2) -> Integer.compare(p2.getSalesQuantity(), p1.getSalesQuantity()));

                List<Map<String, Object>> topProducts = new ArrayList<>();
                for (int i = 0; i < Math.min(topN, storeProducts.size()); i++) {
                    Product product = storeProducts.get(i);
                    Map<String, Object> productInfo = new HashMap<>();
                    productInfo.put("name", product.getName());
                    productInfo.put("salesQuantity", product.getSalesQuantity());
                    productInfo.put("revenue", product.getRevenue());
                    productInfo.put("profitMargin", (product.getRevenue() - (product.getCost() * product.getSalesQuantity())) / product.getRevenue() * 100);
                    topProducts.add(productInfo);
                }

                bestSellingProducts.put(owner.getUsername(), topProducts);
            }
        }

        return bestSellingProducts;
    }


    // ----------------------------------------------------------------------------------------------------

    public Map<String, Double> getProfitReport(String ownerUsername) {
        User user = getUserByUsername(ownerUsername);
        if (user instanceof StoreOwner) {
            StoreOwner owner = (StoreOwner) user;
            Map<String, Double> profitReport = new HashMap<>();
            for (Product product : owner.getInventory()) {
                double profit = (product.getPrice() - product.getCost()) * product.getSalesQuantity();
                profitReport.put(product.getName(), profit);
            }
            msg = "Profit report generated successfully.";
            lastOperationSuccessful = true;
            return profitReport;
        }
        msg = "Failed to generate profit report. User is not a store owner.";
        lastOperationSuccessful = false;
        return null;
    }


    public Map<String, Integer> getUserStatisticsByCity() {
        Map<String, Integer> cityStats = new HashMap<>();

        for (User user : users) {
            String city = user.getCity();
            if (city != null && !city.isEmpty()) {
                cityStats.put(city, cityStats.getOrDefault(city, 0) + 1);
            }
        }

        return cityStats;
    }


    //------------------------------------------------------------------------------------
    //recipes


    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        msg = "Recipe added successfully.";
        lastOperationSuccessful = true;
    }

    public boolean deleteRecipe(int recipeId) {
        boolean removed = recipes.removeIf(recipe -> recipe.getId() == recipeId);
        if (removed) {
            msg = "Recipe deleted successfully.";
            lastOperationSuccessful = true;
        } else {
            msg = "Recipe not found.";
            lastOperationSuccessful = false;
        }
        return removed;
    }


    public Recipe getRecipeById(int recipeId) {
        return recipes.stream()
                .filter(recipe -> recipe.getId() == recipeId)
                .findFirst()
                .orElse(null);
    }



   /* public List<Recipe> searchRecipes(String keyword) {
        System.out.println("Searching for recipes containing: " + keyword);
        System.out.println("Total recipes before search: " + recipes.size());
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            System.out.println("Checking recipe: " + recipe.getName());
            if (recipe.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                recipe.getIngredients().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(recipe);
                System.out.println("Match found: " + recipe.getName());
            }
        }
        message = "Search completed. Found " + results.size() + " recipes.";
        lastOperationSuccessful = true;
        System.out.println(message);
        return results;
    }*/


    // saerch for recipe

    public List<Recipe> searchRecipes(String keyword) {
        System.out.println("Searching for recipes containing: " + keyword);
        System.out.println("Total recipes before search: " + recipes.size());
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            System.out.println("Checking recipe: " + recipe.getName());
            if (recipe.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    recipe.getIngredients().toLowerCase().contains(keyword.toLowerCase()) ||
                    recipe.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(recipe);
                System.out.println("Match found: " + recipe.getName());
            }
        }
        System.out.println("Search completed. Found " + results.size() + " recipes.");
        return results;
    }


    // browsing according to category
    public List<Recipe> browseRecipes(String category) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (category == null || category.isEmpty() || recipe.getCategory().equalsIgnoreCase(category)) {
                results.add(recipe);
            }
        }
        msg = "Browsing completed. Found " + results.size() + " recipes.";
        lastOperationSuccessful = true;
        return results;
    }


    public List<Recipe> filterRecipesByDiet(String dietaryRestriction) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getDietaryRestrictions().contains(dietaryRestriction)) {
                results.add(recipe);
            }
        }
        msg = "Filtered " + results.size() + " recipes for " + dietaryRestriction + " diet.";
        lastOperationSuccessful = true;
        return results;
    }


    public List<Recipe> filterRecipesByAllergy(String allergen) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (!recipe.getIngredients().toLowerCase().contains(allergen.toLowerCase())) {
                results.add(recipe);
            }
        }
        msg = "Filtered " + results.size() + " recipes excluding " + allergen + ".";
        lastOperationSuccessful = true;
        return results;
    }


    public List<Recipe> searchRecipesWithDietaryFilter(String keyword, String dietaryRestriction) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getDietaryRestrictions().contains(dietaryRestriction) &&
                    (recipe.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                            recipe.getIngredients().toLowerCase().contains(keyword.toLowerCase()))) {
                results.add(recipe);
            }
        }
        msg = "Found " + results.size() + " " + dietaryRestriction + " recipes containing " + keyword + ".";
        lastOperationSuccessful = true;
        return results;
    }


    //getter

    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipes);
    }


    // for feedback

    //--------------------------------------------------------------------------------------------------


    public boolean sendMessage(String senderUsername, String recipientUsername, String content) {
        User sender = getUserByUsername(senderUsername);
        User recipient = getUserByUsername(recipientUsername);
        if (sender != null && recipient != null) {
            Message message = new Message(senderUsername, recipientUsername, content);
            userMessages.computeIfAbsent(recipientUsername, k -> new ArrayList<>()).add(message);
            msg = "Message sent successfully.";
            lastOperationSuccessful = true;
            return true;
        }
        msg = "Failed to send message. User not found.";
        lastOperationSuccessful = false;
        return false;
    }

    public List<Message> getMessages(String username) {
        return userMessages.getOrDefault(username, new ArrayList<>());
    }

    public boolean provideFeedback(String username, String itemName, String content, boolean isRecipe) {
        User user = getUserByUsername(username);
        if (user != null) {
            Feedback feedback = new Feedback(feedbacks.size() + 1, content, username);
            feedbacks.add(feedback);
            itemFeedback.computeIfAbsent(itemName, k -> new ArrayList<>()).add(feedback);
            msg = "Feedback provided successfully for " + (isRecipe ? "recipe" : "product") + ".";
            lastOperationSuccessful = true;
            return true;
        }
        msg = "Failed to provide feedback. User not found.";
        lastOperationSuccessful = false;
        return false;
    }

    public List<Feedback> getItemFeedback(String itemName) {
        return itemFeedback.getOrDefault(itemName, new ArrayList<>());
    }

    public Recipe getRecipeByName(String name) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name)) {
                return recipe;
            }
        }
        return null;
    }


    public List<Feedback> getProductFeedback(String productName) {
        return productFeedback.getOrDefault(productName, new ArrayList<>());
    }


    public List<Feedback> getAllFeedback() {
        return new ArrayList<>(feedbacks);
    }


    public void addFeedback(String content, String author) {
        int newId = feedbacks.size() + 1; // Simple ID generation
        Feedback newFeedback = new Feedback(newId, content, author);
        feedbacks.add(newFeedback);
        msg = "Feedback added successfully.";
        lastOperationSuccessful = true;
    }


    public void resolveFeedback(int feedbackId) {
        for (Feedback feedback : feedbacks) {
            if (feedback.getId() == feedbackId) {
                feedback.setResolved(true);
                msg = "Feedback marked as resolved.";
                lastOperationSuccessful = true;
                return;
            }
        }
        msg = "Feedback not found.";
        lastOperationSuccessful = false;
    }

    public Feedback getFeedbackById(int feedbackId) {
        return feedbacks.stream()
                .filter(f -> f.getId() == feedbackId)
                .findFirst()
                .orElse(null);
    }


    // Product management
    public void addProduct(Product product) {
        products.add(product);
        msg = "Product added successfully.";
        lastOperationSuccessful = true;
    }


    public boolean setProductDiscount(String ownerUsername, String productName, double discountPercentage, LocalDate startDate, LocalDate endDate) {
        User user = getUserByUsername(ownerUsername);
        if (user instanceof StoreOwner) {
            StoreOwner owner = (StoreOwner) user;
            Product product = owner.getProductByName(productName);
            if (product != null) {
                product.setDiscount(discountPercentage, startDate, endDate);
                msg = "Discount applied successfully.";
                lastOperationSuccessful = true;
                return true;
            }
        }
        msg = "Failed to apply discount. Product not found or user is not a store owner.";
        lastOperationSuccessful = false;
        return false;
    }

    public boolean removeProductDiscount(String ownerUsername, String productName) {
        User user = getUserByUsername(ownerUsername);
        if (user instanceof StoreOwner) {
            StoreOwner owner = (StoreOwner) user;
            Product product = owner.getProductByName(productName);
            if (product != null) {
                product.removeDiscount();
                msg = "Discount removed successfully.";
                lastOperationSuccessful = true;
                return true;
            }
        }
        msg = "Failed to remove discount. Product not found or user is not a store owner.";
        lastOperationSuccessful = false;
        return false;
    }


    // Getters


    public boolean isLastOperationSuccessful() {
        return lastOperationSuccessful;
    }

    public User getCurrentUser() {
        return currentUser;
    }


    public void setMessage(String message) {
        this.msg = message;
    }

    // motasem
    //FOR MESSAGING SYSTEM
    public String checkRecipient(String recipient) {
        User userx = new User();
        userx.setRole(recipient);
        return userx.getRole();
    }


    public boolean receivedCheck(User u1) {
        if (u1.getRole().equals("admin") || u1.getRole().equals("supplier")) {
            return true;
        }
        return false;
    }

    //ORDERS MANAGEMENT
    public String orderSupervisor(String sup) {
        for (Order order : orders) {
            if (order.getSupervisor().equals(sup)) {
                return order.getSupervisor();
            }
        }
        return null;
    }

    public boolean orderStateIsProcessed(String status) {
        for (Order order : orders) {
            if (order.getStatus().equals(status)) return true;
        }
        return false;
    }

    public String getOrderState(String status) {
        for (Order order : orders) {
            if (order.getStatus().equals(status)) return status;
        }
        return null;
    }

    public void updateOrder(String id, String oldState, String newState) {

        for (Order order : orders) {
            if (order.getId().equals(id)) {
                Order x = new Order(order.getId(),order.getRecipient(),order.getPhone(),order.getAddress(),newState,order.getSupervisor());
                orders.remove(order);
                orders.add(x);

//                    order.setStatus(newState);
                break;
            }
        }

    }

    public String checkOrderState(String id) {
        for (Order order : orders) {
            if (order.getId().equals(id)) {

                return order.getStatus();
            }
        }

        return null;
    }

    public Order getOrderById(String id) {
        for (Order order : orders) {
            if (order.getId().equals(id)) {

                return order;
            }
        }

        return null;
    }
}
