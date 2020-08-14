package com.hanming.easy.sharding.common;

/**
 * 自定义异常
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class ShardingException extends Exception {

    public ShardingException(String message){
        super(message);
    }

    public static String FILE_NOT_EXIST="File does not exist，path：";
    public static String CONFIG_NOT_EXIST="ShardingDateSourcesConfig does not exist，sharding DataSourceName：";
    public static String CONFIG_EXIST="ShardingDateSourcesConfig already exist，sharding DataSourceName：";

}
