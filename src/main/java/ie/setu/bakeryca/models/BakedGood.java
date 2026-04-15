package ie.setu.bakeryca.models;

import java.io.Serializable;

public class BakedGood implements Serializable {

    private String name;
    private String placeOfOrigin;
    private String description;
    private String imageUrl;
    private Recipe recipe;

    public BakedGood(String name, String placeOfOrigin, String description, String imageUrl) {
        this.name = name;
        this.placeOfOrigin = placeOfOrigin;
        this.description = description;
        this.imageUrl = imageUrl;
        recipe = new Recipe();
    }

    public String getName() { return name; }
    public String getPlaceOfOrigin() { return placeOfOrigin; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public Recipe getRecipe() { return recipe; }

    // adds an ingredient + quantity to the baked good's recipe
    public void addRecipeEntry(RecipeGuide entry) {
        recipe.addEntry(entry);
    }

    // removes a recipe entry by index, returns true if it worked
    public boolean removeRecipeEntry(int index) {
        return recipe.removeEntry(index);
    }

    // adds up the calories from every ingredient in the recipe
    public double getTotalCalories() {
        return recipe.getTotalCalories();
    }

    // checks name, origin, description, and all recipe ingredients for what is being searched
    public boolean matchesSearch(String term) {
        String lower = term.toLowerCase();
        if (name.toLowerCase().contains(lower)
                || placeOfOrigin.toLowerCase().contains(lower)
                || description.toLowerCase().contains(lower)) {
            return true;
        }
        for (int i = 0; i < recipe.size(); i++) {  // checks each recipe entry too
            if (recipe.getRecipe(i).getIngredient().matchesSearch(term)) {
                return true;
            }
        }
        return false;
    }

    public String getFullDetails() {
        return "Name: " + name + "\n"
                + "Place of Origin: " + placeOfOrigin + "\n"
                + "Description: " + description + "\n"
                + "Image URL: " + imageUrl + "\n"
                + "Total Calories: " + String.format("%.0f", getTotalCalories()) + " kcal\n"
                + "\n--- Recipe / Ingredients ---\n"
                + recipe.getDisplayText();
    }

    @Override
    public String toString() {
        return name + " (" + placeOfOrigin + ")";
    }
}