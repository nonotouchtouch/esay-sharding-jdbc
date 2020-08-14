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
     * 构造方法判断合法性，存在即合理
     *
     * @param ruleName        规则名称 每个配置文件中的规则唯一标识 非空
     * @param strategyEnumStr 策略 需要能转化成枚举
     * @param column
     * @param value
     */
    public Rule(String ruleName, String strategyEnumStr, String column, Object value) {
        if (null == ruleName || ruleName.trim().length() == 0 || null == column || column.trim().length() == 0) {
            throw new IllegalArgumentException("Create Rule fail! RuleName or column can not be empty!");
        }
        try {
            this.strategyEnum = StrategyEnum.valueOf(strategyEnumStr);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Create Rule fail! Strategy can not be null! ");
        }
        this.ruleName = ruleName;
        this.column = column;
        this.value = value;
    }

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
    private Object value;

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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "ruleName='" + ruleName + '\'' +
                ", strategyEnum=" + strategyEnum.name() +
                ", column='" + column + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
