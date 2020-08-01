package com.hanming.easy.sharding;

import org.junit.Test;

import javax.sql.DataSource;

/**
 * 分表测试
 */
public class TableShardingTest {

    /**
     * 数据源配置文件
     */
    public static String TABLE_SHARDING_CONFIG = "shardingTableExample.yml";

    @Test
    public void baseTest() {
        EasySharding easySharding=new EasyShardingFactory();
        DataSource shardingDataSource = easySharding.createDataSource(TABLE_SHARDING_CONFIG);

    }
}
