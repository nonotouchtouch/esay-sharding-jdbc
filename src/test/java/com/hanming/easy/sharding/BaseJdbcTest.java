package com.hanming.easy.sharding;

import com.hanming.easy.sharding.common.DataSourcesUtil;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

/**
 * 一个基础的jdbc编程测试类
 * 用于测试数据库链接是否正常
 *
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class BaseJdbcTest {

    /**
     * 数据源配置文件
     */
    public static String DATA_SOURCES_CONF_FILE = "dbconf/easy_0.properties";

    @Test
    public void baseJdbcTest() {

        DataSource dataSource;
        Connection conn = null;
        Statement stmt = null;
        try {
            //获取数据源
            dataSource = DataSourcesUtil.getDataSource(DATA_SOURCES_CONF_FILE);

            // 获取链接
            conn = dataSource.getConnection();

            // 创建statement
            stmt = conn.createStatement();
            String sql;

            //执行插入
            sql = "insert into tbl_order (price) values (680)";
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
                System.out.println("ID: " + id);
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
