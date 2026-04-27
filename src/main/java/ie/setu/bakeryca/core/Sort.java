package ie.setu.bakeryca.core;

import ie.setu.bakeryca.models.*;

public class Sort {

    private Sort() {}

    // sorts baked goods alphabetically by name
    public static void sortBakedGoodsByName(LinkedList<BakedGood> list) {
        for (int e = 1; e < list.size(); e++) {
            BakedGood elem = list.get(e);
            int i;
            for (i = e; i >= 1 && list.get(i - 1).getName().compareToIgnoreCase(elem.getName()) > 0; i--) {
                list.set(i, list.get(i - 1));
            }
            list.set(i, elem);
        }
    }

    // sorts baked goods by total calories, lowest to highest
    public static void sortBakedGoodsByCalories(LinkedList<BakedGood> list) {
        for (int e = 1; e < list.size(); e++) {
            BakedGood elem = list.get(e);
            double elemCals = elem.getRecipe().getTotalCalories();
            int i;
            for (i = e; i >= 1 && list.get(i - 1).getRecipe().getTotalCalories() > elemCals; i--) {
                list.set(i, list.get(i - 1));
            }
            list.set(i, elem);
        }
    }

    // sorts ingredients alphabetically by name
    public static void sortIngredientsByName(LinkedList<Ingredient> list) {
        for (int e = 1; e < list.size(); e++) {
            Ingredient elem = list.get(e);
            int i;
            for (i = e; i >= 1 && list.get(i - 1).getName().compareToIgnoreCase(elem.getName()) > 0; i--) {
                list.set(i, list.get(i - 1));
            }
            list.set(i, elem);
        }
    }

    // sorts ingredients by calories per 100g, lowest to highest
    public static void sortIngredientsByCalories(LinkedList<Ingredient> list) {
        for (int e = 1; e < list.size(); e++) {
            Ingredient elem = list.get(e);
            double elemCals = elem.getCaloriesPer100g();
            int i;
            for (i = e; i >= 1 && list.get(i - 1).getCaloriesPer100g() > elemCals; i--) {
                list.set(i, list.get(i - 1));
            }
            list.set(i, elem);
        }
    }

    // sorts mixed search results alphabetically by name
    public static void sortSearchResultsByName(LinkedList<SearchResult> list) {
        for (int e = 1; e < list.size(); e++) {
            SearchResult elem = list.get(e);
            int i;
            for (i = e; i >= 1 && nameOf(list.get(i - 1)).compareToIgnoreCase(nameOf(elem)) > 0; i--) {
                list.set(i, list.get(i - 1));
            }
            list.set(i, elem);
        }
    }

    // sorts mixed search results by calories, lowest first
    // (baked goods use total recipe calories, ingredients use kcal/100g)
    public static void sortSearchResultsByCalories(LinkedList<SearchResult> list) {
        for (int e = 1; e < list.size(); e++) {
            SearchResult elem = list.get(e);
            double elemCals = caloriesOf(elem);
            int i;
            for (i = e; i >= 1 && caloriesOf(list.get(i - 1)) > elemCals; i--) {
                list.set(i, list.get(i - 1));
            }
            list.set(i, elem);
        }
    }

    // helper - pulls the name out of either kind of search result
    private static String nameOf(SearchResult sr) {
        if (sr.getType().equals("BakedGood")) {
            return sr.getBakedGood().getName();
        } else {
            return sr.getIngredient().getName();
        }
    }

    // helper - pulls the calorie value out of either kind of search result
    private static double caloriesOf(SearchResult sr) {
        if (sr.getType().equals("BakedGood")) {
            return sr.getBakedGood().getRecipe().getTotalCalories();
        } else {
            return sr.getIngredient().getCaloriesPer100g();
        }
    }
}