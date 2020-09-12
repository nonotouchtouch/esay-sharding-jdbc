package com.hanming.easy.sharding.algorithm;

import com.hanming.easy.sharding.config.Rule;
import com.hanming.easy.sharding.config.ShardingDateSourcesConfig;
import com.hanming.easy.sharding.handler.StrategyHandler;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.*;

/**
 * @author hanming.xiao
 * @date 2020-09-12
 */
public class EasyRangeShardingAlgorithm implements RangeShardingAlgorithm {

    /**
     * 分表配置
     */
    private ShardingDateSourcesConfig shardingDateSourcesConfig;


    public EasyRangeShardingAlgorithm(ShardingDateSourcesConfig shardingDateSourcesConfig) {
        this.shardingDateSourcesConfig=shardingDateSourcesConfig;
    }


    @Override
    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {
        //逻辑表名
        String logicTableName=rangeShardingValue.getLogicTableName();

        //返回表名，先把要处理的表名放入其中，处理后替换成真实表名
        Set<String> realTableNameSet=new LinkedHashSet<>();
        //逻辑表名放入set中
        realTableNameSet.add(logicTableName);

        //待处理规则
        List<String> ruleNameList= shardingDateSourcesConfig.getTableMap().get(logicTableName).getTableShardingRuleNames();

        for (String ruleName:ruleNameList) {
            Rule rule=shardingDateSourcesConfig.getRuleMap().get(ruleName);
            StrategyHandler suffixationHandler=rule.getStrategyEnum().getSuffixationHandler();
            realTableNameSet = suffixationHandler.generateSuffixation(realTableNameSet, rule, rangeShardingValue);
        }


        return new LinkedList<String>(realTableNameSet);
    }
}
