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
    MODEL("取模"),

    /**
     * 精确匹配路由
     */
    PRECISE_MATCH("精确匹配路由"),

    /**
     * 精确指定路由
     */
    PRECISE("精确指定路由");



    private String desc;

    StrategyEnum(String desc){
        this.desc=desc;
    }

}
