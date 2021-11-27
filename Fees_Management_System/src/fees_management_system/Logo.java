/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees_management_system;

import java.awt.*;

/**
 *
 * @author sanjay
 */
public class Logo {
    public static Image setIcon(){
        String currentDir = System.getProperty("user.dir");
        Image icon = Toolkit.getDefaultToolkit().getImage(currentDir+"\\ICON\\Icon-fee.png");
//        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\sanjay\\Documents\\NetBeansProjects\\Fees_Management_System\\ICON\\Icon-fee.png");
//        this.setIconImage(icon); 
        return icon;
    }
}
