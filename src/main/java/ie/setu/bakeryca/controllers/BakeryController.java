package ie.setu.bakeryca.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import ie.setu.bakeryca.services.*;
import ie.setu.bakeryca.models.*;
import ie.setu.bakeryca.core.*;
import ie.setu.bakeryca.*;

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
        // GPT wrote all the countries name as Strings
        choiceBgOrigin.getItems().addAll("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Costa Rica", "Côte d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe");

        reportMessage("Welcome!");
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
            reportArea.setText("Please fill in Name, Origin, and Description before adding a baked good.");
            reportMessage("Please fill all sections: Name, Place of Origin and Description");
            return;
        }

        boolean added = AppData.getStore().addBakedGood(new BakedGood(name, origin, desc, img));
        if (added) {
            addBgName.clear();
            choiceBgOrigin.getSelectionModel().clearSelection();
            addBgDesc.clear();
            addBgImg.clear();
            allStockDetails.setText(AppData.getStore().getAllStockReport());
            reportArea.setText("Baked good '" + name + "' added successfully.");
        } else {
            reportArea.setText("A baked good named '" + name + "' already exists. Choose a different name.");
        }
    }

    @FXML private void addIngredient() {

    }

    @FXML private void addRecipeEntry() {

    }

    @FXML private void removeBakedGood() {

    }

    @FXML private void removeIngredient() {

    }

    @FXML private void removeRecipeEntry() {

    }

    @FXML private void removeRecipeBgChanged() {

    }

    @FXML private void browseBakedGoodChanged() {

    }

    @FXML private void browseIngredientChanged() {

    }

    @FXML private void viewAllStock() {

    }

    @FXML private void search() {

    }

    @FXML private void caloriesReport() {

    }

    @FXML private void saveStore() {

    }

    @FXML private void loadStore() {

    }

    @FXML private void resetSystem() {

    }

    private void reportMessage(String message) {
        topMessage.setText(message);
    }
}