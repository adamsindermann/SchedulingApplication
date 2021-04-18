/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOA;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import model.Country;
import model.Customer;
import model.Division;

/**
 *
 * @author Adam Sindermann
 */
public class DBCustomer {

    public static ObservableList<Customer> getAllCustomers() {
        Connection conn = DBConnection.getConnection();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM customers";

            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                Division division = DBDivision.getDivision(divisionID);
                Country country = DBCountry.getCountry(division.getCountryID());

                Customer customer = new Customer(customerID, name, address, postalCode, phone, divisionID);
                customer.setDivision(division.getName());
                customer.setCountry(country.getName());
                allCustomers.add(customer);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    public static Customer getCustomer(int custID) {
        Connection conn = DBConnection.getConnection();
        Customer customer = new Customer(custID);
        try {
            String query = "Select Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(custID));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");

                customer.setName(name);
                customer.setAddress(address);
                customer.setPostalCode(postalCode);
                customer.setPhone(phone);
                customer.setDivisionID(divisionID);
                Division division = DBDivision.getDivision(divisionID);
                Country country = DBCountry.getCountry(division.getCountryID());
                customer.setDivision(division.getName());
                customer.setCountry(country.getName());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

}