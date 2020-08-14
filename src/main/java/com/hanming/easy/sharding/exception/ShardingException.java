package com.hanming.easy.sharding.exception;

/**
 * 自定义异常
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class ShardingException extends Exception {

    public ShardingException(String message){
        super(message);
    }

    /**
     * 文件不存在
     */
    public static String FILE_NOT_EXIST="File does not exist，path：";

    /**
     * 配置不存在
     */
    public static String CONFIG_NOT_EXIST="ShardingDateSourcesConfig does not exist，sharding DataSourceName：";

    /**
     * 配置已存在
     */
    public static String CONFIG_EXIST="ShardingDateSourcesConfig already exist，sharding DataSourceName：";

    /**
     * 无真实数据源
     */
    public static String DATA_SOURCE_NOT_EXIST="DateSource does not exist，dataSourceName：";

}
