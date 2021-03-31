package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.*;

/**
 *
 * @author Adam Sindermann
 */
public class DBUser {

    public static ObservableList<User> getAllUsers() {
        Connection conn = DBConnection.getConnection();
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try {
            String query = "SELECT * from users";

            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                User user = new User(userID, userName, password);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public static User getUser(String userName) {
        Connection conn = DBConnection.getConnection();
        User user = new User(0, "", "");
        try {
            String query = "Select User_ID, User_Name, Password FROM users WHERE User_Name = ?";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                
                user = new User(userID, username, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
