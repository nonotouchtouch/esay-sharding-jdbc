package com.hanming.easy.sharding;


import javax.sql.DataSource;
import java.util.Map;

/**
 * 几种使用sharding的方式
 * @author hanming.xiao
 * @date 2020-08-01
 */
public interface EasySharding {


    /**
     * 根据配置生成sharding数据源
     * 如果数据源配置方式和默认的不一样，手动传入基础数据源
     * 分库情况下，把不同dataSources包装成一个sharding数据源
     *
     * @param dataSourceMap 数据源（分库的情况下有多个）
     * @param configFileName 配置文件名称，配置文件需放在默认目录下
     * @return DataSource sharding数据源
     */
    DataSource createDataSource(Map<String, DataSource> dataSourceMap, String configFileName);

    /**
     * 根据配置生成sharding数据源(使用默认方式配置数据源)
     * 分库情况下，把不同dataSources包装成一个sharding数据源
     *
     * @param configFileName 配置文件名称
     * @return DataSource sharding数据源
     */
    DataSource createDataSource(String configFileName);


}
