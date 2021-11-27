/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees_management_system;

import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author sanjay
 */
public class UpdateFeesDetails extends javax.swing.JFrame {

    /**
     * Creates new form AddFees
     */
    public UpdateFeesDetails() {
        initComponents();
        this.setTitle("Update Fees");
        this.setIconImage(Logo.setIcon());
        displayCashFirst();
        fillComboBox();
        setRecieptNo();
        
        setRecords();
    }
    
    public void displayCashFirst(){
//      moveChildPanelUp();
        lbl_ddNo.setVisible(false);
        lbl_chequeNo.setVisible(false);
        lbl_bankName.setVisible(false);
        
        txt_ddNo.setVisible(false);
        txt_chequeNo.setVisible(false);
        txt_bankName.setVisible(false);
    }
    
//    NEW
    public void moveChildPanelUp(){
        // 0, 110
        int x = 0;
        int y = 110;
        child_panel.setLocation(x, y);
    }
    public void moveChildPanelDown(){
        // 0, 160
        int x = 0;
        int y = 160;
        child_panel.setLocation(x, y);
        
//        lbl_text.setText("Hello");
    }
    
    public boolean validation(){
        if(txt_recievedFrom.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter username");
            return false;
        }
        if(dateChooser.getDate() == null){
            JOptionPane.showMessageDialog(this, "Please select date");
            return false;
        }
        if(txt_totalCourseAmount.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter total amount (In number)");
            return false;
        }
        if(combo_paymentMode.getSelectedItem().toString().equalsIgnoreCase("cheque")){
            if(txt_bankName.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter bank name");
                return false;
            }
            if(txt_chequeNo.getText().equals("") || txt_chequeNo.getText().matches("[0-9]+") == false){
                JOptionPane.showMessageDialog(this, "Please enter cheque number");
                return false;
            }
        }
        if(combo_paymentMode.getSelectedItem().toString().equalsIgnoreCase("DD")){
            if(txt_bankName.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter bank name");
                return false;
            }
            if(txt_ddNo.getText().equals("") || txt_ddNo.getText().matches("[0-9]+") == false){
                JOptionPane.showMessageDialog(this, "Please enter DD number");
                return false;
            }
        }
        if(combo_paymentMode.getSelectedItem().toString().equalsIgnoreCase("card")){
            if(txt_bankName.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter bank name");
                return false;
            }
        }
        
        return true;        
    }
    
    public void fillComboBox(){
        String currentDir = System.getProperty("user.dir");
        try{
            
            Connection con = DBconnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select cname from course");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                combo_course.addItem(rs.getString("cname"));
            }
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setRecieptNo(){
        int recieptNo = 0;
        try{
            Connection con = DBconnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select max(reciept_no) from fees_details");
            ResultSet rs = pst.executeQuery();

            if(rs.next() == true){
                recieptNo = rs.getInt(1) + 1;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        txt_recieptNo.setText(Integer.toString(recieptNo));
    }
    
    // inserting data in database
    public String updateData(){
        
        String status = "";
        
//        taking value and storing in variables
        int recieptNo = Integer.parseInt(txt_recieptNo.getText());
        String studentName = txt_recievedFrom.getText();
        String rollNo = txt_rollNo.getText();
        String paymentMode = combo_paymentMode.getSelectedItem().toString();
        String chequeNo = txt_chequeNo.getText();
        String bankName = txt_bankName.getText();
        String ddNo = txt_ddNo.getText();
        String courseName = txt_course.getText();
        String gstin = lbl_gstin.getText();
        float totalAmount = Float.parseFloat(txt_totalAmount.getText());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(dateChooser.getDate());
        float initialAmount = Float.parseFloat(txt_totalCourseAmount.getText());
        float cgst = Float.parseFloat(txt_cgstAmount.getText());
        float sgst = Float.parseFloat(txt_sgstAmount.getText());
        String totalAmountWords = txt_totalAmountWords.getText();
        String remark = txt_remark.getText();
        int year1 = Integer.parseInt(txt_year1.getText());
        int year2 = Integer.parseInt(txt_year2.getText());
        
        try{
            Connection con = DBconnection.getConnection();
            PreparedStatement pst = con.prepareStatement("update fees_details set student_name=?, roll_no=?, payment_mode=?, cheque_no=?, bank_name=?, dd_no=?,course_name=?, gstin=?, total_amount=?, date=?, amount=?, cgst=?, sgst=?, total_in_words=?, remark=?, year1=?, year2=? where reciept_no = ?");
            
            pst.setString(1, studentName);
            pst.setString(2, rollNo);
            pst.setString(3, paymentMode);
            pst.setString(4, chequeNo);
            pst.setString(5, bankName);
            pst.setString(6, ddNo);
            pst.setString(7, courseName);
            pst.setString(8, gstin);
            pst.setFloat(9, totalAmount);
            pst.setString(10, date);
            pst.setFloat(11, initialAmount);
            pst.setFloat(12, cgst);
            pst.setFloat(13, sgst);
            pst.setString(14, totalAmountWords);
            pst.setString(15, remark);
            pst.setInt(16, year1);
            pst.setInt(17, year2);
            pst.setInt(18, recieptNo);
            
            int rowCount = pst.executeUpdate();
            if(rowCount == 1){
                status = "success";
            }else{
                status = "failed";
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return status;
        
    }
    
//    set last records from database to text fields
    public void setRecords(){
        try{
            Connection con = DBconnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from fees_details order by reciept_no desc fetch first 1 rows only");
            ResultSet rs = pst.executeQuery();
            
            rs.next();
            
            txt_recieptNo.setText(rs.getString("reciept_no"));
            combo_paymentMode.setSelectedItem(rs.getString("payment_mode"));
            txt_chequeNo.setText(rs.getString("cheque_no"));
            txt_ddNo.setText(rs.getString("dd_no"));
            txt_bankName.setText(rs.getString("bank_name"));
            txt_recievedFrom.setText(rs.getString("student_name"));
            txt_year1.setText(rs.getString("year1"));
            txt_year2.setText(rs.getString("year2"));
            txt_rollNo.setText(rs.getString("roll_no"));
            combo_course.setSelectedItem(rs.getString("course_name"));
            dateChooser.setDate(rs.getDate("date"));
            txt_totalCourseAmount.setText(rs.getString("amount"));
            txt_cgstAmount.setText(rs.getString("cgst"));
            txt_sgstAmount.setText(rs.getString("sgst"));
            txt_totalAmount.setText(rs.getString("total_amount"));
            txt_totalAmountWords.setText(rs.getString("total_in_words"));
            txt_remark.setText(rs.getString("remark"));
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int setAmount(String course){
        int cost=0;
        
        try{
            Connection con = DBconnection.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM course WHERE cname = ?");
            pst.setString(1, course);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                cost = rs.getInt("cost");
            }
            else{
                cost = 0;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return cost;
    }
    
    public void updateAmounts(){
        Float amnt = Float.parseFloat(txt_totalCourseAmount.getText());
        
        float cgst = (amnt * 0.09f);
        float sgst = (amnt * 0.09f);
        
        txt_cgstAmount.setText(Float.toString(cgst));
        txt_sgstAmount.setText(Float.toString(sgst));
        
        float total = amnt + cgst + sgst;
        txt_totalAmount.setText(Float.toString(total));
        
        txt_totalAmountWords.setText(NumberToWordsConverter.convert((int)total) + " Only");
    }
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parent_panel = new javax.swing.JPanel();
        child_panel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_rollNo = new javax.swing.JTextField();
        txt_year1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_totalAmountWords = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        combo_course = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        lbl_sgst = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_recievedFrom = new javax.swing.JTextField();
        txt_totalAmount = new javax.swing.JTextField();
        txt_totalCourseAmount = new javax.swing.JTextField();
        txt_cgstAmount = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txt_sgstAmount = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        lbl_cgst = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txt_course = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_remark = new javax.swing.JTextArea();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();
        txt_year2 = new javax.swing.JTextField();
        lbl_gstin = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_ddNo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_recieptNo = new javax.swing.JTextField();
        txt_ddNo = new javax.swing.JTextField();
        combo_paymentMode = new javax.swing.JComboBox<>();
        txt_chequeNo = new javax.swing.JTextField();
        dateChooser = new com.toedter.calendar.JDateChooser();
        lbl_bankName = new javax.swing.JLabel();
        txt_bankName = new javax.swing.JTextField();
        lbl_chequeNo = new javax.swing.JLabel();
        lbl_text = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_logout = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btn_home = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btn_search_records = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btn_edit_courses = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        btn_view_all_record = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        btn_course_list = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        btn_back = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        parent_panel.setBackground(new java.awt.Color(30, 32, 32));
        parent_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        child_panel.setBackground(new java.awt.Color(30, 32, 32));
        child_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(232, 232, 232));
        jLabel8.setText("The following payment in the college office for the year ");
        child_panel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, 30));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(232, 232, 232));
        jLabel17.setText("  -");
        child_panel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 20, 30));

        txt_rollNo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_rollNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rollNoActionPerformed(evt);
            }
        });
        child_panel.add(txt_rollNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 80, 50, 30));

        txt_year1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_year1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_year1ActionPerformed(evt);
            }
        });
        child_panel.add(txt_year1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 60, 30));

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(232, 232, 232));
        jLabel18.setText("Amounts (Rs)");
        child_panel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, -1, 30));

        txt_totalAmountWords.setEditable(false);
        txt_totalAmountWords.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_totalAmountWords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalAmountWordsActionPerformed(evt);
            }
        });
        child_panel.add(txt_totalAmountWords, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 390, 30));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(232, 232, 232));
        jLabel19.setText("Roll No : ");
        child_panel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, -1, 30));

        combo_course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_courseActionPerformed(evt);
            }
        });
        child_panel.add(combo_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 460, -1));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(232, 232, 232));
        jLabel20.setText("Recieved From : ");
        child_panel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 30));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        child_panel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 870, 10));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        child_panel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 410, 170, 10));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(232, 232, 232));
        jLabel21.setText("Course : ");
        child_panel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        lbl_sgst.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lbl_sgst.setForeground(new java.awt.Color(232, 232, 232));
        lbl_sgst.setText("SGST 9%");
        child_panel.add(lbl_sgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, -1, 30));

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(232, 232, 232));
        jLabel23.setText("Heads");
        child_panel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, -1, 30));

        txt_recievedFrom.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_recievedFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_recievedFromActionPerformed(evt);
            }
        });
        child_panel.add(txt_recievedFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 370, 30));

        txt_totalAmount.setEditable(false);
        txt_totalAmount.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_totalAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalAmountActionPerformed(evt);
            }
        });
        child_panel.add(txt_totalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 120, 30));

        txt_totalCourseAmount.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_totalCourseAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalCourseAmountActionPerformed(evt);
            }
        });
        child_panel.add(txt_totalCourseAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, 120, 30));

        txt_cgstAmount.setEditable(false);
        txt_cgstAmount.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_cgstAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cgstAmountActionPerformed(evt);
            }
        });
        child_panel.add(txt_cgstAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 220, 120, 30));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        child_panel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 870, 10));

        txt_sgstAmount.setEditable(false);
        txt_sgstAmount.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_sgstAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sgstAmountActionPerformed(evt);
            }
        });
        child_panel.add(txt_sgstAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 260, 120, 30));

        jLabel24.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(232, 232, 232));
        jLabel24.setText("Remark : ");
        child_panel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, 20));

        lbl_cgst.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lbl_cgst.setForeground(new java.awt.Color(232, 232, 232));
        lbl_cgst.setText("CGST 9%");
        child_panel.add(lbl_cgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, 30));

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(232, 232, 232));
        jLabel26.setText("Sr. No");
        child_panel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, 30));

        txt_course.setEditable(false);
        txt_course.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_courseActionPerformed(evt);
            }
        });
        child_panel.add(txt_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 390, 30));

        jLabel27.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(232, 232, 232));
        jLabel27.setText("Reciever Signature");
        child_panel.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 410, -1, 20));

        txt_remark.setColumns(20);
        txt_remark.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_remark.setRows(5);
        jScrollPane1.setViewportView(txt_remark);

        child_panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 390, 130));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        child_panel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 310, 240, 10));

        jLabel28.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(232, 232, 232));
        jLabel28.setText("Total in words : ");
        child_panel.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, 30));

        btn_print.setBackground(new java.awt.Color(3, 218, 197));
        btn_print.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        child_panel.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 460, 110, -1));

        txt_year2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_year2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_year2ActionPerformed(evt);
            }
        });
        child_panel.add(txt_year2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 60, 30));

        parent_panel.add(child_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 890, 590));

        lbl_gstin.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        lbl_gstin.setForeground(new java.awt.Color(232, 232, 232));
        lbl_gstin.setText("2156F4S5F4S");
        parent_panel.add(lbl_gstin, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, -1, -1));

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(232, 232, 232));
        jLabel10.setText("Mode of Payment :");
        parent_panel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, 20));

        lbl_ddNo.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        lbl_ddNo.setForeground(new java.awt.Color(232, 232, 232));
        lbl_ddNo.setText("DD No : ");
        parent_panel.add(lbl_ddNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, -1, 30));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(232, 232, 232));
        jLabel13.setText("Reciept no : MGMCET-");
        parent_panel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, 30));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(232, 232, 232));
        jLabel14.setText("Date : ");
        parent_panel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, -1, 30));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(232, 232, 232));
        jLabel15.setText("GSTIN : ");
        parent_panel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, -1, -1));

        txt_recieptNo.setEditable(false);
        txt_recieptNo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_recieptNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_recieptNoActionPerformed(evt);
            }
        });
        parent_panel.add(txt_recieptNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 120, 30));

        txt_ddNo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_ddNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ddNoActionPerformed(evt);
            }
        });
        parent_panel.add(txt_ddNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 140, 30));

        combo_paymentMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "Cheque", "Cash", "Card" }));
        combo_paymentMode.setSelectedIndex(2);
        combo_paymentMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_paymentModeActionPerformed(evt);
            }
        });
        parent_panel.add(combo_paymentMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 120, -1));

        txt_chequeNo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_chequeNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_chequeNoActionPerformed(evt);
            }
        });
        parent_panel.add(txt_chequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 140, 30));
        parent_panel.add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 40, 130, 30));

        lbl_bankName.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        lbl_bankName.setForeground(new java.awt.Color(232, 232, 232));
        lbl_bankName.setText("Bank Name : ");
        parent_panel.add(lbl_bankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, 30));

        txt_bankName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_bankName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bankNameActionPerformed(evt);
            }
        });
        parent_panel.add(txt_bankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 120, 30));

        lbl_chequeNo.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        lbl_chequeNo.setForeground(new java.awt.Color(232, 232, 232));
        lbl_chequeNo.setText("Cheque No :");
        parent_panel.add(lbl_chequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, -1, 30));

        lbl_text.setForeground(new java.awt.Color(255, 255, 51));
        parent_panel.add(lbl_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 280, 20));

        getContentPane().add(parent_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 890, 700));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_logout.setBackground(new java.awt.Color(187, 134, 252));
        btn_logout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
        btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_logoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_logoutMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_logoutMousePressed(evt);
            }
        });
        btn_logout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel1.setText("Logout");
        btn_logout.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/logoutEdited.png"))); // NOI18N
        btn_logout.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 40));

        jPanel2.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, 230, 40));

        btn_home.setBackground(new java.awt.Color(187, 134, 252));
        btn_home.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
        btn_home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_homeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_homeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_homeMouseExited(evt);
            }
        });
        btn_home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel12.setText("Home");
        btn_home.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, 40));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/home_edited.png"))); // NOI18N
        btn_home.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 40));

        jPanel2.add(btn_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 230, 40));

        btn_search_records.setBackground(new java.awt.Color(187, 134, 252));
        btn_search_records.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
        btn_search_records.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_search_records.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_search_recordsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_search_recordsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_search_recordsMouseExited(evt);
            }
        });
        btn_search_records.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Search Records");
        btn_search_records.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/search2.png"))); // NOI18N
        btn_search_records.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 40));

        jPanel2.add(btn_search_records, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 230, 40));

        btn_edit_courses.setBackground(new java.awt.Color(187, 134, 252));
        btn_edit_courses.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
        btn_edit_courses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_edit_courses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_edit_coursesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_edit_coursesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_edit_coursesMouseExited(evt);
            }
        });
        btn_edit_courses.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Edit Courses");
        btn_edit_courses.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/edit2_edited.png"))); // NOI18N
        btn_edit_courses.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, -1));

        jPanel2.add(btn_edit_courses, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 230, 40));

        btn_view_all_record.setBackground(new java.awt.Color(187, 134, 252));
        btn_view_all_record.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
        btn_view_all_record.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_view_all_record.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_view_all_recordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_view_all_recordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_view_all_recordMouseExited(evt);
            }
        });
        btn_view_all_record.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("View All Record");
        btn_view_all_record.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, 40));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/view_course_edited.png"))); // NOI18N
        btn_view_all_record.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 40));

        jPanel2.add(btn_view_all_record, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 230, 40));

        btn_course_list.setBackground(new java.awt.Color(187, 134, 252));
        btn_course_list.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
        btn_course_list.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_course_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_course_listMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_course_listMouseExited(evt);
            }
        });
        btn_course_list.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel6.setText("Course List");
        btn_course_list.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/list_1_Edited.png"))); // NOI18N
        btn_course_list.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel2.add(btn_course_list, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 230, 40));

        btn_back.setBackground(new java.awt.Color(187, 134, 252));
        btn_back.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
        btn_back.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_backMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_backMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_backMousePressed(evt);
            }
        });
        btn_back.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Back");
        btn_back.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, 40));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/back1.png"))); // NOI18N
        btn_back.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 40));

        jPanel2.add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 230, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_rollNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rollNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rollNoActionPerformed

    private void txt_recieptNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_recieptNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_recieptNoActionPerformed

    private void txt_ddNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ddNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ddNoActionPerformed

    private void combo_paymentModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_paymentModeActionPerformed
        // TODO add your handling code here:
        int index = combo_paymentMode.getSelectedIndex();
        
        if(index == 0){ // when user selects dd from combo
//            moveChildPanelDown();
            lbl_ddNo.setVisible(true);
            txt_ddNo.setVisible(true);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
            
            lbl_chequeNo.setVisible(false);
            txt_chequeNo.setVisible(false);
        }
        else if(index == 1){ // when user selects check from combo
//            moveChildPanelDown();
            lbl_chequeNo.setVisible(true);
            txt_chequeNo.setVisible(true);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
            
            lbl_ddNo.setVisible(false);
            txt_ddNo.setVisible(false);
        }
        else if(index == 2){ // when user selects cash from combo
//            moveChildPanelUp();
            
            lbl_ddNo.setVisible(false);
            lbl_chequeNo.setVisible(false);
            lbl_bankName.setVisible(false);
            txt_ddNo.setVisible(false);
            txt_chequeNo.setVisible(false);
            txt_bankName.setVisible(false);
        }
        else if(index == 3){ // when user selects card from combo
//            moveChildPanelDown();
            lbl_ddNo.setVisible(false);
            lbl_chequeNo.setVisible(false);
            lbl_bankName.setVisible(true);
            txt_ddNo.setVisible(false);
            txt_chequeNo.setVisible(false);
            txt_bankName.setVisible(true);
        }
    }//GEN-LAST:event_combo_paymentModeActionPerformed

    private void txt_chequeNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_chequeNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_chequeNoActionPerformed

    private void txt_totalAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalAmountActionPerformed

    private void txt_year1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_year1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_year1ActionPerformed

    private void txt_totalAmountWordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalAmountWordsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalAmountWordsActionPerformed

    private void txt_recievedFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_recievedFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_recievedFromActionPerformed

    private void txt_bankNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bankNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bankNameActionPerformed

    private void txt_totalCourseAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalCourseAmountActionPerformed
        // TODO add your handling code here:
        Float amnt = Float.parseFloat(txt_totalCourseAmount.getText());
        
        float cgst = (amnt * 0.09f);
        float sgst = (amnt * 0.09f);
        
        txt_cgstAmount.setText(Float.toString(cgst));
        txt_sgstAmount.setText(Float.toString(sgst));
        
        float total = amnt + cgst + sgst;
        txt_totalAmount.setText(Float.toString(total));
        
        txt_totalAmountWords.setText(NumberToWordsConverter.convert((int)total) + " Only");
    }//GEN-LAST:event_txt_totalCourseAmountActionPerformed

    private void txt_cgstAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cgstAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cgstAmountActionPerformed

    private void txt_sgstAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sgstAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sgstAmountActionPerformed

    private void txt_courseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_courseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_courseActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        if(validation() == true){
            
            String result = updateData();
            if(result.equals("success")){   
                JOptionPane.showMessageDialog(this, "record updated successfully");
                PrintReciept printR = new PrintReciept();
                printR.setTitle("Print Reciept");
                printR.setVisible(true);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this, "record not inserted");
            }
            
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void combo_courseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_courseActionPerformed
        // TODO add your handling code here:
        txt_course.setText(combo_course.getSelectedItem().toString());
        
        int amount = setAmount(combo_course.getSelectedItem().toString());
        txt_totalCourseAmount.setText(Integer.toString(amount));
        updateAmounts();
    }//GEN-LAST:event_combo_courseActionPerformed

    private void txt_year2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_year2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_year2ActionPerformed

    private void btn_logoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(189,108,245);
        btn_logout.setBackground(clr);
    }//GEN-LAST:event_btn_logoutMouseEntered

    private void btn_logoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMouseExited
        // TODO add your handling code here:
        Color clr = new Color(187,134,252);
        btn_logout.setBackground(clr);
    }//GEN-LAST:event_btn_logoutMouseExited

    private void btn_logoutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMousePressed
        // TODO add your handling code here:
        Login_page login = new Login_page();
        login.show();
        this.dispose();
    }//GEN-LAST:event_btn_logoutMousePressed

    private void btn_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseClicked
        // TODO add your handling code here:
        Home home = new Home();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_homeMouseClicked

    private void btn_homeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(189,108,245);
        btn_home.setBackground(clr);
    }//GEN-LAST:event_btn_homeMouseEntered

    private void btn_homeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseExited
        // TODO add your handling code here:
        Color clr = new Color(187,134,252);
        btn_home.setBackground(clr);
    }//GEN-LAST:event_btn_homeMouseExited

    private void btn_search_recordsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_search_recordsMouseClicked
        // TODO add your handling code here:
        SearchRecords searchR = new SearchRecords();
        searchR.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_search_recordsMouseClicked

    private void btn_search_recordsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_search_recordsMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(189,108,245);
        btn_search_records.setBackground(clr);
    }//GEN-LAST:event_btn_search_recordsMouseEntered

    private void btn_search_recordsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_search_recordsMouseExited
        // TODO add your handling code here:
        Color clr = new Color(187,134,252);
        btn_search_records.setBackground(clr);
    }//GEN-LAST:event_btn_search_recordsMouseExited

    private void btn_edit_coursesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_edit_coursesMouseClicked
        // TODO add your handling code here:
        EditCourse editc = new EditCourse();
        editc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_edit_coursesMouseClicked

    private void btn_edit_coursesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_edit_coursesMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(189,108,245);
        btn_edit_courses.setBackground(clr);
    }//GEN-LAST:event_btn_edit_coursesMouseEntered

    private void btn_edit_coursesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_edit_coursesMouseExited
        // TODO add your handling code here:
        Color clr = new Color(187,134,252);
        btn_edit_courses.setBackground(clr);
    }//GEN-LAST:event_btn_edit_coursesMouseExited

    private void btn_view_all_recordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_view_all_recordMouseClicked
        // TODO add your handling code here:
        AllRecords allRecords = new AllRecords();
        allRecords.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_view_all_recordMouseClicked

    private void btn_view_all_recordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_view_all_recordMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(189,108,245);
        btn_view_all_record.setBackground(clr);
    }//GEN-LAST:event_btn_view_all_recordMouseEntered

    private void btn_view_all_recordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_view_all_recordMouseExited
        // TODO add your handling code here:
        Color clr = new Color(187,134,252);
        btn_view_all_record.setBackground(clr);
    }//GEN-LAST:event_btn_view_all_recordMouseExited

    private void btn_course_listMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_course_listMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(189,108,245);
        btn_course_list.setBackground(clr);
    }//GEN-LAST:event_btn_course_listMouseEntered

    private void btn_course_listMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_course_listMouseExited
        // TODO add your handling code here:
        Color clr = new Color(187,134,252);
        btn_course_list.setBackground(clr);
    }//GEN-LAST:event_btn_course_listMouseExited

    private void btn_backMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(189,108,245);
        btn_back.setBackground(clr);
    }//GEN-LAST:event_btn_backMouseEntered

    private void btn_backMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseExited
        // TODO add your handling code here:
        Color clr = new Color(187,134,252);
        btn_back.setBackground(clr);
    }//GEN-LAST:event_btn_backMouseExited

    private void btn_backMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMousePressed
        // TODO add your handling code here:
        Home home = new Home();
        home.show();
        home.setTitle("HOME PAGE");
        this.dispose();
    }//GEN-LAST:event_btn_backMousePressed

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
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateFeesDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btn_back;
    private javax.swing.JPanel btn_course_list;
    private javax.swing.JPanel btn_edit_courses;
    private javax.swing.JPanel btn_home;
    private javax.swing.JPanel btn_logout;
    private javax.swing.JButton btn_print;
    private javax.swing.JPanel btn_search_records;
    private javax.swing.JPanel btn_view_all_record;
    private javax.swing.JPanel child_panel;
    private javax.swing.JComboBox<String> combo_course;
    private javax.swing.JComboBox<String> combo_paymentMode;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_bankName;
    private javax.swing.JLabel lbl_cgst;
    private javax.swing.JLabel lbl_chequeNo;
    private javax.swing.JLabel lbl_ddNo;
    private javax.swing.JLabel lbl_gstin;
    private javax.swing.JLabel lbl_sgst;
    private javax.swing.JLabel lbl_text;
    private javax.swing.JPanel parent_panel;
    private javax.swing.JTextField txt_bankName;
    private javax.swing.JTextField txt_cgstAmount;
    private javax.swing.JTextField txt_chequeNo;
    private javax.swing.JTextField txt_course;
    private javax.swing.JTextField txt_ddNo;
    private javax.swing.JTextField txt_recieptNo;
    private javax.swing.JTextField txt_recievedFrom;
    private javax.swing.JTextArea txt_remark;
    private javax.swing.JTextField txt_rollNo;
    private javax.swing.JTextField txt_sgstAmount;
    private javax.swing.JTextField txt_totalAmount;
    private javax.swing.JTextField txt_totalAmountWords;
    private javax.swing.JTextField txt_totalCourseAmount;
    private javax.swing.JTextField txt_year1;
    private javax.swing.JTextField txt_year2;
    // End of variables declaration//GEN-END:variables
}
