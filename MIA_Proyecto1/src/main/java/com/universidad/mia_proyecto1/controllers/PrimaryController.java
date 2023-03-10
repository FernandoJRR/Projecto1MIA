package com.universidad.mia_proyecto1.controllers;

import java.io.IOException;

import com.universidad.mia_proyecto1.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
