package org.company.clientManager.dao;

import org.company.clientManager.utils.DBUtil;
import org.company.clientManager.model.ThinClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.List;                  
import java.util.ArrayList;

public class ThinClientDao{
    public ThinClientDao(){
    
    }

    public List<ThinClient> getClients(){
        Connection ct = null;  
        Statement sm = null;  
        ResultSet rs = null;  
        List<ThinClient> clients = new ArrayList<ThinClient>(); 
        try {   
            ct = DBUtil.getConnection();
            sm = ct.createStatement();  
            rs = sm.executeQuery("select * from thinclient");  
            while(rs.next()) {   
                ThinClient client = new ThinClient(); 
                String name = rs.getString("name");
                String ip = rs.getString("ip");
                String mac = rs.getString("mac");
                client.setName(name);
                client.setIp(ip);
                client.setMac(mac);
                clients.add(client);
            }   
        }   
        catch (Exception ex) {   
            ex.printStackTrace();  
        }finally{  
            try {   
                if (rs!=null) rs.close();  
                if (sm!=null) sm.close();  
                if (ct!=null) ct.close();  
            }catch (Exception ex) {   
                ex.printStackTrace();  
            }   
        }   
 
        return clients;
    }   
}
