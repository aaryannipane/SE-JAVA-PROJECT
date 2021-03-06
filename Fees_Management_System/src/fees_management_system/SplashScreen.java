/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees_management_system;


import javax.swing.JOptionPane;

/**
 *
 * @author sanjay
 */
public class SplashScreen extends javax.swing.JFrame {

    /**
     * Creates new form SplashScreen
     */
    public SplashScreen() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        lbl_loadingText = new javax.swing.JLabel();
        lbl_loadingNumber = new javax.swing.JLabel();
        lbl_versionText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusable(false);
        setFocusableWindowState(false);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(30, 32, 32));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 134, 252))));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FEES MANAGEMENT SYSTEM");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/icon-fee-splash.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 110));
        jPanel1.add(progressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 430, -1));

        lbl_loadingText.setForeground(new java.awt.Color(255, 255, 255));
        lbl_loadingText.setText("Loading...");
        jPanel1.add(lbl_loadingText, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, -1, -1));

        lbl_loadingNumber.setForeground(new java.awt.Color(255, 255, 255));
        lbl_loadingNumber.setText("0%");
        jPanel1.add(lbl_loadingNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 260, 40, -1));

        lbl_versionText.setForeground(new java.awt.Color(255, 255, 255));
        lbl_versionText.setText("V 1.0.0.1");
        jPanel1.add(lbl_versionText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 320));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        SplashScreen sp = new SplashScreen();
        sp.setVisible( true );
        
        int i;
        try{
            for(i=0; i<=100; i=i+2){
                Thread.sleep(100);
                sp.lbl_loadingNumber.setText(i + "%");
                
                if(i == 10){
                    sp.lbl_loadingText.setText("Turning on modules...");
                }
                if(i == 20){
                    sp.lbl_loadingText.setText("Loading modules...");
                }
                if(i == 50){
                    sp.lbl_loadingText.setText("Connecting to Database...");
                }
                if(i == 70){
                    sp.lbl_loadingText.setText("Connection Successfull...");
                }
                if(i == 80){
                    sp.lbl_loadingText.setText("Launching Application");
                }
                sp.progressBar.setValue(i);
            }
            
            sp.dispose();
            Login_page login = new Login_page();
                
            login.setVisible(true);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_loadingNumber;
    private javax.swing.JLabel lbl_loadingText;
    private javax.swing.JLabel lbl_versionText;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
