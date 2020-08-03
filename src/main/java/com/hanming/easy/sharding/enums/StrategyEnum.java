package com.hanming.easy.sharding.enums;

import com.hanming.easy.sharding.handler.ModelHandler;
import com.hanming.easy.sharding.handler.PreciseHandler;
import com.hanming.easy.sharding.handler.StrategyHandler;

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
    MODEL("取模",new ModelHandler()),

    /**
     * 精确匹配路由
     */
    PRECISE_MATCH("精确匹配路由",new PreciseHandler()),

    /**
     * 精确指定路由
     */
    PRECISE_FIX("精确指定路由",null);



    StrategyEnum(String desc,StrategyHandler suffixationHandler){
        this.desc=desc;
        this.suffixationHandler=suffixationHandler;
    }

    private String desc;



    private StrategyHandler suffixationHandler;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public StrategyHandler getSuffixationHandler() {
        return suffixationHandler;
    }

    public void setSuffixationHandler(StrategyHandler suffixationHandler) {
        this.suffixationHandler = suffixationHandler;
    }
}
