package com.hanming.easy.sharding.config;


import java.util.*;

/**
 * easy-sharding配置
 * <p>
 * 用于存放分库分表规则配置
 * 每个对象对应一个sharding yml配置文件
 *
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class ShardingDateSourcesConfig {

    /**
     * 构造方法 sharding数据源名称必传，且不能重复
     *
     * @param shardingDataSourcesName
     */
    public ShardingDateSourcesConfig(String shardingDataSourcesName) {
        if (null == shardingDataSourcesName || shardingDataSourcesName.trim().length() == 0) {
            throw new IllegalArgumentException("Create ShardingDateSourcesConfig fail! ShardingDataSourcesName can not be empty!");
        }
        this.shardingDataSourcesName = shardingDataSourcesName;

    }

    /**
     * sharding数据源名称
     */
    private String shardingDataSourcesName;

    /**
     * 基础数据源map
     * 不分库时候只有一个
     */
    private Set<String> dataSourcesNames = new HashSet<>(16);

    /**
     * 规则配置
     */
    private Map<String, Rule> ruleMap = new HashMap<>(16);

    /**
     * 逻辑表配置
     */
    private Map<String, Table> tableMap = new HashMap<>(16);

    /**
     * 其他配置
     */
    private Properties properties = null;

    public String getShardingDataSourcesName() {
        return shardingDataSourcesName;
    }

    public void setShardingDataSourcesName(String shardingDataSourcesName) {
        this.shardingDataSourcesName = shardingDataSourcesName;
    }

    public Set<String> getDataSourcesNames() {
        return dataSourcesNames;
    }

    public void setDataSourcesNames(Set<String> dataSourcesNames) {
        this.dataSourcesNames = dataSourcesNames;
    }

    public Map<String, Rule> getRuleMap() {
        return ruleMap;
    }

    public void setRuleMap(Map<String, Rule> ruleMap) {
        this.ruleMap = ruleMap;
    }

    public Map<String, Table> getTableMap() {
        return tableMap;
    }

    public void setTableMap(Map<String, Table> tableMap) {
        this.tableMap = tableMap;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "ShardingDateSourcesConfig{" +
                "shardingDataSourcesName='" + shardingDataSourcesName + '\'' +
                ", dataSourceMap=" + dataSourcesNames +
                ", ruleMap=" + ruleMap +
                ", tableMap=" + tableMap +
                ", properties=" + properties +
                '}';
    }
}
