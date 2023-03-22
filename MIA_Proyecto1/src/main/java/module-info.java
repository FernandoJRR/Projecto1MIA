module com.universidad.mia_proyecto1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.universidad.mia_proyecto1 to javafx.fxml;
    opens com.universidad.mia_proyecto1.controllers to javafx.fxml;
    opens com.universidad.mia_proyecto1.modelo to javafx.base;
    exports com.universidad.mia_proyecto1;
    exports com.universidad.mia_proyecto1.controllers;
    exports com.universidad.mia_proyecto1.modelo;
}
