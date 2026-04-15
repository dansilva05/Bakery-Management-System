package ie.setu.bakeryca.models;

import ie.setu.bakeryca.core.LinkedList;
import java.io.Serializable;

public class Recipe implements Serializable {

    private LinkedList<RecipeGuide> entries;

    public Recipe() {
        entries = new LinkedList<>();
    }

    public LinkedList<RecipeGuide> getRecipes() { return entries; }

    public int size() { return entries.size(); }
    public boolean isEmpty() { return entries.isEmpty(); }

    // adds an ingredient + quantity pair to the recipe
    public void addEntry(RecipeGuide entry) {
        entries.add(entry);
    }

    // removes a recipe entry by index, returns true if it worked
    public boolean removeEntry(int index) {
        return entries.remove(index);
    }

    // gets the entry at the given index
    public RecipeGuide getRecipe(int index) {
        return entries.get(index);
    }

    // adds up the calories from every ingredient (weight/quantity taken in consideration)
    public double getTotalCalories() {
        double total = 0;
        for (int i = 0; i < entries.size(); i++) {
            RecipeGuide entry = entries.get(i);
            // e.g. 50g of a 500 kcal/100g ingredient => 250 kcal
            total += (entry.getQuantity() / 100.0) * entry.getIngredient().getCaloriesPer100g();
        }
        return total;
    }

    public String getDisplayText() {
        if (entries.isEmpty()) {
            return "  No ingredients added yet.";
        }
        String result = "";
        for (int i = 0; i < entries.size(); i++) {
            result += "  " + (i + 1) + ". " + entries.get(i).toString() + "\n";
        }
        return result.trim();
    }
}