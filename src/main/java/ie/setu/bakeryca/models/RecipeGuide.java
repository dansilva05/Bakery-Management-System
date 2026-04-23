package ie.setu.bakeryca.models;

import java.io.Serializable;

public class RecipeGuide implements Serializable {

    private Ingredient ingredient;
    private double quantity;  // in grams or millilitres

    public RecipeGuide(Ingredient ingredient, double quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Ingredient getIngredient() { return ingredient; }
    public double getQuantity() { return quantity; }

    public void setQuantity(double quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return ingredient.getName() + " - " + quantity + "g";
    }
}
