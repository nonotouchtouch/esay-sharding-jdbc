package com.hanming.easy.sharding;


import javax.sql.DataSource;

public class EasyShardingFactory {


    /**
     * 根据配置生成sharding数据源
     *
     * 分库情况下，把不同dataSources包装成一个sharding数据源
     * @param configFileName 配置文件名称
     * @return DataSource sharding数据源
     */
    public static DataSource createDataSource(String configFileName){
        return null;
    }
}
