/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DOA.DBAppointment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.TotalReportObj;
import utility.WindowInterface;
import utility.WindowUtility;

/**
 * FXML Controller class
 *
 * @author Adam Sindermann
 */
public class TotalReportController implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private TableColumn<TotalReportObj, String> monthCol;
    @FXML
    private TableColumn<TotalReportObj, String> typeCol;
    @FXML
    private TableColumn<TotalReportObj, Integer> totalCol;

    public static ObservableList<TotalReportObj> getTotals() {
        ObservableList<TotalReportObj> totals = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
        for (Appointment appointment: allAppointments){
            
        }
        
        
        return totals;
    }

    public void initialize(URL url, ResourceBundle rb) {
        monthCol.setCellValueFactory(new PropertyValueFactory<TotalReportObj, String>("month"));
        typeCol.setCellValueFactory(new PropertyValueFactory<TotalReportObj, String>("type"));
        totalCol.setCellValueFactory(new PropertyValueFactory<TotalReportObj, Integer>("total"));

        table.setItems(getTotals());
    }

}
