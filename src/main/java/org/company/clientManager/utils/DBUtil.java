package org.company.clientManager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DBUtil {  
    private static String url = "jdbc:mysql://ip:3306/cvirt";  
    private static String user = "root";
    private static String psw = "ccloudhan1"; 
      
    private static  Connection conn;  
      
    private DBUtil() {  
          
    }  
      
    /** 
     * 获取数据库的连接 
     * @return conn 
     */  
    public static Connection getConnection() throws SQLException{  
        if(null == conn || conn.isClosed()) {  
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");  
                conn = DriverManager.getConnection(url, user, psw);  
            } catch (SQLException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch(ClassNotFoundException e){
                e.printStackTrace();
            } 
        }  
        return conn;  
    }  
      
    /** 
     * 释放资源 
     * @param conn 
     * @param pstmt 
     * @param rs 
     */  
    public static void closeResources(Connection conn,PreparedStatement pstmt,ResultSet rs) {  
        if(null != rs) {  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally {  
                if(null != pstmt) {  
                    try {  
                        pstmt.close();  
                    } catch (SQLException e) {  
                        e.printStackTrace();  
                        throw new RuntimeException(e);  
                    } finally {  
                        if(null != conn) {  
                            try {  
                                conn.close();  
                            } catch (SQLException e) {  
                                e.printStackTrace();  
                                throw new RuntimeException(e);  
                            }  
                        }  
                    }  
                }  
            }  
        }  
    }  
}  
