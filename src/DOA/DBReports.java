package DOA;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.TotalReportObj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBReports {

    public static ObservableList<TotalReportObj> getAppointmentTotals() {
        Connection conn = DBConnection.getConnection();
        ObservableList<TotalReportObj> totals = FXCollections.observableArrayList();
        try {
            String query = "Select Count(Type), Type, MONTHNAME(Start) FROM Appointments " +
                    "WHERE MONTH(Start) = ? GROUP BY Type";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            //Query DB for each month of year
            //Probably a better way but this works
            for (int i = 1; i < 13; i++) {
                ps.setInt(1, i);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int count = rs.getInt("Count(Type)");
                    String type = rs.getString("Type");
                    String month = rs.getString("MONTHNAME(Start)");

                    TotalReportObj totalReportObj = new TotalReportObj(month, type, count);
                    totals.add(totalReportObj);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totals;
    }
}
