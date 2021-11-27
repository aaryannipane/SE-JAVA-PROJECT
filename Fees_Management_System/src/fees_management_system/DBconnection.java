/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees_management_system;
import java.sql.*;

/**
 *
 * @author sanjay
 */
public class DBconnection {
    public static Connection getConnection(){
        Connection con = null;
        String currentDir = System.getProperty("user.dir");
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            con = DriverManager.getConnection("jdbc:derby:"+ currentDir +"\\fees_management_system", "root", "root");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return con;
    }
}
