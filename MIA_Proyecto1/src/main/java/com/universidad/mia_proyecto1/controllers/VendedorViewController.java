/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.universidad.mia_proyecto1.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.universidad.mia_proyecto1.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class VendedorViewController implements Initializable {

	@FXML
	private Button realizarVentaButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Label sucursalLabel;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label tipoSesionLabel;
	@FXML
	private Color x2;
	@FXML
	private Font x1;
	@FXML
	private Color x21;
	@FXML
	private Font x11;
	@FXML
	private Color x4;
	@FXML
	private Font x3;

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
	private void onRealizarVentaButtonClick(ActionEvent event) {
        try {
            App.setRoot("vendedor/realizarVentaView");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@FXML
	private void onModificarClientesButton(ActionEvent event) {
        try {
            App.setRoot("vendedor/modificarClientesView");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@FXML
	private void onLogoutButtonClick() {
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
