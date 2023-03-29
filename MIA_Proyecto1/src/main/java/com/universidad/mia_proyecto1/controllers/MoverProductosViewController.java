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
import com.universidad.mia_proyecto1.database.ModeloInventario;
import com.universidad.mia_proyecto1.exceptions.ProductoDentroException;
import com.universidad.mia_proyecto1.exceptions.ProductoNoExisteException;
import com.universidad.mia_proyecto1.modelo.ProductoSucursal;
import com.universidad.mia_proyecto1.modelo.ProductoUbicacion;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class MoverProductosViewController implements Initializable {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label tipoSesionLabel;
	@FXML
	private Label sucursalLabel;
	@FXML
	private Button moverProductosButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Button moverProductoButton;
	@FXML
	private TableView<ProductoUbicacion> productosTable;
	@FXML
	private TableColumn<ProductoUbicacion, String> idSistemaColumn;
	@FXML
	private TableColumn<ProductoUbicacion, String> codigoSistemaColumn;
	@FXML
	private TableColumn<ProductoUbicacion, String> nombresistemaColumn;
	@FXML
	private TableColumn<ProductoUbicacion, String> ubicacionSistemaColumn;
	@FXML
	private TableView<ProductoSucursal> productosSucursalTable;
	@FXML
	private TableColumn<ProductoSucursal, String> idColumn;
	@FXML
	private TableColumn<ProductoSucursal, String> codigoColumn;
	@FXML
	private TableColumn<ProductoSucursal, String> nombreColumn;
	@FXML
	private Spinner<Integer> idProductoSpinner;
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
		sucursalLabel.setText(App.sesionSucursal.substring(0,1).toUpperCase()+App.sesionSucursal.substring(1));

        idSistemaColumn.setCellValueFactory(new PropertyValueFactory<ProductoUbicacion, String>("id"));
        codigoSistemaColumn.setCellValueFactory(new PropertyValueFactory<ProductoUbicacion, String>("codigo"));
        nombresistemaColumn.setCellValueFactory(new PropertyValueFactory<ProductoUbicacion, String>("nombre"));
        ubicacionSistemaColumn.setCellValueFactory(new PropertyValueFactory<ProductoUbicacion, String>("ubicacion"));
        actualizarProductosSistema();

        idColumn.setCellValueFactory(new PropertyValueFactory<ProductoSucursal, String>("id"));
        codigoColumn.setCellValueFactory(new PropertyValueFactory<ProductoSucursal, String>("codigo"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<ProductoSucursal, String>("nombre"));
        actualizarProductosSucursal();
	}	
	
	@FXML
	private void onMoverProductoButtonClick() {
       //Si todo es correcto se ingresa el producto nuevo
        try {
            ModeloInventario.moverProducto(App.sesionSucursal, idProductoSpinner.getValue());
            invalidDetails.setText("Producto Movido a la Sucursal "+App.sesionSucursal+"!");
            invalidDetails.setStyle(formatoMensajeExito);
            actualizarProductosSistema();
            actualizarProductosSucursal();
        } catch (ProductoNoExisteException e){
            invalidDetails.setText("El producto con id "+idProductoSpinner.getValue()+" no esta disponible");
            invalidDetails.setStyle(formatoMensajeError);
        } catch (ProductoDentroException e){
            invalidDetails.setText("El producto con id "+e.getMessage()+" ya se encuentra en la sucursal "+App.sesionSucursal);
            invalidDetails.setStyle(formatoMensajeError);
        } catch (SQLException e) {
            invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
            invalidDetails.setStyle(formatoMensajeError);
            e.printStackTrace();
        } catch (Exception e) {
            invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
            invalidDetails.setStyle(formatoMensajeError);
            e.printStackTrace();
        }
	}
	
	private void actualizarProductosSistema() {
        productosTable.getItems().clear();
        try {
            List<ProductoUbicacion> productosList = ModeloInventario.getProductosInventario(App.sesionSucursal);
            productosTable.getItems().addAll(productosList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private void actualizarProductosSucursal() {
        productosSucursalTable.getItems().clear();
        try {
            List<ProductoSucursal> productosList = ModeloInventario.getProductosSucursal(App.sesionSucursal);
            productosSucursalTable.getItems().addAll(productosList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
