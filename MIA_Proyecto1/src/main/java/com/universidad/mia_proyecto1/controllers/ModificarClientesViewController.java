/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.universidad.mia_proyecto1.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.universidad.mia_proyecto1.App;
import com.universidad.mia_proyecto1.database.ModeloBodega;
import com.universidad.mia_proyecto1.database.ModeloVentas;
import com.universidad.mia_proyecto1.exceptions.ClienteDuplicadoException;
import com.universidad.mia_proyecto1.exceptions.ClienteNoExisteException;
import com.universidad.mia_proyecto1.modelo.Cliente;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class ModificarClientesViewController implements Initializable {

	@FXML
	private Button ingresarProductosButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Label sucursalLabel;
	@FXML
	private TableView<Cliente> productosTable;
	@FXML
	private TableColumn<Cliente,String> nitColumn;
	@FXML
	private TableColumn<Cliente,String> nombreColumn;
	@FXML
	private TextField nitField;
	@FXML
	private TextField valorNuevoField;
	@FXML
	private ChoiceBox<String> atributoSelector;
	@FXML
	private Button modificarButton;
	@FXML
	private Label invalidDetails;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label tipoSesionLabel;

    // CSS para mensajes y estilos de exito y error
    protected
    String formatoMensajeExito = String.format("-fx-text-fill: GREEN;");
    String formatoMensajeError = String.format("-fx-text-fill: RED;");
    String estiloExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");
    String estiloError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
        usernameLabel.setText(App.sesionUser);
        tipoSesionLabel.setText(App.sesionTipo);
		sucursalLabel.setText(App.sesionSucursal.substring(0,1).toUpperCase()+App.sesionSucursal.substring(1));

        nitColumn.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nit"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombre"));
		actualizarClientes();

        atributoSelector.getItems().addAll("NIT", "Nombre");
        atributoSelector.setValue("NIT");
	}	

	private void actualizarClientes() {
        try {
            List<Cliente> productosBodega = ModeloVentas.getClientes();
            productosTable.getItems().addAll(productosBodega);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
	private void onLogoutButtonClick(ActionEvent event) {
        try {
            App.sesionUser = null;
            App.sesionTipo = null;
            App.sesionSucursal = null;
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@FXML
	private void onModificarButtonClick(ActionEvent event) {
        // Se comprueba si hay un nit ingresado
        if (nitField.getText().isBlank()) {
            invalidDetails.setStyle(formatoMensajeError);
            invalidDetails.setText("Se debe de ingresar un NIT de Cliente!");
        } else {
			//Si es codigo o nombre se verifica que el valorNuevoField no este vacio
			if (valorNuevoField.getText().isBlank()) {
				invalidDetails.setStyle(formatoMensajeError);
				invalidDetails.setText("Se debe de ingresar un valor nuevo!");
			} else {
				//Si esta ingresado un nuevo valor se insertara 
				switch (atributoSelector.getValue()) {
					case "NIT":
						try {
							ModeloVentas.modificarNIT(nitField.getText(), valorNuevoField.getText());
							invalidDetails.setText("Se ha modificado el NIT del Cliente!");
							invalidDetails.setStyle(formatoMensajeExito);
						} catch (SQLException e) {
							invalidDetails.setStyle(formatoMensajeError);
							invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
							e.printStackTrace();
						} catch (ClienteDuplicadoException e) {
							invalidDetails.setStyle(formatoMensajeError);
							invalidDetails.setText(e.getMessage());
						} catch (ClienteNoExisteException e) {
							invalidDetails.setStyle(formatoMensajeError);
							invalidDetails.setText(e.getMessage());
						} catch (Exception e) {
							invalidDetails.setStyle(formatoMensajeError);
							invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
							e.printStackTrace();
						}
						break;
					case "Nombre":
						try {
							ModeloVentas.modificarNombre(nitField.getText(), valorNuevoField.getText());
							invalidDetails.setText("Se ha modificado el Nombre del Cliente!");
							invalidDetails.setStyle(formatoMensajeExito);
						} catch (SQLException e) {
							invalidDetails.setStyle(formatoMensajeError);
							invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
							e.printStackTrace();
						} catch (ClienteNoExisteException e) {
							invalidDetails.setStyle(formatoMensajeError);
							invalidDetails.setText(e.getMessage());
						} catch (Exception e) {
							invalidDetails.setStyle(formatoMensajeError);
							invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
							e.printStackTrace();
						}
						break;
				}
			}
        }
	}
	
}
