/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Adam Sindermann
 */
public class Attendee {
    private String name;
    private String email;
    private String phone;

    public Attendee(String name) {
        this.name = name;
        this.email = "";
        this.phone = "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    
    
    
    
}
