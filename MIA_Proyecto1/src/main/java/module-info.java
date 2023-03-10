module com.universidad.mia_proyecto1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.universidad.mia_proyecto1 to javafx.fxml;
    opens com.universidad.mia_proyecto1.controllers to javafx.fxml;
    exports com.universidad.mia_proyecto1;
    exports com.universidad.mia_proyecto1.controllers;
}
