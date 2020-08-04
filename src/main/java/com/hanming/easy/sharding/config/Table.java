package com.hanming.easy.sharding.config;


import com.alibaba.druid.util.StringUtils;

import java.util.List;

/**
 * 表信息配置
 * 按照逻辑表进行配置，关联分库分表规则
 *
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class Table {

    /**
     * 构造方法判断合理性
     *
     * @param logicTableName
     * @param tableShardingRuleNames
     * @param dataSourcesShardingRuleNames
     */
    public Table(String logicTableName, List<String> tableShardingRuleNames, List<String> dataSourcesShardingRuleNames) {
        if (StringUtils.isEmpty(logicTableName)) {
            throw new IllegalArgumentException("Create Table fail! LogicTableName can not be empty!");
        }
        this.logicTableName = logicTableName;
        this.tableShardingRuleNames = tableShardingRuleNames;
        this.dataSourcesShardingRuleNames = dataSourcesShardingRuleNames;
    }

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
