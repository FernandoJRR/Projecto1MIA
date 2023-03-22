package com.universidad.mia_proyecto1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import com.universidad.mia_proyecto1.database.ModeloBodega;
import com.universidad.mia_proyecto1.database.ModeloReporte;
import com.universidad.mia_proyecto1.modelo.Producto;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    public static String sesionUser = null;
    public static String sesionTipo = null;
    public static String sesionSucursal = null;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        /* 
        try {
            List<Producto> productosBodega = ModeloBodega.getProductos();
            System.out.println("ID\tCodigo\tNombre");
            for (Producto producto : productosBodega) {
                System.out.println(producto.getId()+" "+producto.getCodigo()+" "+producto.getNombre());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        launch();
    }
    
}