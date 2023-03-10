package com.universidad.mia_proyecto1.controllers;

import java.io.IOException;

import com.universidad.mia_proyecto1.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}