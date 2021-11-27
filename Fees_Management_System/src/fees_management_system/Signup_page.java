/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees_management_system;

import java.sql.*;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author sanjay
 */
public class Signup_page extends javax.swing.JFrame {

    /**
     * Creates new form Sign_Up_page
     */
    public Signup_page() {
        initComponents();
        this.setTitle("Signup");
        this.setIconImage(Logo.setIcon());
    }
 
    // declaration of variables 
    String fname, lname, uname, password, con_pass, contact_no;
    Date dob;
    int id=0;
    
    public int getId(){
        ResultSet rs = null;
        String currentDir = System.getProperty("user.dir");
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby:"+currentDir+"\\fees_management_system", "root", "root");
            String sql = "select max(id) from signup";
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                id = rs.getInt(1);
                id++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }
    
    //signup validation
    boolean validation(){
        
//        fetching data from signup page text fields
        fname = txt_firstname.getText();
        lname = txt_lastname.getText();
        uname = txt_username.getText();
        password = txt_password.getText();
        con_pass = txt_con_password.getText();
        contact_no = txt_contactno.getText();
        dob = txt_dob.getDate();
        
//        checking any text fields is empty than return false
        if(fname.equals("")){
            JOptionPane.showMessageDialog(this, "please enter firstname ");
            return false;
        }  
        
        if(lname.equals("")){
            JOptionPane.showMessageDialog(this, "please enter lastname ");
            return false;
        }  
        
        if(uname.equals("")){
            JOptionPane.showMessageDialog(this, "please enter username ");
            return false;
        }  
        
        if(password.equals("")){
            JOptionPane.showMessageDialog(this, "please enter password ");
            return false;
        }  
        
        if(con_pass.equals("")){
            JOptionPane.showMessageDialog(this, "please enter confirm password ");
            return false;
        } 
        
        if(contact_no.equals("")){
            JOptionPane.showMessageDialog(this, "please enter contact number ");
            return false;
        }
        
        if(dob == null){
            JOptionPane.showMessageDialog(this, "please enter date of birth ");
            return false;
        }
        
        if(!password.equals(con_pass)){
            JOptionPane.showMessageDialog(this, "confirm password not match with the password");
            return false;
        }
        
        if(!checkNumber(contact_no)){
            JOptionPane.showMessageDialog(this, "Character is not allowed in phone number");
            return false;
        }
        
        if(contact_no.length() < 10 || contact_no.length()>10){
            JOptionPane.showMessageDialog(this, "Contact number should be of 10 digit");
            return false;
        }
        
     
        // if all fields are full than return true
        return true;
    }
    
    public boolean checkNumber(String x){
        char[] chars = x.toCharArray() ;
        boolean flag = true;
        for(char c : chars){
            if(!Character.isDigit(c)){
                flag = false;
                break;
            }
        }
        
        if(flag){
//            System.out.println("there are all numbers in phone number");
            return true;

        }else{
//            System.out.println("There is Character in phone no");
            return false;
        }
        
    }
    
    public void checkPassword(){
        password = txt_password.getText();
        if(password.length()<8){
            lbl_password_error.setText("*Password should be of 8 digit");
        }
        else{
            lbl_password_error.setText("");
        }
    }
    
    public void checkContactNo(){
        contact_no = txt_contactno.getText();
        
        if(contact_no.length() < 10 || contact_no.length()>10){
            lbl_contactno_error.setText("*contact number should be of 10 digit");
        }
        else{
            lbl_contactno_error.setText("");
        }
    }
    
    public void checkConfirmPassword(){
        password = txt_password.getText();
        con_pass = txt_con_password.getText();
        
        if(!password.equals(con_pass)){
            lbl_con_pass_error.setText("*Password not match");
        }
        else{
            lbl_con_pass_error.setText("");
        }
    }
    
//    database connection to insert details in database
    void insertDetails(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String myDob = format.format(dob);
        String currentDir = System.getProperty("user.dir");
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby:"+ currentDir +"\\fees_management_system", "root", "root");
            String sql = "insert into signup values(?,?,?,?,?,?,?)";          
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, getId());
            stmt.setString(2, fname);
            stmt.setString(3, lname);
            stmt.setString(4, uname);
            stmt.setString(5, password);
            stmt.setString(6, myDob);
            stmt.setString(7, contact_no);
            int i = stmt.executeUpdate();
            
            if(i>0){
                JOptionPane.showMessageDialog(this, "Record Inserted");
            }
            else{
                JOptionPane.showMessageDialog(this, "Record not Inserted");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_firstname = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        txt_lastname = new javax.swing.JTextField();
        txt_contactno = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        txt_con_password = new javax.swing.JPasswordField();
        txt_dob = new com.toedter.calendar.JDateChooser();
        btn_signup = new javax.swing.JButton();
        btn_login = new javax.swing.JButton();
        lbl_password_error = new javax.swing.JLabel();
        lbl_con_pass_error = new javax.swing.JLabel();
        lbl_contactno_error = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(30, 32, 32));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(232, 232, 232));
        jLabel2.setText("Firstname :");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 21, -1, -1));

        jLabel3.setBackground(new java.awt.Color(232, 232, 232));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(232, 232, 232));
        jLabel3.setText("Username :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 128, -1, -1));

        jLabel4.setBackground(new java.awt.Color(232, 232, 232));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(232, 232, 232));
        jLabel4.setText("Password :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 181, -1, -1));

        jLabel5.setBackground(new java.awt.Color(232, 232, 232));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(232, 232, 232));
        jLabel5.setText("Confirm Password :");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 233, -1, -1));

        jLabel6.setBackground(new java.awt.Color(232, 232, 232));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(232, 232, 232));
        jLabel6.setText("D.O.B :");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 288, -1, -1));

        jLabel7.setBackground(new java.awt.Color(232, 232, 232));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(232, 232, 232));
        jLabel7.setText("Contact no :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 331, 172, -1));

        jLabel8.setBackground(new java.awt.Color(232, 232, 232));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(232, 232, 232));
        jLabel8.setText("Lastname :");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 75, -1, -1));

        txt_firstname.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_firstnameActionPerformed(evt);
            }
        });
        jPanel2.add(txt_firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 22, 141, -1));

        txt_username.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });
        jPanel2.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 128, 141, -1));

        txt_lastname.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_lastname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lastnameActionPerformed(evt);
            }
        });
        jPanel2.add(txt_lastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 75, 141, -1));

        txt_contactno.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_contactno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contactnoActionPerformed(evt);
            }
        });
        txt_contactno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_contactnoKeyReleased(evt);
            }
        });
        jPanel2.add(txt_contactno, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 333, 140, -1));

        txt_password.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passwordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_passwordKeyReleased(evt);
            }
        });
        jPanel2.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 181, 141, -1));

        txt_con_password.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_con_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_con_passwordActionPerformed(evt);
            }
        });
        txt_con_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_con_passwordKeyReleased(evt);
            }
        });
        jPanel2.add(txt_con_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 233, 141, -1));

        txt_dob.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.add(txt_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 288, 140, -1));

        btn_signup.setBackground(new java.awt.Color(187, 134, 252));
        btn_signup.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btn_signup.setForeground(new java.awt.Color(18, 18, 18));
        btn_signup.setText("Signup");
        btn_signup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });
        jPanel2.add(btn_signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 411, 108, -1));

        btn_login.setBackground(new java.awt.Color(187, 134, 252));
        btn_login.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btn_login.setText("login");
        btn_login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });
        jPanel2.add(btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 411, 141, -1));

        lbl_password_error.setFont(new java.awt.Font("Vrinda", 1, 13)); // NOI18N
        lbl_password_error.setForeground(new java.awt.Color(255, 1, 1));
        jPanel2.add(lbl_password_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 210, 218, 20));

        lbl_con_pass_error.setFont(new java.awt.Font("Vrinda", 1, 13)); // NOI18N
        lbl_con_pass_error.setForeground(new java.awt.Color(255, 1, 1));
        jPanel2.add(lbl_con_pass_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 260, 218, 30));

        lbl_contactno_error.setFont(new java.awt.Font("Vrinda", 1, 13)); // NOI18N
        lbl_contactno_error.setForeground(new java.awt.Color(255, 1, 1));
        jPanel2.add(lbl_contactno_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 364, 218, 30));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 510, 500));

        jPanel1.setBackground(new java.awt.Color(18, 18, 18));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setBackground(new java.awt.Color(3, 218, 197));
        jSeparator1.setForeground(new java.awt.Color(3, 218, 197));
        jSeparator1.setToolTipText("");
        jSeparator1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 110, 20));

        jLabel1.setFont(new java.awt.Font("Vrinda", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Signup");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 90));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 100));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_firstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_firstnameActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void txt_lastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lastnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_lastnameActionPerformed

    private void txt_con_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_con_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_con_passwordActionPerformed

    private void txt_contactnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactnoActionPerformed

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
        // TODO add your handling code here:
        if(validation()){
            insertDetails();
            Login_page login = new Login_page();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btn_signupActionPerformed

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        // TODO add your handling code here:
        Login_page login = new Login_page();
        login.setTitle("Login Page");
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_loginActionPerformed

    private void txt_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passwordKeyPressed
        // TODO add your handling code here:
//        checkPassword();
    }//GEN-LAST:event_txt_passwordKeyPressed

    private void txt_passwordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passwordKeyReleased
        // TODO add your handling code here:
        checkPassword();
    }//GEN-LAST:event_txt_passwordKeyReleased

    private void txt_contactnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactnoKeyReleased
        // TODO add your handling code here:
        checkContactNo();
    }//GEN-LAST:event_txt_contactnoKeyReleased

    private void txt_con_passwordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_con_passwordKeyReleased
        // TODO add your handling code here:
        checkConfirmPassword();
    }//GEN-LAST:event_txt_con_passwordKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Signup_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Signup_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Signup_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Signup_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Signup_page().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JButton btn_signup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbl_con_pass_error;
    private javax.swing.JLabel lbl_contactno_error;
    private javax.swing.JLabel lbl_password_error;
    private javax.swing.JPasswordField txt_con_password;
    private javax.swing.JTextField txt_contactno;
    private com.toedter.calendar.JDateChooser txt_dob;
    private javax.swing.JTextField txt_firstname;
    private javax.swing.JTextField txt_lastname;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
