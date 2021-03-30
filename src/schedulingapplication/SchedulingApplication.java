package schedulingapplication;

import DBAccess.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Database.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Scanner;

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
//        String insertStatement = "INSERT INTO product_tbl(Description, Price) VALUES(?,?)";
//        
//        DBQuery.setPreparedStatement(conn, insertStatement);
//        
//        PreparedStatement ps = DBQuery.getPreparedStatement();
//
//        String description = "iPhone 11";
//        int price = 1000;
//        
//        ps.setString(1, description);
//        ps.setInt(2, price);
//        
//        ps.execute();
//        
//        if(ps.getUpdateCount() > 0 ){
//            System.out.println(ps.getUpdateCount() + " Rows Affected");
//        } else {
//            System.out.println("No Change");
//        }
        
        launch(args);
        DBConnection.closeConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.FXML"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign In");
        stage.show();
    }

}
