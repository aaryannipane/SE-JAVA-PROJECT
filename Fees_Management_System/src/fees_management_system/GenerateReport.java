/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees_management_system;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;

/**
 *
 * @author sanjay
 */
public class GenerateReport extends javax.swing.JFrame {

    /**
     * Creates new form GenerateReport
     */
    public GenerateReport() {
        initComponents();
        this.setTitle("Generate Report");
        this.setIconImage(Logo.setIcon());
        setResizable(false);
        fillComboBox();
    }
    
    public void fillComboBox(){
        String currentDir = System.getProperty("user.dir");
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby:"+currentDir+"\\fees_management_system", "root", "root");
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
    
    DefaultTableModel model;
    
    public void setRecordsToTable(){
        
        String cname = combo_course.getSelectedItem().toString();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fromDate = dateFormat.format(date_from.getDate());
        String toDate = dateFormat.format(date_to.getDate());
        
        Float total_amount = 0.0f;
        boolean isTableEmpty = true;
        
        try{
            Connection con = DBconnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from fees_details where date between ? and ? and course_name = ?");
            pst.setString(1, fromDate);
            pst.setString(2, toDate);
            pst.setString(3, cname);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                String recieptNo = rs.getString("reciept_no");
                String rollNo = rs.getString("roll_no");
                String studentName = rs.getString("student_name");
                String course = rs.getString("course_name");
                String amount = rs.getString("total_amount");
                String remark = rs.getString("remark");
                total_amount += Float.parseFloat(amount);
                
                Object[] obj = {recieptNo, rollNo, studentName, course, amount, remark};
                model = (DefaultTableModel)tbl_reportTable.getModel();
                model.addRow(obj);
                isTableEmpty = false;
            }
            
            if(isTableEmpty){
                DefaultTableModel model = (DefaultTableModel)tbl_reportTable.getModel();
                model.setRowCount(1);
                txt_totalAmountWords.setText("Zero Only");
                txt_courseSelected.setText(cname);
                txt_totalAmount.setText("0");
                JOptionPane.showMessageDialog(this, "No data available");
                
            }
            else{
                txt_courseSelected.setText(cname);
                txt_totalAmount.setText(total_amount.toString());
                String amountWords = NumberToWordsConverter.convert(total_amount.intValue());
                txt_totalAmountWords.setText(amountWords + " Only");
            }
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void exportToExcel(){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet ws = wb.createSheet();
        
        TreeMap<String, Object[]> map = new TreeMap<>();
        map.put("0", new Object[]{model.getColumnName(0), model.getColumnName(1), model.getColumnName(2),  model.getColumnName(3), model.getColumnName(4), model.getColumnName(5)});
        
        for(int i=1; i < model.getRowCount(); i++){
            map.put(Integer.toString(i), new Object[]{model.getValueAt(i, 0), model.getValueAt(i, 1), model.getValueAt(i, 2), model.getValueAt(i, 3), model.getValueAt(i, 4), model.getValueAt(i, 5)});
        }
        

        Set<String> id = map.keySet();
        
        XSSFRow fRow;
        
        int rowId = 0;
        
        for(String key : id){
            fRow = ws.createRow(rowId++);
            Object[] value = map.get(key);
            int cellId = 0;
            
            for(Object object : value){
                XSSFCell cell = fRow.createCell(cellId++);
                cell.setCellValue(object.toString());
            }
        }
        
        try(FileOutputStream fos = new FileOutputStream(new File(txt_filePath.getText()));){
            wb.write(fos);
            JOptionPane.showMessageDialog(this, "data stored successfully : "  + txt_filePath.getText());
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        combo_course = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        date_from = new com.toedter.calendar.JDateChooser();
        date_to = new com.toedter.calendar.JDateChooser();
        btn_print = new javax.swing.JButton();
        btn_excel = new javax.swing.JButton();
        txt_filePath = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        btn_browse = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_reportTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt_totalAmountWords = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_courseSelected = new javax.swing.JLabel();
        txt_totalAmount = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btn_showChart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_course_listMouseClicked(evt);
            }
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

        jPanel1.setBackground(new java.awt.Color(30, 32, 32));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("To Date");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 60, 30));

        jPanel1.add(combo_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 290, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("From Date");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 80, 30));
        jPanel1.add(date_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 130, 30));
        jPanel1.add(date_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 130, 30));

        btn_print.setBackground(new java.awt.Color(3, 218, 197));
        btn_print.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btn_print.setText("Print");
        btn_print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        jPanel1.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 130, -1));

        btn_excel.setBackground(new java.awt.Color(3, 218, 197));
        btn_excel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btn_excel.setText("Export To Excel");
        btn_excel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_excelActionPerformed(evt);
            }
        });
        jPanel1.add(btn_excel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, -1, -1));

        txt_filePath.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(txt_filePath, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 270, -1));

        jButton3.setBackground(new java.awt.Color(3, 218, 197));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jButton3.setText("Submit");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 120, -1));

        btn_browse.setBackground(new java.awt.Color(3, 218, 197));
        btn_browse.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btn_browse.setText("Browse");
        btn_browse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseActionPerformed(evt);
            }
        });
        jPanel1.add(btn_browse, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 190, -1, -1));

        tbl_reportTable.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tbl_reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Reciept no", "Roll no", "Student name", "Course", "Amount", "Remark"
            }
        ));
        tbl_reportTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_reportTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 980, 420));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total Amount In Words: ");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        txt_totalAmountWords.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_totalAmountWords.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txt_totalAmountWords, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 350, 20));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Total Amount Collected: ");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Course selected: ");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        txt_courseSelected.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_courseSelected.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txt_courseSelected, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 160, 20));

        txt_totalAmount.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_totalAmount.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txt_totalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 160, 20));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 420, 200));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Select Course : ");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        btn_showChart.setBackground(new java.awt.Color(3, 218, 197));
        btn_showChart.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btn_showChart.setText("Show Chart");
        btn_showChart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_showChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showChartActionPerformed(evt);
            }
        });
        jPanel1.add(btn_showChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 130, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 1040, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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

    private void btn_course_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_course_listMouseClicked
        // TODO add your handling code here:
        AllCourses allCourses = new AllCourses();
        allCourses.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_course_listMouseClicked

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

    private void btn_excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excelActionPerformed
        // TODO add your handling code here:
        if(!txt_filePath.getText().equals("")){
            exportToExcel();
        }
        else{
            JOptionPane.showMessageDialog(this, "Enter path name to continue");
        }
        
    }//GEN-LAST:event_btn_excelActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tbl_reportTable.getModel();
        model.setRowCount(1);
        setRecordsToTable();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat Date_Format = new SimpleDateFormat("YYYY-MM-dd"); 
        String datefrom=  Date_Format.format(date_from.getDate());
        String dateto=  Date_Format.format(date_to.getDate());
       
        MessageFormat header=new MessageFormat("Report From "+datefrom+" To " +dateto);
        MessageFormat footer=new MessageFormat("page{0,number,integer}");
        try {
            tbl_reportTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            
        } catch (Exception e) {
            e.getMessage();
        } 

    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(this);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        
        try{
            File f = fileChooser.getSelectedFile();
            String path = f.getAbsolutePath();
            path = path+"_"+date+".xlsx";
            txt_filePath.setText(path);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_browseActionPerformed

    private void btn_showChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showChartActionPerformed
        // TODO add your handling code here:
        
//        String cname = combo_course.getSelectedItem().toString();
        
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String fromDate = dateFormat.format(date_from.getDate());
//        String toDate = dateFormat.format(date_to.getDate());
        
        try{
            Connection con = DBconnection.getConnection();
//            PreparedStatement pst = con.prepareStatement("select total_amount, course_name  from fees_details where date between ? and ? and course_name = ?");
//            pst.setString(1, fromDate);
//            pst.setString(2, toDate);
//            pst.setString(3, cname);
//            ResultSet rs = pst.executeQuery();
            String query = "SELECT  course_name, sum(total_amount) from fees_details group by course_name";
            CategoryDataset dataset = new JDBCCategoryDataset(con, query);
            
//            JFreeChart  chart = ChartFactory.createLineChart("queryChart", "Course", "Amount", dataset, PlotOrientation.VERTICAL, false, true, true);
            
            JFreeChart chart = ChartFactory.createBarChart("course vs amount", "Course", "Amount", dataset, PlotOrientation.VERTICAL, false, true, true);
            
            BarRenderer renderer=null;
            CategoryPlot plot=null;
            renderer=new BarRenderer();
            ChartFrame frame = new ChartFrame("Bar Chart", chart);
            frame.setVisible(true);
            frame.setSize(400, 650);
            frame.setIconImage(Logo.setIcon());
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btn_showChartActionPerformed

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
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GenerateReport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btn_back;
    private javax.swing.JButton btn_browse;
    private javax.swing.JPanel btn_course_list;
    private javax.swing.JPanel btn_edit_courses;
    private javax.swing.JButton btn_excel;
    private javax.swing.JPanel btn_home;
    private javax.swing.JPanel btn_logout;
    private javax.swing.JButton btn_print;
    private javax.swing.JPanel btn_search_records;
    private javax.swing.JButton btn_showChart;
    private javax.swing.JPanel btn_view_all_record;
    private javax.swing.JComboBox<String> combo_course;
    private com.toedter.calendar.JDateChooser date_from;
    private com.toedter.calendar.JDateChooser date_to;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_reportTable;
    private javax.swing.JLabel txt_courseSelected;
    private javax.swing.JTextField txt_filePath;
    private javax.swing.JLabel txt_totalAmount;
    private javax.swing.JLabel txt_totalAmountWords;
    // End of variables declaration//GEN-END:variables
}
