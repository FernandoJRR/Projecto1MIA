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

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class ModificarProductosViewController implements Initializable {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label tipoSesionLabel;
    @FXML
    private TableView<Producto> productosTable;
    @FXML
    private TableColumn<Producto,String> codigoColumn;
    @FXML
    private TableColumn<Producto,String> nombreColumn;
    @FXML
    private TableColumn<Producto,String> precioColumn;
    @FXML
    private TextField codigoField;
    @FXML
    private ChoiceBox<String> atributoSelector;
    @FXML
    private TextField valorNuevoField;
    @FXML
    private Spinner<Double> valorNuevoSpinner;
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

        codigoColumn.setCellValueFactory(new PropertyValueFactory<Producto,String>("codigo"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<Producto,String>("precio"));
        actualizarProductos();
        
        valorNuevoSpinner.setVisible(false);

        atributoSelector.getItems().addAll("Codigo", "Nombre", "Precio");
        atributoSelector.setValue("Codigo");

        atributoSelector.getSelectionModel()
            .selectedItemProperty()
            .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                switch (newValue) {
                    case "Codigo":
                    case "Nombre":
                        valorNuevoField.setVisible(true);
                        valorNuevoSpinner.setVisible(false);
                        break;
                    case "Precio":
                        valorNuevoField.setVisible(false);
                        valorNuevoSpinner.setVisible(true);
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
    private void onIngresarProductosButtonClick(){
        try {
            App.setRoot("bodega/ingresarProductosView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onModificarButtonClick(){
        // Se comprueba si hay un codigo ingresado
        if (codigoField.getText().isBlank()) {
            invalidDetails.setStyle(formatoMensajeError);
            invalidDetails.setText("Se debe de ingresar un codigo de producto!");
        } else {
            //Se comprueba que atributo se quiere cambiar
            switch (atributoSelector.getValue()) {
                case "Codigo":
                case "Nombre":
                    //Si es codigo o nombre se verifica que el valorNuevoField no este vacio
                    if (valorNuevoField.getText().isBlank()) {
                        invalidDetails.setStyle(formatoMensajeError);
                        invalidDetails.setText("Se debe de ingresar un valor nuevo!");
                    } else {
                        //Si esta ingresado un nuevo valor se insertara 
                        switch (atributoSelector.getValue()) {
                            case "Codigo":
                                try {
                                    ModeloBodega.modificarCodigoProducto(codigoField.getText(), valorNuevoField.getText());
                                    invalidDetails.setText("Se ha modificado el Codigo del Producto!");
                                    invalidDetails.setStyle(formatoMensajeExito);
                                } catch (SQLException e) {
                                    invalidDetails.setStyle(formatoMensajeError);
                                    invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                                    e.printStackTrace();
                                } catch (ProductoNoExisteException e) {
                                    invalidDetails.setStyle(formatoMensajeError);
                                    invalidDetails.setText("El codigo de producto ingresado no existe");
                                    e.printStackTrace();
                                } catch (ProductoDuplicadoException e) {
                                    invalidDetails.setStyle(formatoMensajeError);
                                    invalidDetails.setText("El codigo al cual se quiere cambiar, ya existe");
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    invalidDetails.setStyle(formatoMensajeError);
                                    invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                                    e.printStackTrace();
                                }
                                break;
                            case "Nombre":
                                try {
                                    ModeloBodega.modificarNombreProducto(codigoField.getText(), valorNuevoField.getText());
                                    invalidDetails.setText("Se ha modificado el Nombre del Producto!");
                                    invalidDetails.setStyle(formatoMensajeExito);
                                } catch (SQLException e) {
                                    invalidDetails.setStyle(formatoMensajeError);
                                    invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                                    e.printStackTrace();
                                } catch (ProductoNoExisteException e) {
                                    invalidDetails.setStyle(formatoMensajeError);
                                    invalidDetails.setText("El codigo de producto ingresado no existe");
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    invalidDetails.setStyle(formatoMensajeError);
                                    invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }
                    break;
                case "Precio":
                    try {
                        ModeloBodega.modificarPrecioProducto(codigoField.getText(), 
                            Float.parseFloat(valorNuevoSpinner.getValue().toString())
                        );
                        invalidDetails.setText("Se ha modificado el Precio del Producto!");
                        invalidDetails.setStyle(formatoMensajeExito);
                    } catch (NumberFormatException e) {
                        invalidDetails.setStyle(formatoMensajeError);
                        invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                        e.printStackTrace();
                    } catch (SQLException e) {
                        invalidDetails.setStyle(formatoMensajeError);
                        invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                        e.printStackTrace();
                    } catch (ProductoNoExisteException e) {
                        invalidDetails.setStyle(formatoMensajeError);
                        invalidDetails.setText("El codigo de producto ingresado no existe");
                        e.printStackTrace();
                    } catch (Exception e) {
                        invalidDetails.setStyle(formatoMensajeError);
                        invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void actualizarProductos(){
        try {
            List<Producto> productosBodega = ModeloBodega.getProductos();
            productosTable.getItems().addAll(productosBodega);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
