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

    public static String CONFIG_NOT_EXIST="ShardingDateSourcesConfig配置不存在，配置sharding数据源:：";
    public static String CONFIG_EXIST="ShardingDateSourcesConfig配置sharding数据源名称重复，已存在sharding数据源：";
    public static String DATA_SOURCE_FAIL="创建真实DateSources失败，datasource：";

}
