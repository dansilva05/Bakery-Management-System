package ie.setu.bakeryca.models;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private String name;
    private String description;
    private double caloriesPer100g;  // kcal per 100g or 100ml

    public Ingredient(String name, String description, double caloriesPer100g) {
        this.name = name;
        this.description = description;
        this.caloriesPer100g = caloriesPer100g;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getCaloriesPer100g() { return caloriesPer100g; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCaloriesPer100g(double caloriesPer100g) { this.caloriesPer100g = caloriesPer100g; }

    // checks if the searched term matches with anything in the ingredient
    // now depending on 'search by' option
    public boolean matchesSearch(String term, String option) {
        String lower = term.toLowerCase();
        if (option.equals("Name only")) {
            return name.toLowerCase().contains(lower);
        }
        if (option.equals("Description only")) {
            return description.toLowerCase().contains(lower);
        }
        // "Any field"
        return name.toLowerCase().contains(lower)
                || description.toLowerCase().contains(lower)
                || String.valueOf(caloriesPer100g).contains(lower);
    }

    @Override
    public String toString() {
        return name + " (" + caloriesPer100g + " kcal/100g)";
    }

    // full info for display
    public String getFullDetails() {
        return "Ingredient: " + name + "\n"
                + "Description: " + description + "\n"
                + "Calories: " + caloriesPer100g + " kcal per 100g/ml";
    }
}