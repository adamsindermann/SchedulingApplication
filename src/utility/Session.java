/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.time.ZoneId;
import model.User;

/**
 *
 * @author Adam Sindermann
 */
public class Session {
    private static User currentUser;
    private static ZoneId zoneID;


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static ZoneId getZoneID() {
        return zoneID;
    }

    public static void setZoneID(ZoneId newZoneID) {
        zoneID = newZoneID;
    }
    
    
}
