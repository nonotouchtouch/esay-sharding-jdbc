package com.hanming.easy.sharding.algorithm;

import com.hanming.easy.sharding.config.Rule;
import com.hanming.easy.sharding.config.ShardingDateSourcesConfig;
import com.hanming.easy.sharding.handler.StrategyHandler;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.*;

/**
 * @author hanming.xiao
 */
public class EasyPreciseShardingAlgorithm implements PreciseShardingAlgorithm {

    /**
     * 分表配置
     */
    private ShardingDateSourcesConfig shardingDateSourcesConfig;


    public EasyPreciseShardingAlgorithm(ShardingDateSourcesConfig shardingDateSourcesConfig) {
        this.shardingDateSourcesConfig = shardingDateSourcesConfig;
    }

    @Override
    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
        //逻辑表名
        String logicTableName = preciseShardingValue.getLogicTableName();

        //返回表名，先把要处理的表名放入其中，处理后替换成真实表名
        Set<String> realTableNameSet = new LinkedHashSet<>();
        //逻辑表名放入set中
        realTableNameSet.add(logicTableName);

        //待处理规则
        List<String> ruleNameList = shardingDateSourcesConfig.getTableMap().get(logicTableName).getTableShardingRuleNames();

        for (String ruleName : ruleNameList) {
            Rule rule = shardingDateSourcesConfig.getRuleMap().get(ruleName);
            StrategyHandler suffixationHandler = rule.getStrategyEnum().getSuffixationHandler();
            realTableNameSet = suffixationHandler.generateSuffixation(realTableNameSet, rule, preciseShardingValue);
        }


        return new LinkedList<String>(realTableNameSet).get(0);
    }
}
