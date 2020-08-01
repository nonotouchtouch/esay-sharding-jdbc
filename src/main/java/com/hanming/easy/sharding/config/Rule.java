package com.hanming.easy.sharding.config;

import com.hanming.easy.sharding.enums.StrategyEnum;

/**
 * sharding分片规则配置
 * 分库，分表通用
 *
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class Rule {

    /**
     * 由用户自行命名
     * 单个yml文件中不应重复。用于路由时匹配sharding分片策略
     */
    private String ruleName;

    /**
     * 用户根据数据库设计自行选择sharding分片策略
     */
    private StrategyEnum strategyEnum;

    /**
     * 用于分片的字段
     */
    private String column;

    /**
     * 策略配置
     * 一些分片策略要进行参数配置，例如分片策略是MODEL时，配置按多少取模
     */
    private String value;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public StrategyEnum getStrategyEnum() {
        return strategyEnum;
    }

    public void setStrategyEnum(StrategyEnum strategyEnum) {
        this.strategyEnum = strategyEnum;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "ruleName='" + ruleName + '\'' +
                ", strategyEnum=" + strategyEnum +
                ", column='" + column + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
