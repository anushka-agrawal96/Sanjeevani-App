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
import java.util.HashMap;
import java.util.Map;
import yumxpress.dbbuilt.DBConnection;
import yumxpress.pojo.CompanyPojo;

/**
 *
 * @author ABC
 */
public class CompanyDao {
    public static String getNewId()throws SQLException{
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(company_id) from companies");
        rs.next();
        String id = rs.getString(1);
        String compId="";
        if(id!=null){
            id = id.substring(4);
            compId = "CMP-"+(Integer.parseInt(id)+1);
        }
        else{
            compId= "CMP-101";
        }
        return compId;
    }
    public static boolean addSeller(CompanyPojo comp)throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into companies values(?,?,?,?,?,?,?)");
        ps.setString(1, getNewId());
        ps.setString(2, comp.getCompanyName());
        ps.setString(3, comp.getOwnerName());
        ps.setString(4, comp.getPassword());
        ps.setString(5, "ACTIVE");
        ps.setString(6, comp.getEmailId());
        ps.setString(7, comp.getSecurityKey());
        
        return ps.executeUpdate()==1;
        
    }
    
    public static CompanyPojo validate(String compName,String Password)throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from companies where company_name=? and password=? and status='ACTIVE' ");
        ps.setString(1, compName);
        ps.setString(2, Password);
        ResultSet rs = ps.executeQuery();
        CompanyPojo comp = null;
        if(rs.next()){
            comp = new CompanyPojo();
            comp.setCompanyId(rs.getString(1));
            comp.setCompanyName(rs.getString(2));
            comp.setOwnerName(rs.getString(3));
        }
        return comp;
    }
    
    public static Map<String,String> getEmailCredentialByCompanyId(String CompanyId)throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select email_id,security_key from companies where company_id=? and status='ACTIVE' ");
        ps.setString(1, CompanyId);
        ResultSet rs = ps.executeQuery();
        Map<String,String> companyCredential = new HashMap<>();
        if(rs.next()){
            String emailId = rs.getString(1);
            String secKey = rs.getString(2);
            companyCredential.put("emailId", emailId);
            companyCredential.put("securityKey", secKey);
        }
        return companyCredential;
    }
    
    public static Map<String,String> getAllCompanyIdAndName()throws SQLException
    {
         Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select company_id,company_name from companies where status='ACTIVE' and company_id in (select company_id from products)");
        ResultSet rs = ps.executeQuery();
        Map<String,String> compList = new HashMap<>();
        while(rs.next()){
            String c_id = rs.getString(1);
            String c_name = rs.getString(2);
            compList.put(c_name, c_id);
        }
        return compList;
    }
    
}
