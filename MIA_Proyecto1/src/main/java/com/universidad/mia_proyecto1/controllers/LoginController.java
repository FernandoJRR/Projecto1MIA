/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.universidad.mia_proyecto1.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class LoginController implements Initializable {
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label invalidDetails;

    @FXML
    private Button cancelButton, loginButton, forgotButton;

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
        // TODO
    }    
    
    @FXML
    protected void onLoginButtonClick() throws InterruptedException {
        // Se comprueba si las credenciales han sido ingresadas
        if (usernameTextField.getText().isBlank() || passwordField.getText().isBlank()) {
            invalidDetails.setStyle(formatoMensajeError);
        
            // Username Y password dejados en blanco
            if (usernameTextField.getText().isBlank() && passwordField.getText().isBlank()) {
                invalidDetails.setText("The Login fields are required!");
                usernameTextField.setStyle(estiloError);
                passwordField.setStyle(estiloError);
                
            
            } else { // Solo el username es dejado en blanco
                if (usernameTextField.getText().isBlank()) {
                    usernameTextField.setStyle(estiloError);
                    invalidDetails.setText("Es necesario un Username");
                    passwordField.setStyle(estiloExito);
                } else { // Solo el password es dejado en blanco
                    if (passwordField.getText().isBlank()) {
                        passwordField.setStyle(estiloError);
                        invalidDetails.setText("Es necesario un password");
                        usernameTextField.setStyle(estiloExito);
                    }
                }
            }
        } else {
            // Se comprueba si las credenciales son correctas
            if (passwordField.getText().length() < 4) {
                invalidDetails.setText("The Password can't be less than 4 characters!");
                invalidDetails.setStyle(formatoMensajeError);
                passwordField.setStyle(estiloError);
            } else {
            // Si las credenciales son correctas se inicia sesion
                invalidDetails.setText("Se inicia sesion");
                invalidDetails.setStyle(formatoMensajeExito);
                usernameTextField.setStyle(estiloExito);
                passwordField.setStyle(estiloExito);
            }
        }
    }
}