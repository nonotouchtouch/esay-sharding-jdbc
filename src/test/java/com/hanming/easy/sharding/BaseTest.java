package com.hanming.easy.sharding;

import org.junit.Test;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

/**
 * 一个基础的jdbc编程测试类
 */
public class BaseTest {

    /**
     * 数据源配置地址
     */
    public static String DATA_SOURCES_CONF_FILE="dbconf/easy-sharding.properties";


    @Test
    public void baseTest() throws IOException {

        Connection conn = null;
        Statement stmt = null;
        try {
            // 获取链接
            conn =DataSourcesUtils.getDataSource(this.DATA_SOURCES_CONF_FILE).getConnection();

            // 创建statement
            stmt = conn.createStatement();
            String sql;

            //执行插入
            sql = "insert into tbl_order (price) values (10)";
            stmt.execute(sql);

            //执行选择
            sql = "SELECT * FROM tbl_order";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                Date createTime = rs.getDate("create_time");
                BigDecimal price = rs.getBigDecimal("price");

                // 输出数据
                System.out.print("ID: " + id);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
