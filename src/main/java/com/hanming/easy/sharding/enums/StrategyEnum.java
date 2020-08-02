package com.hanming.easy.sharding.enums;

/**
 * sharding分片策略
 * 分库分表策略，由开发者定义
 *
 * 每一个枚举对应一种Handler需要开发者实现
 */
public enum StrategyEnum {

    /**
     * 取模
     */
    MODEL("取模","${0..VALUE}"),

    /**
     * 精确匹配路由
     */
    PRECISE_MATCH("精确匹配路由","${VALUE}"),

    /**
     * 精确指定路由
     */
    PRECISE("精确指定路由","${VALUE}");



    StrategyEnum(String desc,String exp){
        this.desc=desc;
        this.exp=exp;
    }

    private String desc;

    private String exp;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
