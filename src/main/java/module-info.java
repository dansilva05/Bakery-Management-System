module ie.setu.bakeryca {
    requires javafx.controls;
    requires javafx.fxml;

    exports ie.setu.bakeryca;
    exports ie.setu.bakeryca.controllers;
    exports ie.setu.bakeryca.pages;

    opens ie.setu.bakeryca to javafx.fxml;
    opens ie.setu.bakeryca.controllers to javafx.fxml;
    opens ie.setu.bakeryca.pages to javafx.graphics, javafx.fxml;
    opens ie.setu.bakeryca.services to javafx.base;
}