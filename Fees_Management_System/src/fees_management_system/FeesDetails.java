/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees_management_system;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author sanjay
 */
@Entity
@Table(name = "FEES_DETAILS", catalog = "", schema = "ROOT")
@NamedQueries({
    @NamedQuery(name = "FeesDetails.findAll", query = "SELECT f FROM FeesDetails f")
    , @NamedQuery(name = "FeesDetails.findByRecieptNo", query = "SELECT f FROM FeesDetails f WHERE f.recieptNo = :recieptNo")
    , @NamedQuery(name = "FeesDetails.findByStudentName", query = "SELECT f FROM FeesDetails f WHERE f.studentName = :studentName")
    , @NamedQuery(name = "FeesDetails.findByRollNo", query = "SELECT f FROM FeesDetails f WHERE f.rollNo = :rollNo")
    , @NamedQuery(name = "FeesDetails.findByPaymentMode", query = "SELECT f FROM FeesDetails f WHERE f.paymentMode = :paymentMode")
    , @NamedQuery(name = "FeesDetails.findByChequeNo", query = "SELECT f FROM FeesDetails f WHERE f.chequeNo = :chequeNo")
    , @NamedQuery(name = "FeesDetails.findByBankName", query = "SELECT f FROM FeesDetails f WHERE f.bankName = :bankName")
    , @NamedQuery(name = "FeesDetails.findByDdNo", query = "SELECT f FROM FeesDetails f WHERE f.ddNo = :ddNo")
    , @NamedQuery(name = "FeesDetails.findByCourseName", query = "SELECT f FROM FeesDetails f WHERE f.courseName = :courseName")
    , @NamedQuery(name = "FeesDetails.findByGstin", query = "SELECT f FROM FeesDetails f WHERE f.gstin = :gstin")
    , @NamedQuery(name = "FeesDetails.findByTotalAmount", query = "SELECT f FROM FeesDetails f WHERE f.totalAmount = :totalAmount")
    , @NamedQuery(name = "FeesDetails.findByDate", query = "SELECT f FROM FeesDetails f WHERE f.date = :date")
    , @NamedQuery(name = "FeesDetails.findByAmount", query = "SELECT f FROM FeesDetails f WHERE f.amount = :amount")
    , @NamedQuery(name = "FeesDetails.findByCgst", query = "SELECT f FROM FeesDetails f WHERE f.cgst = :cgst")
    , @NamedQuery(name = "FeesDetails.findBySgst", query = "SELECT f FROM FeesDetails f WHERE f.sgst = :sgst")
    , @NamedQuery(name = "FeesDetails.findByTotalInWords", query = "SELECT f FROM FeesDetails f WHERE f.totalInWords = :totalInWords")
    , @NamedQuery(name = "FeesDetails.findByRemark", query = "SELECT f FROM FeesDetails f WHERE f.remark = :remark")
    , @NamedQuery(name = "FeesDetails.findByYear1", query = "SELECT f FROM FeesDetails f WHERE f.year1 = :year1")
    , @NamedQuery(name = "FeesDetails.findByYear2", query = "SELECT f FROM FeesDetails f WHERE f.year2 = :year2")})
public class FeesDetails implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RECIEPT_NO")
    private Integer recieptNo;
    @Column(name = "STUDENT_NAME")
    private String studentName;
    @Column(name = "ROLL_NO")
    private String rollNo;
    @Column(name = "PAYMENT_MODE")
    private String paymentMode;
    @Column(name = "CHEQUE_NO")
    private String chequeNo;
    @Column(name = "BANK_NAME")
    private String bankName;
    @Column(name = "DD_NO")
    private String ddNo;
    @Column(name = "COURSE_NAME")
    private String courseName;
    @Column(name = "GSTIN")
    private String gstin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "AMOUNT")
    private Double amount;
    @Column(name = "CGST")
    private Double cgst;
    @Column(name = "SGST")
    private Double sgst;
    @Column(name = "TOTAL_IN_WORDS")
    private String totalInWords;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "YEAR1")
    private Integer year1;
    @Column(name = "YEAR2")
    private Integer year2;

    public FeesDetails() {
    }

    public FeesDetails(Integer recieptNo) {
        this.recieptNo = recieptNo;
    }

    public Integer getRecieptNo() {
        return recieptNo;
    }

    public void setRecieptNo(Integer recieptNo) {
        Integer oldRecieptNo = this.recieptNo;
        this.recieptNo = recieptNo;
        changeSupport.firePropertyChange("recieptNo", oldRecieptNo, recieptNo);
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        String oldStudentName = this.studentName;
        this.studentName = studentName;
        changeSupport.firePropertyChange("studentName", oldStudentName, studentName);
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        String oldRollNo = this.rollNo;
        this.rollNo = rollNo;
        changeSupport.firePropertyChange("rollNo", oldRollNo, rollNo);
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        String oldPaymentMode = this.paymentMode;
        this.paymentMode = paymentMode;
        changeSupport.firePropertyChange("paymentMode", oldPaymentMode, paymentMode);
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        String oldChequeNo = this.chequeNo;
        this.chequeNo = chequeNo;
        changeSupport.firePropertyChange("chequeNo", oldChequeNo, chequeNo);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        String oldBankName = this.bankName;
        this.bankName = bankName;
        changeSupport.firePropertyChange("bankName", oldBankName, bankName);
    }

    public String getDdNo() {
        return ddNo;
    }

    public void setDdNo(String ddNo) {
        String oldDdNo = this.ddNo;
        this.ddNo = ddNo;
        changeSupport.firePropertyChange("ddNo", oldDdNo, ddNo);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        String oldCourseName = this.courseName;
        this.courseName = courseName;
        changeSupport.firePropertyChange("courseName", oldCourseName, courseName);
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        String oldGstin = this.gstin;
        this.gstin = gstin;
        changeSupport.firePropertyChange("gstin", oldGstin, gstin);
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        Double oldTotalAmount = this.totalAmount;
        this.totalAmount = totalAmount;
        changeSupport.firePropertyChange("totalAmount", oldTotalAmount, totalAmount);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        Date oldDate = this.date;
        this.date = date;
        changeSupport.firePropertyChange("date", oldDate, date);
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        Double oldAmount = this.amount;
        this.amount = amount;
        changeSupport.firePropertyChange("amount", oldAmount, amount);
    }

    public Double getCgst() {
        return cgst;
    }

    public void setCgst(Double cgst) {
        Double oldCgst = this.cgst;
        this.cgst = cgst;
        changeSupport.firePropertyChange("cgst", oldCgst, cgst);
    }

    public Double getSgst() {
        return sgst;
    }

    public void setSgst(Double sgst) {
        Double oldSgst = this.sgst;
        this.sgst = sgst;
        changeSupport.firePropertyChange("sgst", oldSgst, sgst);
    }

    public String getTotalInWords() {
        return totalInWords;
    }

    public void setTotalInWords(String totalInWords) {
        String oldTotalInWords = this.totalInWords;
        this.totalInWords = totalInWords;
        changeSupport.firePropertyChange("totalInWords", oldTotalInWords, totalInWords);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        String oldRemark = this.remark;
        this.remark = remark;
        changeSupport.firePropertyChange("remark", oldRemark, remark);
    }

    public Integer getYear1() {
        return year1;
    }

    public void setYear1(Integer year1) {
        Integer oldYear1 = this.year1;
        this.year1 = year1;
        changeSupport.firePropertyChange("year1", oldYear1, year1);
    }

    public Integer getYear2() {
        return year2;
    }

    public void setYear2(Integer year2) {
        Integer oldYear2 = this.year2;
        this.year2 = year2;
        changeSupport.firePropertyChange("year2", oldYear2, year2);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recieptNo != null ? recieptNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeesDetails)) {
            return false;
        }
        FeesDetails other = (FeesDetails) object;
        if ((this.recieptNo == null && other.recieptNo != null) || (this.recieptNo != null && !this.recieptNo.equals(other.recieptNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fees_management_system.FeesDetails[ recieptNo=" + recieptNo + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
