/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yumxpress.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static yumxpress.Dao.StaffDao.getNewId;
import yumxpress.dbbuilt.DBConnection;
import yumxpress.pojo.CustomerPojo;
import yumxpress.pojo.StaffPojo;

/**
 *
 * @author ABC
 */
public class CustomerDao {
    public static String getNewId()throws SQLException{
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(customer_id) from customers");
        rs.next();
        String id = rs.getString(1);
        String custId="";
        if(id!=null){
            id = id.substring(4);
            custId = "CUS-"+(Integer.parseInt(id)+1);
        }
        else{
            custId= "CUS-101";
        }
        return custId;
    }
    
    public static boolean addCustomer(CustomerPojo cust)throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into customers values(?,?,?,?,?,?)");
        cust.setCustomerId(getNewId());
        ps.setString(1,cust.getCustomerId());
        ps.setString(2,cust.getCustomerName());
        ps.setString(3,cust.getEmailId());
        ps.setString(4,cust.getPassword());
        ps.setString(5,cust.getMobileNo());
        ps.setString(6, cust.getAddress());
        
        return (ps.executeUpdate()==1);
    }
    
    public static CustomerPojo validate(String emailId,String Password)throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from customers where email_id=? and password=? ");
        ps.setString(1, emailId);
        ps.setString(2, Password);
        ResultSet rs = ps.executeQuery();
        CustomerPojo cust = null;
        if(rs.next()){
            cust = new CustomerPojo();
            cust.setEmailId(emailId);
            cust.setCustomerName(rs.getString(2));
            cust.setCustomerId(rs.getString(1));
        }
        return cust;
    }
    
    public static CustomerPojo getCustomerDetailsById(String custId)throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from customers where customer_id=?");
        ps.setString(1, custId);
        ResultSet rs = ps.executeQuery();
        CustomerPojo cust = null;
        if(rs.next()){
            cust = new CustomerPojo();
            cust.setEmailId(rs.getString("email_id"));
            cust.setCustomerName(rs.getString(2));
            cust.setCustomerId(rs.getString(1));
            cust.setMobileNo(rs.getString("mobile_no"));
            cust.setPassword(rs.getString("password"));
            cust.setAddress(rs.getString("address"));
        }
        return cust;
    }
    
    public static boolean updateCustomer(CustomerPojo customer)throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update customers set customer_name=?,mobile_no=?,password=?,address=? where customer_id=?");
        ps.setString(5, customer.getCustomerId());
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getMobileNo());
        ps.setString(3, customer.getPassword());
        ps.setString(4, customer.getAddress());
        return ps.executeUpdate()==1;
    }
}
