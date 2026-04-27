package ie.setu.bakeryca.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import ie.setu.bakeryca.services.*;
import ie.setu.bakeryca.models.*;
import ie.setu.bakeryca.core.*;
import ie.setu.bakeryca.*;
import static java.lang.Float.*;

public class BakeryController {

    // Add Baked Good section
    @FXML private TextField addBgName;
    @FXML private ChoiceBox<String> choiceBgOrigin;
    @FXML private TextField addBgDesc;
    @FXML private TextField addBgImg;

    // Add Ingredient section
    @FXML private TextField addIngName;
    @FXML private TextField addIngDesc;
    @FXML private TextField addIngCals;

    // Add recipe guide section
    @FXML private ChoiceBox<String> choiceRecipeBg;
    @FXML private ChoiceBox<String> choiceRecipeIng;
    @FXML private TextField addRecipeQty;

    // Edit Baked Good section
    @FXML private ChoiceBox<String> choiceEditBg;
    @FXML private TextField editBgName;
    @FXML private ChoiceBox<String> editChoiceBgOrigin;
    @FXML private TextField editBgDesc;
    @FXML private TextField editBgImg;

    // Edit Ingredient section
    @FXML private ChoiceBox<String> choiceEditIng;
    @FXML private TextField editIngName;
    @FXML private TextField editIngDesc;
    @FXML private TextField editIngCals;

    // Edit recipe guide section
    @FXML private ChoiceBox<String> choiceEditRecipe;
    @FXML private ChoiceBox<String> editChoiceRecipeBg;
    @FXML private ChoiceBox<String> editChoiceRecipeIng;
    @FXML private TextField editRecipeQty;

    // Remove section
    @FXML private ChoiceBox<String> choiceRemoveBg;
    @FXML private ChoiceBox<String> choiceRemoveIng;
    @FXML private ChoiceBox<String> choiceRemoveRecipeBg;
    @FXML private ChoiceBox<String> choiceRemoveGuide;

    // Search section
    @FXML private ComboBox<String> browseBakedGood;
    @FXML private ComboBox<String> browseIngredient;
    @FXML private ImageView itemImage;
    @FXML private TextArea itemDetails;
    @FXML private TextArea allStockDetails;
    @FXML private ChoiceBox<String> choiceSearchBy;
    @FXML private TextField searchBar;
    @FXML private ListView<String> searchResults;

    // Reports section
    @FXML private Label topMessage;
    @FXML private TextArea reportArea;

    private LinkedList<SearchResult> lastSearchResults = new LinkedList<>();

    @FXML public void initialize() {
        reportMessage("Welcome!");
        fillAllChoiceBoxes();
        refreshAllStock();

        searchResults.setOnMouseClicked(event -> searchResultClicked());

        // GPT wrote all the countries name as Strings
        choiceBgOrigin.getItems().addAll("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Costa Rica", "Côte d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe");
        editChoiceBgOrigin.getItems().addAll("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Costa Rica", "Côte d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe");
        choiceSearchBy.getItems().addAll("Any field", "Name only", "Description only");
        choiceSearchBy.getSelectionModel().selectFirst();

        itemDetails.setText("Select a baked good or ingredient to see details here.");
        reportArea.setText("Use the buttons above to generate reports, save, load, or reset.");
        allStockDetails.setText(AppData.getStore().getAllStockReport());
    }

    // add baked good
    @FXML private void addBakedGood() {
        String name = addBgName.getText().trim();
        String origin = choiceBgOrigin.getValue();
        String desc = addBgDesc.getText().trim();
        String img = addBgImg.getText().trim();

        // checks if all fields were filled
        if (name.isEmpty() || origin.isEmpty() || desc.isEmpty()) {
            reportMessage("Please fill all sections: Name, Place of Origin and Description");
            return;
        }

        // tries to add the baked good
        boolean added = AppData.getStore().addBakedGood(new BakedGood(name, origin, desc, img));
        if (added) { // it worked so all fields are cleared for new baked goods to be added
            addBgName.clear();
            choiceBgOrigin.getSelectionModel().clearSelection();
            addBgDesc.clear();
            addBgImg.clear();
            fillAllChoiceBoxes();
            refreshAllStock();
            allStockDetails.setText(AppData.getStore().getAllStockReport());
            reportMessage("Baked good '" + name + "' added successfully.");
        } else {
            reportMessage("A baked good named '" + name + "' already exists. Choose a different name.");
        }
    }

    // add ingredient
    @FXML private void addIngredient() {
        String name = addIngName.getText().trim();
        String calStr = addIngCals.getText().trim();
        String desc = addIngDesc.getText().trim();

        // checks if all fields were filled
        if (name.isEmpty() || calStr.isEmpty() || desc.isEmpty()) {
            reportMessage("Please fill all sections: Name, Place of Origin and Description");
            return;
        }

        // tries to parse number required fields
        float cals;
        try {
        cals = parseFloat(calStr);
        } catch (NumberFormatException e) {
            reportMessage("calories must be a number (eg. 350.0");
            return;
        }

        // tries to add ingredient
        boolean added = AppData.getStore().addIngredient(new Ingredient(name, desc, cals));
        if (added) { // it worked so all fields are cleared for new baked goods to be added
            addIngName.clear();
            addIngDesc.clear();
            addIngCals.clear();
            fillAllChoiceBoxes();
            refreshAllStock();
            reportMessage("Ingredient '" + name + "' added successfully.");
        } else {
            reportMessage("An ingredient named '" + name + "' already exists. Try adding another ingredient.");
        }
    }

    // add recipe guide
    @FXML private void addRecipeGuide() {
        String bgName = choiceRecipeBg.getValue();
        String ingName = choiceRecipeIng.getValue();
        String qtyStr = addRecipeQty.getText().trim();

        // checks if all fields were filled
        if (bgName == null || ingName == null || qtyStr.isEmpty()) {
            reportMessage("Please select a baked good, an ingredient, and enter a quantity.");
            return;
        }

        // tries to parse number required fields
        float qty;
        try {
            qty = parseFloat(qtyStr);
        } catch (NumberFormatException e) {
            reportMessage("Quantity must be a number (e.g. 50.0).");
            return;
        }

        // tries to add recipe guide
        boolean added = AppData.getStore().addRecipe(bgName, ingName, qty);
        if (added) { // it worked so all fields are cleared for new baked goods to be added
            addRecipeQty.clear();
            fillAllChoiceBoxes();
            refreshAllStock();
            reportMessage("Added " + qty + "g of '" + ingName + "' to '" + bgName + "'.");
        } else {
            reportMessage("Could not add recipe guide. Make sure the baked good and ingredient exist.");
        }
    }

    // edit baked good
    @FXML private void editBakedGood() {
        String currentName = choiceEditBg.getValue();
        if (currentName == null) {
            reportMessage("Please choose a baked good to edit.");
            return;
        }

        String newName = editBgName.getText().trim();
        String origin = editChoiceBgOrigin.getValue();
        String desc = editBgDesc.getText().trim();
        String img = editBgImg.getText().trim();

        // tries to edit baked good
        boolean edited = AppData.getStore().editBakedGood(currentName, newName, origin, desc, img);
        if (edited) { // it worked so all fields are cleared for other baked goods to be edited
            editBgName.clear();
            editChoiceBgOrigin.getSelectionModel().clearSelection();
            editBgDesc.clear();
            editBgImg.clear();
            fillAllChoiceBoxes();
            refreshAllStock();
            reportMessage("Baked good '" + currentName + "' edited successfully.");
        } else {
            reportMessage("Could not edit baked good. The new name might already be taken.");
        }
    }

    // edit ingredient
    @FXML private void editIngredient() {
        String currentName = choiceEditIng.getValue();
        if (currentName == null) {
            reportMessage("Please choose an ingredient to edit.");
            return;
        }

        String newName = editIngName.getText().trim();
        String desc = editIngDesc.getText().trim();
        String calsStr = editIngCals.getText().trim();

        // tries to edit ingredient
        boolean edited = AppData.getStore().editIngredient(currentName, newName, desc, calsStr);
        if (edited) { // it worked so all fields are cleared for other ingredients to be edited
            editIngName.clear();
            editIngDesc.clear();
            editIngCals.clear();
            fillAllChoiceBoxes();
            refreshAllStock();
            reportMessage("Ingredient '" + currentName + "' edited successfully.");
        } else {
            reportMessage("Could not edit ingredient. The new name might be taken, or calories is not a number.");
        }
    }

    // edit recipe guide
    @FXML private void editRecipeGuide() {
        String bgName = choiceEditRecipe.getValue();
        String ingName = editChoiceRecipeIng.getValue();
        String qtyStr = editRecipeQty.getText().trim();

        // checks if all fields were filled
        if (bgName == null || ingName == null || qtyStr.isEmpty()) {
            reportMessage("Please choose a baked good, ingredient, and enter a new quantity.");
            return;
        }

        // tries to parse number required fields
        float qty;
        try {
            qty = parseFloat(qtyStr);
        } catch (NumberFormatException e) {
            reportMessage("Quantity must be a number (e.g. 50.0).");
            return;
        }

        // tries to edit recipe guide
        boolean edited = AppData.getStore().editRecipeGuide(bgName, ingName, qty);
        if (edited) { // it worked so all fields are cleared for other recipe guides to be added
            editRecipeQty.clear();
            fillAllChoiceBoxes();
            refreshAllStock();
            reportMessage("Recipe entry edited: " + ingName + " in " + bgName + " is now " + qty + "g.");
        } else {
            reportMessage("Could not edit recipe entry. Please re-select and try again.");
        }
    }

    // remove baked good
    @FXML private void removeBakedGood() {
        String name = choiceRemoveBg.getValue();
        if (name == null) {
            reportMessage("Please select a baked good to remove.");
            return;
        }

        // tries to remove baked good
        boolean removed = AppData.getStore().removeBakedGood(name);
        if (removed) { // it worked so all fields are cleared for other baked goods to be removed
            fillAllChoiceBoxes();
            refreshAllStock();
            itemDetails.setText("Baked good removed. Select another to view details.");
            itemImage.setImage(null);
            reportMessage("Baked good '" + name + "' removed.");
        } else {
            reportMessage("Could not remove baked good. Please re-select and try again.");
        }
    }

    // remove ingredient
    @FXML private void removeIngredient() {
        String name = choiceRemoveIng.getValue();
        if (name == null) {
            reportMessage("Please select an ingredient to remove.");
            return;
        }

        // tries to remove ingredient
        boolean removed = AppData.getStore().removeIngredient(name);
        if (removed) { // it worked so all fields are cleared for other ingredient to be removed
            fillAllChoiceBoxes();
            refreshAllStock();
            itemDetails.setText("Ingredient removed. Select another to view details.");
            itemImage.setImage(null);
            reportMessage("Ingredient '" + name + "' removed (and cleared from any recipes using it).");
        } else {
            reportMessage("Could not remove ingredient. Please re-select and try again.");
        }
    }

    // remove recipe guide
    @FXML private void removeRecipeGuide() {
        String bgName = choiceRemoveRecipeBg.getValue();
        int entryIndex = choiceRemoveGuide.getSelectionModel().getSelectedIndex();

        // checks if all fields were filled
        if (bgName == null || entryIndex < 0) {
            reportMessage("Please select a baked good and a recipe guide to remove.");
            return;
        }

        // tries to remove recipe guide
        boolean removed = AppData.getStore().removeRecipeGuide(bgName, entryIndex);
        if (removed) { // it worked so all fields are cleared for other recipe guides to be removed
            fillAllChoiceBoxes();
            refreshAllStock();
            reportMessage("Recipe guide removed from '" + bgName + "'.");
        } else {
            reportMessage("Could not remove recipe guide. Please re-select and try again.");
        }
    }

    // called when recipe guide 'baked good' is filled. Fills recipe guide ChoiceBox.
    @FXML private void removeRecipeBgChanged() {
        fillRecipeGuideForRemove(choiceRemoveGuide);
    }

    // called when baked good is filled. Fills ingredient ChoiceBox.
    @FXML private void editRecipeBgChanged() { fillRecipeIngForEdit(editChoiceRecipeIng); }

    // browse baked good
    @FXML private void browseBakedGood() {
        String name = browseBakedGood.getValue();
        itemImage.setImage(null);
        if (name == null) {
            itemDetails.setText("Select a baked good to see its full details.");
            return;
        }

        // looks for the baked good
        BakedGood bg = AppData.getStore().findBakedGood(name);
        if (bg == null) return;

        // shows its details to the area
        itemDetails.setText(bg.getFullDetails());
        showImage(bg.getImageUrl());
    }

    @FXML private void browseIngredient() {
        String name = browseIngredient.getValue();
        itemImage.setImage(null);
        if (name == null) {
            itemDetails.setText("Select an ingredient to see its full details.");
            return;
        }

        Ingredient ing = AppData.getStore().findIngredient(name);
        if (ing == null) return;

        itemDetails.setText(buildIngredientDetailsText(ing));
    }

    @FXML private void search() {
        String term = searchBar.getText().trim();
        String option = choiceSearchBy.getValue();
        if (term.isEmpty()) {
            reportMessage("Please type something in the search box before searching.");
            return;
        }

        lastSearchResults = AppData.getStore().search(term, option);
        searchResults.getItems().clear();

        if (lastSearchResults.isEmpty()) {
            itemDetails.setText("No results found matching '" + term + "' (" + option + ").");
            itemImage.setImage(null);
        } else {
            for (int i = 0; i < lastSearchResults.size(); i++) {
                searchResults.getItems().add(lastSearchResults.get(i).toString());
            }
            itemDetails.setText("Click a result in the list to see full details.");
        }
    }

    private String buildIngredientDetailsText(Ingredient ing) {
        String usedIn;
        LinkedList<BakedGood> users = AppData.getStore().findBakedGoodsWithIngredient(ing);
        if (users.isEmpty()) {
            usedIn = "  (not used in any baked good yet)";
        } else {
            String list = "";
            for (int i = 0; i < users.size(); i++) {
                list += "  - " + users.get(i).toString() + "\n";
            }
            usedIn = list.trim();
        }
        return ing.getFullDetails()
                + "\n\n--- Used in Baked Goods ---\n" + usedIn;
    }

    private void searchResultClicked() {
        int selectedIndex = searchResults.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= lastSearchResults.size()) return;

        SearchResult result = lastSearchResults.get(selectedIndex);
        if (result.getType().equals("BakedGood")) {
            BakedGood bg = result.getBakedGood();
            itemDetails.setText(bg.getFullDetails());
            showImage(bg.getImageUrl());
        } else {
            Ingredient ing = result.getIngredient();
            itemDetails.setText(buildIngredientDetailsText(ing));
            itemImage.setImage(null);
        }
    }

    @FXML private void caloriesReport() { reportArea.setText(AppData.getStore().getCaloriesReport()); }

    private void showImage(String url) {
        if (url == null || url.isBlank()) {
            itemImage.setImage(null);
            return;
        }
        try {
            itemImage.setImage(new Image(url, true));
        } catch (Exception e) {
            itemImage.setImage(null);
        }
    }

    private void fillAllChoiceBoxes() {
        fillChoiceBoxBg(choiceRecipeBg);
        fillChoiceBoxBg(choiceRemoveBg);
        fillChoiceBoxBg(choiceRemoveRecipeBg);

        fillChoiceBoxBg(choiceEditBg);
        fillChoiceBoxIng(choiceEditIng);
        fillChoiceBoxBg(choiceEditRecipe);

        fillChoiceBoxIng(choiceRecipeIng);
        fillChoiceBoxIng(choiceRemoveIng);

        fillRecipeGuideForRemove(choiceRemoveGuide);

        fillRecipeIngForEdit(editChoiceRecipeIng);

        browseBakedGood.getItems().clear();
        browseIngredient.getItems().clear();
        BakeryStore store = AppData.getStore();
        for (int i = 0; i < store.getBakedGoods().size(); i++) {
            browseBakedGood.getItems().add(store.getBakedGoods().get(i).getName());
        }
        for (int i = 0; i < store.getIngredients().size(); i++) {
            browseIngredient.getItems().add(store.getIngredients().get(i).getName());
        }
    }

    private void fillChoiceBoxBg(ChoiceBox<String> box) {
        box.getItems().clear();
        BakeryStore store = AppData.getStore();
        for (int i = 0; i < store.getBakedGoods().size(); i++) {
            box.getItems().add(store.getBakedGoods().get(i).getName());
        }
        if (!box.getItems().isEmpty()) {
            box.getSelectionModel().selectFirst();
        }
    }

    private void fillChoiceBoxIng(ChoiceBox<String> box) {
        box.getItems().clear();
        BakeryStore store = AppData.getStore();
        for (int i = 0; i < store.getIngredients().size(); i++) {
            box.getItems().add(store.getIngredients().get(i).getName());
        }
        if (!box.getItems().isEmpty()) {
            box.getSelectionModel().selectFirst();
        }
    }

    private void fillRecipeGuideForRemove(ChoiceBox<String> box) {
        box.getItems().clear();
        String bgName = choiceRemoveRecipeBg.getValue();
        if (bgName == null) return;

        BakedGood bg = AppData.getStore().findBakedGood(bgName);
        if (bg == null) return;

        Recipe r = bg.getRecipe();
        for (int i = 0; i < r.size(); i++) {
            box.getItems().add(r.getRecipe(i).toString());
        }
        if (!box.getItems().isEmpty()) {
            box.getSelectionModel().selectFirst();
        }
    }

    private void fillRecipeIngForEdit(ChoiceBox<String> box) {
        box.getItems().clear();
        String bgName = choiceEditRecipe.getValue();
        if (bgName == null) return;

        BakedGood bg = AppData.getStore().findBakedGood(bgName);
        if (bg == null) return;

        Recipe r = bg.getRecipe();
        for (int i = 0; i < r.size(); i++) {
            box.getItems().add(r.getRecipe(i).getIngredient().getName());
        }
        if (!box.getItems().isEmpty()) {
            box.getSelectionModel().selectFirst();
        }
    }

    @FXML private void viewAllStock() {
        refreshAllStock();
    }

    private void refreshAllStock() {
        allStockDetails.setText(AppData.getStore().getAllStockReport());
    }

    private void reportMessage(String message) {
        topMessage.setText(message);
    }


    @FXML private void saveStore() {
        try {
            StoreFileManager.saveStore(AppData.getStore(), AppData.getSaveFile());
            reportArea.setText("Store saved successfully to: " + AppData.getSaveFile());
        } catch (Exception e) {
            reportArea.setText("Save failed: " + e.getMessage());
        }
    }

    @FXML private void loadStore() {
        try {
            AppData.setStore(StoreFileManager.loadStore(AppData.getSaveFile()));
            fillAllChoiceBoxes();
            refreshAllStock();
            reportArea.setText("Store loaded successfully from: " + AppData.getSaveFile());
        } catch (Exception e) {
            reportArea.setText("Load failed. Have you saved the store at least once?\nError: " + e.getMessage());
        }
    }

    @FXML private void resetSystem() {
        AppData.resetStore();
        lastSearchResults = new LinkedList<>();
        searchResults.getItems().clear();
        fillAllChoiceBoxes();
        refreshAllStock();
        itemDetails.setText("System reset. All data cleared.");
        itemImage.setImage(null);
        reportArea.setText("All data has been cleared.");
    }
}