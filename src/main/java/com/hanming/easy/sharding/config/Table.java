package com.hanming.easy.sharding.config;

import java.util.List;

/**
 * 表信息配置
 * 按照逻辑表进行配置，关联分库分表规则
 */
public class Table {

    /**
     * 逻辑表名
     */
    private String logicTableName;

    /**
     * 分表规则配置
     */
    private List<String> tableShardingRuleNames;

    /**
     * 分库规则配置
     */
    private List<String> dataSourcesShardingRuleNames;


    public String getLogicTableName() {
        return logicTableName;
    }

    public void setLogicTableName(String logicTableName) {
        this.logicTableName = logicTableName;
    }

    public List<String> getTableShardingRuleNames() {
        return tableShardingRuleNames;
    }

    public void setTableShardingRuleNames(List<String> tableShardingRuleNames) {
        this.tableShardingRuleNames = tableShardingRuleNames;
    }

    public List<String> getDataSourcesShardingRuleNames() {
        return dataSourcesShardingRuleNames;
    }

    public void setDataSourcesShardingRuleNames(List<String> dataSourcesShardingRuleNames) {
        this.dataSourcesShardingRuleNames = dataSourcesShardingRuleNames;
    }

    @Override
    public String toString() {
        return "Table{" +
                "logicTableName='" + logicTableName + '\'' +
                ", tableShardingRuleNames=" + tableShardingRuleNames +
                ", dataSourcesShardingRuleNames=" + dataSourcesShardingRuleNames +
                '}';
    }
}
