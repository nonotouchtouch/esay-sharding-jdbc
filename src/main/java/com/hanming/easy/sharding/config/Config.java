package com.hanming.easy.sharding.config;

import com.hanming.easy.sharding.common.ShardingDateSourcesConfigLoader;
import com.hanming.easy.sharding.common.ShardingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放所有sharding配置
 * 1.提前解析默认路径下配置的yml文件
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
     * sharding数据源配置
     * key：sharding数据源名称
     * vale：配置内容
     */
    private static Map<String, ShardingDateSourcesConfig> stringShardingDateSourcesConfigMap;


    /**
     * 获取配置
     *
     * @param shardingDataSourcesName sharding数据源名称
     * @return shardingDateSourcesConfig 分库分表配置
     * @throws ShardingException 配置不存在
     */
    public static ShardingDateSourcesConfig getStringShardingDateSourcesConfig(String shardingDataSourcesName) throws ShardingException {
        ShardingDateSourcesConfig shardingDateSourcesConfig = stringShardingDateSourcesConfigMap.get(shardingDataSourcesName);
        if (null == shardingDateSourcesConfig) {
            throw new ShardingException(ShardingException.CONFIG_NOT_EXIST +shardingDataSourcesName);
        }
        return shardingDateSourcesConfig;
    }

    /**
     * 存入配置，查重
     *
     * @param shardingDataSourcesName sharding数据源名称（不能重复）
     * @param shardingDateSourcesConfig sharding数据源配置
     * @throws ShardingException 配置已存在
     */
    public static void putShardingDateSourcesConfig(String shardingDataSourcesName, ShardingDateSourcesConfig shardingDateSourcesConfig) throws ShardingException {
        ShardingDateSourcesConfig rep = stringShardingDateSourcesConfigMap.put(shardingDataSourcesName, shardingDateSourcesConfig);
        if (null != rep) {
            throw new ShardingException(ShardingException.CONFIG_EXIST + rep.getShardingDataSourcesName());
        }
    }
}
