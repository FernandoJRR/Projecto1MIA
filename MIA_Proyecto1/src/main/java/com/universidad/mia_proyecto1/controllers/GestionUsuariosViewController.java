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
import com.universidad.mia_proyecto1.database.ModeloUsuario;
import com.universidad.mia_proyecto1.exceptions.UsuarioDuplicadoException;
import com.universidad.mia_proyecto1.modelo.Usuario;
import com.universidad.mia_proyecto1.utilidades.ConvertidorHash;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class GestionUsuariosViewController implements Initializable {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label tipoSesionLabel;
    
    @FXML
    private TableView<Usuario> usuariosTable;
    
    @FXML
    private TableColumn<Usuario, String> usernameColumn;

    @FXML
    private TableColumn<Usuario, String> tipoColumn;

    @FXML
    private TableColumn<Usuario, String> sucursalColumn;
    
    @FXML
    private ChoiceBox<String> tipoSelector;

    @FXML
    private ChoiceBox<String> sucursalSelector;
    
    @FXML
    private AnchorPane sucursalAnchorPane;
    
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label invalidDetails;

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
        
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("tipo"));
        sucursalColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("sucursal"));
        actualizarUsuarios();
        
        tipoSelector.getItems().addAll("Administrador", "Bodega", "Vendedor", "Inventario");
        tipoSelector.setValue("Administrador");
        
        sucursalSelector.getItems().addAll("Central", "Norte", "Sur");
        sucursalSelector.setValue("Central");
        
        sucursalAnchorPane.setVisible(false);
        
        tipoSelector.getSelectionModel()
            .selectedItemProperty()
            .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                switch (newValue) {
                    case "Administrador":
                    case "Bodega":
                        sucursalAnchorPane.setVisible(false);
                        break;
                    case "Vendedor":
                    case "Inventario":
                        sucursalAnchorPane.setVisible(true);
                        break;
                }
            }); 
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

    @FXML
    private void onVisualizarReportesButtonClick(){
        try {
            App.setRoot("administrador/visualizarReportesView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onIngresarButtonClick(){
        // Se comprueba si las credenciales han sido ingresadas
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            invalidDetails.setStyle(formatoMensajeError);
        
            // Username Y password dejados en blanco
            if (usernameField.getText().isBlank() && passwordField.getText().isBlank()) {
                invalidDetails.setText("The Login fields are required!");
                usernameField.setStyle(estiloError);
                passwordField.setStyle(estiloError);
                
            
            } else { // Solo el username es dejado en blanco
                if (usernameField.getText().isBlank()) {
                    usernameField.setStyle(estiloError);
                    invalidDetails.setText("Es necesario un Username");
                    passwordField.setStyle(estiloExito);
                } else { // Solo el password es dejado en blanco
                    if (passwordField.getText().isBlank()) {
                        passwordField.setStyle(estiloError);
                        invalidDetails.setText("Es necesario un password");
                        usernameField.setStyle(estiloExito);
                    }
                }
            }
        } else {
            try {
                ModeloUsuario.insertarUsuario(
                    usernameField.getText(), 
                    passwordField.getText(), 
                    tipoSelector.getValue().toLowerCase(), 
                    sucursalSelector.getValue().toLowerCase()
                );
                invalidDetails.setText("Usuario Ingresado!");
                invalidDetails.setStyle(formatoMensajeExito);
                passwordField.setStyle(estiloExito);
            } catch (UsuarioDuplicadoException e) {
                invalidDetails.setText(e.getMessage());
                invalidDetails.setStyle(formatoMensajeError);
                passwordField.setStyle(estiloError);
            } catch (SQLException e) {
                invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                invalidDetails.setStyle(formatoMensajeError);
                passwordField.setStyle(estiloError);
                e.printStackTrace();
            } catch (Exception e) {
                invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                invalidDetails.setStyle(formatoMensajeError);
                passwordField.setStyle(estiloError);
                e.printStackTrace();
            }
        }
    }
    
    private void actualizarUsuarios(){
        try {
            List<Usuario> usuariosList = ModeloUsuario.getUsuarios();
            usuariosTable.getItems().addAll(usuariosList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
