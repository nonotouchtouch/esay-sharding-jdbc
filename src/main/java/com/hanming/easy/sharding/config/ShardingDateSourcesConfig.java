package com.hanming.easy.sharding.config;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 用于存放分库分表规则配置
 * 每个对象对应一个sharding yml配置文件
 * @author hanming.xiao
 * @date 2020-08-01
 *
 */
public class ShardingDateSourcesConfig {

    /**
     * 基础数据源map
     * 不分库时候只有一个
     */
    private Map<String, DataSource> dataSourceMap;

    /**
     * 规则配置
     */
    private Map<String,Rule> ruleMap;

    /**
     * 逻辑表配置
     */
    private Map<String,Table> tableMap;

    public Map<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
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
}
