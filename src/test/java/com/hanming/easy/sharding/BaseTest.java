package com.hanming.easy.sharding;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

/**
 * 一个基础的jdbc编程测试类
 */
public class BaseTest {


    // MySQL 8.0 以下版本 使用
    //static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB";

    // MySQL 8.0 以上版本 使用
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/easy-sharding?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    // 自己设置用户名，密码
    static final String USER = "root";
    static final String PASS = "root";


    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 创建链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 创建statement
            stmt = conn.createStatement();
            String sql;

            //执行插入
            sql = "insert into  tbl_order (price) values (10)";
            stmt.execute(sql);

            //执行选择
            sql = "SELECT *  FROM tbl_order";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                Date name = rs.getDate("create_time");
                BigDecimal price = rs.getBigDecimal("price");

                // 输出数据
                System.out.print("ID: " + id);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
