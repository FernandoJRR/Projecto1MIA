/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.universidad.mia_proyecto1.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.universidad.mia_proyecto1.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class BodegaViewController implements Initializable {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label tipoSesionLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameLabel.setText(App.sesionUser);
        tipoSesionLabel.setText(App.sesionTipo);
    }    

    @FXML
    private void onIngresarProductosButtonClick(){
    }
    
    @FXML
    private void onModificarProductosButtonClick(){
    }
    
    @FXML
    private void onLogoutButtonClick(){
        try {
            App.sesionUser = null;
            App.sesionTipo = null;
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
