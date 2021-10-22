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
public class TotalReportObj {
    private String month;
    private String type;
    private int total;

    public TotalReportObj(String month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }

    public int getTotal() {
        return total;
    }
    
    
}
