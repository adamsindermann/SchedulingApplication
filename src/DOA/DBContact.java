/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOA;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Country;
import model.Customer;
import model.Division;

/**
 *
 * @author Adam Sindermann
 */
public class DBContact {
    public static ObservableList<Contact> getAllContacts(){
               Connection conn = DBConnection.getConnection();
       ObservableList<Contact> allContacts = FXCollections.observableArrayList();
       try {
           String query = "SELECT * FROM contacts";
           
           DBQuery.setPreparedStatement(conn, query);
           PreparedStatement ps = DBQuery.getPreparedStatement();
           
           ResultSet rs = ps.executeQuery();
           
           while (rs.next()){
               int contactID = rs.getInt("Contact_ID");
               String name = rs.getString("Contact_Name");
               String email = rs.getString("Email");
                       
               
               Contact contact = new Contact(contactID, name, email);
               allContacts.add(contact);
               
               
           }
       } catch (SQLException e){
           e.printStackTrace();
       }
       return allContacts;
    }
    
    public static Contact getContact(int contactID){
        Connection conn = DBConnection.getConnection();
        Contact contact = new Contact(contactID);
        
        try {
            String query = "Select Contact_Name, Email FROM contacts WHERE Contact_ID = ?";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(contactID));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String email = rs.getString("Email");
                String name = rs.getString("Contact_Name");
                
                contact.setEmail(email);
                contact.setName(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }
}
