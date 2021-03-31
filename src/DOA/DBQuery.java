package DOA;


import java.sql.*;
/**
 *
 * @author Adam Sindermann
 */
public class DBQuery {
    //Statement reference
    private static PreparedStatement ps; 
    
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException{
        ps = conn.prepareStatement(sqlStatement);
    }
    
    public static PreparedStatement getPreparedStatement(){
        return ps;
    }
}
