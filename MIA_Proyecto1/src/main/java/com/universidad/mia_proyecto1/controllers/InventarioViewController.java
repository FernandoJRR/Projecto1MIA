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
public class InventarioViewController implements Initializable {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label tipoSesionLabel;
	@FXML
	private Label sucursalLabel;
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
        usernameLabel.setText(App.sesionUser);
        tipoSesionLabel.setText(App.sesionTipo);
		sucursalLabel.setText(App.sesionSucursal.substring(0,1).toUpperCase()+App.sesionSucursal.substring(1));
	}	
	
	@FXML
	private void onMoverProductosButtonClick() {
        try {
            App.setRoot("inventario/moverProductosView");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    @FXML
    private void onLogoutButtonClick(){
        try {
            App.sesionUser = null;
            App.sesionTipo = null;
            App.sesionSucursal = null;
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
