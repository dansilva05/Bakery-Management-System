package ie.setu.bakeryca.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import ie.setu.bakeryca.*;

public class BakeryController {

    // Add Baked Good section
    @FXML private TextField addBgName;
    @FXML private TextField addBgOrigin;
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

    // Browse / search section
    @FXML private ComboBox<String> browseBakedGood;
    @FXML private ComboBox<String> browseIngredient;
    @FXML private ImageView itemImage;
    @FXML private TextArea itemDetails;
    @FXML private TextArea allStockDetails;
    @FXML private TextField searchBar;
    @FXML private ListView<String> searchResults;

    // Reports section
    @FXML private TextArea reportArea;


    @FXML private void addBakedGood() {

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
}
