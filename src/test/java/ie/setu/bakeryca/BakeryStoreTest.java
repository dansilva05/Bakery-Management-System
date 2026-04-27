package ie.setu.bakeryca;

import ie.setu.bakeryca.models.*;
import ie.setu.bakeryca.core.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BakeryStoreTest {

    @Test
    public void addBakedGoodRejectsDuplicates() {
        BakeryStore store = new BakeryStore();
        boolean firstAdd = store.addBakedGood(new BakedGood("Cake", "Ireland", "sponge", ""));
        boolean secondAdd = store.addBakedGood(new BakedGood("Cake", "France", "different", ""));

        assertTrue(firstAdd, "first add should succeed");
        assertFalse(secondAdd, "duplicate name should be rejected");
        assertEquals(1, store.getBakedGoods().size(), "should still only have 1 cake");
    }

    @Test
    public void addIngredientRejectsDuplicates() {
        BakeryStore store = new BakeryStore();
        boolean firstAdd = store.addIngredient(new Ingredient("Flour", "plain", 364));
        boolean secondAdd = store.addIngredient(new Ingredient("Flour", "wholemeal", 340));

        assertTrue(firstAdd, "first add should succeed");
        assertFalse(secondAdd, "duplicate name should be rejected");
        assertEquals(1, store.getIngredients().size(), "should still only have 1 flour");
    }

    @Test
    public void getTotalCaloriesIsWeightedByQuantity() {
        Ingredient darkChoc = new Ingredient("Dark Chocolate", "70% cocoa", 500);
        Ingredient flour = new Ingredient("Flour", "plain", 364);

        BakedGood brownies = new BakedGood("Brownies", "USA", "Chocolatey", "");
        brownies.getRecipe().addEntry(new RecipeGuide(darkChoc, 50)); // 250 kcal
        brownies.getRecipe().addEntry(new RecipeGuide(flour, 100)); // 364 kcal

        double expected = 250 + 364; // 614 kcal
        assertEquals(expected, brownies.getRecipe().getTotalCalories(), 0.001);
    }

    @Test
    public void removeIngredientStripsItFromRecipes() {
        BakeryStore store = new BakeryStore();
        store.addIngredient(new Ingredient("Flour", "plain", 364));
        store.addIngredient(new Ingredient("Sugar", "white", 387));
        store.addBakedGood(new BakedGood("Cake", "Ireland", "sponge", ""));
        store.addBakedGood(new BakedGood("Bread", "France", "loaf", ""));

        // Flour goes into both recipes, Sugar only into Cake
        store.addRecipe("Cake", "Flour", 200);
        store.addRecipe("Cake", "Sugar", 150);
        store.addRecipe("Bread", "Flour", 300);

        // before remove: Cake has 2 entries, Bread has 1
        assertEquals(2, store.findBakedGood("Cake").getRecipe().size());
        assertEquals(1, store.findBakedGood("Bread").getRecipe().size());

        boolean removed = store.removeIngredient("Flour");

        assertTrue(removed, "remove should report success");
        assertNull(store.findIngredient("Flour"), "Flour should not be in the store");
        assertEquals(1, store.findBakedGood("Cake").getRecipe().size(), "Cake should now only have Sugar");
        assertEquals(0, store.findBakedGood("Bread").getRecipe().size(), "Bread should have no entries left");
    }

    @Test
    public void searchMatchesByNameDescriptionAndRecipe() {
        BakeryStore store = new BakeryStore();
        store.addIngredient(new Ingredient("Dark Chocolate", "70% cocoa", 500));
        store.addBakedGood(new BakedGood("Brownies", "USA", "Chocolatey treat", ""));
        store.addBakedGood(new BakedGood("Cake", "Ireland", "Plain sponge", ""));

        store.addRecipe("Brownies", "Dark Chocolate", 50);

        // search by name - should match Brownies (name contains "brown")
        LinkedList<SearchResult> byName = store.search("brown", "Any field");
        assertEquals(1, byName.size(), "should find Brownies by name");

        // search by description - should match Brownies (description contains "treat")
        LinkedList<SearchResult> byDesc = store.search("treat", "Any field");
        assertEquals(1, byDesc.size(), "should find Brownies by description");

        // search by recipe ingredient - "chocolate" matches Brownies' recipe AND the ingredient itself
        LinkedList<SearchResult> byRecipeIng = store.search("chocolate", "Any field");
        assertEquals(2, byRecipeIng.size(), "should find Brownies (via recipe) and Dark Chocolate (the ingredient)");

        // negative case - nothing matches
        LinkedList<SearchResult> noMatch = store.search("pizza", "Any field");
        assertEquals(0, noMatch.size(), "no results for pizza");
    }

}