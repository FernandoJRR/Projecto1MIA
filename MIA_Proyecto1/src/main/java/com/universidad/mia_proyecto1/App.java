package com.universidad.mia_proyecto1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import com.universidad.mia_proyecto1.database.Conexion;
import com.universidad.mia_proyecto1.database.LoginUsuario;
import com.universidad.mia_proyecto1.exceptions.PasswordIncorrecto;
import com.universidad.mia_proyecto1.exceptions.UsuarioNoExisteException;
import com.universidad.mia_proyecto1.modelo.Usuario;
import com.universidad.mia_proyecto1.utilidades.ConvertidorHash;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
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
        launch();
    }
    
}