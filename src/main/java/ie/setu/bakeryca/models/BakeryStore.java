package ie.setu.bakeryca.models;

import ie.setu.bakeryca.core.LinkedList;
import java.io.Serializable;

public class BakeryStore implements Serializable {

    private LinkedList<BakedGood> bakedGoods;
    private LinkedList<Ingredient> ingredients;

    public BakeryStore() {
        bakedGoods = new LinkedList<>();
        ingredients = new LinkedList<>();
    }

    public LinkedList<BakedGood> getBakedGoods() {
        return bakedGoods;
    }

    public LinkedList<Ingredient> getIngredients() {
        return ingredients;
    }

    // adds a baked good if the name isn't already taken
    public boolean addBakedGood(BakedGood bg) {
        if (findBakedGood(bg.getName()) != null) {
            return false;
        }
        bakedGoods.add(bg);
        return true;
    }

    // adds an ingredient if the name isn't already taken
    public boolean addIngredient(Ingredient ing) {
        if (findIngredient(ing.getName()) != null) {
            return false;
        }
        ingredients.add(ing);
        return true;
    }

    // adds an ingredient + quantity to a baked good's recipe
    public boolean addRecipe(String bakedGoodName, String ingredientName, double quantity) {
        BakedGood bg = findBakedGood(bakedGoodName);
        Ingredient ing = findIngredient(ingredientName);
        if (bg == null || ing == null) {
            return false;
        }
        bg.getRecipe().addEntry(new RecipeGuide(ing, quantity));
        return true;
    }

    // edits a baked good's field, when field is filled. Null or empty values, keep old value
    public boolean editBakedGood(String currentName, String newName, String origin, String desc, String img) {
        BakedGood bg = findBakedGood(currentName);
        if (bg == null) return false;

        // if new name is different and not empty, it checks if name is not already taken
        if (newName != null && !newName.isEmpty() && !newName.equalsIgnoreCase(currentName)) {
            if (findBakedGood(newName) != null) return false; // new name clashes with another
            bg.setName(newName);
        }
        if (origin != null && !origin.isEmpty()) bg.setPlaceOfOrigin(origin);
        if (desc != null && !desc.isEmpty()) bg.setDescription(desc);
        if (img != null && !img.isEmpty()) bg.setImageUrl(img);
        return true;
    }

    // edits a baked good's field, when field is filled. Null or empty values = keep old value
    public boolean editIngredient(String currentName, String newName, String desc, String calsStr) {
        Ingredient ing = findIngredient(currentName);
        if (ing == null) return false;

        if (newName != null && !newName.isEmpty() && !newName.equalsIgnoreCase(currentName)) {
            if (findIngredient(newName) != null) return false;
            ing.setName(newName);
        }
        if (desc != null && !desc.isEmpty()) ing.setDescription(desc);
        if (calsStr != null && !calsStr.isEmpty()) {
            try {
                ing.setCaloriesPer100g(Float.parseFloat(calsStr));
            } catch (NumberFormatException e) {
                return false;  // user didn't type a number
            }
        }
        return true;
    }

    // finds a baked good by its name, returns null if not found
    public BakedGood findBakedGood(String name) {
        for (int i = 0; i < bakedGoods.size(); i++) {
            if (bakedGoods.get(i).getName().equalsIgnoreCase(name)) {
                return bakedGoods.get(i);
            }
        }
        return null;
    }

    // finds an ingredient by its name, returns null if not found
    public Ingredient findIngredient(String name) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().equalsIgnoreCase(name)) {
                return ingredients.get(i);
            }
        }
        return null;
    }

    // removes a baked good by name, returns true if it worked
    public boolean removeBakedGood(String name) {
        for (int i = 0; i < bakedGoods.size(); i++) {
            if (bakedGoods.get(i).getName().equalsIgnoreCase(name)) {
                return bakedGoods.remove(i);
            }
        }
        return false;
    }

    // removes an ingredient by name, returns true if it worked
    // also takes it out of every recipe to leave it anywhere behind
    public boolean removeIngredient(String name) {
        Ingredient target = findIngredient(name);
        if (target == null) return false;

        // takes out from every recipe first
        for (int i = 0; i < bakedGoods.size(); i++) {
            Recipe r = bakedGoods.get(i).getRecipe();
            int j = 0;
            while (j < r.size()) {
                if (r.getRecipe(j).getIngredient() == target) {
                    r.removeEntry(j);  // list shrinks
                } else {
                    j++;
                }
            }
        }
        // then remove from the main ingredients list
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().equalsIgnoreCase(name)) {
                return ingredients.remove(i);
            }
        }
        return false;
    }

    // removes a recipe entry by index from a baked good
    public boolean removeRecipeGuide(String bakedGoodName, int entryIndex) {
        BakedGood bg = findBakedGood(bakedGoodName);
        if (bg == null) return false;
        return bg.getRecipe().removeEntry(entryIndex);
    }

    // searches every baked good and every ingredient for anything matching the term
    public LinkedList<SearchResult> search(String term) {
        LinkedList<SearchResult> results = new LinkedList<>();
        for (int i = 0; i < bakedGoods.size(); i++) {
            if (bakedGoods.get(i).matchesSearch(term)) {
                results.add(new SearchResult(bakedGoods.get(i)));
            }
        }
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).matchesSearch(term)) {
                results.add(new SearchResult(ingredients.get(i)));
            }
        }
        return results;
    }

    // finds every baked good that contains a given ingredient (used for drill-down)
    public LinkedList<BakedGood> findBakedGoodsWithIngredient(Ingredient target) {
        LinkedList<BakedGood> results = new LinkedList<>();
        for (int i = 0; i < bakedGoods.size(); i++) {
            BakedGood bg = bakedGoods.get(i);
            Recipe r = bg.getRecipe();
            for (int j = 0; j < r.size(); j++) {
                if (r.getRecipe(j).getIngredient() == target) {
                    results.add(bg);
                    break;  // already added, move on to the next baked good
                }
            }
        }
        return results;
    }

    // builds a text report of total calories for every baked good (GPT)
    public String getCaloriesReport() {
        if (bakedGoods.isEmpty()) {
            return "No baked goods in the store to report.";
        }
        String report = "=== Calories Report ===\n\n";
        for (int i = 0; i < bakedGoods.size(); i++) {
            BakedGood bg = bakedGoods.get(i);
            report += bg.getName() + "  ->  " + String.format("%.0f", bg.recipe.getTotalCalories()) + " kcal\n";
        }
        return report.trim();
    }

    // lists everything in the store as text
    public String getAllStockReport() {
        if (bakedGoods.isEmpty() && ingredients.isEmpty()) {
            return "No stock in the store yet.";
        }
        String report = "--- Baked Goods ---\n";
        if (bakedGoods.isEmpty()) {
            report += "  (none)\n";
        } else {
            for (int i = 0; i < bakedGoods.size(); i++) {
                BakedGood bg = bakedGoods.get(i);
                Recipe bgRecipe = bg.getRecipe();
                report += bg.toString() + "\n";
                if (bgRecipe.isEmpty()) {
                    report += "   (no ingredients in recipe)\n";
                } else {
                    for (int j = 0; j < bgRecipe.size(); j++) {
                        report += "   - " + bgRecipe.getRecipe(j).toString() + "\n";
                    }
                }
            }
        }
        report += "\n--- Ingredients ---\n";
        if (ingredients.isEmpty()) {
            report += "  (none)\n";
        } else {
            for (int i = 0; i < ingredients.size(); i++) {
                report += ingredients.get(i).toString() + "\n";
            }
        }
        return report.trim();
    }

    // clears everything
    public void clear() {
        bakedGoods.clear();
        ingredients.clear();
    }
}