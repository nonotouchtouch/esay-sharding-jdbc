package com.hanming.easy.sharding.config;

import com.hanming.easy.sharding.common.ShardingDateSourcesConfigLoader;
import com.hanming.easy.sharding.common.ShardingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放所有sharding配置
 * 1.提前解析配置的yml文件
 * 2.也可手动传入ShardingDateSourcesConfig对象
 *
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class Config {

    static {
        //解析shardingconf目录下所有 yml配置
        try {
            stringShardingDateSourcesConfigMap = ShardingDateSourcesConfigLoader.loadAllByDefaultPath();
        } catch (ShardingException e) {
            stringShardingDateSourcesConfigMap=new HashMap<>(16);
            e.printStackTrace();
        }
    }

    /**
     * yml配置
     * key：文件名
     * vale：配置内容
     */
    private static Map<String, ShardingDateSourcesConfig> stringShardingDateSourcesConfigMap;


    /**
     * 获取配置
     *
     * @param shardingDataSourcesName
     * @return shardingDateSourcesConfig
     */
    public static ShardingDateSourcesConfig getStringShardingDateSourcesConfig(String shardingDataSourcesName) throws ShardingException {
        ShardingDateSourcesConfig shardingDateSourcesConfig = stringShardingDateSourcesConfigMap.get(shardingDataSourcesName);
        if (null != shardingDateSourcesConfig) {
            throw new ShardingException(ShardingException.CONFIG_NOT_EXIST +shardingDataSourcesName);
        }
        return shardingDateSourcesConfig;
    }

    /**
     * 存入配置，查重
     *
     * @param shardingDataSourcesName
     * @param shardingDateSourcesConfig
     */
    public static void putShardingDateSourcesConfig(String shardingDataSourcesName, ShardingDateSourcesConfig shardingDateSourcesConfig) throws ShardingException {
        ShardingDateSourcesConfig rep = stringShardingDateSourcesConfigMap.put(shardingDataSourcesName, shardingDateSourcesConfig);
        if (null != rep) {
            throw new ShardingException(ShardingException.CONFIG_EXIST + rep.getShardingDataSourcesName());
        }
    }
}
