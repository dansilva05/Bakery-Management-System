package ie.setu.bakeryca.models;

// asked Claude for help on how to get the result of two lists
public class SearchResult {

    private String type;
    private BakedGood bakedGood; // populated when type is "BakedGood"
    private Ingredient ingredient;

    // builds a result for a baked good match
    public SearchResult(BakedGood bakedGood) {
        this.type = "BakedGood";
        this.bakedGood = bakedGood;
    }

    // builds a result for an ingredient match
    public SearchResult(Ingredient ingredient) {
        this.type = "Ingredient";
        this.ingredient = ingredient;
    }

    public String getType() { return type; }
    public BakedGood getBakedGood() { return bakedGood; }
    public Ingredient getIngredient() { return ingredient; }

    // shown in the search results
    @Override
    public String toString() {
        if (type.equals("BakedGood")) {
            return "[Baked Good] " + bakedGood.toString();
        } else {
            return "[Ingredient] " + ingredient.toString();
        }
    }
}