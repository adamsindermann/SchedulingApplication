package schedulingapplication;

import DOA.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Database.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Scanner;
import utility.Session;

/**
 *
 * @author Adam Sindermann
 */
public class SchedulingApplication extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Connection conn = DBConnection.startConnection();
        Session.setZoneID(ZoneId.systemDefault());
//        Locale.setDefault(Locale.FRENCH);
        
        launch(args);
        DBConnection.closeConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.FXML"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign In");
        if(Locale.getDefault().toString().equals("fr")){
            stage.setTitle("S'identifier");
        }
        stage.show();
    }

}
