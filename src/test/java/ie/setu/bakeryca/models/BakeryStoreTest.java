package ie.setu.bakeryca.models;

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
}