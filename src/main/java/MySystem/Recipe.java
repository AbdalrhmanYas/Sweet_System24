package MySystem;

import java.util.HashSet;
import java.util.Set;

public class Recipe {
    private int id;
    private String name;
    private String ingredients;
    private String instructions;
    private String author;

    private String category;


    private Set<String> dietaryRestrictions;

    public Recipe(int id, String name, String ingredients, String instructions, String author, String category) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.author = author;
        this.category = category;
        this.dietaryRestrictions = new HashSet<>();
    }



    //This method returns a copy of the set of dietary restrictions associated with a Recipe
    public Set<String> getDietaryRestrictions()
    {
        return new HashSet<>(dietaryRestrictions);
    }

    public void addDietaryRestriction(String restriction) {
        dietaryRestrictions.add(restriction);
    }

    public void removeDietaryRestriction(String restriction) {
        dietaryRestrictions.remove(restriction);
    }



    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }  // to return the name of the recipe
    public String getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
    public String getAuthor() { return author; }

    public String getCategory() { return category; }


    private double price; // Price of the recipe, if it's for sale
    private int purchaseCount; // Number of times the recipe has been purchased

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getPurchaseCount() { return purchaseCount; }
    public void incrementPurchaseCount() { this.purchaseCount++; }
}