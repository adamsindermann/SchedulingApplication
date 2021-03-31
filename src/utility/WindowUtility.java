/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
/**
 *
 * @author Adam Sindermann
 */

public class WindowUtility {

    public static void newWindow(FXMLLoader loader, String title) throws IOException {
        Parent parent = loader.load();

        Scene newScene = new Scene(parent);
        Stage stage = new Stage();

        stage.setTitle("title");
        stage.setScene(newScene);
        stage.show();
    }
}
