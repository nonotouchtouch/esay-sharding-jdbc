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

    public static String CONFIG_EXIST="ShardingDateSourcesConfig配置key重复，已存在配置key：";
}
