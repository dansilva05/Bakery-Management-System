package ie.setu.bakeryca.models;

import java.io.Serializable;

public class BakedGood implements Serializable {

    private String name;
    private String placeOfOrigin;
    private String description;
    private String imageUrl;
    Recipe recipe;

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

    public void setName(String name) { this.name = name; }
    public void setPlaceOfOrigin(String placeOfOrigin) { this.placeOfOrigin = placeOfOrigin; }
    public void setDescription(String description) { this.description = description; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // adds an ingredient + quantity to the baked good's recipe
    public void addRecipeEntry(RecipeGuide entry) {
        recipe.addEntry(entry);
    }

    // removes a recipe entry by index, returns true if it worked
    public boolean removeRecipeEntry(int index) {
        return recipe.removeEntry(index);
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
                + "Total Calories: " + String.format("%.0f", recipe.getTotalCalories()) + " kcal\n"
                + "\n--- Recipe / Ingredients ---\n"
                + recipe.getDisplayText();
    }

    @Override
    public String toString() {
        return name + " (" + placeOfOrigin + ")";
    }
}