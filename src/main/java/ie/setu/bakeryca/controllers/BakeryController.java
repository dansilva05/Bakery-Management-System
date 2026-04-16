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

    // Add Recipe Entry section
    @FXML private ChoiceBox<String> choiceRecipeBg;
    @FXML private ChoiceBox<String> choiceRecipeIng;
    @FXML private TextField addRecipeQty;

    // Remove section
    @FXML private ChoiceBox<String> choiceRemoveBg;
    @FXML private ChoiceBox<String> choiceRemoveIng;
    @FXML private ChoiceBox<String> choiceRemoveRecipeBg;
    @FXML private ChoiceBox<String> choiceRemoveEntry;

    // Search section
    @FXML private ComboBox<String> browseBakedGood;
    @FXML private ComboBox<String> browseIngredient;
    @FXML private ImageView itemImage;
    @FXML private TextArea itemDetails;
    @FXML private TextArea allStockDetails;
    @FXML private TextField searchBar;
    @FXML private ListView<String> searchResults;

    // Reports section
    @FXML private Label topMessage;
    @FXML private TextArea reportArea;

    private LinkedList<SearchResult> lastSearchResults = new LinkedList<>();
    @FXML
    public void initialize() {
        reportMessage("Welcome!");
        fillAllChoiceBoxes();
        refreshAllStock();

        // GPT wrote all the countries name as Strings
        choiceBgOrigin.getItems().addAll("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Costa Rica", "Côte d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe");

        itemDetails.setText("Select a baked good or ingredient to see details here.");
        reportArea.setText("Use the buttons above to generate reports, save, load, or reset.");
        allStockDetails.setText(AppData.getStore().getAllStockReport());
    }

    @FXML
    private void addBakedGood() {
        String name = addBgName.getText().trim();
        String origin = choiceBgOrigin.getValue();
        String desc = addBgDesc.getText().trim();
        String img = addBgImg.getText().trim();

        if (name.isEmpty() || origin.isEmpty() || desc.isEmpty()) {
            reportMessage("Please fill all sections: Name, Place of Origin and Description");
            return;
        }

        boolean added = AppData.getStore().addBakedGood(new BakedGood(name, origin, desc, img));
        if (added) {
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

    @FXML private void addIngredient() {
        String name = addIngName.getText().trim();
        String calStr = addIngCals.getText().trim();
        String desc = addIngDesc.getText().trim();

        if (name.isEmpty() || calStr.isEmpty() || desc.isEmpty()) {
            reportMessage("Please fill all sections: Name, Place of Origin and Description");
            return;
        }

        float cals;
        try {
        cals = parseFloat(calStr);
        } catch (NumberFormatException e) {
            reportMessage("calories must be a number (eg. 350.0");
            return;
        }

        boolean added = AppData.getStore().addIngredient(new Ingredient(name, desc, cals));
        if (added) {
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

    @FXML private void addRecipeEntry() {
        String bgName = choiceRecipeBg.getValue();
        String ingName = choiceRecipeIng.getValue();
        String qtyStr = addRecipeQty.getText().trim();

        if (bgName == null || ingName == null || qtyStr.isEmpty()) {
            reportMessage("Please select a baked good, an ingredient, and enter a quantity.");
            return;
        }

        float qty;
        try {
            qty = parseFloat(qtyStr);
        } catch (NumberFormatException e) {
            reportMessage("Quantity must be a number (e.g. 50.0).");
            return;
        }

        boolean added = AppData.getStore().addRecipe(bgName, ingName, qty);
        if (added) {
            addRecipeQty.clear();
            fillAllChoiceBoxes();
            refreshAllStock();
            reportMessage("Added " + qty + "g of '" + ingName + "' to '" + bgName + "'.");
        } else {
            reportMessage("Could not add recipe entry. Make sure the baked good and ingredient exist.");
        }
    }

    @FXML private void removeBakedGood() {
        String name = choiceRemoveBg.getValue();
        if (name == null) {
            reportMessage("Please select a baked good to remove.");
            return;
        }

        boolean removed = AppData.getStore().removeBakedGood(name);
        if (removed) {
            fillAllChoiceBoxes();
            refreshAllStock();
            itemDetails.setText("Baked good removed. Select another to view details.");
            itemImage.setImage(null);
            reportMessage("Baked good '" + name + "' removed.");
        } else {
            reportMessage("Could not remove baked good. Please re-select and try again.");
        }
    }

    @FXML private void removeIngredient() {
        String name = choiceRemoveIng.getValue();
        if (name == null) {
            reportMessage("Please select an ingredient to remove.");
            return;
        }

        boolean removed = AppData.getStore().removeIngredient(name);
        if (removed) {
            fillAllChoiceBoxes();
            refreshAllStock();
            itemDetails.setText("Ingredient removed. Select another to view details.");
            itemImage.setImage(null);
            reportMessage("Ingredient '" + name + "' removed (and cleared from any recipes using it).");
        } else {
            reportMessage("Could not remove ingredient. Please re-select and try again.");
        }
    }

    @FXML private void removeRecipeGuide() {
        String bgName = choiceRemoveRecipeBg.getValue();
        int entryIndex = choiceRemoveEntry.getSelectionModel().getSelectedIndex();

        if (bgName == null || entryIndex < 0) {
            reportMessage("Please select a baked good and a recipe entry to remove.");
            return;
        }

        boolean removed = AppData.getStore().removeRecipeGuide(bgName, entryIndex);
        if (removed) {
            fillAllChoiceBoxes();
            refreshAllStock();
            reportMessage("Recipe entry removed from '" + bgName + "'.");
        } else {
            reportMessage("Could not remove recipe entry. Please re-select and try again.");
        }
    }

    @FXML private void removeRecipeBgChanged() {

    }

    @FXML private void browseBakedGood() {
        String name = browseBakedGood.getValue();
        itemImage.setImage(null);
        if (name == null) {
            itemDetails.setText("Select a baked good to see its full details.");
            return;
        }

        BakedGood bg = AppData.getStore().findBakedGood(name);
        if (bg == null) return;

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

    }

    @FXML private void caloriesReport() {

    }

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

        fillChoiceBoxIng(choiceRecipeIng);
        fillChoiceBoxIng(choiceRemoveIng);

        fillRecipeEntryForRemove(choiceRemoveEntry);

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

    private void fillRecipeEntryForRemove(ChoiceBox<String> box) {
        choiceRemoveEntry.getItems().clear();
        String bgName = choiceRemoveRecipeBg.getValue();
        if (bgName == null) return;

        BakedGood bg = AppData.getStore().findBakedGood(bgName);
        if (bg == null) return;

        Recipe r = bg.getRecipe();
        for (int i = 0; i < r.size(); i++) {
            choiceRemoveEntry.getItems().add(r.getRecipe(i).toString());
        }
        if (!choiceRemoveEntry.getItems().isEmpty()) {
            choiceRemoveEntry.getSelectionModel().selectFirst();
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