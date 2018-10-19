package com.itheima.test;


import com.itheima.domain.Customer;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcTest {
    @Test
    public void test1(){
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jpa", "root", "root");
            String sql="insert into tb_user values (default,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,"英雄");
            try (InputStream in = new FileInputStream("test.jpg")) {    // Java 7的TWR
                ps.setBinaryStream(2, in);      // 将SQL语句中第二个占位符换成二进制流
                // 4. 发出SQL语句获得受影响行数
                System.out.println(ps.executeUpdate() == 1 ? "插入成功" : "插入失败");
            } catch(IOException e) {
                System.out.println("读取照片失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(ps!=null){
                    ps.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
