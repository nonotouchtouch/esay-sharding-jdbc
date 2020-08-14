package com.hanming.easy.sharding;


import com.hanming.easy.sharding.common.ShardingException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * 几种获取sharding数据源的方式
 * @author hanming.xiao
 * @date 2020-08-01
 */
public interface EasySharding {


    /**
     * 根据配置生成sharding数据源(使用默认方式配置数据源)
     * 分库情况下，把不同dataSources包装成一个sharding数据源
     *
     * @param shardingDataSourceName 配置的sharding数据源名称（yml中配置）
     * @return DataSource sharding数据源
     */
    DataSource createDataSource(String shardingDataSourceName) throws SQLException, ShardingException;


    /**
     * 根据配置生成sharding数据源
     * 如果数据源配置方式和默认的不一样，手动传入基础数据源
     * 分库情况下，把不同dataSources包装成一个sharding数据源
     *
     * @param dataSourceMap 数据源（分库的情况下有多个）
     * @param shardingDataSourceName 配置sharding数据源名称（配置文件里配置）
     * @return DataSource sharding数据源
     */
    DataSource createDataSource(Map<String, DataSource> dataSourceMap, String shardingDataSourceName);



}
