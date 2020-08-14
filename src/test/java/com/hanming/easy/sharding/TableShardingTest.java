package com.hanming.easy.sharding;

import com.hanming.easy.sharding.common.ShardingException;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * 分表测试
 * @author hanming.xiao
 * @date 2020-08-04
 */
public class TableShardingTest {

    /**
     * 数据源配置文件
     */
    public static String TABLE_SHARDING_CONFIG = "shardingTableExample.yml";

    /**
     * sharding数据源名称（配置文件里配置的）
     */
    public static String SHARDING_DATASOURCE_CONFIG = "tableShardingDs";

    /**
     * 取模分表配置
     * 根据price字段值，按规则配置中的value取模
     * @throws ShardingException
     * @throws SQLException
     */
    @Test
    public void baseTest() throws ShardingException, SQLException {
        //创建工厂
        EasySharding easySharding=new EasyShardingFactory();
        //获取sharding数据源
        DataSource dataSource = easySharding.createDataSource(SHARDING_DATASOURCE_CONFIG);
        // 获取链接
        Connection conn = dataSource.getConnection();

        // 创建statement
        Statement stmt = conn.createStatement();
        String sql;

        //执行插入
        sql = "insert into tbl_order (price) values (101)";
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

    }
}
