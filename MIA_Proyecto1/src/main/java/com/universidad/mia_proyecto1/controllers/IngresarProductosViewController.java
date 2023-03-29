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
import com.universidad.mia_proyecto1.exceptions.ProductoDuplicadoException;
import com.universidad.mia_proyecto1.exceptions.ProductoNoExisteException;
import com.universidad.mia_proyecto1.modelo.Producto;
import com.universidad.mia_proyecto1.modelo.ProductoIngresado;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class IngresarProductosViewController implements Initializable {
    @FXML
    private TextField codigoField;
    @FXML
    private TextField nombreField;
    @FXML
    private TableView<ProductoIngresado> productosTable;
    @FXML
    private TableColumn<ProductoIngresado,String> codigoColumn;
    @FXML
    private TableColumn<ProductoIngresado,String> nombreColumn;
    @FXML
    private TableColumn<ProductoIngresado,String> idColumn;
    @FXML
    private Spinner<Double> precioSpinner;
    @FXML
    private Spinner<Integer> cantidadSpinner;
    @FXML
    private Button modificarProductosButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button ingresarButton;
    @FXML
    private CheckBox productoNuevoCheck;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label tipoSesionLabel;
    @FXML
    private AnchorPane productoNuevoAnchorPane;
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
        
        productoNuevoAnchorPane.setVisible(false);

        productoNuevoCheck.selectedProperty()
            .addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean anteriorValor, Boolean nuevoValor) {
                    productoNuevoAnchorPane.setVisible(nuevoValor);
                }
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<ProductoIngresado,String>("id"));
        codigoColumn.setCellValueFactory(new PropertyValueFactory<ProductoIngresado,String>("codigo"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<ProductoIngresado,String>("nombre"));
        actualizarProductos();
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
    private void onModificarProductosButtonClick(){
        try {
            App.setRoot("bodega/modificarProductosView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onIngresarButtonClick(){
        // Se comprueba si hay un codigo ingresado
        if (codigoField.getText().isBlank()) {
            invalidDetails.setStyle(formatoMensajeError);
            invalidDetails.setText("Se debe de ingresar un codigo de producto!");
        } else {
            //Se comprueba si se quiere ingresar un producto nuevo
            if (productoNuevoCheck.selectedProperty().getValue()){
                //Se comprueban las credenciales del producto nuevo
                if (nombreField.getText().isBlank()) {
                    invalidDetails.setStyle(formatoMensajeError);
                    invalidDetails.setText("Se debe de ingresar un nombre de producto!");
                } else {
                    //Si todo es correcto se ingresa el producto nuevo
                    try {
                        ModeloBodega.ingresarProductoNuevo(
                            codigoField.getText(), 
                            nombreField.getText(), 
                            Float.parseFloat(precioSpinner.getValue().toString()), 
                            cantidadSpinner.getValue()
                        );

                        invalidDetails.setText("Productos Nuevos Ingresados!");
                        invalidDetails.setStyle(formatoMensajeExito);
                    } catch (SQLException e) {
                        invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                        invalidDetails.setStyle(formatoMensajeError);
                        e.printStackTrace();
                    } catch (ProductoDuplicadoException e) {
                        invalidDetails.setText(e.getMessage());
                        invalidDetails.setStyle(formatoMensajeError);
                    } catch (Exception e) {
                        invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                        invalidDetails.setStyle(formatoMensajeError);
                        e.printStackTrace();
                    }
                    
                }
            } else {
                //Si es valido se ingresa un producto existente
                try {
                    ModeloBodega.ingresarProductoExistente(codigoField.getText(), cantidadSpinner.getValue());
                    invalidDetails.setText("Productos Ingresados!");
                    invalidDetails.setStyle(formatoMensajeExito);
                } catch (SQLException e) {
                    invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                    invalidDetails.setStyle(formatoMensajeError);
                    e.printStackTrace();
                } catch (ProductoNoExisteException e) {
                    invalidDetails.setText(e.getMessage());
                    invalidDetails.setStyle(formatoMensajeError);
                } catch (Exception e) {
                    invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                    invalidDetails.setStyle(formatoMensajeError);
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void actualizarProductos(){
        try {
            List<ProductoIngresado> productosBodega = ModeloBodega.getProductosIngresados();
            productosTable.getItems().addAll(productosBodega);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
