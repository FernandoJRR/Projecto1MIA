/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.universidad.mia_proyecto1.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.universidad.mia_proyecto1.App;
import com.universidad.mia_proyecto1.database.ModeloVentas;
import com.universidad.mia_proyecto1.exceptions.ClienteNoExisteException;
import com.universidad.mia_proyecto1.modelo.Cliente;
import com.universidad.mia_proyecto1.modelo.ProductoVenta;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class RealizarVentaViewController implements Initializable {

	@FXML
	private Button moverProductosButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Label sucursalLabel;
	@FXML
	private TableView<ProductoVenta> productosSucursalTable;
	@FXML
	private TableColumn<ProductoVenta, String> idColumn;
	@FXML
	private TableColumn<ProductoVenta, String> codigoColumn;
	@FXML
	private TableColumn<ProductoVenta, String> nombreColumn;
	@FXML
	private TableColumn<ProductoVenta, String> precioColumn;
	@FXML
	private Button venderButton;
	@FXML
	private Label invalidDetails;
	@FXML
	private FlowPane listaProductosPane;
	@FXML
	private Button agregarProductoButton;
	@FXML
	private Button removerProductoButton;
	@FXML
	private TextField nitField;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label tipoSesionLabel;
	private List<Spinner<Integer>> spinnersProductos = new ArrayList<Spinner<Integer>>();
	@FXML
	private CheckBox consumidorFinalCheck;
	@FXML
	private CheckBox clienteNuevoCheck;
	@FXML
	private AnchorPane clienteNuevoPane;
	@FXML
	private TextField nombreClienteField;

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
		agregarProducto();
		
        idColumn.setCellValueFactory(new PropertyValueFactory<ProductoVenta, String>("id"));
        codigoColumn.setCellValueFactory(new PropertyValueFactory<ProductoVenta, String>("codigo"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<ProductoVenta, String>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<ProductoVenta, String>("precio"));
        actualizarProductos();
		
		clienteNuevoPane.setVisible(false);

        clienteNuevoCheck.selectedProperty()
            .addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean anteriorValor, Boolean nuevoValor) {
                    clienteNuevoPane.setVisible(nuevoValor);
                }
        });

        consumidorFinalCheck.selectedProperty()
            .addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean anteriorValor, Boolean nuevoValor) {
					nitField.setDisable(nuevoValor);
                }
        });
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
	private void onVenderButtonClick(ActionEvent event) {
		//Se comprueba si es una venta con consumidor final
		if (consumidorFinalCheck.isSelected()) {
			//Si es consumidor final se realiza la venta
			//Se recolectan los valores de los spinners
			List<Integer> idProductos = new ArrayList<Integer>();
			for (Spinner<Integer> spinner : spinnersProductos) {
				idProductos.add(spinner.getValue());
			}
			try {
				ModeloVentas.crearVentaCF(App.sesionUser, idProductos);
				invalidDetails.setText("Venta existosa!");
			} catch (SQLException e) {
				invalidDetails.setStyle(formatoMensajeError);
				invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
				e.printStackTrace();
			} catch (Exception e) {
				invalidDetails.setStyle(formatoMensajeError);
				invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
				e.printStackTrace();
			}
		} else {
			//Si no es consumidor final si se ingreso NIT
			if (nitField.getText().isBlank()) {
				//Si no se ingreso nit se tira un error
				invalidDetails.setStyle(formatoMensajeError);
				invalidDetails.setText("Se debe un NIT");
			} else {
				//Si se ingreso NIT se comprueba si es un cliente nuevo
				if (clienteNuevoCheck.isSelected()) {
					//Si es cliente nuevo se crea un nuevo cliente en la base de datos y se realiza la compra
					//Se comprueba si se ingreso un nombre
					if (nombreClienteField.getText().isBlank()) {
						//Si no se ingreso un nombre se tira error
					} else {
						//Se recolectan los valores de los spinners
						List<Integer> idProductos = new ArrayList<Integer>();
						for (Spinner<Integer> spinner : spinnersProductos) {
							idProductos.add(spinner.getValue());
						}
						try {
							ModeloVentas.crearVentaClienteNuevo(nitField.getText(), nombreClienteField.getText(), App.sesionUser, idProductos);
						} catch (SQLException e) {
							invalidDetails.setStyle(formatoMensajeError);
							invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
							e.printStackTrace();
						} catch (Exception e) {
							invalidDetails.setStyle(formatoMensajeError);
							invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
							e.printStackTrace();
						}
					}
				} else {
					//Si el cliente no es nuevo se comprueba si el cliente existe
					try {
						Cliente cliente = ModeloVentas.getCliente(nitField.getText());
						if (cliente == null) {
							//Si el cliente no existe se tira un error
							throw new ClienteNoExisteException(nitField.getText());
						} else {
							//Si el cliente existe se realiza la venta
							//Se recolectan los valores de los spinners
							List<Integer> idProductos = new ArrayList<Integer>();
							for (Spinner<Integer> spinner : spinnersProductos) {
								idProductos.add(spinner.getValue());
							}
							
							ModeloVentas.crearVenta(nitField.getText(), App.sesionUser, idProductos);
							invalidDetails.setText("Venta existosa!");
						}
					} catch (ClienteNoExisteException e){
						invalidDetails.setStyle(formatoMensajeError);
						invalidDetails.setText(e.getMessage());
					} catch (SQLException e) {
						invalidDetails.setStyle(formatoMensajeError);
						invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
						e.printStackTrace();
					} catch (Exception e) {
						invalidDetails.setStyle(formatoMensajeError);
						invalidDetails.setText("Ha ocurrido un error, intenta mas tarde");
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void actualizarProductos() {
        try {
            List<ProductoVenta> productosList = ModeloVentas.getProductosSucursal(App.sesionSucursal);
            productosSucursalTable.getItems().addAll(productosList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void onAgregarProductoButtonClick(ActionEvent event) {
		agregarProducto();
	}
	
	@FXML
	private void onRemoverProductoButtonClick(ActionEvent event) {
		removerProducto();
	}

	private void agregarProducto(){
		if (spinnersProductos.size() < 9){
			Spinner<Integer> spinner = new Spinner<>(new IntegerSpinnerValueFactory(1, 9999));
			spinner.setEditable(true);
			spinnersProductos.add(spinner);
			listaProductosPane.getChildren().add(spinner);
		}
	}
	
	private void removerProducto() {
		if (spinnersProductos.size() > 0) {
			spinnersProductos.remove(spinnersProductos.size()-1);
			listaProductosPane.getChildren().remove(listaProductosPane.getChildren().size()-1);
		}
	}

	@FXML
	private void onModificarClientesButtonClick(ActionEvent event) {
        try {
            App.setRoot("vendedor/modificarClientesView");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
