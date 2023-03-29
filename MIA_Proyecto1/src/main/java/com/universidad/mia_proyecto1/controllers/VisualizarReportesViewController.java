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
import com.universidad.mia_proyecto1.database.ModeloReporte;
import com.universidad.mia_proyecto1.modelo.Duo;
import com.universidad.mia_proyecto1.modelo.Reporte;
import com.universidad.mia_proyecto1.modelo.Trio;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author fernanrod
 */
public class VisualizarReportesViewController implements Initializable {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label tipoSesionLabel;

    @FXML
    private TableView<Trio> top10ProductoVendidoTable;
    @FXML
    private TableColumn<Trio, String> top11TableColumn;
    @FXML
    private TableColumn<Trio, String> top12TableColumn;
    @FXML
    private TableColumn<Trio, String> top13TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top10ClientesGananciasTable;
    @FXML
    private TableColumn<Trio, String> top21TableColumn;
    @FXML
    private TableColumn<Trio, String> top22TableColumn;
    @FXML
    private TableColumn<Trio, String> top23TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top3SucursalesVentasTable;
    @FXML
    private TableColumn<Duo, String> top31TableColumn;
    @FXML
    private TableColumn<Duo, String> top32TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top3SucursalesGananciasTable;
    @FXML
    private TableColumn<Duo, String> top41TableColumn;
    @FXML
    private TableColumn<Duo, String> top42TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top3EmpleadosVentasTable;
    @FXML
    private TableColumn<Duo, String> top51TableColumn;
    @FXML
    private TableColumn<Duo, String> top52TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top3EmpleadosIngresosTable;
    @FXML
    private TableColumn<Duo, String> top61TableColumn;
    @FXML
    private TableColumn<Duo, String> top62TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top10ProductoVendidoTable1;
    @FXML
    private TableColumn<Trio, String> top71TableColumn;
    @FXML
    private TableColumn<Trio, String> top72TableColumn;
    @FXML
    private TableColumn<Trio, String> top73TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top10ProductoIngresosTable;
    @FXML
    private TableColumn<Trio, String> top81TableColumn;
    @FXML
    private TableColumn<Trio, String> top82TableColumn;
    @FXML
    private TableColumn<Trio, String> top83TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top5ProductosVendidosCentralTable;
    @FXML
    private TableColumn<Trio, String> top91TableColumn;
    @FXML
    private TableColumn<Trio, String> top92TableColumn;
    @FXML
    private TableColumn<Trio, String> top93TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top5ProductosVendidosNorteTable;
    @FXML
    private TableColumn<Trio, String> top101TableColumn;
    @FXML
    private TableColumn<Trio, String> top102TableColumn;
    @FXML
    private TableColumn<Trio, String> top103TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top5ProductosVendidosSurTable;
    @FXML
    private TableColumn<Trio, String> top111TableColumn;
    @FXML
    private TableColumn<Trio, String> top112TableColumn;
    @FXML
    private TableColumn<Trio, String> top113TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top5ProductosIngresosCentralTable;
    @FXML
    private TableColumn<Trio, String> top121TableColumn;
    @FXML
    private TableColumn<Trio, String> top122TableColumn;
    @FXML
    private TableColumn<Trio, String> top123TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top5ProductosIngresosSurTable;
    @FXML
    private TableColumn<Trio, String> top131TableColumn;
    @FXML
    private TableColumn<Trio, String> top132TableColumn;
    @FXML
    private TableColumn<Trio, String> top133TableColumn;
    //--------------------------------------------------------------
    @FXML
    private TableView<Reporte> top5ProductosIngresosNorteTable;
    @FXML
    private TableColumn<Trio, String> top141TableColumn;
    @FXML
    private TableColumn<Trio, String> top142TableColumn;
    @FXML
    private TableColumn<Trio, String> top143TableColumn;
    //--------------------------------------------------------------

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameLabel.setText(App.sesionUser);
        tipoSesionLabel.setText(App.sesionTipo);
        
        top11TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top12TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top13TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));

        top21TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top22TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top23TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));

        top31TableColumn.setCellValueFactory(new PropertyValueFactory<Duo, String>("primero"));
        top32TableColumn.setCellValueFactory(new PropertyValueFactory<Duo, String>("segundo"));

        top41TableColumn.setCellValueFactory(new PropertyValueFactory<Duo, String>("primero"));
        top42TableColumn.setCellValueFactory(new PropertyValueFactory<Duo, String>("segundo"));

        top51TableColumn.setCellValueFactory(new PropertyValueFactory<Duo, String>("primero"));
        top52TableColumn.setCellValueFactory(new PropertyValueFactory<Duo, String>("segundo"));

        top61TableColumn.setCellValueFactory(new PropertyValueFactory<Duo, String>("primero"));
        top62TableColumn.setCellValueFactory(new PropertyValueFactory<Duo, String>("segundo"));

        top71TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top72TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top73TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));

        top81TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top82TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top83TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));

        top91TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top92TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top93TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));

        top101TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top102TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top103TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));

        top111TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top112TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top113TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));

        top121TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top122TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top123TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));

        top131TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top132TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top133TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));

        top141TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("primero"));
        top142TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("segundo"));
        top143TableColumn.setCellValueFactory(new PropertyValueFactory<Trio, String>("tercero"));
        
        actualizarReportes();
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
    private void onGestionUsuariosButtonClick(){
        try {
            App.setRoot("administrador/gestionUsuariosView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void actualizarReportes(){
        try {
            Reporte top10ProductosVendidos = ModeloReporte.getTop10ProductosVendidos();
            actualizaReporte(top10ProductosVendidos, top10ProductoVendidoTable, true);

            Reporte top10ClientesGanancias = ModeloReporte.getTop10ClientesGanancias();
            actualizaReporte(top10ClientesGanancias, top10ClientesGananciasTable, true);

            Reporte top3SucursalesVentas = ModeloReporte.getTop3SucursalesVentas();
            actualizaReporte(top3SucursalesVentas, top3SucursalesVentasTable, false);

            Reporte top3SucursalesGanancias = ModeloReporte.getTop3SucursalesGanancias();
            actualizaReporte(top3SucursalesGanancias, top3SucursalesGananciasTable, false);

            Reporte top3EmpleadosVentas = ModeloReporte.getTop3VendedoresVentas();
            actualizaReporte(top3EmpleadosVentas, top3EmpleadosVentasTable, false);

            Reporte top3EmpleadosIngresos = ModeloReporte.getTop3VendedoresGanancias();
            actualizaReporte(top3EmpleadosIngresos, top3EmpleadosIngresosTable, false);

            Reporte top10ProductosVendidos1 = ModeloReporte.getTop10ProductosVendidos();
            actualizaReporte(top10ProductosVendidos1, top10ProductoVendidoTable1, true);

            Reporte top10ProductosIngresos = ModeloReporte.getTop10ProductosGanancias();
            actualizaReporte(top10ProductosIngresos, top10ProductoIngresosTable, true);

            Reporte top5ProductosVendidosCentral = ModeloReporte.getTop5ProductosVentasSucursal("central");
            actualizaReporte(top5ProductosVendidosCentral, top5ProductosVendidosCentralTable, true);

            Reporte top5ProductosVendidosNorte = ModeloReporte.getTop5ProductosVentasSucursal("norte");
            actualizaReporte(top5ProductosVendidosNorte, top5ProductosVendidosNorteTable, true);

            Reporte top5ProductosVendidosSur = ModeloReporte.getTop5ProductosVentasSucursal("sur");
            actualizaReporte(top5ProductosVendidosSur, top5ProductosVendidosSurTable, true);

            Reporte top5ProductosIngresosCentral = ModeloReporte.getTop5ProductosGananciasSucursal("central");
            actualizaReporte(top5ProductosIngresosCentral, top5ProductosIngresosCentralTable, true);

            Reporte top5ProductosIngresosNorte = ModeloReporte.getTop5ProductosGananciasSucursal("norte");
            actualizaReporte(top5ProductosIngresosNorte, top5ProductosIngresosNorteTable, true);

            Reporte top5ProductosIngresosSur = ModeloReporte.getTop5ProductosGananciasSucursal("sur");
            actualizaReporte(top5ProductosIngresosSur, top5ProductosIngresosSurTable, true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //true=modo trio    false=modo duo
    private void actualizaReporte(Reporte reporte, TableView table, boolean modo){
        for (int i = 0; i < reporte.getCampos().get(0).size(); i++) {
            List<String> fila = reporte.getFila(i);
            if (modo) {
                Trio data = new Trio(fila.get(0), fila.get(1), fila.get(2));
                table.getItems().add(data);
            } else {
                Duo data = new Duo(fila.get(0), fila.get(1));
                table.getItems().add(data);
            }
        }
    }
}
